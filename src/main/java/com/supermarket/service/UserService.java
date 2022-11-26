package com.supermarket.service;

import com.supermarket.dto.AuthenticationRequestDTO;
import com.supermarket.dto.LoginResponse;
import com.supermarket.dto.UserDTO;
import com.supermarket.entity.Status;

import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 9/25/2022
 **/
public interface UserService {
    String saveUser (UserDTO dto);
    Map<String, Object> userLogin(AuthenticationRequestDTO dto);
    String deleteUser (String AUTHORIZATION_HEADER);
    LoginResponse UserLogin (String phoneNumber, int otp);
    Map<String, Object> getUserInfo(String AUTHORIZATION_HEADER);
    String createJWT(String phoneNumber,String language, Status status);
}
