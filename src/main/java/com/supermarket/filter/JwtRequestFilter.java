package com.supermarket.filter;

import com.supermarket.entity.AdminLoginCredential;
import com.supermarket.entity.Status;
import com.supermarket.entity.UserLoginCredential;
import com.supermarket.repository.AdminLoginCredentialRepo;
import com.supermarket.repository.StatusRepo;
import com.supermarket.repository.UserLoginCredentialRepo;
import com.supermarket.util.JwtUtil;
import com.supermarket.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserLoginCredentialRepo userLoginCredentialRepo;
    @Autowired
    AdminLoginCredentialRepo adminLoginCredentialRepo;
    @Autowired
    StatusRepo statusRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String AUTHORIZATION_HEADER = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        if (!Objects.equals(AUTHORIZATION_HEADER, null) && AUTHORIZATION_HEADER.startsWith("Bearer ")) {
            jwt = AUTHORIZATION_HEADER.substring(7);
            userName = jwtUtil.extractPhoneNumber(jwt);
        }
        if (!Objects.equals(userName, null) && Objects.equals(SecurityContextHolder.getContext().getAuthentication(), null)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            Status active = statusRepo.findById(StatusId.ACTIVE);

            if (!Objects.equals(userDetails, null)) {
                UserLoginCredential userLoginCredential = userLoginCredentialRepo.findByUserNameAndStatusId(userName, active);
                if (Objects.equals(userLoginCredential, null)) {
                    AdminLoginCredential adminLoginCredential = adminLoginCredentialRepo.findByUserNameAndStatusId(userName, active);
                    if (Objects.equals(adminLoginCredential, null)) {
                        throw new BadCredentialsException("User Not Found");
                    } else {
                        if (!adminLoginCredential.getActiveJwt().equals(jwt)) {
                            throw new BadCredentialsException("Invalid Token");
                        }
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    }
                } else {
                    if (!userLoginCredential.getActiveJwt().equals(jwt)) {
                        throw new BadCredentialsException("Invalid Token");
                    }
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }

            }
        }

        chain.doFilter(request, response);
    }
}
