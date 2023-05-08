package ru.wasabi.my_atm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wasabi.my_atm.entity.account.Account;
import ru.wasabi.my_atm.entity.exception.NotEnoughMoneyInAccount;
import ru.wasabi.my_atm.entity.exception.ResourceNotFoundException;
import ru.wasabi.my_atm.entity.transaction.TransactionHistory;
import ru.wasabi.my_atm.entity.transaction.TransactionType;
import ru.wasabi.my_atm.repository.AccountRepository;
import ru.wasabi.my_atm.service.AccountService;
import ru.wasabi.my_atm.service.TransactionHistoryService;
import ru.wasabi.my_atm.service.TransactionService;
import ru.wasabi.my_atm.web.dto.TransactionHistoryDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountService accountService;
    private final TransactionHistoryService transactionHistoryService;


    @Override
    @Transactional(readOnly = true)
    public Account getById(Long id) {
        return accountService.getAccountById(id);
    }


    @Override
    @Transactional
    public void takeMoney(Long accountId, BigDecimal amount) {
        Account user = getById(accountId);
        checkIfEnoughMoney(user, amount);
        BigDecimal userNewBalance = user.getBalance().subtract(amount);
        accountService.changeBalance(user.getId(), userNewBalance);
        createTransactionHistory(user, amount, TransactionType.WITHDRAWAL);
    }


    @Override
    @Transactional
    public void putMoney(Long accountId, BigDecimal amount) {
        Account user = getById(accountId);
        BigDecimal userNewBalance = user.getBalance().add(amount);
        accountService.changeBalance(accountId, userNewBalance);
        createTransactionHistory(user, amount, TransactionType.DEPOSIT);
    }


    @Override
    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        Account sender = getById(senderId);
        Account receiver = getById(receiverId);

        checkIfEnoughMoney(sender, amount);
        BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
        BigDecimal receiverNewBalance = receiver.getBalance().add(amount);
        accountService.changeBalance(sender.getId(), senderNewBalance);
        accountService.changeBalance(receiver.getId(), receiverNewBalance);

        createTransactionHistory(sender, amount.negate(), TransactionType.TRANSFER);
        createTransactionHistory(receiver, amount, TransactionType.TRANSFER);
    }


    private void createTransactionHistory(Account account, BigDecimal amount, TransactionType transactionType) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAccount(account);
        transactionHistory.setAmount(amount);
        transactionHistory.setTransactionType(transactionType);
        transactionHistory.setCreatedAt(LocalDateTime.now());
        transactionHistoryService.save(transactionHistory);
    }


    private void checkIfEnoughMoney(Account sender, BigDecimal amount) {
        if (amount.compareTo(sender.getBalance()) > 0) {
            throw new NotEnoughMoneyInAccount("На аккаунте не хватает денег");
        }
    }
}
