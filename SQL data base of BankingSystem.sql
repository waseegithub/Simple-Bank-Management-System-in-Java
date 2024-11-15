create database bankingsystem;
use bankingsystem;

CREATE TABLE Account1 (
    AccountName VARCHAR(255),
    AccountNumber VARCHAR(20) PRIMARY KEY,
    Balance DOUBLE
);
Alter table Account1 add column PIN int not null;

insert into Account1(AccountName,PIN,AccountNumber,Balance) values("Waseem Abbas",1212,"0465900",2000);
select * from Account1;

CREATE TABLE Transaction (
    TransactionID INT AUTO_INCREMENT,
    AccountNumber1 VARCHAR(20),
    Amount DOUBLE,
    TransactionType VARCHAR(50),
    Timestamp DATETIME,
    PRIMARY KEY (TransactionID,AccountNumber1),
    FOREIGN KEY (AccountNumber1) REFERENCES Account1(AccountNumber)
);
truncate table Transaction;
insert into Transaction(AccountNumber1,  Amount,TransactionType,Timestamp) values("0465",3900,"D","2024-11-11 15:30:45");
select *from Transaction;
CREATE TABLE USER (
    Name VARCHAR(100) NOT NULL,
    CNIC VARCHAR(20) NOT NULL PRIMARY KEY,
    Address VARCHAR(100),
    Contact VARCHAR(20),
    AccountNumber VARCHAR(20),
    FOREIGN KEY (AccountNumber) REFERENCES Account1(AccountNumber)
);
insert into User (Name,CNIC,Address,Contact,AccountNumber) values ("Ali","3302","lahore","44","0465900")
select * from USER




