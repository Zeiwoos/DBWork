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
#                        CoverImage BLOB, -- 封面图片
                       Stock INT DEFAULT 0, -- 存货量
                       StorageLocation TEXT, -- 存放位置
                       SeriesID INT, -- 丛书ID（关联到series表）
                       SupplierID INT,
                       FOREIGN KEY (SupplierID) REFERENCES suppliers(SupplierID),
                       FOREIGN KEY (SeriesID) REFERENCES series(SeriesID)
);


-- 缺书登记表
DROP TABLE IF EXISTS `missingBooks`;
CREATE TABLE missingBooks (
                              MissingID INT AUTO_INCREMENT PRIMARY KEY,
                              BookID INT,
                              CustomerID INT, -- 顾客ID（如果有）
                              Quantity INT NOT NULL,
                              RegisterDate DATE NOT NULL,
                              Status ENUM('SOLVED', 'UNSOLVED') DEFAULT 'UNSOLVED',
                              FOREIGN KEY (BookID) REFERENCES books(BookID) ,
                              FOREIGN KEY (CustomerID) REFERENCES customers(CustomerID)

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



CREATE TRIGGER check_stock_before_update
    BEFORE UPDATE ON books
    FOR EACH ROW
BEGIN

    DECLARE quantityDiff INT;
    -- 如果新的库存量低于最低存书量（20）
    IF NEW.Stock < 20 THEN
        -- 如果缺书记录不存在，则插入缺书记录
        IF NOT EXISTS (
            SELECT 1
            FROM missingBooks
            WHERE BookID = NEW.BookID AND Status = 'UNSOLVED'
        ) THEN
            -- 插入一条新的缺书记录，假设数量为20
            INSERT INTO missingBooks (BookID, CustomerID, Quantity, RegisterDate, Status)
            VALUES (NEW.BookID, NULL, 20, CURDATE(), 'UNSOLVED');
        ELSE
            -- 如果缺书记录已存在，插入差值数量的缺书记录
            -- 计算库存量的差值
            SET quantityDiff = OLD.Stock - NEW.Stock;

            -- 如果差值大于0，则插入缺书记录
            IF quantityDiff > 0 THEN
                INSERT INTO missingBooks (BookID, CustomerID, Quantity, RegisterDate, Status)
                VALUES (NEW.BookID, NULL, quantityDiff, CURDATE(), 'UNSOLVED');
            END IF;
        END IF;
    END IF;
END;



CREATE TRIGGER create_missing_book_record
    AFTER INSERT ON orderDetails
    FOR EACH ROW
BEGIN
    DECLARE stock INT;
    DECLARE missing_quantity INT;
    DECLARE customer_id INT;

    -- 获取库存量
    SELECT Stock INTO stock FROM books WHERE BookID = NEW.BookID;

    -- 获取顾客ID
    SELECT CustomerID INTO customer_id FROM orders WHERE OrderID = NEW.OrderID;

    -- 判断库存是否足够
    IF stock < NEW.Quantity THEN
        -- 计算缺书的数量
        SET missing_quantity = NEW.Quantity - stock + 20;

        -- 插入缺书记录（如果没有重复记录）
    INSERT INTO missingBooks (BookID, RequestedByCustomerID, Quantity, RegisterDate, Status)
    SELECT NEW.BookID, customer_id, missing_quantity, CURDATE(), 'UNSOLVED'
        WHERE NOT EXISTS (
            SELECT 1
            FROM missingBooks
            WHERE BookID = NEW.BookID AND RequestedByCustomerID = customer_id
        );
END IF;
END;

CREATE TRIGGER create_or_update_purchase_order_from_missing_book
    AFTER INSERT ON missingBooks
    FOR EACH ROW
BEGIN
    DECLARE supplierID INT;
    DECLARE purchaseID INT;
    DECLARE existingQty INT;

    -- 根据缺书的书籍信息，从 books 表获取供应商ID
    SELECT SupplierID INTO supplierID
    FROM books
    WHERE BookID = NEW.BookID;

    -- 如果找到供应商ID
    IF supplierID IS NOT NULL THEN
        -- 检查是否存在一个状态为 'Pending' 的采购单
        SELECT PurchaseID INTO purchaseID
        FROM purchaseOrders
        WHERE SupplierID = supplierID AND Status = 'Pending'
        LIMIT 1;

        -- 如果找到已有的 'Pending' 采购单，则将缺书的书籍添加到该采购单的采购详情中
        IF purchaseID IS NOT NULL THEN
            -- 检查该采购单明细中是否已存在此书籍
            SELECT COUNT(*) INTO existingQty
            FROM purchaseDetails
            WHERE PurchaseID = purchaseID AND BookID = NEW.BookID;

            -- 如果已有数量，则增加数量，否则插入新的采购详情
            IF existingQty > 0 THEN
                UPDATE purchaseDetails
                SET Quantity = Quantity + NEW.Quantity
                WHERE PurchaseID = purchaseID AND BookID = NEW.BookID;
            ELSE
                INSERT INTO purchaseDetails (PurchaseID, BookID, Quantity)
                VALUES (purchaseID, NEW.BookID, NEW.Quantity);
            END IF;
        ELSE
            -- 如果没有找到现有的 'Pending' 采购单，则创建一个新的采购单
            INSERT INTO purchaseOrders (SupplierID, OrderDate, Status)
            VALUES (supplierID, CURDATE(), 'Pending');

            -- 获取刚刚插入的采购单ID
            SET purchaseID = LAST_INSERT_ID();

            -- 插入采购详情
            INSERT INTO purchaseDetails (PurchaseID, BookID, Quantity)
            VALUES (purchaseID, NEW.BookID, NEW.Quantity);
        END IF;
    ELSE
        -- 如果没有找到对应的供应商ID，记录异常或执行其他操作
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'SupplierID not found for the given BookID';
    END IF;
END;




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
            IF NOT FOUND THEN
                LEAVE read_loop; -- 如果没有数据，则退出循环
END IF;

-- 增加库存量
UPDATE books
SET Stock = Stock + orderQuantity
WHERE BookID = bookID;

-- 删除缺书记录（如果没有指定顾客的缺书记录）
DELETE FROM missingBooks
WHERE BookID = bookID
  AND Quantity = orderQuantity
  AND RequestedByCustomerID IS NULL;  -- 删除没有指定顾客的缺书记录
END LOOP;

        -- 关闭游标
CLOSE cur;
END IF;
END;


SHOW VARIABLES LIKE 'event_scheduler';
SHOW VARIABLES LIKE 'character_set%';
SET GLOBAL event_scheduler = ON;
CREATE EVENT adjust_customer_credit
    ON SCHEDULE
        EVERY 1 MONTH
        STARTS '2025-01-01 00:00:00'
    DO
BEGIN
        -- 计算每个顾客的总购买金额
        CREATE TEMPORARY TABLE customer_totals AS
SELECT orderDetails.CustomerID,
       COALESCE(SUM(orderDetails.Quantity * books.Price), 0) AS total_spent
FROM orderDetails
         JOIN books ON orderDetails.BookID = books.BookID
GROUP BY orderDetails.CustomerID;

-- 更新信用等级
UPDATE customers c
    JOIN customer_totals ct ON c.CustomerID = ct.CustomerID
    SET c.CreditLevel =
        CASE
        WHEN c.Balance + ct.total_spent <= 500 THEN 1
        WHEN c.Balance + ct.total_spent <= 1500 THEN 2
        WHEN c.Balance + ct.total_spent <= 2500 THEN 3
        WHEN c.Balance + ct.total_spent <= 3500 THEN 4
        ELSE 5
END;

        -- 删除临时表
        DROP TEMPORARY TABLE IF EXISTS customer_totals;
END;


