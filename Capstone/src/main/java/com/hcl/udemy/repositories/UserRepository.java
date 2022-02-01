package com.hcl.udemy.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.hcl.udemy.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
}