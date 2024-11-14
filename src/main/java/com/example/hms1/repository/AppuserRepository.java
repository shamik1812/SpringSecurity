package com.example.hms1.repository;

import com.example.hms1.entity.Appuser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppuserRepository extends JpaRepository<Appuser, Long> {


    Optional<Appuser> findByUsername(String username);

    Optional<Appuser> findByEmail(String email);
}