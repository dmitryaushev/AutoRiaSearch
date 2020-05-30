package com.aushev.autoriasearch.controller;

import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showUsers")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "show_users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/notActiveUsers")
    public String showNotActiveUsers(Model model) {
        model.addAttribute("users", userService.findNotActiveUsers());
        return "not_active_users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("activateUsers")
    public String activateUsers(@ModelAttribute("notActiveUsers") NotActiveUsers users, Model model) {
        userService.activateUsers(users);
        model.addAttribute("users", userService.findNotActiveUsers());
        return "not_active_users";
    }

    @ModelAttribute("user")
    public User getDefaultUser() {
        return new User();
    }

    @ModelAttribute("notActiveUsers")
    public NotActiveUsers getNotActiveUsers() {
        return new NotActiveUsers();
    }
}
