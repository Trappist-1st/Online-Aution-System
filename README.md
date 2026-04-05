# 在线拍卖系统 (Online Auction System)

基于 Java RMI 的分布式在线拍卖系统实验项目

## 📋 项目简介

这是一个使用 Java RMI（Remote Method Invocation）技术实现的分布式在线拍卖系统。系统采用客户端-服务器架构，支持多个买家同时参与拍卖，实时同步最高出价信息。

## ✨ 核心功能

### 服务器端（拍卖中心）
- 管理拍卖物品和出价记录
- 验证出价有效性
- 实时通知所有客户端最新的最高出价
- 处理客户端注册和注销

### 客户端（买家）
- 查看当前拍卖物品和价格
- 输入买家姓名进行出价
- 实时接收最高出价更新

## 🛠️ 技术栈

- **语言**: Java 21
- **通信机制**: Java RMI
- **注册中心**: RMI Registry
- **架构**: 客户端-服务器模式

## 📁 项目结构

```
Online-Auction-System/
├── src/main/java/com/auction/
│   ├── common/              # 公共接口
│   │   ├── AuctionService.java
│   │   └── ClientCallback.java
│   ├── server/              # 服务器端
│   │   ├── AuctionServer.java
│   │   ├── AuctionServiceImpl.java
│   │   └── AuctionItem.java
│   └── client/              # 客户端
│       ├── AuctionClient.java
│       └── ClientCallbackImpl.java
├── pom.xml                  # Maven 配置
├── README.md                # 本文件
└── 项目文档.md              # 详细技术文档
```

## 🚀 快速开始

### 前置要求

- JDK 8 或更高版本
- Maven（可选）

### 编译项目

**方式一：使用 Maven**
```bash
mvn clean compile
```

**方式二：使用 javac**
```bash
javac -d bin src/main/java/com/auction/**/*.java
```

### 运行系统

**1. 启动服务器**
```bash
java -cp bin com.auction.server.AuctionServer
```

**2. 启动客户端（可以启动多个）**
```bash
# 在新的终端窗口中运行
java -cp bin com.auction.client.AuctionClient
```

**3. 开始拍卖**
- 在客户端输入买家姓名
- 输入出价金额（必须高于当前最高价）
- 观察所有客户端实时同步更新

## 📖 使用示例

### 服务器输出示例
```
拍卖服务器已启动...
RMI Registry 已在端口 1099 上启动
拍卖服务已绑定到 RMI Registry
当前拍卖物品: 古董花瓶
起拍价: 1000.0 元
等待客户端连接...
```

### 客户端输出示例
```
成功连接到拍卖服务器！
========================================
当前拍卖物品: 古董花瓶
当前最高价: 1000.0 元
当前最高出价者: 无
========================================
请输入您的姓名: 张三
请输入出价金额: 1500

出价成功！
[更新] 当前最高价: 1500.0 元，出价者: 张三
```

## 🧪 测试场景

### 基础测试
1. 单客户端出价测试
2. 多客户端并发出价测试
3. 无效出价（低于当前价）测试
4. 客户端断开重连测试

### 压力测试
- 10+ 客户端同时在线
- 快速连续出价
- 网络延迟模拟

## 📚 详细文档

完整的技术文档、架构设计、实现细节请参阅：[项目文档.md](./项目文档.md)

文档包含：
- 详细需求分析
- 系统架构设计
- 接口定义和数据模型
- 实现步骤指导
- 常见问题解决方案
- 扩展功能建议

## 🔧 配置说明

### RMI Registry 端口
默认使用 1099 端口，可在代码中修改：
```java
Registry registry = LocateRegistry.createRegistry(1099);
```

### 服务器 IP（局域网部署）
如需在局域网中部署，启动服务器时指定 IP：
```bash
java -Djava.rmi.server.hostname=192.168.1.100 -cp bin com.auction.server.AuctionServer
```

客户端连接时使用服务器 IP：
```java
Registry registry = LocateRegistry.getRegistry("192.168.1.100", 1099);
```

## 🌟 可能的扩展

- [ ] 支持多个物品同时拍卖
- [ ] 添加拍卖倒计时功能
- [ ] 实现出价历史记录查询
- [ ] 用户认证和权限管理
- [ ] 图形界面（JavaFX/Swing）
- [ ] 数据持久化（数据库）

## ⚠️ 常见问题

### 连接问题
**问题**: 客户端无法连接到服务器  
**解决**: 
1. 确认服务器已启动
2. 检查防火墙设置
3. 验证 IP 和端口是否正确

### 出价失败
**问题**: 出价总是失败  
**解决**: 确保出价金额高于当前最高价

### 回调不生效
**问题**: 客户端收不到更新通知  
**解决**: 检查客户端是否成功注册回调

更多问题请参阅[项目文档.md](./项目文档.md)的"常见问题"章节。

## 👥 贡献

欢迎提出建议和改进意见！

## 📄 许可

本项目仅用于学习和教育目的。

---
**技术关键词**: Java RMI, 分布式通信, 客户端-服务器架构, 远程方法调用
