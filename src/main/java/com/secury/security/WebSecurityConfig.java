package com.secury.security;

import com.secury.repo.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.secury")
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //declare Service to check and take information
    //declare object to resolve pass and check pass
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //Authority
    protected void configure(HttpSecurity http) throws Exception{
        System.out.println("---come to 2---");

        http.csrf().disable();

        //pages need not to login
        http.authorizeRequests().antMatchers("/admin/login", "/admin/logout").permitAll();

        //if have not logged in redirect to login page
        http.authorizeRequests().antMatchers("/user/**").access("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/role/**").access("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')");


        //if access denied will throw to 403 page
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/error/403");

        http.formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login") //Link declared form login action
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .failureUrl("/admin/login?error=true");

        //config logout
        http.logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .deleteCookies("JSESSIONID");
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        super.configure(web);
    }
}
