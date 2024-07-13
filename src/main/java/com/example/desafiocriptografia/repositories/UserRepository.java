package com.example.desafiocriptografia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiocriptografia.models.UserWithDataSensitive;

public interface UserRepository extends JpaRepository<UserWithDataSensitive, Long> {

}
