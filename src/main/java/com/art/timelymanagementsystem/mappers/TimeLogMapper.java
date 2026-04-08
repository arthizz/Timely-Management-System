package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.entities.TimeLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeLogMapper {

    TimeLogDto toDto(TimeLog timeLog);

}
