CREATE DATABASE IF NOT EXISTS `OnLineBookStore`;
USE `OnLineBookStore`;

-- Customers Table
DROP TABLE IF EXISTS `customers`;
CREATE TABLE customers (
                           CustomerID INT AUTO_INCREMENT PRIMARY KEY,
                           CustomerName VARCHAR(50) NOT NULL,
                           Phone VARCHAR(20) NOT NULL,
                           Email VARCHAR(50) NOT NULL,
                           Address VARCHAR(255) NOT NULL,
                           RegisterDate DATE NOT NULL
);

-- Suppliers Table
DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE suppliers (
                           SupplierID INT AUTO_INCREMENT PRIMARY KEY,
                           SupplierName VARCHAR(50) NOT NULL,
                           Phone VARCHAR(20) NOT NULL,
                           Email VARCHAR(50) NOT NULL,
                           Address VARCHAR(255) NOT NULL,
                           RegisterDate DATE NOT NULL
);

-- Books Table
DROP TABLE IF EXISTS `books`;
CREATE TABLE books (
                       BookID INT AUTO_INCREMENT PRIMARY KEY,
                       Title VARCHAR(255) NOT NULL,
                       Author VARCHAR(255) NOT NULL,
                       Publisher VARCHAR(255) NOT NULL,
                       ISBN VARCHAR(13) UNIQUE NOT NULL,
                       Category VARCHAR(50) NOT NULL,
                       Price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
                       Stock INT NOT NULL DEFAULT 0
);

-- Orders Table
DROP TABLE IF EXISTS `orders`;
CREATE TABLE orders (
                        OrderID INT AUTO_INCREMENT PRIMARY KEY,
                        CustomerID INT NOT NULL,
                        OrderDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        Status ENUM('Pending', 'Shipped', 'Cancelled') NOT NULL DEFAULT 'Pending',
                        TotalAmount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
                        FOREIGN KEY (CustomerID) REFERENCES customers(CustomerID)
);

-- OrderDetails Table
DROP TABLE IF EXISTS `OrderDetails`;
CREATE TABLE OrderDetails (
                              OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
                              OrderID INT NOT NULL,
                              BookID INT NOT NULL,
                              Quantity INT NOT NULL DEFAULT 1,
                              Price DECIMAL(10, 2) NOT NULL,
                              FOREIGN KEY (OrderID) REFERENCES orders(OrderID),
                              FOREIGN KEY (BookID) REFERENCES books(BookID)
);

-- Purchases Table
DROP TABLE IF EXISTS `Purchases`;
CREATE TABLE Purchases (
                           PurchaseID INT AUTO_INCREMENT PRIMARY KEY,
                           SupplierID INT NOT NULL,
                           PurchaseDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           TotalAmount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
                           FOREIGN KEY (SupplierID) REFERENCES suppliers(SupplierID)
);

-- PurchaseDetails Table
DROP TABLE IF EXISTS `PurchaseDetails`;
CREATE TABLE PurchaseDetails (
                                 PurchaseDetailID INT AUTO_INCREMENT PRIMARY KEY,
                                 PurchaseID INT NOT NULL,
                                 BookID INT NOT NULL,
                                 Quantity INT NOT NULL DEFAULT 1,
                                 Price DECIMAL(10, 2) NOT NULL,
                                 FOREIGN KEY (PurchaseID) REFERENCES Purchases(PurchaseID),
                                 FOREIGN KEY (BookID) REFERENCES books(BookID)
);

-- BrowsingLogs Table
DROP TABLE IF EXISTS `BrowsingLogs`;
CREATE TABLE BrowsingLogs (
                              LogID INT AUTO_INCREMENT PRIMARY KEY,
                              CustomerID INT NOT NULL,
                              BookID INT NOT NULL,
                              BrowseDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (CustomerID) REFERENCES customers(CustomerID),
                              FOREIGN KEY (BookID) REFERENCES books(BookID)
);
