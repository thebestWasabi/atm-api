package ru.wasabi.my_atm.web.mapper;

import org.mapstruct.Mapper;
import ru.wasabi.my_atm.entity.transaction.TransactionHistory;
import ru.wasabi.my_atm.web.dto.TransactionHistoryDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionHistoryMapper {

    TransactionHistoryDto toDto(TransactionHistory transactionHistory);

    List<TransactionHistoryDto> toDto(List<TransactionHistory> transactionHistoryList);

    TransactionHistory toEntity(TransactionHistoryDto transactionHistoryDto);
}
