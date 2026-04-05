package com.auction.server;

import java.io.Serializable;

/**
 * 拍卖物品信息，需实现 {@link Serializable} 以便 RMI 传值。
 */
public class AuctionItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String itemName;
    private final double startingPrice;
    private double currentPrice;
    private String currentBidder;
    private boolean active;

    public AuctionItem(String itemName, double startingPrice) {
        this.itemName = itemName;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.currentBidder = "无";
        this.active = true;
    }

    public String getItemName() {
        return itemName;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrentBidder() {
        return currentBidder;
    }

    public void setCurrentBidder(String currentBidder) {
        this.currentBidder = currentBidder;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "物品: " + itemName
                + ", 起拍价: " + startingPrice
                + ", 当前价: " + currentPrice
                + ", 出价者: " + currentBidder;
    }
}
