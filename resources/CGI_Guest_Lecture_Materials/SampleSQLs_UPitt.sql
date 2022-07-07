------------ Customer_Test table queries ------------
select top 5 * from TestDB.dbo.Customer_Test;

select column_name, data_type from AdventureWorksLT2016.information_schema.columns 
	where upper(table_schema) = upper('saleslt') and upper(table_name) = upper('Customer');
select column_name, data_type from TestDB.information_schema.columns 
	where upper(table_schema) = upper('dbo') and upper(table_name) = upper('Customer_Test');


select count(*) from AdventureWorksLT2016.saleslt.customer;
select count(*) from TestDB.dbo.Customer_Test;


select * from TestDB.dbo.Customer_Test
except
select * from AdventureWorksLT2016.saleslt.customer;

select * from AdventureWorksLT2016.saleslt.customer
except
select * from TestDB.dbo.Customer_Test;

------------ Product_sold_v2 table queries ------------
select top 5 * from TestDB.dbo.product_sold_v2;

select column_name, data_type from TestDB.information_schema.columns 
	where upper(table_schema) = upper('dbo') and upper(table_name) = upper('product_sold');
select column_name, data_type from TestDB.information_schema.columns 
	where upper(table_schema) = upper('dbo') and upper(table_name) = upper('product_sold_v2');


select count(*) from TestDB.dbo.product_sold;
select count(*) from TestDB.dbo.product_sold_v2;

select * from TestDB.dbo.product_sold
except
select * from TestDB.dbo.product_sold_v2;

select * from TestDB.dbo.product_sold_v2
except
select * from TestDB.dbo.product_sold;

------------ Product_sold table queries ------------
select top 5 * from TestDB.dbo.product_sold;

select column_name, data_type from AdventureWorksLT2016.information_schema.columns 
	where upper(table_schema) = upper('saleslt') and upper(table_name) = upper('SalesOrderDetail');
select column_name, data_type from TestDB.information_schema.columns 
	where upper(table_schema) = upper('dbo') and upper(table_name) = upper('product_sold');

select prd.productid, prd.name, prd.productnumber, prd.listprice, orders.totalOrders
	from AdventureWorksLT2016.saleslt.product prd
	join (select productid, sum(orderqty) totalOrders from AdventureWorksLT2016.saleslt.SalesOrderDetail group by productid) orders
		on prd.productid = orders.productid
except
select * from TestDB.dbo.product_sold;

select * from TestDB.dbo.product_sold
except
select prd.productid, prd.name, prd.productnumber, prd.listprice, orders.totalOrders
	from AdventureWorksLT2016.saleslt.product prd
	join (select productid, sum(orderqty) totalOrders from AdventureWorksLT2016.saleslt.SalesOrderDetail group by productid) orders
		on prd.productid = orders.productid;