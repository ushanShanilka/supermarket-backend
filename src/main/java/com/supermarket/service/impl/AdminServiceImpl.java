package com.supermarket.service.impl;

import com.supermarket.dto.AdminRegisterDTO;
import com.supermarket.dto.AuthenticationRequestDTO;
import com.supermarket.dto.LoginResponse;
import com.supermarket.entity.*;
import com.supermarket.excepton.EntryDuplicateException;
import com.supermarket.repository.*;
import com.supermarket.service.AdminService;
import com.supermarket.util.AdminTypeId;
import com.supermarket.util.JwtUtil;
import com.supermarket.util.StatusId;
import com.supermarket.util.TokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionalException;
import java.util.Date;
import java.util.Objects;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    StatusRepo statusRepo;
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    AdminTypeRepo adminTypeRepo;
    @Autowired
    AdminLoginCredentialRepo adminLoginCredentialRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AdminPasswordRepo adminPasswordRepo;
    @Autowired
    TokenChecker tokenChecker;

    @Override
    public LoginResponse adminLogin (AuthenticationRequestDTO dto) {
        LoginResponse response = new LoginResponse();

        Status status = statusRepo.findById(StatusId.ACTIVE);
        AdminLoginCredential adminLogin = adminLoginCredentialRepo.findByUserNameAndStatusId(dto.getUsername(), status);

        if (Objects.equals(adminLogin, null)) {
            response.setMessage("not found");
            response.setCode(404);
            return response;
        }

        boolean exists = adminRepo.existsById(adminLogin.getAdminId().getId());
        if (!exists){
            response.setMessage("not found");
            response.setCode(404);
            return response;
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails, "ADMIN");

        adminLogin.setActiveJwt(jwt);
        adminLogin.setUpdatedAt(new Date());
        adminLoginCredentialRepo.save(adminLogin);

        response.setCode(200);
        response.setJwt(jwt);
        response.setMessage("success");

        return response;
    }

    @Transactional
    @Override
    public String adminSingUp (String AUTHORIZATION_HEADER, AdminRegisterDTO dto) {
        Status status = statusRepo.findById(StatusId.ACTIVE);
        Date date = new Date();

        AdminLoginCredential login = tokenChecker.adminTokenChecker(AUTHORIZATION_HEADER);
        if (!Objects.equals(login,null)){

            AdminLoginCredential userLogin = adminLoginCredentialRepo.findByUserNameAndStatusId(login.getUserName(), status);
            if (Objects.equals(userLogin, null)) {
                throw new BadCredentialsException("Invalid Passenger");
            }

            Admin byEmailAndStatusId = adminRepo.findByEmailAndStatusId(dto.getEmail(), status);
            if (!Objects.equals(byEmailAndStatusId,null)){
                throw new EntryDuplicateException("Email already exist");
            }

            if (!Objects.equals(userLogin.getAdminId().getAdminTypeId().getId(), AdminTypeId.SUPERADMIN)) {
                throw new BadCredentialsException("Invalid Permission");
            }

            if (!Objects.equals(dto.getEmail(), dto.getConfirmEmail())){
                throw new BadCredentialsException("Email and Confirm Email don't match");
            }

            if (!Objects.equals(dto.getPassword(), dto.getConfirmPassword())){
                throw new BadCredentialsException("Password and Confirm Password don't match");
            }

            AdminType adminType = adminTypeRepo.findById(AdminTypeId.SUPERADMIN);

            Admin admin = new Admin();
            admin.setFirstName(dto.getFirstName());
            admin.setLastName(dto.getLastName());
            admin.setEmail(dto.getEmail());
            admin.setCountryCode(dto.getCountryCode());
            admin.setPhoneNumber(dto.getPhoneNumber());
            admin.setAdminTypeId(adminType);
            admin.setStatusId(status);
            admin.setCreatedAt(date);
            Admin adminSave = adminRepo.save(admin);
            if (!Objects.equals(adminSave.getId(),null)){
                AdminLoginCredential adminLoginCredential = new AdminLoginCredential();
                adminLoginCredential.setAdminId(admin);
                adminLoginCredential.setUserName(dto.getEmail());
                adminLoginCredential.setStatusId(status);
                adminLoginCredential.setCreatedAt(date);
                adminLoginCredential.setUpdatedAt(date);
                AdminLoginCredential loginCredential = adminLoginCredentialRepo.save(adminLoginCredential);

                if (!Objects.equals(loginCredential.getId(),null)){
                    AdminPassword password = new AdminPassword();
                    password.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
                    password.setAdminLoginCredentialId(adminLoginCredential);
                    password.setStatusId(status);
                    password.setCreatedAt(date);
                    adminPasswordRepo.save(password);
                    return "Success";
                }else {
                    throw new TransactionalException("something went wrong",null);
                }
            }else {
                throw new TransactionalException("something went wrong",null);
            }
        }
        throw new BadCredentialsException("Invalid admin");
    }

}
