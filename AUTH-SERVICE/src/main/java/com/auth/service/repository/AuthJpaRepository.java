package com.auth.service.repository;

import com.auth.service.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthJpaRepository extends JpaRepository <AuthUser, Long>
{
    Optional<AuthUser> findByUsername(String username);

}
