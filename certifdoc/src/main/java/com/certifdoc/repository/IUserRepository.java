package com.certifdoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.certifdoc.entity.User;
import com.certifdoc.entity.Role;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByFirstname(String firstname);
    User findByLastname(String lastname);
    User findByRole(Role role); // Assurez-vous que Role est bien une entité en base de données
}
