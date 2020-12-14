package com.termproject.bookstore.service;

import com.termproject.bookstore.models.Role;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * <p>Saves the user</p>
     * @param user the user to save
     * @return the saved user
     */
    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * <p>Finds a user based on their username</p>
     * @param username the user's username
     * @return the user
     */
    public User getUserByUsername(String username) {

        User user = userRepository.findByUsername(username);
        return user;
    }

    /**
     * <p>Finds a user based on their email</p>
     * @param email the user's email
     * @return the user
     */
    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        return user;
    }

    /**
     * <p>Finds a user based on their verification code</p>
     * @param verificationCode the user's verification code
     * @return the user
     */
    public User getUserByVerificationCode(String verificationCode) {

        User user = userRepository.findByVerificationCode(verificationCode);
        return user;
    }

    /**
     * <p>Gets the list of users</p>
     * @return all existing users
     */
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    /**
     * <p>Suspends the user</p>
     * @param user the user to suspend
     */
    public void suspendUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        updatedUser.setSuspended(true);
        userRepository.save(updatedUser);
    }

    /**
     * <p>Unsuspends the user</p>
     * @param user the user to unsuspend
     */
    public void unSuspendUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        updatedUser.setSuspended(false);
        userRepository.save(updatedUser);
    }

    /**
     * <p>Promotes the user</p>
     * @param user the user to promote
     */
    public void promoteUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        if (updatedUser.getRole().name().equals("USER")){
            updatedUser.setRole(Role.valueOf("EMPLOYEE"));
        }
        else if (updatedUser.getRole().name().equals("EMPLOYEE")){
            updatedUser.setRole(Role.valueOf("ADMIN"));
        }

        userRepository.save(updatedUser);
    }

    /**
     * <p>Demotes the user</p>
     * @param user the user to demote
     */
    public void demoteUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername());

        if (updatedUser.getRole().name().equals("ADMIN")){
            updatedUser.setRole(Role.valueOf("EMPLOYEE"));
        }
        else if (updatedUser.getRole().name().equals("EMPLOYEE")){
            updatedUser.setRole(Role.valueOf("USER"));
        }
        userRepository.save(updatedUser);
    }

    public boolean checkAdmin(User user) {
        boolean isAdmin = false;

        if (user.getRole().name().equals("ADMIN")){
            isAdmin = true;
        }
        return isAdmin;
    }

    public boolean loggedIn(User user, HttpSession session) {
        boolean loggedIn = false;

        if (user.getSessionCode().equals(session.getId())){
            loggedIn = true;
        }

        return loggedIn;
    }
}
