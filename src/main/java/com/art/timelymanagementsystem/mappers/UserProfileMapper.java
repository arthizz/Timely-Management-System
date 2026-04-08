package com.art.timelymanagementsystem.mappers;

import com.art.timelymanagementsystem.dto.UserProfileDto;
import com.art.timelymanagementsystem.entities.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileDto toDto(UserProfile userProfile);

}
