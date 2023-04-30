package ru.wasabi.my_atm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.service.TransactionService;
import ru.wasabi.my_atm.service.exception.AccountNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return transactionService.getAllAccounts();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Account getAccountById(@PathVariable(value = "id") Long id) {
        return transactionService.findById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> accountNotFoundExceptionHandler(AccountNotFoundException e) {
        return new ResponseEntity<>("Аккаунт с таким id не найден", HttpStatus.NOT_FOUND);
    }
}
