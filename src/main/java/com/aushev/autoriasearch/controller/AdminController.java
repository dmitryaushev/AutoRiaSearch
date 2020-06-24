package com.aushev.autoriasearch.controller;

import com.aushev.autoriasearch.exception.UserNotExistException;
import com.aushev.autoriasearch.model.Config;
import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.service.AdminService;
import com.aushev.autoriasearch.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    private MailService mailService;
    
    private static final String SORT_BY_ROLE = "userRole";
    private static final String SORT_BY_STATUS = "userStatus";

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/time")
    public String showSetTimePage() {
        return "set_time";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/setTime")
    public String setTime(@ModelAttribute("config") Config config, Model model) {
        mailService.setMailingTime(config);
        mailService.saveMailingTime(config);
        model.addAttribute("message", String.format("Время рассылки - %s", config.getValue()));
        return "set_time";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/findUser")
    public String showFindUserPage() {
        return "find_user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find")
    public String findUser(@RequestParam("email") String email, Model model) {
        try {
            model.addAttribute("users", Collections.singletonList(adminService.findUser(email)));
            return "show_users";
        } catch (UserNotExistException e) {
            model.addAttribute("error", e.getMessage());
            return "find_user";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showUsers")
    public String showAllUsers(@PageableDefault(sort = {SORT_BY_ROLE, SORT_BY_STATUS}) Pageable pageable, Model model) {
        Page<User> users = adminService.findAllUsers(pageable);
        model.addAttribute("users", users.toList());
        model.addAttribute("current", pageable.getPageNumber() + 1);
        model.addAttribute("prev", users.previousOrFirstPageable().getPageNumber());
        model.addAttribute("next", users.nextOrLastPageable().getPageNumber());
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/role")
    public String switchRole(@RequestParam("id") int id) {
        adminService.switchRole(id);
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
