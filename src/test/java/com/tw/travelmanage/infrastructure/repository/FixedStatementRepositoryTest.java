
package com.tw.travelmanage.infrastructure.repository;

import com.tw.travelmanage.infrastructure.repository.entity.FixedStatement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class FixedStatementRepositoryTest {

    @Autowired
    FixedStatementRepository fixedStatementRepository;

    @Test
    public void given_fixed_statement_id_should_return_date_from_database(){
        FixedStatement fixedStatement = fixedStatementRepository.findFixedStatementById(4);
        Assertions.assertEquals("123445", fixedStatement.getRemitBankNo());
        Assertions.assertEquals("中国银行", fixedStatement.getRemitBankName());
    }

    @Test
    public void test_save_fixed_statement_entity_to_database(){
        FixedStatement fixedStatement = FixedStatement.builder()
                .amount(BigDecimal.valueOf(100))
                .beneBankName("建设银行")
                .beneBankNo("334455")
                .beneficiary("任你行平台")
                .remitBankName("中国银行")
                .payStatus("0")
                .title("固定费用")
                .build();
        FixedStatement fixedStatementData = fixedStatementRepository.save(fixedStatement);
        Assertions.assertEquals(BigDecimal.valueOf(100), fixedStatementData.getAmount());
        Assertions.assertEquals("建设银行", fixedStatementData.getBeneBankName());
    }

}