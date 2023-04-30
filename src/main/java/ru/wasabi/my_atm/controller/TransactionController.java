package ru.wasabi.my_atm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.wasabi.my_atm.dto.TransactionRequest;
import ru.wasabi.my_atm.service.TransactionService;
import ru.wasabi.my_atm.service.exception.AccountNotFoundException;
import ru.wasabi.my_atm.service.exception.NotEnoughMoneyInAccount;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PatchMapping(path = "/take", consumes = "application/json", produces = "application/json")
    public void takeMoney(@RequestBody TransactionRequest request) {
        transactionService.takeMoney(
                request.getAccountId(),
                request.getAmount());
    }

    @PatchMapping(path = "/put", consumes = "application/json", produces = "application/json")
    public void putMoney(@RequestBody TransactionRequest request) {
        transactionService.putMoney(
                request.getAccountId(),
                request.getAmount());
    }

    @PatchMapping(path = "/transfer", consumes = "application/json", produces = "application/json")
    public void TransferMoney(@RequestBody TransactionRequest request) {
        transactionService.transferMoney(
                request.getSenderAccountId(),
                request.getReceiverAccountId(),
                request.getAmount()
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> accountNotFoundExceptionHandler(AccountNotFoundException e) {
        return new ResponseEntity<>("Аккаунт с таким id не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> notEnoughMoney(NotEnoughMoneyInAccount e) {
        return new ResponseEntity<>("На балансе не достаточно средств", HttpStatus.OK);
    }
}
