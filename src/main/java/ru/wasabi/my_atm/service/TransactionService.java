package ru.wasabi.my_atm.service;

import ru.wasabi.my_atm.entity.account.Account;

import java.math.BigDecimal;

public interface TransactionService {

    Account getById(Long id);

    void takeMoney(Long id, BigDecimal amount);

    void putMoney(Long id, BigDecimal amount);

    void transferMoney(Long senderId, Long reviverId, BigDecimal amount);
}
