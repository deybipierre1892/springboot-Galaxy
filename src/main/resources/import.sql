-- Carga de datos para Categorías
INSERT INTO category (name) VALUES ('Electrónica');
INSERT INTO category (name) VALUES ('Hogar');
INSERT INTO category (name) VALUES ('Libros');

-- Carga de datos para Proveedores
INSERT INTO supplier (name, contact_person) VALUES ('TechCorp', 'Juan Perez');
INSERT INTO supplier (name, contact_person) VALUES ('HomeGoods Inc.', 'Maria Rodriguez');
INSERT INTO supplier (name, contact_person) VALUES ('Editorial Planeta', 'Carlos Gomez');

-- Carga de datos para Productos
INSERT INTO product (name, sku, description, price, stock, creation_date, category_id, supplier_id) VALUES ('Laptop Modelo X', 'LP-TC-001', 'Laptop de última generación', 1200.50, 50, '2024-01-15', 1, 1);
INSERT INTO product (name, sku, description, price, stock, creation_date, category_id, supplier_id) VALUES ('Smartphone Z', 'SP-TC-002', 'Smartphone con cámara de 108MP', 850.00, 150, '2024-02-20', 1, 1);
INSERT INTO product (name, sku, description, price, stock, creation_date, category_id, supplier_id) VALUES ('Aspiradora Robot', 'AR-HG-001', 'Limpia tu casa de forma autónoma', 300.75, 80, '2024-03-10', 2, 2);
INSERT INTO product (name, sku, description, price, stock, creation_date, category_id, supplier_id) VALUES ('El Señor de los Anillos', 'BK-EP-001', 'Edición de coleccionista', 45.99, 200, '2024-01-05', 3, 3);