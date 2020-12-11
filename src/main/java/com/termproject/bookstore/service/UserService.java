package com.termproject.bookstore.service;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if(user == null){
            System.out.println("Could not find user");
        }

        return user;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void suspendUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        updatedUser.setSuspended(true);
        userRepository.save(updatedUser);
    }

    public void unSuspendUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        updatedUser.setSuspended(false);
        userRepository.save(updatedUser);
    }

    public void promoteUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        updatedUser.setRole("admin");
        userRepository.save(updatedUser);
    }
    public void demoteUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        updatedUser.setRole("employee");
        userRepository.save(updatedUser);
    }
}
