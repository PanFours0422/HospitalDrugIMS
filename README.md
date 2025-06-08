# 🏥 医院药品管理系统 (Hospital Drug IMS)

## 📌 项目简介

本项目是一个基于 Java Swing 的医院药品管理系统桌面应用程序。系统采用客户端-服务器架构，客户端使用 Java Swing 构建图形界面，后端提供数据服务，旨在实现药品的入库、出库、查询、库存管理等功能，提高医院药房的运营效率。

## ⚙️ 技术栈

### 客户端 (frontend-dev)

-   **语言**: Java 22
-   **GUI框架**: Java Swing
-   **构建工具**: Maven
-   **主要功能模块**:
    - 登录界面 (LoginFrame)
    - 管理员界面 (AdminFrame)
    - 医生界面 (DoctorFrame)
    - 护士界面 (NurseFrame)

### 后端 (backend-dev)

-   **语言**: Java 22
-   **构建工具**: Maven
-   **数据库**: Microsoft SQL Server
-   **主要依赖**:
    - Gson 2.8.6 (JSON处理)
    - SQL Server JDBC Driver 9.4.1
    - JUnit 4.13.2 & JUnit Jupiter 5.7.1 (单元测试)
    - Mockito 3.6.0 (测试模拟)

## 🚀 快速开始

### 1. 克隆项目

```bash
git clone <你的项目仓库地址>
cd HospitalDrugIMS
```

### 2. 后端设置与运行

进入后端项目目录：

```bash
cd backend-dev
```

#### 环境要求
- JDK 22
- Maven 3.x
- Microsoft SQL Server

#### 配置步骤
1. 确保已安装 JDK 22 和 Maven
2. 配置数据库连接（具体配置方式待补充）
3. 运行项目：
   ```bash
   mvn clean install
   mvn exec:java
   ```

### 3. 客户端设置与运行

进入客户端项目目录：

```bash
cd frontend-dev
```

#### 环境要求
- JDK 22
- Maven 3.x

#### 配置步骤
1. 确保已安装 JDK 22 和 Maven
2. 运行项目：
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="com.hdims.client.LoginFrame"
   ```

## 📂 项目结构

```
HospitalDrugIMS/
├── backend-dev/             # 后端项目代码
│   ├── src/
│   │   ├── main/           # 主要源代码
│   │   └── test/           # 测试代码
│   └── pom.xml             # Maven 配置文件
├── frontend-dev/            # 客户端项目代码
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │               └── hdims/
│   │                   └── client/    # 客户端源代码
│   │                       ├── LoginFrame.java    # 登录界面
│   │                       ├── AdminFrame.java    # 管理员界面
│   │                       ├── DoctorFrame.java   # 医生界面
│   │                       ├── NurseFrame.java    # 护士界面
│   │                       ├── Client.java        # 客户端主类
│   │                       └── UserSession.java   # 用户会话管理
│   └── pom.xml             # Maven 配置文件
└── README.md                # 项目说明
```

## ✨ 功能特性

### 用户角色
- 管理员：系统管理、用户管理、药品管理
- 医生：药品查询、处方开具
- 护士：药品领取、库存查询

### 核心功能
- 用户登录与权限管理
- 药品信息管理（增、删、改、查）
- 药品入库与出库管理
- 库存预警与统计报表
- 供应商与客户管理

## 🤝 贡献 (待完善)

欢迎贡献！如果您有任何建议或发现 Bug，请随时提交 Issue 或 Pull Request。

## 📄 许可证 (待完善)

本项目采用 [许可证名称] 协议。

---

**注意**: 本 README 提供了一个通用模板。请根据您项目的实际情况修改和完善其中的技术栈、运行指令、功能特性、贡献指南和许可证信息。

## 🎓 某门课的期末大作业

> ⚠️ **免责声明：这是一个为了应付学业大作业的项目。**

## 📌 项目背景

本项目的诞生并不出于兴趣、热情或理想，而是因为老师明确要求：

> **"项目必须上传到 GitHub。"**

我照做了。至于代码质量……我尽力了，真的。

## ⚙️ 关于运行

我**无法保证你能成功运行这个项目**，因为：

* 可能缺依赖（我也忘了装了啥）
* 可能路径写死（急着交作业，谁还管这些）
* 可能有调不通的接口（反正能跑一次就算成功）

不过你真想跑它，也不是没可能，你可以：

1. 仔细看看代码结构（祝你好运）
2. 安装所有看起来可能需要的库
3. 一点点修补那些"临时写的"代码段

## 🤷 使用建议

**仅供参考/观摩/学术批判**
如有雷同，纯属作业多了脑抽。

## 🧹 未来计划

无。
项目提交后一段时间可能会删除，届时这份代码将随风而去，尘归尘，土归土。

---

欢迎围观，但请勿较真。
你认真你就输了。

---
