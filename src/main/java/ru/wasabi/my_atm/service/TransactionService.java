package ru.wasabi.my_atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.repository.AccountRepository;
import ru.wasabi.my_atm.repository.BankingOperationRepository;
import ru.wasabi.my_atm.service.exception.AccountNotFoundException;
import ru.wasabi.my_atm.service.exception.NotEnoughMoneyInAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final BankingOperationRepository bankingOperationRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository,
                              BankingOperationRepository bankingOperationRepository) {
        this.accountRepository = accountRepository;
        this.bankingOperationRepository = bankingOperationRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public void takeMoney(Long id, BigDecimal amount) {
        Account user = findById(id);
        LocalDateTime date = LocalDateTime.now();

        if (amount.compareTo(user.getBalance()) <= 0) {
            BigDecimal userNewBalance = user.getBalance().subtract(amount);
            accountRepository.changeBalance(id, userNewBalance);
            bankingOperationRepository.saveOperation(id, amount, date);
        } else {
            throw new NotEnoughMoneyInAccount();
        }
    }

    @Transactional
    public void putMoney(Long id, BigDecimal amount) {
        Account user = findById(id);
        LocalDateTime date = LocalDateTime.now();
        BigDecimal userNewBalance = user.getBalance().add(amount);
        accountRepository.changeBalance(id, userNewBalance);
        bankingOperationRepository.saveOperation(id, amount, date);
    }

    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        Account sender = findById(senderId);
        Account receiver = findById(receiverId);

        if (amount.compareTo(sender.getBalance()) <= 0) {
            BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
            BigDecimal receiverNewBalance = receiver.getBalance().add(amount);
            accountRepository.changeBalance(senderId, senderNewBalance);
            accountRepository.changeBalance(receiverId, receiverNewBalance);
        } else {
            throw new NotEnoughMoneyInAccount();
        }
    }
}
