package com.example.hms1.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class LoginDto {

    private String username;

    private String password;
}
