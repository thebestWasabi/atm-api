package ru.wasabi.my_atm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wasabi.my_atm.entity.account.Account;
import ru.wasabi.my_atm.entity.exception.ResourceNotFoundException;
import ru.wasabi.my_atm.entity.exception.SaveToDataBaseException;
import ru.wasabi.my_atm.repository.AccountRepository;
import ru.wasabi.my_atm.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Аккаунт с таким id не найден: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Аккаунт с такой почтой не найден: " + email));
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        if (account.getId() != null) {
            throw new IllegalStateException("У нового аккаунта не может быть id");
        }
        if (account.getBalance() != null) {
            throw new IllegalStateException("Нельзя задать стартовый баланс вручную");
        }
        account.setBalance(BigDecimal.valueOf(0));
        return accountRepository.save(account);
    }


    @Override
    @Transactional
    public Account updateAccount(Long accountId, Account account) {
        Account existingAccount = getAccountById(accountId);
        existingAccount.setEmail(account.getEmail());
        try {
            return accountRepository.save(existingAccount);
        } catch (DataIntegrityViolationException e) {
            throw new SaveToDataBaseException("Не удалось сохранить объект в базу данных с id:" + accountId);
        }
    }


    @Transactional
    public void changeBalance(Long accountId, BigDecimal balance) {
        accountRepository.changeBalance(accountId, balance);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.deleteById(account.getId());
    }
}
