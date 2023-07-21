package com.market.demo.simulator.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ControlSecurity {

    @Bean
    public UserDetailsManager detailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from user where user_id=?"
        );

        //define query to retrieve an authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?"
        );

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(configurer -> configurer
                        .requestMatchers( "/item/home").hasRole("USER")
                        .requestMatchers( "/item/showResult").hasRole("USER")
                        .requestMatchers( "/item/page").hasRole("USER")
                        .requestMatchers( "/item/home").hasRole("USER")
                        .requestMatchers( "/item/searchForItem").hasRole("USER")
                        .requestMatchers( "/item/showBasket").hasRole("USER")
                        .requestMatchers( "/item/showBasket2").hasRole("USER")
                        .requestMatchers( "/item/orderItems").hasRole("USER")
                        .requestMatchers( "/item/confirm").hasRole("USER")
                        .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("/item/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(form ->
                        form
                                .loginPage("/showLoginForm")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                ).exceptionHandling(configurer -> configurer
                        .accessDeniedPage("/access-Denied")
                );



        return http.build();
    }



}
