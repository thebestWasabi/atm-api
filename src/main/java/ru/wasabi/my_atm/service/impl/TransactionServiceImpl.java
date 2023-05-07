package ru.wasabi.my_atm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.entity.exception.ResourceNotFoundException;
import ru.wasabi.my_atm.repository.AccountRepository;
import ru.wasabi.my_atm.repository.BankingOperationRepository;
import ru.wasabi.my_atm.entity.exception.NotEnoughMoneyInAccount;
import ru.wasabi.my_atm.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final BankingOperationRepository bankingOperationRepository;

    @Override
    @Transactional(readOnly = true)
    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Аккаунт с таким id не найден: " + id));
    }

    @Override
    @Transactional
    public void takeMoney(Long accountId, BigDecimal amount) {
        Account user = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Аккаунт с таким id не найден: " + accountId));

        if (amount.compareTo(user.getBalance()) <= 0) {
            BigDecimal userNewBalance = user.getBalance().subtract(amount);
            accountRepository.changeBalance(accountId, userNewBalance);
        } else {
            throw new NotEnoughMoneyInAccount("На аккауте не хватает денег");
        }
    }

    @Transactional
    public void putMoney(Long id, BigDecimal amount) {
        Account user = getById(id);
        BigDecimal userNewBalance = user.getBalance().add(amount);
        accountRepository.changeBalance(id, userNewBalance);
    }

    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        Account sender = getById(senderId);
        Account receiver = getById(receiverId);

        if (amount.compareTo(sender.getBalance()) <= 0) {
            BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
            BigDecimal receiverNewBalance = receiver.getBalance().add(amount);
            accountRepository.changeBalance(senderId, senderNewBalance);
            accountRepository.changeBalance(receiverId, receiverNewBalance);
        } else {
            throw new NotEnoughMoneyInAccount("На аккауте не хватает денег");
        }
    }
}
