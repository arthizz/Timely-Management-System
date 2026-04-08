package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.TimeLogPauseDto;
import com.art.timelymanagementsystem.entities.TimeLogPause;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeLogPauseMapper {

    TimeLogPauseDto toDto(TimeLogPause timeLogPause);

}
