package ru.wasabi.my_atm.service;

import ru.wasabi.my_atm.entity.transaction.TransactionHistory;
import ru.wasabi.my_atm.repository.TransactionHistoryRepository;

import java.util.List;

public interface TransactionHistoryService {

    void save(TransactionHistory history);
    List<TransactionHistory> getByAccountId(Long accountId);

}
