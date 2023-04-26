package ru.wasabi.my_atm.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    private long senderAccountId;
    private long receiverAccountId;
    private Long accountId;
    private BigDecimal amount;
}
