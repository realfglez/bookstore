package com.termproject.bookstore.repositories;

import com.termproject.bookstore.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUserName(String userName);
}