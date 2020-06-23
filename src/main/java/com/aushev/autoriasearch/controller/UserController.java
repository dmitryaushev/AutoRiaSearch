package com.aushev.autoriasearch.controller;

import com.aushev.autoriasearch.exception.PasswordMatchException;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/get")
    public String userDetails(Authentication authentication, Model model) {
        model.addAttribute("user", userService.getUser(authentication));
        return "user_details";
    }

    @GetMapping("/edit")
    public String showEditUserPage(Authentication authentication, Model model) {
        model.addAttribute("user", userService.getUser(authentication));
        return "edit_user";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model,
                           Authentication authentication) {
        if (result.hasErrors()) {
            return "edit_user";
        }
        userService.updateUser(user, authentication);
        model.addAttribute("message", "Successfully");
        return "user_details";
    }

    @GetMapping("password")
    public String showChangePasswordPage(Authentication authentication, Model model) {
        model.addAttribute("user", userService.getUser(authentication));
        return "change_password";
    }

    @PostMapping("changePassword")
    public String changePassword(@ModelAttribute("user") User user, @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword, Model model,
                                 Authentication authentication) {
        try {
            userService.changePassword(user, oldPassword, newPassword, authentication);
            model.addAttribute("message", "Successfully");
            return "user_details";
        } catch (PasswordMatchException e) {
            model.addAttribute("message", e.getMessage());
            return "change_password";
        }
    }

    @ModelAttribute("user")
    public User getDefaultUser() {
        return new User();
    }
}
