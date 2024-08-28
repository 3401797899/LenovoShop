--
-- Create model Category
--
CREATE TABLE `categories` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `name` varchar(255) NOT NULL);
--
-- Create model Config
--
CREATE TABLE `configs` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `name` varchar(255) NOT NULL, `value` varchar(255) NOT NULL);
--
-- Create model Product
--
CREATE TABLE `products` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `product_id` varchar(100) NOT NULL, `name` varchar(255) NOT NULL, `pic_url` varchar(255) NOT NULL, `price` integer NOT NULL, `category_id` bigint NOT NULL);
CREATE TABLE `products_configs` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `product_id` bigint NOT NULL, `config_id` bigint NOT NULL);
--
-- Create model User
--
CREATE TABLE `users` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `email` varchar(254) NOT NULL UNIQUE, `nickname` varchar(255) NOT NULL, `avatar` varchar(255) NOT NULL, `gender` integer NOT NULL, `password` varchar(100) NOT NULL, `balance` integer NOT NULL, `created_time` datetime(6) NOT NULL);
--
-- Create model ProductCount
--
CREATE TABLE `product_counts` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `price` integer NOT NULL, `count` integer NOT NULL, `product_id_id` bigint NOT NULL);
--
-- Create model Order
--
CREATE TABLE `orders` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `order_id` varchar(100) NOT NULL, `payment` integer NOT NULL, `status` integer NOT NULL, `express_name` varchar(50) NULL, `express_number` varchar(100) NULL, `created_time` datetime(6) NOT NULL, `payment_time` datetime(6) NULL, `consign_time` datetime(6) NULL, `end_time` datetime(6) NULL, `name` varchar(60) NOT NULL, `phone` varchar(30) NULL, `dz` longtext NULL, `user_id` bigint NOT NULL);
CREATE TABLE `orders_products` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `order_id` bigint NOT NULL, `productcount_id` bigint NOT NULL);
--
-- Create model Address
--
CREATE TABLE `addresses` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `name` varchar(60) NOT NULL, `phone` varchar(30) NULL, `dz` longtext NULL, `user_id` bigint NOT NULL);
ALTER TABLE `products` ADD CONSTRAINT `products_category_id_a7a3a156_fk_categories_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);
ALTER TABLE `products_configs` ADD CONSTRAINT `products_configs_product_id_config_id_7c38a931_uniq` UNIQUE (`product_id`, `config_id`);
ALTER TABLE `products_configs` ADD CONSTRAINT `products_configs_product_id_bd33fefe_fk_products_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
ALTER TABLE `products_configs` ADD CONSTRAINT `products_configs_config_id_1a0a2ed3_fk_configs_id` FOREIGN KEY (`config_id`) REFERENCES `configs` (`id`);
ALTER TABLE `product_counts` ADD CONSTRAINT `product_counts_product_id_id_345ae554_fk_products_id` FOREIGN KEY (`product_id_id`) REFERENCES `products` (`id`);
ALTER TABLE `orders` ADD CONSTRAINT `orders_user_id_7e2523fb_fk_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
ALTER TABLE `orders_products` ADD CONSTRAINT `orders_products_order_id_productcount_id_8b04e40b_uniq` UNIQUE (`order_id`, `productcount_id`);
ALTER TABLE `orders_products` ADD CONSTRAINT `orders_products_order_id_6d29d1f1_fk_orders_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);
ALTER TABLE `orders_products` ADD CONSTRAINT `orders_products_productcount_id_de1ac395_fk_product_counts_id` FOREIGN KEY (`productcount_id`) REFERENCES `product_counts` (`id`);
ALTER TABLE `addresses` ADD CONSTRAINT `addresses_user_id_d930d1dc_fk_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
