package com.termproject.bookstore.repositories;

import com.termproject.bookstore.models.Admin;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, String> {

    
    Admin findByAdminID(int id);

}