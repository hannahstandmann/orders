INSERT INTO Customer(ref_id,email,address,password) VALUES ('740e450b-a2c4-4725-bad9-6c21552ffd99', 'ana.mattuzzi@me.com','Bulevar Mihajla Pupina 103/3', '$2a$10$OsfVZkfqmIV1esHt6FhWg.xun43hY/1sQ7R/bK835vd8z/zGCwa/q');
INSERT INTO Customer(ref_id,email,address,password) VALUES ('740e450b-a2c4-4725-bad9-6c21552ffe00', 'ana.mattuzzi@gmail.com','Bulevar Mihajla Pupina 103/3', '$2a$10$OsfVZkfqmIV1esHt6FhWg.xun43hY/1sQ7R/bK835vd8z/zGCwa/q');
INSERT INTO Product(ref_id, name, price) VALUES ('740e450b-a2c4-4725-bad9-6c21552ffd80', 'Notebook', 300.00);
INSERT INTO Product(ref_id, name, price) VALUES ('740e450b-a2c4-4725-bad9-6c21552ffd81', 'O Bag', 13000.00);
INSERT INTO Product(ref_id, name, price) VALUES ('740e450b-a2c4-4725-bad9-6c21552ffd82', 'O Clock', 3000.00);
INSERT INTO Order_Order(ref_id, customer_id, order_status_id) VALUES ('740e450b-a2c4-4725-bad9-6c21552ffd70', 1, 'new');
INSERT INTO OrderLine(order_id, product_id, quantity) VALUES (1,1,1);
INSERT INTO OrderLine(order_id, product_id, quantity) VALUES (1,2,1);
INSERT INTO OrderLine(order_id, product_id, quantity) VALUES (1,3,1);