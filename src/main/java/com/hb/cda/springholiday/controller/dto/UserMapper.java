package com.hb.cda.springholiday.controller.dto;

import com.hb.cda.springholiday.controller.dto.user.UserDto;
import com.hb.cda.springholiday.controller.dto.user.UserRegisterDto;
import com.hb.cda.springholiday.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User convertToUser(UserRegisterDto dto);
    UserDto convertToUserDto(User user);
}
