package ru.wasabi.my_atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wasabi.my_atm.service.TransactionService;
import ru.wasabi.my_atm.web.dto.TransactionDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PutMapping(path = "/take", consumes = "application/json", produces = "application/json")
    public void takeMoney(@RequestBody TransactionDto transactionDto) {
        transactionService.takeMoney(
                transactionDto.getAccountId(),
                transactionDto.getAmount()
        );
    }

    @PutMapping(path = "/put", consumes = "application/json", produces = "application/json")
    public void putMoney(@RequestBody TransactionDto transactionDto) {
        transactionService.putMoney(
                transactionDto.getAccountId(),
                transactionDto.getAmount()
        );
    }

    @PutMapping(path = "/transfer", consumes = "application/json", produces = "application/json")
    public void transferMoney(@RequestBody TransactionDto transactionDto) {
        transactionService.transferMoney(
                transactionDto.getSenderAccountId(),
                transactionDto.getReceiverAccountId(),
                transactionDto.getAmount()
        );
    }

}
