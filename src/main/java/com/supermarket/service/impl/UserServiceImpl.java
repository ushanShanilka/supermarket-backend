package com.supermarket.service.impl;

import com.supermarket.dto.AuthenticationRequestDTO;
import com.supermarket.dto.LoginResponse;
import com.supermarket.dto.UserDTO;
import com.supermarket.entity.Status;
import com.supermarket.entity.User;
import com.supermarket.entity.UserLoginCredential;
import com.supermarket.entity.UserPassword;
import com.supermarket.excepton.EntryDuplicateException;
import com.supermarket.excepton.EntryNotFoundException;
import com.supermarket.excepton.TransactionException;
import com.supermarket.repository.PasswordRepo;
import com.supermarket.repository.StatusRepo;
import com.supermarket.repository.UserLoginCredentialRepo;
import com.supermarket.repository.UserRepo;
import com.supermarket.service.UserService;
import com.supermarket.util.JwtUtil;
import com.supermarket.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    UserLoginCredentialRepo userLoginCredentialRepo;
    @Autowired
    PasswordRepo passwordRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;

    @Transactional
    @Override
    public String saveUser (UserDTO dto) {
        Status active = statusRepo.findById(StatusId.ACTIVE);
        boolean b = userRepo.existsByEmailAndStatusId(dto.getEmail(), active);
        if (!b){

            if (!Objects.equals(dto.getEmail(), dto.getConfirmEmail())){
                throw new ValidationException("Email and Confirm Email don't match");
            }
            if (!Objects.equals(dto.getPassword(), dto.getConfirmPassword())){
                throw new ValidationException("Password and Confirm Password don't match");
            }

            Date date = new Date();

            User user = new User(
                    null,
                    date,
                    date,
                    dto.getUserName(),
                    dto.getEmail(),
                    active
            );
            User saveUser = userRepo.save(user);

            if (!Objects.equals(saveUser.getId(), null)){
                UserLoginCredential userLoginCredential = new UserLoginCredential(
                        null,
                        date,
                        date,
                        saveUser.getEmail(),
                        null,
                        active,
                        saveUser
                );

                UserLoginCredential saveLogin = userLoginCredentialRepo.save(userLoginCredential);

                if (!Objects.equals(userLoginCredential.getId(), null)){
                    UserPassword userPassword = new UserPassword(
                            null,
                            BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)),
                            date,
                            date,
                            active,
                            saveLogin
                    );
                    passwordRepo.save(userPassword);
                    return "success";
                }
                throw new TransactionException("something went wrong!");
            }
            throw new TransactionException("something went wrong!");
        }
        throw new EntryDuplicateException("email already exist");
    }

    @Override
    public Map<String, Object> userLogin (AuthenticationRequestDTO dto) {
        Status active = statusRepo.findById(StatusId.ACTIVE);
        UserLoginCredential userLogin = userLoginCredentialRepo.findByUserNameAndStatusId(dto.getUsername(), active);

        if (Objects.equals(userLogin, null)) {
            throw new EntryNotFoundException("not found");
        }

        boolean b = userRepo.existsByIdAndStatusId(userLogin.getUserId().getId(), active);
        if (!b){
            throw new EntryNotFoundException("user not found");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails, "USER");

        Date date = new Date();

        userLogin.setActiveJwt(jwt);
        userLogin.setUpdatedAt(date);
        userLoginCredentialRepo.save(userLogin);

        Map<String, Object> obj = new HashMap<>();

        obj.put("jwt", jwt);
        obj.put("username", userLogin.getUserId().getUserName());
        obj.put("email", userLogin.getUserId().getEmail());

        return obj;
    }

    @Override
    public String deleteUser (String AUTHORIZATION_HEADER) {
        return null;
    }

    @Override
    public LoginResponse UserLogin (String phoneNumber, int otp) {
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo (String AUTHORIZATION_HEADER) {
        return null;
    }

    @Override
    public String createJWT (String phoneNumber, String language, Status status) {
        return null;
    }
}
