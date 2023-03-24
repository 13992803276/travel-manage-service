DROP TABLE IF EXISTS travel_user;
CREATE TABLE travel_user
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `account` varchar(255)   DEFAULT NULL,
    `address` varchar(255)   DEFAULT NULL,
    `balance` decimal(19, 2) DEFAULT NULL,
    `bank_no` varchar(255)   DEFAULT NULL,
    `created` date           DEFAULT NULL,
    `name`    varchar(255)   DEFAULT NULL,
    `phone`   varchar(255)   DEFAULT NULL,
    `company` varchar(255)   DEFAULT NULL,
    `status`  varchar(255)   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;