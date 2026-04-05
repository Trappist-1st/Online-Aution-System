package com.auction.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

/**
 * 拍卖中心：创建 RMI Registry、绑定 {@link AuctionService}。
 *
 * <p>可选参数：{@code [端口] [物品名称] [起拍价]}，均省略时使用默认值。</p>
 */
public class AuctionServer {

    private static final int DEFAULT_RMI_PORT = 1099;
    private static final String SERVICE_NAME = "AuctionService";
    private static final String DEFAULT_ITEM = "明代青花瓷";
    private static final double DEFAULT_START_PRICE = 10000.0;

    public static void main(String[] args) {
        int port = DEFAULT_RMI_PORT;
        String itemName = DEFAULT_ITEM;
        double startPrice = DEFAULT_START_PRICE;

        try {
            if (args.length >= 1) {
                port = Integer.parseInt(args[0]);
            }
            if (args.length >= 2) {
                itemName = args[1];
            }
            if (args.length >= 3) {
                startPrice = Double.parseDouble(args[2]);
            }
        } catch (NumberFormatException e) {
            System.err.println("参数格式: AuctionServer [端口] [物品名称] [起拍价]");
            System.exit(1);
            return;
        }

        try {
            System.out.println("========================================");
            System.out.println("      拍卖服务器启动中...");
            System.out.println("========================================");

            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RMI Registry 已在端口 " + port + " 上启动");

            AuctionServiceImpl auctionService = new AuctionServiceImpl(itemName, startPrice);
            registry.rebind(SERVICE_NAME, auctionService);
            System.out.println("拍卖服务已绑定到 RMI Registry (名称: " + SERVICE_NAME + ")");

            System.out.println("========================================");
            System.out.println("      服务器已就绪，等待客户端连接...");
            System.out.println("========================================\n");

            new CountDownLatch(1).await();
        } catch (Exception e) {
            System.err.println("服务器启动失败: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
