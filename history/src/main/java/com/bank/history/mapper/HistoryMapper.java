package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.History;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryMapper {
    @Mapping(target = "id",ignore = true)
    History toEntity(HistoryDto historyDto);

    HistoryDto toHistoryDto(History history);

    List<History> toEntityList(List<HistoryDto> historyDtoList);

    List<HistoryDto> toDtoList(List<History> historyEntityList);
}
