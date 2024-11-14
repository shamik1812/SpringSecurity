package com.example.hms1.controller;

import com.example.hms1.entity.Appuser;
import com.example.hms1.payload.AppuserDto;
import com.example.hms1.payload.LoginDto;
import com.example.hms1.payload.TokenDto;
import com.example.hms1.service.AppuserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class AppuserController {

    private AppuserService appuserService;

    public AppuserController(AppuserService appuserService) {
        this.appuserService = appuserService;
    }

    @PostMapping("/signup-User")
    public ResponseEntity<?> createUser(
            @RequestBody Appuser appuser
    ){
        Optional<Appuser> optional = appuserService.findingUsername(appuser);
        if(optional.isPresent()){
            return new ResponseEntity<>("Username already taken",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<Appuser> optional1 = appuserService.findingEmail(appuser);
        if(optional1.isPresent()){
            return new ResponseEntity<>("Email already present",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AppuserDto dto = appuserService.saving(appuser);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PostMapping("/signup-Owner-User")
    public ResponseEntity<?> createOwner(
            @RequestBody Appuser appuser
    ){
        Optional<Appuser> optional = appuserService.findingUsername(appuser);
        if(optional.isPresent()){
            return new ResponseEntity<>("Username already taken",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<Appuser> optional1 = appuserService.findingEmail(appuser);
        if(optional1.isPresent()){
            return new ResponseEntity<>("Email already present",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AppuserDto dto = appuserService.savingOwner(appuser);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> newLogin(
            @RequestBody LoginDto loginDto
    ){
       String token = appuserService.logIn(loginDto);
       if(token!=null){
           TokenDto dto1 = new TokenDto();
           dto1.setToken(token);
           dto1.setType("JWT");
           return new ResponseEntity<>(dto1,HttpStatus.OK);

       }else {
           return new ResponseEntity<>("Invalid Username/Password",HttpStatus.FORBIDDEN);
       }
    }
}
