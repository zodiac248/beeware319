package com.panpal;

import org.springframework.data.repository.CrudRepository;

import com.panpal.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(Integer id);
}