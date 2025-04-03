package com.certifdoc.repository;

import javax.management.relation.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certifdoc.entity.User;
@Repository
public interface IUserRepository extends JpaRepository <User, Long> {
    User findByFirstName(String firstname);
    User findByLastName(String lastNname);
   User findByRole(Role role);
}
