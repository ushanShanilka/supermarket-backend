package com.supermarket.util;

import com.supermarket.entity.AdminLoginCredential;
import com.supermarket.entity.Status;
import com.supermarket.entity.UserLoginCredential;
import com.supermarket.repository.AdminLoginCredentialRepo;
import com.supermarket.repository.StatusRepo;
import com.supermarket.repository.UserLoginCredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenChecker {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    AdminLoginCredentialRepo adminLoginCredentialRepo;
    @Autowired
    UserLoginCredentialRepo userLoginCredentialRepo;

    public AdminLoginCredential adminTokenChecker(String AUTHORIZATION_HEADER){
        String jwt = null;
        String username = null;

        if (!Objects.equals(AUTHORIZATION_HEADER, null) && AUTHORIZATION_HEADER.startsWith("Bearer ")) {
            jwt = AUTHORIZATION_HEADER.substring(7);
            username = jwtUtil.extractUserName(jwt);

            if (username.isEmpty())
                throw new BadCredentialsException("Invalid Attempt");
        } else {
            throw new BadCredentialsException("Invalid Attempt");
        }

        Status active = statusRepo.findById(StatusId.ACTIVE);

        return adminLoginCredentialRepo.findByUserNameAndStatusId(username, active);
    }

    public UserLoginCredential userTokenChecker(String AUTHORIZATION_HEADER){
        String jwt = null;
        String userName = null;

        if (!Objects.equals(AUTHORIZATION_HEADER, null) && AUTHORIZATION_HEADER.startsWith("Bearer ")) {
            jwt = AUTHORIZATION_HEADER.substring(7);
            userName = jwtUtil.extractPhoneNumber(jwt);

            if (userName.isEmpty())
                throw new BadCredentialsException("Invalid Attempt");
        } else {
            throw new BadCredentialsException("Invalid Attempt");
        }

        Status active = statusRepo.findById(StatusId.ACTIVE);

        return userLoginCredentialRepo.findByUserNameAndStatusId(userName, active);
    }
}
