package com.secury.admin;

import com.secury.model.Role;
import com.secury.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminRoleController {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/role/role-list")
    public String role(HttpSession session, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "role/role-list";
    }

    @GetMapping("/role/role-add")
    public String roleAddGet(Model model) {
        model.addAttribute("role", new Role());
        return "role/role-add";
    }

    @PostMapping("/role/role-add")
    public String roleAddPost(Model model, HttpSession session, @Valid @ModelAttribute("role") Role role, BindingResult errors) {
        if(errors.hasErrors()) {
            return "role/role-add";
        }
        roleRepository.save(role);
        return "redirect:/role/role-list";
    }

    @GetMapping("/role/role-edit")
    public String roleEditGet(Model model, @RequestParam int id) {
        Optional<Role> role=roleRepository.findById(id);
        model.addAttribute("role", role);
        return "role/role-edit";
    }

    @PostMapping("/role/role-edit")
    public String roleEditPost(@Valid @ModelAttribute("role") Role role, BindingResult errors) {
        if (errors.hasErrors()) {
            return "role/role-edit";
        }
        roleRepository.save(role);
        return "redirect:/role/role-list";
    }

    @GetMapping("/role/role-delete")
    public String deleteRole(@RequestParam("id") int id) {
        roleRepository.deleteById(id);
        return "redirect:/role/role-list";
    }
}
