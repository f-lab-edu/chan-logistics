package com.chan.repository;

import com.chan.domain.Center;
import com.chan.domain.Invoice;
import com.chan.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByCenterAndOrderStatusAndDeliveryDateAndMeridiem(Center center, OrderStatus orderStatus, LocalDate deliveryDate, boolean meridiem);
    Invoice findByIdAndOrderStatus(Long id,OrderStatus status);
}
