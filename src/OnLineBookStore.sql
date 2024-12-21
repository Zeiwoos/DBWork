CREATE DATABASE IF NOT EXISTS `OnLineBookStore`;
USE `OnLineBookStore`;

-- 丛书表
DROP TABLE IF EXISTS `series`;
CREATE TABLE series (
                        SeriesID INT AUTO_INCREMENT PRIMARY KEY, -- 丛书ID
                        SeriesName VARCHAR(200) NOT NULL, -- 丛书名称
                        Description TEXT -- 丛书描述（可选）
);

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
                       StorageLocation TEXT, -- 存放位置
                       SeriesID INT, -- 丛书ID（关联到series表）
                       FOREIGN KEY (SeriesID) REFERENCES series(SeriesID)
);

-- 书目供应商关系表（一本书可能有多个供应商）
DROP TABLE IF EXISTS `bookSuppliers`;
CREATE TABLE bookSuppliers (
                               BookID INT,
                               SupplierID INT,
                               PRIMARY KEY (BookID, SupplierID),
                               FOREIGN KEY (BookID) REFERENCES books(BookID),
                               FOREIGN KEY (SupplierID) REFERENCES suppliers(SupplierID)
);

-- 缺书登记表
DROP TABLE IF EXISTS `missingBooks`;
CREATE TABLE missingBooks (
                              MissingID INT AUTO_INCREMENT PRIMARY KEY,
                              BookID INT,
                              RequestedByCustomerID INT, -- 顾客ID（如果有）
                              Quantity INT NOT NULL,
                              RegisterDate DATE NOT NULL,
                              FOREIGN KEY (BookID) REFERENCES books(BookID) ,
                              FOREIGN KEY (RequestedByCustomerID) REFERENCES customers(CustomerID)

);

-- 采购单表
DROP TABLE IF EXISTS `purchaseOrders`;

CREATE TABLE purchaseOrders (
                                PurchaseID INT AUTO_INCREMENT PRIMARY KEY,
                                SupplierID INT NOT NULL,
                                OrderDate DATE NOT NULL,
                                Status ENUM('Pending', 'Shipped', 'Delivered') DEFAULT 'Pending',
                                FOREIGN KEY (SupplierID) REFERENCES suppliers(SupplierID)
);


-- 采购单明细表
DROP TABLE IF EXISTS `purchaseDetails`;
CREATE TABLE purchaseDetails (
                                 PurchaseDetailID INT AUTO_INCREMENT PRIMARY KEY,
                                 PurchaseID INT NOT NULL,
                                 BookID INT NOT NULL,
                                 Quantity INT NOT NULL,
                                 FOREIGN KEY (PurchaseID) REFERENCES purchaseOrders(PurchaseID) ,
                                 FOREIGN KEY (BookID) REFERENCES books(BookID)
);

-- 顾客订单表
DROP TABLE IF EXISTS `orders`;
CREATE TABLE orders (
                        OrderID INT AUTO_INCREMENT PRIMARY KEY,
                        CustomerID INT NOT NULL,
                        OrderDate DATE NOT NULL,
                        TotalAmount DECIMAL(10, 2) NOT NULL,
                        ShippingAddress TEXT NOT NULL,
                        Status ENUM('Pending', 'Shipped', 'Delivered') DEFAULT 'Pending',
                        FOREIGN KEY (CustomerID) REFERENCES customers(CustomerID)
);

-- 订单明细表
DROP TABLE IF EXISTS `orderDetails`;
CREATE TABLE orderDetails (
                              OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
                              OrderID INT NOT NULL,
                              BookID INT NOT NULL,
                              Quantity INT NOT NULL,
                              Price DECIMAL(10, 2) NOT NULL, -- 单价
                              FOREIGN KEY (OrderID) REFERENCES orders(OrderID) ,
                              FOREIGN KEY (BookID) REFERENCES books(BookID)
);

-- 发货记录表
DROP TABLE IF EXISTS `shipments`;
CREATE TABLE shipments (
                           ShipmentID INT AUTO_INCREMENT PRIMARY KEY,
                           OrderID INT NOT NULL,
                           ShipDate DATE NOT NULL,
                           TrackingNumber VARCHAR(100),
                           Status VARCHAR(20) DEFAULT 'Shipped',
                           FOREIGN KEY (OrderID) REFERENCES orders(OrderID)
);

-- 浏览日志表
DROP TABLE IF EXISTS `browsingLogs`;
CREATE TABLE browsingLogs (
                              LogID INT AUTO_INCREMENT PRIMARY KEY,
                              CustomerID INT,
                              BookID INT,
                              BrowseDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (CustomerID) REFERENCES customers(CustomerID) ,
                              FOREIGN KEY (BookID) REFERENCES books(BookID)
);



