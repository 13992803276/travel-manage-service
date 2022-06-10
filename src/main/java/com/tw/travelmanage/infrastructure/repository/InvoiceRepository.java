package com.tw.travelmanage.infrastructure.repository;

import com.tw.travelmanage.infrastructure.repository.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lexu
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findInvoiceById(Integer id);
}
