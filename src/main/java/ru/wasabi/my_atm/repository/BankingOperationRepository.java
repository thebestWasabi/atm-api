package ru.wasabi.my_atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Repository
//public interface BankingOperationRepository extends JpaRepository<BankingOperation, Long> {
//
//    @Modifying
//    @Query(value = "insert into banking_operation(account_id, amount, date) values (:accountId, :amount, :date)", nativeQuery = true)
//    void saveOperation(Long accountId, BigDecimal amount, LocalDateTime date);
//
//}
