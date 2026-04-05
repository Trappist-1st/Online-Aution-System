package com.auction.client;

import com.auction.common.ClientCallback;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 接收服务器推送的最高价更新。
 */
public class ClientCallbackImpl extends UnicastRemoteObject implements ClientCallback {

    public ClientCallbackImpl() throws RemoteException {
        super();
    }

    @Override
    public void notifyBidUpdate(String itemName, double currentPrice, String currentBidder)
            throws RemoteException {
        System.out.println("\n========================================");
        System.out.println("          拍卖更新通知");
        System.out.println("========================================");
        System.out.println("物品: " + itemName);
        System.out.println("当前最高价: " + currentPrice + " 元");
        System.out.println("当前最高出价者: " + currentBidder);
        System.out.println("========================================\n");
        System.out.print("请选择操作 (1-出价, 2-查看信息, 0-退出): ");
    }
}
