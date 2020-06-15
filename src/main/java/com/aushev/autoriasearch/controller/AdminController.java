package com.aushev.autoriasearch.controller;

import com.aushev.autoriasearch.model.Config;
import com.aushev.autoriasearch.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @ModelAttribute("config")
    public Config getDefaultTime() {
        return new Config();
    }
}
