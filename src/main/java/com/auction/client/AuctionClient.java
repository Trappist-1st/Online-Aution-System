package com.auction.client;

import com.auction.common.AuctionService;
import com.auction.common.ClientCallback;
import com.auction.server.AuctionItem;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * 买家客户端：连接拍卖中心、注册回调、查看信息与出价。
 *
 * <p>可选参数：{@code [主机] [端口]}，默认 localhost:1099。</p>
 */
public class AuctionClient {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 1099;
    private static final String SERVICE_NAME = "AuctionService";

    private AuctionService auctionService;
    private ClientCallback callback;
    private final Scanner scanner = new Scanner(System.in);

    private void connect(String host, int port) {
        try {
            System.out.println("正在连接到拍卖服务器 " + host + ":" + port + " ...");

            Registry registry = LocateRegistry.getRegistry(host, port);
            auctionService = (AuctionService) registry.lookup(SERVICE_NAME);

            callback = new ClientCallbackImpl();
            auctionService.registerClient(callback);

            System.out.println("成功连接到拍卖服务器！\n");
        } catch (Exception e) {
            System.err.println("连接失败: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void displayAuctionInfo() {
        try {
            AuctionItem item = auctionService.getAuctionItem();
            System.out.println("\n========================================");
            System.out.println("          当前拍卖信息");
            System.out.println("========================================");
            System.out.println("物品名称: " + item.getItemName());
            System.out.println("起拍价: " + item.getStartingPrice() + " 元");
            System.out.println("当前最高价: " + item.getCurrentPrice() + " 元");
            System.out.println("当前最高出价者: " + item.getCurrentBidder());
            System.out.println("========================================\n");
        } catch (Exception e) {
            System.err.println("获取拍卖信息失败: " + e.getMessage());
        }
    }

    private void placeBid() {
        try {
            System.out.print("请输入您的姓名: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("姓名不能为空。");
                return;
            }

            System.out.print("请输入出价金额: ");
            String amountStr = scanner.nextLine().trim();
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                System.out.println("无效的金额格式。");
                return;
            }
            if (amount <= 0) {
                System.out.println("出价必须大于 0。");
                return;
            }

            boolean success = auctionService.placeBid(name, amount);
            if (success) {
                System.out.println("出价成功！");
            } else {
                AuctionItem item = auctionService.getAuctionItem();
                System.out.println("出价失败。出价须高于当前最高价: " + item.getCurrentPrice() + " 元");
            }
        } catch (Exception e) {
            System.err.println("出价失败: " + e.getMessage());
        }
    }

    private void disconnect() {
        try {
            if (callback != null && auctionService != null) {
                auctionService.unregisterClient(callback);
            }
            System.out.println("\n已退出拍卖系统，再见！");
        } catch (Exception e) {
            System.err.println("断开连接时出错: " + e.getMessage());
        }
    }

    private void runLoop() {
        displayAuctionInfo();

        while (true) {
            System.out.println("\n请选择操作:");
            System.out.println("1 - 出价");
            System.out.println("2 - 查看当前拍卖信息");
            System.out.println("0 - 退出");
            System.out.print("请输入选项: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    placeBid();
                    break;
                case "2":
                    displayAuctionInfo();
                    break;
                case "0":
                    disconnect();
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
            }
        }
    }

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length >= 1) {
            host = args[0];
        }
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("用法: AuctionClient [主机] [端口]");
                System.exit(1);
                return;
            }
        }

        System.out.println("========================================");
        System.out.println("      欢迎使用在线拍卖系统");
        System.out.println("========================================\n");

        AuctionClient client = new AuctionClient();
        client.connect(host, port);
        client.runLoop();
    }
}
