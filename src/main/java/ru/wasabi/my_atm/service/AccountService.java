package ru.wasabi.my_atm.service;

import ru.wasabi.my_atm.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account getAccountByEmail(String email);

    Account createAccount(Account account);

    Account updateAccount(Long accountId, Account account);

    void deleteAccount(Long id);

    void changeBalance(Long accountId, BigDecimal balance);
}
