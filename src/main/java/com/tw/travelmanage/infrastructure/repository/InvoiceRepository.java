package com.tw.travelmanage.infrastructure.repository;

import com.sun.tools.javac.util.List;
import com.tw.travelmanage.infrastructure.repository.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lexu
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice getInvoiceById(Integer id);
    List<Invoice> getAll();
}
