package com.smartgro.smartgro.mapper;

import com.smartgro.smartgro.dto.UserDto;
import com.smartgro.smartgro.entity.User;

public class UserMapper {
    // Convert Entity -> DTO
    public static UserDto toDto(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPasswordHash(user.getPasswordHash());
        dto.setEmail(user.getEmail());
        return dto;
    }

    // Convert DTO -> Entity
    public static User toEntity(UserDto dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId()); // optional if auto-generated
        user.setName(dto.getName());
        user.setPasswordHash(dto.getPasswordHash());
        user.setEmail(dto.getEmail());

        return user;
    }
}
