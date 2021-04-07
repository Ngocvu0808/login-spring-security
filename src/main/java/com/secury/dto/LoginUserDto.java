package com.secury.dto;

public class LoginUserDto {

    private String email;

    private String fullname;

    private String password;

    public LoginUserDto(String email, String fullname, String password) {
        super();
        this.email = email;
        this.fullname = fullname;
        this.password = password;
    }

    public LoginUserDto() {
        super();
        // TODO Auto-generated constructor stub
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

    @Override
    public String toString() {
        return "LoginUserDto [email =" +email + ", fullname = "+fullname +", password =" +password + "]";
    }
}
