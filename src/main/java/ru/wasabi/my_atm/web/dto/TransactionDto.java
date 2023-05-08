package ru.wasabi.my_atm.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {

    private Long senderAccountId;
    private Long receiverAccountId;
    private Long accountId;
    private BigDecimal amount;
}
