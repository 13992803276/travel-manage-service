DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice (
                     `id` int NOT NULL AUTO_INCREMENT,
                     `payer_name` varchar(255) DEFAULT NULL,
                     `amount` decimal(19,2) DEFAULT NULL,
                     `payer_tax_no` varchar(255) DEFAULT NULL,
                     `payer_address` varchar (255) DEFAULT NULL,
                     `payer_bank_no` varchar (255) DEFAULT NULL,
                     `sale_tax_no` varchar(255) DEFAULT NULL,
                     `sale_address` varchar(255) DEFAULT NULL,
                     `sale_bank_no` varchar(255) DEFAULT NULL,
                     `invoice_date` date DEFAULT NULL,
                     `invoice_name` varchar(255) DEFAULT NULL,
                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;