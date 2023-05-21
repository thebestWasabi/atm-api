package ru.wasabi.my_atm.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.wasabi.my_atm.entity.account.Account;
import ru.wasabi.my_atm.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private static final Long ID = 1L;
    private static final String EMAIL_1 = "wasabi@yandex.ru";
    private static final String EMAIL_2 = "maxdadude@yandex.ru";

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    @DisplayName("Получить все аккаунты")
    void getAllAccounts_shouldCallRepository() {
        // given
        List<Account> accounts = new ArrayList<>();
        accounts.add(Account.builder().email(EMAIL_1).balance(BigDecimal.valueOf(1000)).build());
        accounts.add(Account.builder().email(EMAIL_2).balance(BigDecimal.valueOf(1100)).build());
        when(accountRepository.findAll()).thenReturn(accounts);

        // when
        List<Account> result = accountService.getAllAccounts();

        // then
        assertNotNull(result);
        assertEquals(EMAIL_1, result.get(0).getEmail());
        assertEquals(BigDecimal.valueOf(1000), result.get(0).getBalance());
        assertEquals(EMAIL_2, result.get(1).getEmail());
        assertEquals(BigDecimal.valueOf(1100), result.get(1).getBalance());

        verify(accountRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получить аккаунт по id")
    void getAccountById_shouldCallRepository() {
//        Account account = Account.builder()   // можно запустить тест через builder
//                .id(1L)
//                .build();

        // given
        final Account account = mock(Account.class);
        when(accountRepository.findById(ID)).thenReturn(Optional.of(account));

        // when
        Account result = accountService.getAccountById(ID);

        // then
        assertNotNull(result);
        assertEquals(account, result);
        verify(accountRepository, times(1)).findById(ID);
    }


    @Test
    @DisplayName("Получить аккаунт по email")
    void getAccountByEmail_shouldCallRepository() {
        // given
        Account account = mock(Account.class);
        when(accountRepository.findByEmail(EMAIL_1)).thenReturn(Optional.of(account));

        // when
        Account result = accountService.getAccountByEmail(EMAIL_1);

        // then
        assertNotNull(result);
        assertEquals(account, result);
        verify(accountRepository, times(1)).findByEmail(EMAIL_1);
    }


    @Test
    @DisplayName("Создать новый аккаунт")
    void createNewAccount_shouldCallRepository() {
        // given
        Account account = Account.builder().email(EMAIL_1).build();
        when(accountRepository.save(account)).thenReturn(account);

        // when
        Account result = accountService.createAccount(account);

        // then
        assertNotNull(result);
        assertEquals(EMAIL_1, result.getEmail());
        assertEquals(BigDecimal.valueOf(0), result.getBalance());
        assertEquals(account, result);

        verify(accountRepository, times(1)).save(account);
    }

}