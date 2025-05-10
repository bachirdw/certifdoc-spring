package com.certifdoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.certifdoc.entity.UserEntity;
import com.certifdoc.entity.RoleEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByFirstname(String firstname);
    UserEntity findByLastname(String lastname);
    UserEntity findByEmail(String email);
    UserEntity findByRole(RoleEntity role); // Assurez-vous que Role est bien une entité en base de données
}
