ALTER TABLE ecommerce_site.users DROP COLUMN password;
ALTER TABLE ecommerce_site.users ADD auth_id varchar(255) NOT NULL;
ALTER TABLE ecommerce_site.users CHANGE auth_id auth_id varchar(255) NOT NULL AFTER id;
ALTER TABLE ecommerce_site.users ADD CONSTRAINT users_unique UNIQUE KEY (auth_id);
