package com.certifdoc.repository;

import java.util.Optional;

import com.certifdoc.entity.User;

public class IUserRepository  extends JpaRepository<User, Long>{
    
	User findUserByEmail(String email);
	User findByRole(Role role);
	User findUserByFristName(String fristname);
	User findUserByLastName(String lastname);

}
