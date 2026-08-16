package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.dto.UserWithTimeLogsDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    UserWithTimeLogsDto toUserWithTimeLogsDto(User user);

    User toEntity(UserRequest request);

    void update(UserRequest userRequest, @MappingTarget User user);
}
