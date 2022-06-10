DROP TABLE IF EXISTS fixed_statement;
CREATE TABLE fixed_statement (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `remit_bank_no` varchar(255) DEFAULT NULL,
                             `remit_bank_name` varchar(255) DEFAULT NULL,
                             `bene_bank_no` varchar (255) DEFAULT NULL,
                             `bene_bank_name` varchar (255) DEFAULT NULL,
                             `remitter` varchar(255) DEFAULT NULL,
                             `amount` decimal(19,2) DEFAULT NULL,
                             `remit_time` varchar(255) DEFAULT NULL,
                             `created` date DEFAULT NULL,
                             `title` varchar(255) DEFAULT NULL,
                             `beneficiary` varchar(255) DEFAULT NULL,
                             `remittance` varchar(255) DEFAULT NULL,
                             `pay_status` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;