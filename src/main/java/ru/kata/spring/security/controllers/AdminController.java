package ru.kata.spring.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.security.models.Role;
import ru.kata.spring.security.models.User;
import ru.kata.spring.security.security.UserUserDetailsImpl;
import ru.kata.spring.security.services.UserService;
import ru.kata.spring.security.services.UserServiceImpl;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showAllUsers(Model model, Principal principal) {

        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);

        UserUserDetailsImpl user = (UserUserDetailsImpl) ((Authentication) principal).getPrincipal();
        List<User> users = Collections.singletonList(userService.getUser(user.getId()));
        model.addAttribute("user", users);

        List<Role> allRoles = userService.findAllRoles();
        model.addAttribute("allRoles", allRoles);

        User newUser = new User();
        model.addAttribute("newUser", newUser);

        return "admin/admin";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult, Model model) {

        List<Role> roles = userService.findAllRoles();
        model.addAttribute("allRoles", roles);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/admin/users";
        }

        if (userService.existsUserByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "user.email.exists",
                    "Пользователь с таким email уже существует");

            return "redirect:/admin/users";
        }

        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PatchMapping( "/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}