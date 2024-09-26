-- address_id 컬럼 추가
ALTER TABLE `ecommerce_site`.`shipments`
    ADD COLUMN `address_id` bigint NOT NULL;

-- 외래 키 제약 조건 및 인덱스 추가
ALTER TABLE `ecommerce_site`.`shipments`
    ADD CONSTRAINT `FK_address_TO_shipment`
        FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);
