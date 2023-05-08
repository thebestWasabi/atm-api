package ru.wasabi.my_atm.service;

import ru.wasabi.my_atm.entity.account.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account getAccountByEmail(String email);

    Account createAccount(Account account);

    Account update(Account account, String email);

    void deleteAccountById(Long id);

    void changeBalance(Long accountId, BigDecimal balance);

    Long getId(Long id);

    BigDecimal getBalance(Long id);

}
