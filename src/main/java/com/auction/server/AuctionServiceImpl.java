package com.auction.server;

import com.auction.common.AuctionService;
import com.auction.common.ClientCallback;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 拍卖业务实现：维护当前拍品、出价校验，并向所有注册客户端广播更新。
 */
public class AuctionServiceImpl extends UnicastRemoteObject implements AuctionService {

    private final AuctionItem auctionItem;
    private final List<ClientCallback> clients;

    public AuctionServiceImpl(String itemName, double startingPrice) throws RemoteException {
        super();
        this.auctionItem = new AuctionItem(itemName, startingPrice);
        this.clients = new ArrayList<>();
        System.out.println("拍卖服务已初始化");
        System.out.println("物品: " + itemName + ", 起拍价: " + startingPrice + " 元");
    }

    @Override
    public synchronized AuctionItem getAuctionItem() throws RemoteException {
        return auctionItem;
    }

    @Override
    public synchronized boolean placeBid(String bidderName, double amount) throws RemoteException {
        if (bidderName == null || bidderName.trim().isEmpty()) {
            System.out.println("无效出价: 买家姓名为空");
            return false;
        }
        if (amount <= auctionItem.getCurrentPrice()) {
            System.out.println("无效出价: " + bidderName + " 出价 " + amount
                    + " 元（当前最高价: " + auctionItem.getCurrentPrice() + " 元）");
            return false;
        }

        auctionItem.setCurrentPrice(amount);
        auctionItem.setCurrentBidder(bidderName.trim());

        System.out.println("新的最高价: " + amount + " 元，出价者: " + bidderName.trim());

        notifyAllClients();
        return true;
    }

    @Override
    public synchronized void registerClient(ClientCallback callback) throws RemoteException {
        if (callback == null) {
            return;
        }
        if (!clients.contains(callback)) {
            clients.add(callback);
            System.out.println("客户端已注册，当前在线客户端数: " + clients.size());
        }
    }

    @Override
    public synchronized void unregisterClient(ClientCallback callback) throws RemoteException {
        if (clients.remove(callback)) {
            System.out.println("客户端已注销，当前在线客户端数: " + clients.size());
        }
    }

    private void notifyAllClients() {
        List<ClientCallback> failed = new ArrayList<>();
        for (ClientCallback client : clients) {
            try {
                client.notifyBidUpdate(
                        auctionItem.getItemName(),
                        auctionItem.getCurrentPrice(),
                        auctionItem.getCurrentBidder());
            } catch (RemoteException e) {
                System.out.println("通知客户端失败，将移除该客户端: " + e.getMessage());
                failed.add(client);
            }
        }
        clients.removeAll(failed);
    }
}
