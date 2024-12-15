CREATE DATABASE  IF NOT EXISTS `OnLineBookStore` ;
USE `OnLineBookStore`;

DROP TABLE IF EXISTS `customers`;
CREATE TABLE customers (
    CustomerID  INT AUTO_INCREMENT PRIMARY KEY,
    CustomerName VARCHAR(20) NOT NULL,
    Phone VARCHAR(20) NOT NULL ,
    Email VARCHAR(20) NOT NULL ,
    Address VARCHAR(20) NOT NULL ,
    RegisterDate DATE NOT NULL
);

DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE suppliers (
    SupplierID  INT AUTO_INCREMENT PRIMARY KEY,
    SupplierName VARCHAR(20) NOT NULL,
    Phone VARCHAR(20) NOT NULL ,
    Email VARCHAR(20) NOT NULL ,
    Address VARCHAR(20) NOT NULL ,
    RegisterDate DATE NOT NULL
);

DROP TABLE IF EXISTS `books`;
CREATE TABLE books (

);

DROP TABLE IF EXISTS `orders`;
CREATE TABLE orders (

);

DROP TABLE IF EXISTS `OrderDetails`;
CREATE TABLE OrderDetails (

);

DROP TABLE IF EXISTS `Purchases`;
CREATE TABLE Purchases (

);

DROP TABLE IF EXISTS `PurchaseDetails`;
CREATE TABLE PurchaseDetails (

);

DROP TABLE IF EXISTS `BrowsingLogs`;
CREATE TABLE BrowsingLogs (

);

