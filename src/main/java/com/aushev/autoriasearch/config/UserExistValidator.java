package com.aushev.autoriasearch.config;

import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserExistValidator implements ConstraintValidator<UserExist, User> {

   private UserService userService;

   @Autowired
   public void setUserService(UserService userService) {
      this.userService = userService;
   }

   public void initialize(UserExist constraint) {
   }

   public boolean isValid(User user, ConstraintValidatorContext context) {

      User existUser = userService.userExist(user.getEmail());
      if (Objects.isNull(existUser)) {
         return true;
      }
      if (user.getId() != existUser.getId()) {
         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                 .addPropertyNode("email").addConstraintViolation();
         return false;
      }
      return true;
   }
}
