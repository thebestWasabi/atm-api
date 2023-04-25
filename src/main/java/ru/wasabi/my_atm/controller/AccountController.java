package ru.wasabi.my_atm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.wasabi.my_atm.dto.TransactionRequest;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.service.TransactionService;
import ru.wasabi.my_atm.service.exception.AccountNotFoundException;
import ru.wasabi.my_atm.service.exception.NotEnoughMoneyInAccount;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final TransactionService transactionService;

    public AccountController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return transactionService.getAllAccounts();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Account getAccountById(@PathVariable(value = "id") Long id) {
        return transactionService.findById(id);
    }

    @PutMapping("/take-money")
    public void takeMoney(@RequestBody TransactionRequest request) {
        transactionService.takeMoney(
                request.getAccountId(),
                request.getAmount());
    }

    @PutMapping("/put-money")
    public void putMoney(@RequestBody TransactionRequest request) {
        transactionService.putMoney(
                request.getAccountId(),
                request.getAmount());
    }

    @ExceptionHandler
    public ResponseEntity<String> accountNotFoundExceptionHandler(AccountNotFoundException e) {
        return new ResponseEntity<>("Аккаунт не найден", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> notEnoughMoney(NotEnoughMoneyInAccount e) {
        return new ResponseEntity<>("На балансе не достаточно средств", HttpStatus.OK);
    }
}
