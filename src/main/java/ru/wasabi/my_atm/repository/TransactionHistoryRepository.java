package ru.wasabi.my_atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.wasabi.my_atm.entity.transaction.TransactionHistory;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
