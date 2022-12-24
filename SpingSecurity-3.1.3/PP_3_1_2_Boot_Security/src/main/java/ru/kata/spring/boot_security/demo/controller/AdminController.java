package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    private String getAllUsers(Principal principal, Model model) {
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("admin", userService.findUserByName(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser (@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id,user);
        return "redirect:/admin";
    }

}
