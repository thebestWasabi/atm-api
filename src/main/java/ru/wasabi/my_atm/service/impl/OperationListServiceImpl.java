package ru.wasabi.my_atm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.entity.OperationList;
import ru.wasabi.my_atm.entity.OperationType;
import ru.wasabi.my_atm.repository.OperationListRepository;
import ru.wasabi.my_atm.service.OperationListService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationListServiceImpl implements OperationListService {

    private final OperationListRepository operationListRepository;

    @Override
    public void save(OperationList history) {
        operationListRepository.save(history);
    }

    @Override
    public List<OperationList> getAccountTransactions(Long accountId) {
        return operationListRepository.findByAccountIdOrderByCreatedAtDesc(accountId);
    }

    public List<OperationList> getOperationListSort(Long accountId, LocalDate startDate, LocalDate endDate) {
        return operationListRepository.findByAccountIdAndCreatedAtBetween(accountId, startDate, endDate);
    }

    public void createTransactionHistory(Account account, BigDecimal amount, OperationType operationType) {
        OperationList operation = new OperationList();
        operation.setAccount(account);
        operation.setAmount(amount);
        operation.setOperationType(operationType);
        operation.setCreatedAt(LocalDateTime.now());
        operationListRepository.save(operation);
    }
}
