package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.dto.UserWithTimeLogsDto;
import com.art.timelymanagementsystem.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    UserWithTimeLogsDto toUserWithTimeLogsDto(User user);

}
