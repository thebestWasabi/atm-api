package ru.wasabi.my_atm.service;

import java.math.BigDecimal;

public interface TransactionService {

    void takeMoney(Long id, BigDecimal amount);

    void putMoney(Long id, BigDecimal amount);

    void transferMoney(Long senderId, Long reviverId, BigDecimal amount);
}
