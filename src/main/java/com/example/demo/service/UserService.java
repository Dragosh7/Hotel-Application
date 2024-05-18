package com.example.demo.service;

import com.example.demo.dto.CredentialsDto;
import com.example.demo.dto.RegisterRoles;
import com.example.demo.dto.RegistrationRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.mapper.UserMapper;
import com.example.demo.myPasswordEncoder.MyPasswordEncoder;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserDto createUser(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(registrationRequest.getPassword())
                .role((UserRole.USER))
                .build();
        user.setPassword(MyPasswordEncoder.encode(user.getPassword()));
        return userMapper.userEntityToDto(userRepository.save(user));
    }

    public UserDto login(CredentialsDto dto){
        Optional<User> owner = userRepository.findByUsername(dto.username());
        if (owner.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user = owner.orElse(null);
        if(user.getPassword().equals(MyPasswordEncoder.encode(dto.password()))) {
            return userMapper.userEntityToDto(owner.orElse(null));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    public UserDto registerUserRole(RegisterRoles registration) {
        String role = registration.getRole();
        String secretKey = registration.getSecretKey();
        if( role.isEmpty()  )
        {
            role="USER";
        }
        else{
            try{
                UserRole.valueOf(role.toUpperCase());
            }
            catch (IllegalArgumentException e)
            {
                role="USER";
            }
        }
        if(secretKey.equals("root")) {
            RegistrationRequest registrationRequest = registration.getRegistrationRequest();
            User user = User.builder()
                    .username(registrationRequest.getUsername())
                    .password(registrationRequest.getPassword())
                    .role(UserRole.valueOf(role.toUpperCase()))
                    .build();
            user.setPassword(MyPasswordEncoder.encode(user.getPassword()));
            return userMapper.userEntityToDto(userRepository.save(user));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to add this role: "+ role);
    }
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existingUser.setUsername(updatedUser.getUsername());
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(MyPasswordEncoder.encode(updatedUser.getPassword()));
        }
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userRepository.delete(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
