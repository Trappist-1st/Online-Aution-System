package com.auction.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 客户端回调接口：服务器向已注册客户端推送最高出价更新。
 */
public interface ClientCallback extends Remote {

    /**
     * 通知客户端当前拍卖状态已更新。
     *
     * @param itemName      物品名称
     * @param currentPrice  当前最高价
     * @param currentBidder 当前最高出价者
     */
    void notifyBidUpdate(String itemName, double currentPrice, String currentBidder)
            throws RemoteException;
}
