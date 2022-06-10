package com.tw.travelmanage.infrastructure.repository;


import com.tw.travelmanage.infrastructure.repository.entity.FixedStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lexu
 */
@Repository
public interface FixedStatementRepository extends JpaRepository<FixedStatement, Integer> {

    FixedStatement findFixedStatementById(Integer id);
}
