ALTER TABLE ecommerce_site.carts ADD CONSTRAINT carts_unique UNIQUE KEY (user_id,product_id);
CREATE INDEX carts_user_id_IDX USING BTREE ON ecommerce_site.carts (user_id,product_id);
