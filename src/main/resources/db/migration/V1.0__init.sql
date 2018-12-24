CREATE TABLE Customer ( 
   customer_id INT AUTO_INCREMENT, 
   ref_id VARCHAR(50) NOT NULL, 
   email VARCHAR(30) NOT NULL, 
   address VARCHAR(50), 
   password VARCHAR(80) NOT NULL
);

CREATE TABLE Product (
   id INT AUTO_INCREMENT, 
   ref_id VARCHAR(50) NOT NULL, 
   name VARCHAR(30) NOT NULL, 
   price DECIMAL(20,2)  NOT NULL
);

CREATE TABLE Order_Order (
   id INT AUTO_INCREMENT, 
   ref_id VARCHAR(50) NOT NULL, 
   customer_id INT,
   order_status_id VARCHAR(50) NOT NULL, 
   foreign key (customer_id) references customer(customer_id)
);

CREATE TABLE OrderLine (
   order_line_id INT AUTO_INCREMENT, 
   order_id INT,
   product_id INT,
   quantity INT NOT NULL,
   foreign key (order_id) references Order_Order(id), 
   foreign key (product_id) references product(id)
);