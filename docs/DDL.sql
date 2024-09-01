-- ecommerce_site.users definition
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(20) NOT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                         `email` varchar(30) NOT NULL,
                         `status` varchar(30) NOT NULL DEFAULT 'ACTIVE',
                         `created_at` datetime NOT NULL,
                         `updated_at` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='회원';

-- ecommerce_site.partners definition
CREATE TABLE `partners` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `business_number` varchar(50) NOT NULL,
                            `name` varchar(20) NOT NULL,
                            `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                            `email` varchar(30) NOT NULL,
                            `status` varchar(30) NOT NULL DEFAULT 'ACTIVE',
                            `created_at` datetime NOT NULL,
                            `updated_at` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='파트너';

-- ecommerce_site.categories definition
CREATE TABLE `categories` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `name` varchar(50) NOT NULL,
                              `description` varchar(100) DEFAULT NULL,
                              `parent_category_id` bigint DEFAULT NULL,
                              `created_at` datetime NOT NULL,
                              `updated_at` datetime DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='카테고리';

-- ecommerce_site.addresses definition

CREATE TABLE `addresses` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `user_id` bigint NOT NULL,
                             `phone_number` varchar(11) NOT NULL,
                             `address_type` varchar(30) DEFAULT NULL,
                             `is_primary` tinyint(1) NOT NULL,
                             `postal_code` varchar(10) NOT NULL,
                             `road_address` varchar(255) NOT NULL,
                             `jibun_address` varchar(255) DEFAULT NULL,
                             `road_name_code` varchar(13) DEFAULT NULL,
                             `building_name` varchar(255) DEFAULT NULL,
                             `address_detail` varchar(255) DEFAULT NULL,
                             `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
                             `created_at` datetime NOT NULL,
                             `updated_at` datetime DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FK_user_TO_address` (`user_id`),
                             CONSTRAINT `FK_user_TO_address` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='배송지';

-- ecommerce_site.products definition

CREATE TABLE `products` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `partner_id` bigint NOT NULL,
                            `category_id` bigint NOT NULL,
                            `name` varchar(100) NOT NULL,
                            `description` json NOT NULL,
                            `price` bigint NOT NULL,
                            `stock_quantity` bigint NOT NULL DEFAULT '1',
                            `is_enable` tinyint(1) NOT NULL DEFAULT '1',
                            `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
                            `created_at` datetime DEFAULT NULL,
                            `updated_at` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK_partner_TO_product` (`partner_id`),
                            KEY `FK_category_TO_product` (`category_id`),
                            CONSTRAINT `FK_category_TO_product` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
                            CONSTRAINT `FK_partner_TO_product` FOREIGN KEY (`partner_id`) REFERENCES `partners` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='상품';

-- ecommerce_site.carts definition

CREATE TABLE `carts` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `user_id` bigint NOT NULL,
                         `product_id` bigint NOT NULL,
                         `quantity` bigint NOT NULL,
                         `created_at` datetime NOT NULL,
                         `updated_at` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FK_user_TO_cart` (`user_id`),
                         KEY `FK_product_TO_cart` (`product_id`),
                         CONSTRAINT `FK_product_TO_cart` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                         CONSTRAINT `FK_user_TO_cart` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='장바구니';

-- ecommerce_site.orders definition

CREATE TABLE `orders` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `user_id` bigint NOT NULL,
                          `total_amount` bigint NOT NULL,
                          `status` varchar(30) NOT NULL DEFAULT 'PENDING',
                          `created_at` datetime NOT NULL,
                          `updated_at` datetime DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FK_user_TO_order` (`user_id`),
                          CONSTRAINT `FK_user_TO_order` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='주문';

-- ecommerce_site.order_details definition

CREATE TABLE `order_details` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `order_id` bigint NOT NULL,
                                 `product_id` bigint NOT NULL,
                                 `quantity` bigint NOT NULL,
                                 `unit_price` bigint NOT NULL,
                                 `created_at` datetime NOT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FK_order_TO_order_detail` (`order_id`),
                                 KEY `FK_product_TO_order_detail` (`product_id`),
                                 CONSTRAINT `FK_order_TO_order_detail` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                                 CONSTRAINT `FK_product_TO_order_detail` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='주문 상세';

-- ecommerce_site.payments definition

CREATE TABLE `payments` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `order_id` bigint NOT NULL,
                            `payment_method` varchar(30) NOT NULL DEFAULT 'CREDIT_CARD',
                            `status` varchar(30) NOT NULL DEFAULT 'PENDING',
                            `amount` bigint NOT NULL,
                            `payment_date` datetime NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK_order_TO_payment` (`order_id`),
                            CONSTRAINT `FK_order_TO_payment` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='결제';

-- ecommerce_site.reviews definition

CREATE TABLE `reviews` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `user_id` bigint NOT NULL,
                           `product_id` bigint NOT NULL,
                           `rating` int NOT NULL,
                           `comment` longtext NOT NULL,
                           `created_at` datetime DEFAULT NULL,
                           `updated_at` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FK_user_TO_review` (`user_id`),
                           KEY `FK_product_TO_review` (`product_id`),
                           CONSTRAINT `FK_product_TO_review` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                           CONSTRAINT `FK_user_TO_review` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='리뷰';

-- ecommerce_site.shipments definition

CREATE TABLE `shipments` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `order_id` bigint NOT NULL,
                             `order_detail_id` bigint NOT NULL,
                             `tracking_number` varchar(30) NOT NULL,
                             `status` varchar(30) NOT NULL DEFAULT 'SHIPPED',
                             `created_at` datetime NOT NULL,
                             `updated_at` datetime DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FK_order_TO_shipment` (`order_id`),
                             KEY `FK_order_detail_TO_shipment` (`order_detail_id`),
                             CONSTRAINT `FK_order_detail_TO_shipment` FOREIGN KEY (`order_detail_id`) REFERENCES `order_details` (`id`),
                             CONSTRAINT `FK_order_TO_shipment` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='배송';