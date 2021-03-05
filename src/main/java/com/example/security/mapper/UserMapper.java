package com.example.security.mapper;


import com.example.security.dto.UserDTO;
import com.example.security.entity.User;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO convertToDTO(User user) {
        final UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
