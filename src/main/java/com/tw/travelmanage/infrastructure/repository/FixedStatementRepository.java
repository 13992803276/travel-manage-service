package com.tw.travelmanage.infrastructure.repository;

import com.sun.tools.javac.util.List;
import com.tw.travelmanage.infrastructure.repository.entity.FixedStatement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lexu
 */
public interface FixedStatementRepository extends JpaRepository<FixedStatement, Integer> {

    FixedStatement getFixedStatementById(Integer id);

    List<FixedStatement> getAll();
}
