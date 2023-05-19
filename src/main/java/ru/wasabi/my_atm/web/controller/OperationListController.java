package ru.wasabi.my_atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.wasabi.my_atm.entity.exception.ResourceNotFoundException;
import ru.wasabi.my_atm.entity.transaction.OperationList;
import ru.wasabi.my_atm.service.AccountService;
import ru.wasabi.my_atm.service.OperationListService;
import ru.wasabi.my_atm.web.dto.OperationListDto;
import ru.wasabi.my_atm.web.mapper.OperationListMapper;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationListController {

    private final OperationListService operationListService;
    private final OperationListMapper operationListMapper;
    private final AccountService accountService;


    @GetMapping
    public List<OperationListDto> getOperationListByAccountIdAndCreatedAtBetween(
            @RequestParam Long accountId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {

        List<OperationList> operationList;
        accountService.getAccountById(accountId);

        if (startDate != null && endDate != null) {
            operationList = operationListService.getOperationListSort(accountId, startDate, endDate);
        } else {
            operationList = operationListService.getAccountTransactions(accountId);
        }

        if (operationList.isEmpty()) {
            throw new ResourceNotFoundException("На вашем аккаунте нет транзакций");
        }
        return operationListMapper.toDto(operationList);
    }
}
