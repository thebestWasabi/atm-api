package ru.wasabi.my_atm.service;

import ru.wasabi.my_atm.entity.account.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountById(Long id);

    Account getAccountByEmail(String email);

    List<Account> getAllAccounts();

    Account update(Account account, String email);

    void deleteAccountById(Long id);

    BigDecimal getBalance(Long id);

    Long getId(Long id);

}
