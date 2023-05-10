package ru.wasabi.my_atm.service;

import ru.wasabi.my_atm.entity.account.Account;
import ru.wasabi.my_atm.entity.transaction.OperationList;
import ru.wasabi.my_atm.entity.transaction.OperationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OperationListService {

    void save(OperationList history);

    List<OperationList> getAccountTransactions(Long accountId);

    List<OperationList> getOperationListSort(Long accountId, LocalDate startDate, LocalDate endDate);

    void createTransactionHistory(Account account, BigDecimal amount, OperationType operationType);
}
