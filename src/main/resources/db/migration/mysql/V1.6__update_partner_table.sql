ALTER TABLE ecommerce_site.partners DROP COLUMN password;
ALTER TABLE ecommerce_site.partners
    ADD auth_id varchar(255) NULL;
ALTER TABLE ecommerce_site.partners CHANGE auth_id auth_id varchar (255) NULL AFTER id;
