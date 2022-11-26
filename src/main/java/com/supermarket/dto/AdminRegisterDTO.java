package com.supermarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminRegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;
    private String countryCode;
    private String phoneNumber;
}
