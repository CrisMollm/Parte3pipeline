package com.example.dockerpipeline.repository;

import com.example.dockerpipeline.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
