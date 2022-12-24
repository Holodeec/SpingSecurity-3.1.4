package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    private final RoleService roleService;
    private final UserServiceImpl userService;
    @Autowired
    public UserController(RoleService roleService, UserServiceImpl userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        User user =  userService.findUserByName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("titleTable", "Страница пользователя: ");
        return "user";
    }

}
