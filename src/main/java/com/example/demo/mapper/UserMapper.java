package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public UserDto userEntityToDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public User userDtoToEntity(UserDto userDto, String password){
        return User.builder()
                .username(userDto.getUsername())
                .password(password)
                .role((userDto.getRole()))
                .build();
    }

}
