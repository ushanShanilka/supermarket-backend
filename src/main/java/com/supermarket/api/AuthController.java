package com.supermarket.api;

import com.supermarket.dto.AdminRegisterDTO;
import com.supermarket.dto.AuthenticationRequestDTO;
import com.supermarket.dto.LoginResponse;
import com.supermarket.dto.UserDTO;
import com.supermarket.service.AdminService;
import com.supermarket.service.UserService;
import com.supermarket.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/authenticates")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/admin")
    public ResponseEntity<StandardResponse> adminSingUp(@RequestHeader("Authorization") String AUTHORIZATION_HEADER,
                                                     @RequestBody AdminRegisterDTO dto){
        String s = adminService.adminSingUp(AUTHORIZATION_HEADER, dto);
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED
        );
    }

    @PostMapping(path = "/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody AuthenticationRequestDTO dto){
        LoginResponse response = adminService.adminLogin(dto);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/user")
    public ResponseEntity<StandardResponse> userSingUp(@RequestBody UserDTO dto){
        String s = userService.saveUser(dto);
        return new ResponseEntity<>(
                new StandardResponse(201,s,null),
                HttpStatus.CREATED);
    }

    @PostMapping(path = "/user/login")
    public ResponseEntity<StandardResponse> userLogin(@RequestBody AuthenticationRequestDTO dto){
        Map<String, Object> objectMap = userService.userLogin(dto);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", objectMap),
                HttpStatus.CREATED);
    }


}
