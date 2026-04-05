package com.auction.common;

import com.auction.server.AuctionItem;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 拍卖中心远程服务接口。
 */
public interface AuctionService extends Remote {

    /** 获取当前拍卖物品快照（可序列化传输）。 */
    AuctionItem getAuctionItem() throws RemoteException;

    /**
     * 提交出价。
     *
     * @return 成功则 true；出价不高于当前价则 false
     */
    boolean placeBid(String bidderName, double amount) throws RemoteException;

    void registerClient(ClientCallback callback) throws RemoteException;

    void unregisterClient(ClientCallback callback) throws RemoteException;
}
