package ru.wasabi.my_atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.wasabi.my_atm.entity.account.Account;
import ru.wasabi.my_atm.service.AccountService;
import ru.wasabi.my_atm.web.dto.AccountDto;
import ru.wasabi.my_atm.web.mapper.AccountMapper;
import ru.wasabi.my_atm.web.validation.OnUpdate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;


    @GetMapping
    public List<AccountDto> getAllAccounts() {
        List<Account> allAccounts = accountService.getAllAccounts();
        return accountMapper.toDto(allAccounts);
    }


    @GetMapping(path = "/{id}")
    public AccountDto getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return accountMapper.toDto(account);
    }


    @PostMapping
    public AccountDto createAccount(@Validated @RequestBody AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        Account createdAccount = accountService.createAccount(account);
        return accountMapper.toDto(createdAccount);
    }


    @PutMapping("/{accountId}")
    public AccountDto updateAccount(@PathVariable Long accountId,
                                    @Validated(OnUpdate.class) @RequestBody AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        Account updatedAccount = accountService.updateAccount(accountId, account);
        return accountMapper.toDto(updatedAccount);
    }


    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
