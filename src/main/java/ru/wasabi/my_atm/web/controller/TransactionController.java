package ru.wasabi.my_atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wasabi.my_atm.service.TransactionService;
import ru.wasabi.my_atm.web.dto.TransactionParticipantDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PutMapping(path = "/take", consumes = "application/json", produces = "application/json")
    public void takeMoney(@RequestBody TransactionParticipantDto transactionParticipantDto) {
        transactionService.takeMoney(
                transactionParticipantDto.getAccountId(),
                transactionParticipantDto.getAmount()
        );
    }

    @PutMapping(path = "/put", consumes = "application/json", produces = "application/json")
    public void putMoney(@RequestBody TransactionParticipantDto transactionParticipantDto) {
        transactionService.putMoney(
                transactionParticipantDto.getAccountId(),
                transactionParticipantDto.getAmount()
        );
    }

    @PutMapping(path = "/transfer", consumes = "application/json", produces = "application/json")
    public void transferMoney(@RequestBody TransactionParticipantDto transaction) {
        transactionService.transferMoney(
                transaction.getSenderAccountId(),
                transaction.getReceiverAccountId(),
                transaction.getAmount()
        );
    }
}
