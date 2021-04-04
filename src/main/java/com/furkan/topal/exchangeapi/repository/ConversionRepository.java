package com.furkan.topal.exchangeapi.repository;

import com.furkan.topal.exchangeapi.entity.Conversion;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

  Conversion findByTransactionId(Long transactionId);

  Conversion findByTransactionIdAndTransactionDate(Long transactionId, String transactionDate);

  List<Conversion> findByTransactionDate(String transactionDate, Pageable pageable);
}
