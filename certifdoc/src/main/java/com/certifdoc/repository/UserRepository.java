package com.certifdoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.certifdoc.entity.UserEntity; 
import com.certifdoc.entity.RoleEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByLastname(String lastname);
    UserEntity findByRole(RoleEntity role);
}