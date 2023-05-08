package ru.wasabi.my_atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wasabi.my_atm.web.dto.TransactionRequest;
import ru.wasabi.my_atm.service.impl.TransactionServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @PutMapping(path = "/take", consumes = "application/json", produces = "application/json")
    public void takeMoney(@RequestBody TransactionRequest request) {
        transactionService.takeMoney(
                request.getMyAccountId(), request.getAmount()
        );
    }

    @PutMapping(path = "/put", consumes = "application/json", produces = "application/json")
    public void putMoney(@RequestBody TransactionRequest request) {
        transactionService.putMoney(
                request.getMyAccountId(), request.getAmount()
        );
    }

    @PutMapping(path = "/transfer", consumes = "application/json", produces = "application/json")
    public void transferMoney(@RequestBody TransactionRequest request) {
        transactionService.transferMoney(
                request.getSenderAccountId(),
                request.getReceiverAccountId(),
                request.getAmount()
        );
    }

}
