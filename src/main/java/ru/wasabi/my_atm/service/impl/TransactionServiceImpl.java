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
import ru.wasabi.my_atm.service.TransactionHistoryService;
import ru.wasabi.my_atm.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionHistoryService transactionHistoryService;

    @Override
    @Transactional(readOnly = true)
    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Аккаунт с таким id не найден: " + id));
    }

    @Override
    @Transactional
    public void takeMoney(Long accountId, BigDecimal amount) {
        Account user = getById(accountId);

        if (amount.compareTo(user.getBalance()) > 0) {
            throw new NotEnoughMoneyInAccount("На аккаунте не хватает денег");
        }

        BigDecimal userNewBalance = user.getBalance().subtract(amount);
        accountRepository.changeBalance(accountId, userNewBalance);

        TransactionHistory history = new TransactionHistory();
        history.setAccount(user);
        history.setAmount(amount.negate());
        history.setTransactionType(TransactionType.WITHDRAWAL);
        history.setCreatedAt(LocalDateTime.now());
        transactionHistoryService.save(history);
    }

    @Override
    @Transactional
    public void putMoney(Long accountId, BigDecimal amount) {
        Account user = getById(accountId);
        BigDecimal userNewBalance = user.getBalance().add(amount);
        accountRepository.changeBalance(accountId, userNewBalance);

        TransactionHistory history = new TransactionHistory();
        history.setAccount(user);
        history.setAmount(amount);
        history.setTransactionType(TransactionType.DEPOSIT);
        history.setCreatedAt(LocalDateTime.now());
        transactionHistoryService.save(history);
    }

    @Override
    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        Account sender = getById(senderId);
        Account receiver = getById(receiverId);

        if (amount.compareTo(sender.getBalance()) > 0) {
            throw new NotEnoughMoneyInAccount("На аккаунте не хватает денег");
        }

        BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
        BigDecimal receiverNewBalance = receiver.getBalance().add(amount);
        accountRepository.changeBalance(senderId, senderNewBalance);
        accountRepository.changeBalance(receiverId, receiverNewBalance);

        TransactionHistory senderHistory = new TransactionHistory();
        senderHistory.setAccount(sender);
        senderHistory.setAmount(amount.negate());
        senderHistory.setTransactionType(TransactionType.TRANSFER);
        transactionHistoryService.save(senderHistory);

        TransactionHistory receiverHistory = new TransactionHistory();
        receiverHistory.setAccount(receiver);
        receiverHistory.setAmount(amount);
        receiverHistory.setTransactionType(TransactionType.TRANSFER);
        transactionHistoryService.save(receiverHistory);
    }
}
