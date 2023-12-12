package com.myblogp2.payload;

import lombok.Data;

@Data
public class SignInDto{
    private String usernameOrEmail;
    private String password;
}
