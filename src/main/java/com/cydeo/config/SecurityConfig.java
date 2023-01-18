package com.cydeo.config;




import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration //creating bean
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;//DI
    }


    //    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//
//        List<UserDetails> userList= new ArrayList<>();
//        userList.add(
//                new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));// import user from Springseurityuser
//
//        userList.add(
//                new User("ozzy", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));// import user from Springseurityuser
//
//
//        return  new InMemoryUserDetailsManager(userList);
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//form
        return http
                .authorizeRequests()
//                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAuthority("Admin")
               .antMatchers("/project/**").hasAuthority("Manager")
               .antMatchers("/task/employee/**").hasAuthority("Employee")
               .antMatchers("/task/**").hasAuthority("Manager")
               // .antMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//             .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE") for exp._must match in DB
                .antMatchers(
                        "/",//to log in
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()//for all permission to log in for each request
                .anyRequest().authenticated()
                .and()
                //.httpBasic()
                .formLogin()//want to use my own form
                    .loginPage("/login")
//                    .defaultSuccessUrl("/welcome")//anybody can  landing on welcome page
                     .successHandler(authSuccessHandler)//to put some restriction we create class (bean)
                    .failureUrl("/login?error=true")//if user put wrong information
                    .permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(120)//86400
                    .key("cydeo")
                    .userDetailsService(securityService)//to remember who
                .and()
                .build();//security filter chain
    }

}
