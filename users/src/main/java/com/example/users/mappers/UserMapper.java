package com.example.users.mappers;

import com.example.users.models.dtos.UserResponse;
import com.example.users.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse mapToUserResponseDTO(User user);
}