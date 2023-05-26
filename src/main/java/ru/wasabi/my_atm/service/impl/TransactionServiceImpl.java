package ru.wasabi.my_atm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.entity.exception.NotEnoughMoneyInAccount;
import ru.wasabi.my_atm.entity.OperationType;
import ru.wasabi.my_atm.service.AccountService;
import ru.wasabi.my_atm.service.OperationListService;
import ru.wasabi.my_atm.service.TransactionService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountService accountService;
    private final OperationListService operationListService;


    @Override
    @Transactional
    public void takeMoney(Long accountId, BigDecimal amount) {
        Account user = accountService.getAccountById(accountId);
        checkIfEnoughMoney(user, amount);
        BigDecimal userNewBalance = user.getBalance().subtract(amount);
        accountService.changeBalance(user.getId(), userNewBalance);
        operationListService.createTransactionHistory(user, amount.negate(), OperationType.WITHDRAWAL);
    }


    @Override
    @Transactional
    public void putMoney(Long accountId, BigDecimal amount) {
        Account user = accountService.getAccountById(accountId);
        BigDecimal userNewBalance = user.getBalance().add(amount);
        accountService.changeBalance(accountId, userNewBalance);
        operationListService.createTransactionHistory(user, amount, OperationType.DEPOSIT);
    }


    @Override
    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        Account sender = accountService.getAccountById(senderId);
        Account receiver = accountService.getAccountById(receiverId);

        checkIfEnoughMoney(sender, amount);
        BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
        BigDecimal receiverNewBalance = receiver.getBalance().add(amount);
        accountService.changeBalance(sender.getId(), senderNewBalance);
        accountService.changeBalance(receiver.getId(), receiverNewBalance);

        operationListService.createTransactionHistory(sender, amount.negate(), OperationType.TRANSFER);
        operationListService.createTransactionHistory(receiver, amount, OperationType.TRANSFER);
    }


    private void checkIfEnoughMoney(Account sender, BigDecimal amount) {
        if (amount.compareTo(sender.getBalance()) > 0) {
            throw new NotEnoughMoneyInAccount("На аккаунте не хватает денег");
        }
    }
}
