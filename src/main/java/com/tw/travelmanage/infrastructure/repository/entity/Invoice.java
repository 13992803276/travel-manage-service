package com.tw.travelmanage.infrastructure.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author lexu
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String payerName;
    private BigDecimal amount;
    private String payerTaxNo;
    private String payerAddress;
    private String payerBankNo;
    private String saleTaxNo;
    private String saleAddress;
    private String saleBankNo;
    private LocalDate invoiceDate;
    private String invoiceName;
}
