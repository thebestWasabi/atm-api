package ru.wasabi.my_atm.web.mapper;

import org.mapstruct.Mapper;
import ru.wasabi.my_atm.entity.OperationList;
import ru.wasabi.my_atm.web.dto.OperationListDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationListMapper {

    OperationListDto toDto(OperationList operationList);

    List<OperationListDto> toDto(List<OperationList> operationListList);

    OperationList toEntity(OperationListDto operationListDto);
}
