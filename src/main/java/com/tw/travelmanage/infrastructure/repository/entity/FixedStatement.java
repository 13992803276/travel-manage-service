package com.tw.travelmanage.infrastructure.repository.entity;

import com.tw.travelmanage.constant.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lexu
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fixed_statement")
public class FixedStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String remitBankNo;
    private String remitBankName;
    private String beneBankNo;
    private String beneBankName;
    private String remitter;
    private BigDecimal amount;
    private LocalDateTime remitTime;
    private String title;
    private String payStatus;
    @CreatedDate
    private LocalDate created;

}
