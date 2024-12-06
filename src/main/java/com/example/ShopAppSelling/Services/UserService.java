package com.example.ShopAppSelling.Services;

import org.springframework.stereotype.Service;

import com.example.ShopAppSelling.DTO.UserDTO;
import com.example.ShopAppSelling.DTO.UserLoginDTO;
import com.example.ShopAppSelling.Models.Role;
import com.example.ShopAppSelling.Models.User;
import com.example.ShopAppSelling.Repositories.RoleRepository;
import com.example.ShopAppSelling.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Phone number already exists");
        }

        User newUser = User.builder().fullName(userDTO.getFullName()).address(userDTO.getAddress())
                .phoneNumber(userDTO.getPhoneNumber()).password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth()).facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId()).build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        newUser.setRole(role);

        if (userDTO.getFacebookAccountId() == 0 || userDTO.getGoogleAccountId() == 0) {
            // String password = newUser.getPassword();
            // String encodedPassword = passwordEncoder.encode(password);
            // newUser.setPassword(encodedPassword);
        }

        return userRepository.save(newUser);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        return null;
    }

}
