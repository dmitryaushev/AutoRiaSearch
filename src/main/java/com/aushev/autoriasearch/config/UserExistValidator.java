package com.aushev.autoriasearch.config;

import com.aushev.autoriasearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistValidator implements ConstraintValidator<UserExist, String> {

   private UserService userService;

   @Autowired
   public void setUserService(UserService userService) {
      this.userService = userService;
   }

   public void initialize(UserExist constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
      return userService.userExist(email);
   }
}
