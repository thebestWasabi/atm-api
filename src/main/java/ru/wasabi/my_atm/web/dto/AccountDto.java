package ru.wasabi.my_atm.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.wasabi.my_atm.web.validation.OnCreate;
import ru.wasabi.my_atm.web.validation.OnUpdate;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long id;

    @NotNull(message = "email не может быть null", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "email введен не корректно", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    private BigDecimal balance;

}
