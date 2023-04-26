package ru.wasabi.my_atm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.repository.AccountRepository;
import ru.wasabi.my_atm.service.exception.AccountNotFoundException;
import ru.wasabi.my_atm.service.exception.NotEnoughMoneyInAccount;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;

    public TransactionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public void takeMoney(Long id, BigDecimal amount) {
        Account user = findById(id);
        if (amount.compareTo(user.getAmount()) <= 0) {
            BigDecimal userNewBalance = user.getAmount().subtract(amount);
            accountRepository.changeBalance(id, userNewBalance);
        } else {
            throw new NotEnoughMoneyInAccount();
        }
    }

    @Transactional
    public void putMoney(Long id, BigDecimal amount) {
        Account user = findById(id);
        BigDecimal userNewBalance = user.getAmount().add(amount);
        accountRepository.changeBalance(id, userNewBalance);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