-- 创建书存量检查触发器，如果库存量低于最低存书量，则插入缺书记录
CREATE TRIGGER check_stock_before_update
    BEFORE UPDATE ON books
    FOR EACH ROW
BEGIN
    -- 如果库存量低于最低存书量
    IF NEW.Stock < 10 THEN
        -- 如果缺书记录不存在，则插入缺书记录
        INSERT INTO missingBooks (BookID, Quantity, RegisterDate)
    SELECT NEW.BookID, 10 - NEW.Stock, CURDATE()
        WHERE NOT EXISTS (
            SELECT 1
            FROM missingBooks
            WHERE BookID = NEW.BookID AND RequestedByCustomerID IS NULL
        );
END IF;
END ;



-- 创建触发器，当插入订单明细时，检查库存量是否足够，如果不够则插入缺书记录
CREATE TRIGGER create_missing_book_record
    AFTER INSERT ON orderDetails
    FOR EACH ROW
BEGIN
    DECLARE stock INT;
    DECLARE missing_quantity INT;

    -- 获取库存量
    SELECT Stock INTO stock FROM books WHERE BookID = NEW.BookID;

    -- 判断库存是否足够(每次下单后库存量先减少)
    IF stock < NEW.Quantity THEN
        -- 计算缺书的数量
        SET missing_quantity = NEW.Quantity - stock;

        -- 插入缺书记录（如果没有重复记录）
        INSERT INTO missingBooks (BookID, RequestedByCustomerID, Quantity, RegisterDate)
        SELECT NEW.BookID, (SELECT CustomerID FROM orders WHERE OrderID = NEW.OrderID), missing_quantity, CURDATE()
        WHERE NOT EXISTS (
            SELECT 1 FROM missingBooks WHERE BookID = NEW.BookID AND RequestedByCustomerID = (SELECT CustomerID FROM orders WHERE OrderID = NEW.OrderID)
        );
    END IF;
END ;



-- 创建触发器，当插入缺书记录时，自动创建或更新采购单
CREATE TRIGGER create_or_update_purchase_order_from_missing_book
    AFTER INSERT ON missingBooks
    FOR EACH ROW
BEGIN
    DECLARE supplierID INT;
    DECLARE purchaseID INT;
    DECLARE existingQty INT;

    -- 根据缺书的书籍信息，选择一个供应商（假设选择第一个供应商）
    SELECT SupplierID INTO supplierID
    FROM bookSuppliers
    WHERE BookID = NEW.BookID
    LIMIT 1;

    -- 如果找到供应商
    IF supplierID IS NOT NULL THEN
        -- 检查是否存在一个状态为 'Pending' 的采购单
        SELECT PurchaseID INTO purchaseID
        FROM purchaseOrders
        WHERE SupplierID = supplierID AND Status = 'Pending'
        LIMIT 1;

        -- 如果找到已有的 'Pending' 采购单，则将缺书的书籍添加到该采购单的采购详情中
        IF purchaseID IS NOT NULL THEN
            -- 计算采购详情中该书籍的现有数量（如果有的话）
            SELECT SUM(Quantity) INTO existingQty
            FROM purchaseDetails
            WHERE PurchaseID = purchaseID AND BookID = NEW.BookID;

            -- 如果已有数量，则增加数量，否则插入新的采购详情
            IF existingQty IS NOT NULL THEN
                UPDATE purchaseDetails
                SET Quantity = Quantity + NEW.Quantity
                WHERE PurchaseID = purchaseID AND BookID = NEW.BookID;
            ELSE
                INSERT INTO purchaseDetails (PurchaseID, BookID, Quantity)
                VALUES (purchaseID, NEW.BookID, NEW.Quantity);
            END IF;

            -- 如果没有找到现有的 'Pending' 采购单，则创建一个新的采购单
        ELSE
            -- 创建新的采购单
            INSERT INTO purchaseOrders (SupplierID, OrderDate, Status)
            VALUES (supplierID, CURDATE(), 'Pending');

            -- 获取刚刚插入的采购单ID
            SET purchaseID = LAST_INSERT_ID();

            -- 插入采购详情
            INSERT INTO purchaseDetails (PurchaseID, BookID, Quantity)
            VALUES (purchaseID, NEW.BookID, NEW.Quantity);
        END IF;
    END IF;
END ;

-- 创建触发器，当插入订单时，更新顾客的余额？


