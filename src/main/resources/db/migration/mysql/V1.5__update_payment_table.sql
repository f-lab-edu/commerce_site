ALTER TABLE `ecommerce_site`.`payments`
DROP
COLUMN `payment_date`;

ALTER TABLE `ecommerce_site`.`payments`
    ADD COLUMN `created_at` DATETIME NOT NULL;

ALTER TABLE `ecommerce_site`.`payments`
    ADD COLUMN `updated_at` DATETIME NULL;

ALTER TABLE `ecommerce_site`.`payments`
    ADD COLUMN `user_id` BIGINT NOT NULL;