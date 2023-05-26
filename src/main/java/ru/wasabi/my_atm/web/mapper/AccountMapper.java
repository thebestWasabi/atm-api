package ru.wasabi.my_atm.web.mapper;

import org.mapstruct.Mapper;
import ru.wasabi.my_atm.entity.Account;
import ru.wasabi.my_atm.web.dto.AccountDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);

    List<AccountDto> toDto(List<Account> accounts);

    Account toEntity(AccountDto dto);
}