-- 创建触发器，当解决采购单时，更新库存量

# CREATE TRIGGER after_purchase_order_delivered
#     AFTER UPDATE ON purchaseOrders
#     FOR EACH ROW
# BEGIN
#     -- 检查采购单状态是否已更改为 "Delivered"
#     IF NEW.Status = 'Delivered' AND OLD.Status != 'Delivered' THEN
#         DECLARE bookID INT;
#         DECLARE orderQuantity INT;
#
#         -- 遍历采购单明细，增加库存并删除缺书记录
#         DECLARE done INT DEFAULT 0;
#         DECLARE cur CURSOR FOR
#         SELECT BookID, Quantity FROM purchaseDetails WHERE PurchaseID = NEW.PurchaseID;
#
#         -- 打开游标
#         OPEN cur;
#
#         -- 循环处理每条采购单明细
#         read_loop: LOOP
#             FETCH cur INTO bookID, orderQuantity;
#             IF done THEN
#                 LEAVE read_loop;
#             END IF;
#
#             -- 增加库存量
#             UPDATE books
#             SET Stock = Stock + orderQuantity
#             WHERE BookID = bookID;
#
#             -- 删除缺书记录
#             DELETE FROM missingBooks
#             WHERE BookID = bookID
#               AND Quantity = orderQuantity
#               AND RequestedByCustomerID IS NULL;  -- 删除没有指定顾客的缺书记录
#
#         END LOOP;
#
#         -- 关闭游标
#         CLOSE cur;
#     END IF;
# END ;


CREATE TRIGGER after_purchase_order_delivered
    AFTER UPDATE ON purchaseOrders
    FOR EACH ROW
BEGIN
    -- 声明所需的变量
    DECLARE bookID INT;
    DECLARE orderQuantity INT;
    DECLARE done INT DEFAULT 0;
    DECLARE cur CURSOR FOR
        SELECT BookID, Quantity FROM purchaseDetails WHERE PurchaseID = NEW.PurchaseID;

    -- 检查采购单状态是否已更改为 "Delivered"
    IF NEW.Status = 'Delivered' AND OLD.Status != 'Delivered' THEN
        -- 打开游标
        OPEN cur;

        -- 循环处理每条采购单明细
        read_loop: LOOP
            FETCH cur INTO bookID, orderQuantity;
            IF done THEN
                LEAVE read_loop;
            END IF;

            -- 确保变量值有效
            IF bookID IS NOT NULL AND orderQuantity IS NOT NULL THEN
                -- 增加库存量
                UPDATE books
                SET Stock = Stock + orderQuantity
                WHERE BookID = bookID;

                -- 删除缺书记录
                DELETE FROM missingBooks
                WHERE BookID = bookID
                  AND Quantity = orderQuantity
                  AND RequestedByCustomerID IS NULL;  -- 删除没有指定顾客的缺书记录
            END IF;
        END LOOP;

        -- 关闭游标
        CLOSE cur;
    END IF;
END ;


SHOW VARIABLES LIKE 'event_scheduler';

SHOW VARIABLES LIKE 'character_set%';

SET GLOBAL event_scheduler = ON;

CREATE EVENT adjust_customer_credit
    ON SCHEDULE
        EVERY 1 MONTH
            STARTS '2025-01-01 00:00:00'  -- 从2025年1月1号开始执行
    DO
    BEGIN
    UPDATE customers
    SET CreditLevel =
    CASE
        WHEN Balance + (SELECT COALESCE(SUM(Price * Quantity), 0)
                        FROM orderDetails
                                 JOIN books ON orderDetails.BookID = books.BookID
                        WHERE orderDetails.CustomerID = customers.CustomerID) <= 500 THEN 1
        WHEN Balance + (SELECT COALESCE(SUM(Price * Quantity), 0)
                        FROM orderDetails
                                 JOIN books ON orderDetails.BookID = books.BookID
                        WHERE orderDetails.CustomerID = customers.CustomerID) <= 1500 THEN 2
        WHEN Balance + (SELECT COALESCE(SUM(Price * Quantity), 0)
                        FROM orderDetails
                                 JOIN books ON orderDetails.BookID = books.BookID
                        WHERE orderDetails.CustomerID = customers.CustomerID) <= 2500 THEN 3
        WHEN Balance + (SELECT COALESCE(SUM(Price * Quantity), 0)
                        FROM orderDetails
                                 JOIN books ON orderDetails.BookID = books.BookID
                        WHERE orderDetails.CustomerID = customers.CustomerID) <= 3500 THEN 4
        ELSE 5
        END;
END ;




