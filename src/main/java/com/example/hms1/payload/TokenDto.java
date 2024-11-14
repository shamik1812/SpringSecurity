package com.example.hms1.payload;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class TokenDto {

    private String token;

    private String type;
}
