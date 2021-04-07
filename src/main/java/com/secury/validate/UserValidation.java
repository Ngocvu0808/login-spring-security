package com.secury.validate;

import com.secury.model.User;
import com.secury.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserValidation {
    @Autowired
    UserRepository userRepository;

    public boolean supports(Class<?> clazz) {
        //check the object is User?
        return User.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        //Cast object to User
        User user = (User)target;

        if(user.getPassword() == null || user.getPassword().length() == 0) {
            errors.rejectValue("password", "user", "Please enter password!");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmpassword", "user", "Password is not match!");
        }
        else if(user.getConfirmPassword() == null || user.getConfirmPassword().length() == 0) {
            errors.rejectValue("confirmpassword", "user", "Please confirm password!");
        }
    }

}

