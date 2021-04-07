package com.secury.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Component
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Please enter a email")
    @Email(message = "Please enter a email")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Enter a fullname")
    @Length(min = 2, max = 50, message = "Please enter your full name!")
    private String fullname;

    private String password;

    private String confirmPassword;
    @Column(name = "role_id", nullable = false)
    private int roleId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
