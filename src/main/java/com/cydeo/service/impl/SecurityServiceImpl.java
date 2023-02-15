package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service//bean
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;//DI -need to retrieve user from entity

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= userRepository.findByUserNameAndIsDeleted(username, false);//accepting two parameters

        if(user==null){//validation

            throw  new UsernameNotFoundException(username);    //spring is providing exception in build
        }
        return new UserPrincipal(user);        //get the user from db and convert to new user that spring understand by  using userPrinciple class
    }
}
