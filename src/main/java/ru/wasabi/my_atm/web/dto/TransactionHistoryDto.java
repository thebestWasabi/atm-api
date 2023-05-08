package ru.wasabi.my_atm.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.wasabi.my_atm.entity.account.Account;
import ru.wasabi.my_atm.entity.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionHistoryDto {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Account account;

    private BigDecimal amount;

    private TransactionType transactionType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;
}
