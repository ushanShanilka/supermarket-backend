package com.supermarket.webSecurity;


import com.supermarket.entity.*;
import com.supermarket.repository.*;
import com.supermarket.util.AdminTypeId;
import com.supermarket.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserLoginCredentialRepo userLoginCredentialRepo;
    @Autowired
    AdminLoginCredentialRepo adminLoginCredentialRepo;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    PasswordRepo passwordRepo;
    @Autowired
    AdminPasswordRepo adminPasswordRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Status status = statusRepo.findById(StatusId.ACTIVE);

        UserLoginCredential userLoginCredential = userLoginCredentialRepo.findByUserNameAndStatusId(username, status);

        String password = null;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (!Objects.equals(userLoginCredential, null)) {
                UserPassword pass = passwordRepo.findByUserLoginCredentialIdAndStatusId(userLoginCredential, status);

                if (Objects.equals(pass, null)) {
                    throw new BadCredentialsException("Invalid Credentials");
                }

                authorities.add(new SimpleGrantedAuthority("USER"));

                password = pass.getPassword();

        } else {

            AdminLoginCredential adminLoginCredential = adminLoginCredentialRepo.findByUserNameAndStatusId(username, status);

            if (Objects.equals(adminLoginCredential.getAdminId().getAdminTypeId().getId(), AdminTypeId.ADMIN)) {
                AdminPassword pass = adminPasswordRepo.findByAdminLoginCredentialIdAndStatusId(adminLoginCredential, status);

                if (Objects.equals(pass, null)) {
                    throw new BadCredentialsException("Invalid Credentials");
                }

                authorities.add(new SimpleGrantedAuthority("ADMIN"));

                password = pass.getPassword();
            }

            if (Objects.equals(adminLoginCredential.getAdminId().getAdminTypeId().getId(), AdminTypeId.SUPERADMIN)) {
                AdminPassword pass = adminPasswordRepo.findByAdminLoginCredentialIdAndStatusId(adminLoginCredential, status);

                if (Objects.equals(pass, null)) {
                    throw new BadCredentialsException("Invalid Credentials");
                }

                authorities.add(new SimpleGrantedAuthority("SUPERADMIN"));

                password = pass.getPassword();
            }

        }

        return new User(username, password, authorities);
    }
}
