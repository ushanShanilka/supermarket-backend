package com.supermarket.service;



import com.supermarket.dto.*;

public interface AdminService {
    LoginResponse adminLogin(AuthenticationRequestDTO dto);
    String adminSingUp(String AUTHORIZATION_HEADER, AdminRegisterDTO dto);
}
