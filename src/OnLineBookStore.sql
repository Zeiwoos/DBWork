-- 创建数据库
CREATE DATABASE IF NOT EXISTS `OnLineBookStore`;
USE `OnLineBookStore`;

-- 顾客表
DROP TABLE IF EXISTS `customers` ;
CREATE TABLE customers (
                             CustomerID INT AUTO_INCREMENT PRIMARY KEY,
                             CustomerName VARCHAR(50) NOT NULL,
                             Email VARCHAR(100) NOT NULL,
                             Phone VARCHAR(20) NOT NULL,
                             Address TEXT NOT NULL,
                             Balance DECIMAL(10, 2) DEFAULT 0.00,
                             CreditLevel INT DEFAULT 1, -- 信用等级
                             Password VARCHAR(100) NOT NULL -- 客户登录密码
);

-- 供应商表
DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE suppliers (
                             SupplierID INT AUTO_INCREMENT PRIMARY KEY,
                             SupplierName VARCHAR(100) NOT NULL,
                             ContactName VARCHAR(50),
                             Phone VARCHAR(20),
                             Email VARCHAR(100),
                             Address TEXT
);

-- 图书表
DROP TABLE IF EXISTS `books`;
CREATE TABLE books (
                         BookID INT AUTO_INCREMENT PRIMARY KEY,
                         Title VARCHAR(200) NOT NULL,
                         Author VARCHAR(200), -- 多个作者用逗号分隔
                         Publisher VARCHAR(100),
                         Price DECIMAL(10, 2) NOT NULL,
                         Keywords TEXT, -- 关键字，用分隔符存储
                         Description TEXT, -- 本书目录或描述
                         CoverImage BLOB, -- 封面图片
                         Stock INT DEFAULT 0, -- 存货量
                         StorageLocation TEXT -- 存放位置
);

-- 书目供应商关系表（一本书可能有多个供应商）
DROP TABLE IF EXISTS `bookSuppliers`;
CREATE TABLE bookSuppliers (
                                 BookID INT,
                                 SupplierID INT,
                                 PRIMARY KEY (BookID, SupplierID),
                                 FOREIGN KEY (BookID) REFERENCES `Books`(BookID),
                                 FOREIGN KEY (SupplierID) REFERENCES `Suppliers`(SupplierID)
);

-- 缺书登记表
DROP TABLE IF EXISTS `missingBooks`;
CREATE TABLE missingBooks (
                                MissingID INT AUTO_INCREMENT PRIMARY KEY,
                                BookID INT,
                                RequestedByCustomerID INT, -- 顾客ID（如果有）
                                Quantity INT NOT NULL,
                                RegisterDate DATE NOT NULL,
                                FOREIGN KEY (BookID) REFERENCES `Books`(BookID) ,
                                FOREIGN KEY (RequestedByCustomerID) REFERENCES `Customers`(CustomerID)

);

-- 采购单表
DROP TABLE IF EXISTS `purchaseOrders`;
CREATE TABLE purchaseOrders (
                                  PurchaseID INT AUTO_INCREMENT PRIMARY KEY,
                                  SupplierID INT NOT NULL,
                                  OrderDate DATE NOT NULL,
                                  Status VARCHAR(20) DEFAULT 'Pending',
                                  FOREIGN KEY (SupplierID) REFERENCES `Suppliers`(SupplierID)
);

-- 采购单明细表
DROP TABLE IF EXISTS `purchaseDetails`;
CREATE TABLE purchaseDetails (
                                   PurchaseDetailID INT AUTO_INCREMENT PRIMARY KEY,
                                   PurchaseID INT NOT NULL,
                                   BookID INT NOT NULL,
                                   Quantity INT NOT NULL,
                                   FOREIGN KEY (PurchaseID) REFERENCES `PurchaseOrders`(PurchaseID) ,
                                   FOREIGN KEY (BookID) REFERENCES `Books`(BookID)
);

-- 顾客订单表
DROP TABLE IF EXISTS `orders`;
CREATE TABLE orders (
                          OrderID INT AUTO_INCREMENT PRIMARY KEY,
                          CustomerID INT NOT NULL,
                          OrderDate DATE NOT NULL,
                          TotalAmount DECIMAL(10, 2) NOT NULL,
                          ShippingAddress TEXT NOT NULL,
                          Status VARCHAR(20) DEFAULT 'Pending',
                          FOREIGN KEY (CustomerID) REFERENCES `Customers`(CustomerID)
);

-- 订单明细表
DROP TABLE IF EXISTS `orderDetails`;
CREATE TABLE orderDetails (
                                OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
                                OrderID INT NOT NULL,
                                BookID INT NOT NULL,
                                Quantity INT NOT NULL,
                                Price DECIMAL(10, 2) NOT NULL, -- 单价
                                FOREIGN KEY (OrderID) REFERENCES `Orders`(OrderID) ,
                                FOREIGN KEY (BookID) REFERENCES `Books`(BookID)
);

-- 发货记录表
DROP TABLE IF EXISTS `shipments`;
CREATE TABLE shipments (
                             ShipmentID INT AUTO_INCREMENT PRIMARY KEY,
                             OrderID INT NOT NULL,
                             ShipDate DATE NOT NULL,
                             TrackingNumber VARCHAR(100),
                             Status VARCHAR(20) DEFAULT 'Shipped',
                             FOREIGN KEY (OrderID) REFERENCES `Orders`(OrderID)
);

-- 浏览日志表
DROP TABLE IF EXISTS `browsingLogs`;
CREATE TABLE browsingLogs (
                                LogID INT AUTO_INCREMENT PRIMARY KEY,
                                CustomerID INT,
                                BookID INT,
                                BrowseDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (CustomerID) REFERENCES `Customers`(CustomerID) ,
                                FOREIGN KEY (BookID) REFERENCES `Books`(BookID)
);


