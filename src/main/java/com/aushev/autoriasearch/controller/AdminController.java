package com.aushev.autoriasearch.controller;

import com.aushev.autoriasearch.model.Config;
import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/time")
    public String showSetTimePage() {
        return "set_time";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/setTime")
    public String setTime(@ModelAttribute("config") Config config, Model model) {
        adminService.setMailingTime(config);
        adminService.saveMailingTime(config);
        model.addAttribute("message", String.format("Время рассылки - %s", config.getValue()));
        return "set_time";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showUsers")
    public String showAllUsers(Model model) {
        model.addAttribute("users", adminService.findAllUsers());
        return "show_users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/notActiveUsers")
    public String showNotActiveUsers(Model model) {
        model.addAttribute("users", adminService.findNotActiveUsers());
        return "not_active_users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("activateUsers")
    public String activateUsers(@ModelAttribute("notActiveUsers") NotActiveUsers users, Model model) {
        adminService.activateUsers(users);
        model.addAttribute("users", adminService.findNotActiveUsers());
        return "not_active_users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/activate")
    public String activateUser(@RequestParam("id") int id) {
        adminService.activateUser(id);
        return "redirect:/admin/showUsers";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/deactivate")
    public String deactivateUser(@RequestParam("id") int id) {
        adminService.deactivateUser(id);
        return "redirect:/admin/showUsers";
    }

    @ModelAttribute("config")
    public Config getDefaultTime() {
        return new Config();
    }

    @ModelAttribute("notActiveUsers")
    public NotActiveUsers getNotActiveUsers() {
        return new NotActiveUsers();
    }
}
