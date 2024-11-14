package com.example.hms1.service;

import com.example.hms1.entity.Appuser;
import com.example.hms1.payload.AppuserDto;
import com.example.hms1.payload.LoginDto;
import com.example.hms1.repository.AppuserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Optional;

@Service
public class AppuserService {

    private AppuserRepository appuserRepository;
    private JWTService jwtService;
    public AppuserService(AppuserRepository appuserRepository, JWTService jwtService) {
        this.appuserRepository = appuserRepository;
        this.jwtService = jwtService;
    }

    public AppuserDto saving(Appuser appuser) {
        String hashpw = BCrypt.hashpw(appuser.getPassword(), BCrypt.gensalt(5));
        appuser.setPassword(hashpw);
        appuser.setRole("ROLE_USER");
        Appuser save = appuserRepository.save(appuser);
        AppuserDto dto = new AppuserDto();
        dto.setName(save.getName());
        dto.setUsername(save.getUsername());
        dto.setEmail(save.getEmail());
        return dto;
    }

    public Optional<Appuser> findingUsername(Appuser appuser) {
        Optional<Appuser> byUsername = appuserRepository.findByUsername(appuser.getUsername());
        return byUsername;
    }

    public Optional<Appuser> findingEmail(Appuser appuser) {
        Optional<Appuser> byEmail = appuserRepository.findByEmail(appuser.getEmail());
        return byEmail;
    }

    public AppuserDto savingOwner(Appuser appuser) {
        String hashpw = BCrypt.hashpw(appuser.getPassword(), BCrypt.gensalt(5));
        appuser.setPassword(hashpw);
        appuser.setRole("ROLE_OWNER");
        Appuser save = appuserRepository.save(appuser);
        AppuserDto dto = new AppuserDto();
        dto.setName(save.getName());
        dto.setUsername(save.getUsername());
        dto.setEmail(save.getEmail());
        return dto;
    }


    public String logIn(LoginDto dto) {
        Optional<Appuser> byUsername = appuserRepository.findByUsername(dto.getUsername());
        if (byUsername.isPresent()){
            Appuser appuser = byUsername.get();
           if(BCrypt.checkpw(dto.getPassword(),appuser.getPassword())){
               String token = jwtService.generateToken(appuser.getUsername());
               return token;
           }
           return null;
        }
        return null;
    }
}
