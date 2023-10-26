package com.squad.squad.service;

import com.squad.squad.domain.User;
import com.squad.squad.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Optional;

public class UserService {
    UserRepository repository ;

    public UserService() {
        this.repository = new UserRepository();
    }
    public Optional<User> save(User user){
        Optional<User> byEmail = repository.findByEmail(user.getEmail());
        if(byEmail.isEmpty()){
           repository.save(user);
        }
        return byEmail;
    }
    public Optional<User> CheckEmail(String email , String pass){
        Optional<User> user1 = repository.findByEmail(email);
        if(user1.isPresent()) {
            if( BCrypt.checkpw(pass ,user1.get().getPassword())) {
                return user1;
            }
        }
        return Optional.empty();
    }

    public void validateUserData(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is Null");
        }
        if (user.getFirstName().isEmpty() || user.getFirstName().isBlank()) {
            throw new IllegalArgumentException("User Firstname is Empty");
        }
        if (user.getLastName().isEmpty() || user.getLastName().isBlank()) {
            throw new IllegalArgumentException("User Lastname is Empty");
        }
        if (user.getEmail().isEmpty() || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is Empty");
        }
        if (!validateEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is not Valid");
        }
        if (user.getPassword().isEmpty() || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is Empty");
        }
        if (!validatePassword(user.getPassword())) {
            throw new IllegalArgumentException("Password is not Valid");
        }
    }

    public boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

}
