package ru.wasabi.my_atm.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    private long senderAccountId;
    private long receiverAccountId;
    private long myAccountId;
    private BigDecimal amount;
}
