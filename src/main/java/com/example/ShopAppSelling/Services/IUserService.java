package com.example.ShopAppSelling.Services;

import com.example.ShopAppSelling.DTO.UserDTO;
import com.example.ShopAppSelling.DTO.UserLoginDTO;
import com.example.ShopAppSelling.Models.User;

import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    String login(UserLoginDTO userLoginDTO) throws Exception;

}
