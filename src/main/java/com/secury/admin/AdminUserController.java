package com.secury.admin;

import com.secury.model.User;
import com.secury.repo.RoleRepository;
import com.secury.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AdminUserController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/user-list")
    public String User(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/user-list";
    }

    @GetMapping("/user/user-add")
    public String UserAddGet(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", new User());
        return "user/user-add";
    }


    @PostMapping("/user/user-add")
    public String UserAddPost(Model model, @Valid @ModelAttribute("user") User user, BindingResult errors) {
        User users = userRepository.findByEmail(user.getEmail());

        if(errors.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "/user/user-add";
        }
        if(users !=null) {
            model.addAttribute("message","Vui lòng chọn email khác!");
            model.addAttribute("roles", roleRepository.findAll());
            return "/user/user-add";
        }
        else {
            String hashsed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashsed);
            user.setConfirmPassword(hashsed);
            userRepository.save(user);
            model.addAttribute("message", "Thêm mới thành công!");
            return "redirect:/user/user-list";
        }
    }

    @GetMapping("/user/user-edit")
    public String UserEditGet(Model model, @RequestParam int id) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", userRepository.findById(id));
        return "user/user-edit";
    }

    @PostMapping("/user/user-edit")
    public String UserEditPost(Model model,@Valid @ModelAttribute("user") User user, BindingResult errors) {
        if(errors.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "/user/user-edit";
        }
        userRepository.save(user);
        return "redirect:/user/user-list";
    }

    @GetMapping("/user/user-delete")
    public String deleteUser(@RequestParam("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/user/user-list";
    }
}
