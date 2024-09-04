-- 创建分类表
CREATE TABLE `categories` (
                              `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                              `name` varchar(255) NOT NULL,
                              `img_url` varchar(255) NOT NULL DEFAULT '/media/logo.png'
);

-- 创建配置表
CREATE TABLE `configs` (
                           `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                           `product_id` bigint NOT NULL,
                           `config_id` bigint NOT NULL,
                           `name` varchar(255) NOT NULL,
                           `brief` varchar(255),
                           `price` integer NOT NULL,
                           `value` varchar(1024) NOT NULL,
                           `product_code` varchar(100) NOT NULL UNIQUE
);

-- 创建产品表
CREATE TABLE `products` (
                            `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            `product_id` varchar(100) NOT NULL,
                            `name` varchar(255) NOT NULL,
                            `price` integer NOT NULL,
                            `brief` varchar(255),
                            `pic_url` varchar(255) NOT NULL DEFAULT '/media/logo.png',
                            `category_id` bigint NOT NULL
);

-- 创建用户表
CREATE TABLE `users` (
                         `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                         `email` varchar(254) NOT NULL UNIQUE,
                         `nickname` varchar(255) NOT NULL,
                         `avatar` varchar(255) NOT NULL,
                         `gender` integer NOT NULL,
                         `password` varchar(100) NULL,
                         `balance` integer NOT NULL,
                         `created_time` datetime(6) NOT NULL
);

-- 创建产品计数表
CREATE TABLE `product_counts` (
                                  `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                  `order_id` bigint NOT NULL,
                                  `product_code` varchar(100) NOT NULL,
                                  `price` integer NOT NULL,
                                  `count` integer NOT NULL
);

-- 创建订单表
CREATE TABLE `orders` (
                          `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          `payment` integer NOT NULL,
                          `status` integer NOT NULL,
                          `express_name` varchar(50) NULL,
                          `express_number` varchar(100) NULL,
                          `created_time` datetime(6) NOT NULL,
                          `payment_time` datetime(6) NULL,
                          `consign_time` datetime(6) NULL,
                          `end_time` datetime(6) NULL,
                          `name` varchar(60) NOT NULL,
                          `phone` varchar(30) NULL,
                          `dz` longtext NULL,
                          `user_id` bigint NOT NULL
);

-- 创建地址表
CREATE TABLE `addresses` (
                             `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                             `name` varchar(60) NOT NULL,
                             `phone` varchar(30) NULL,
                             `dz` longtext NULL,
                             `user_id` bigint NOT NULL
);

-- 创建订单和产品计数的多对多关系表
CREATE TABLE `orders_products` (
                                   `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                   `order_id` bigint NOT NULL,
                                   `productcount_id` bigint NOT NULL
);

-- 添加约束
ALTER TABLE `products`
    ADD CONSTRAINT `products_category_id_fk_categories_id`
        FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `configs`
    ADD CONSTRAINT `configs_product_id_fk_products_id`
        FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `product_counts`
    ADD CONSTRAINT `product_counts_product_code_fk_configs_product_code`
        FOREIGN KEY (`product_code`) REFERENCES `configs` (`product_code`);

ALTER TABLE `orders`
    ADD CONSTRAINT `orders_user_id_fk_users_id`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `orders_products`
    ADD CONSTRAINT `orders_products_order_id_fk_orders_id`
        FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `orders_products`
    ADD CONSTRAINT `orders_products_productcount_id_fk_product_counts_id`
        FOREIGN KEY (`productcount_id`) REFERENCES `product_counts` (`id`);

ALTER TABLE `addresses`
    ADD CONSTRAINT `addresses_user_id_fk_users_id`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (3, 'Lenovo 电脑', 'https://p2.lefile.cn/fes/cms/2024/08/23/mxumf9f2zderqqvb8z7i4ih6880fx7823883.jpg;https://p3.lefile.cn/fes/cms/2024/08/02/q4urk875fj8hi93ciflfkzy8ac73g3711725.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (4, 'Lenovo 台式机', 'https://p3.lefile.cn/fes/cms/2024/08/17/fj0xcmnm3xb5gn5870odsho1c2apgo497402.jpg;https://p4.lefile.cn/fes/cms/2024/08/08/alwlzpyc9soqqdrm2vqjjrdfoca3wi671310.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (5, '手机&配件', 'https://p4.lefile.cn/fes/cms/2024/09/03/7ucuspywb0z8girquvy8ham5ui00sj666943.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (6, '平板电脑', 'https://p4.lefile.cn/fes/cms/2024/08/02/fxwtumpkt26c2gv68n8psdd4pnk0ok892835.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (7, '选件', 'https://p4.lefile.cn/fes/cms/2024/07/31/5j87ekmejdlaeullqhqnkvt9p7b5lr159729.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (8, '服务/配件', 'https://p1.lefile.cn/fes/cms/2024/08/29/mpj3ryyiaqad1nexbqb4p4rkg2uz8d028497.jpg;https://p3.lefile.cn/fes/cms/2024/09/02/tdizyain91hhvgqopw0wrxwv2zl230763149.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (9, '智能', 'https://p1.lefile.cn/fes/cms/2024/07/11/65j92u9o2id2rd4j8u45dfur339t8r441719.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (10, '显示器', 'https://p1.lefile.cn/fes/cms/2024/08/26/z3czih31yfo19oensm7x2qe2hodtno964393.jpg');
INSERT INTO `categories` (`id`, `name`, `img_url`) VALUES (11, 'IP周边', 'https://p2.lefile.cn/fes/cms/2024/08/05/grnmihkn6qfxcxd4phe43qwsg1o3u2157877.jpg');