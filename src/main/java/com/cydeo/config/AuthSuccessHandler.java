package com.cydeo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration//can be also @Component-we dont need to define  @bean from this class
public class AuthSuccessHandler implements AuthenticationSuccessHandler {//customize who can use what page


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            // purpose ? based on the role -for user to land on the page
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());//set is used to not have duplicate value for user (Spring is using set)

        if (roles.contains("Admin")) {//used if statement for roles to separate pages for each user
            response.sendRedirect("/user/create");
        }
        if (roles.contains("Manager")) {
            response.sendRedirect("/task/create");

        }
        if (roles.contains("Employee")) {
            response.sendRedirect("/task/employee/pending-tasks");
        }
    }
}