package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {      //using for mapping staff

    private User user;   //create has a relationship //user is coming from entity

    public UserPrincipal(User user) {//created mapper
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//returning collections

        List<GrantedAuthority> authorityList=new ArrayList<>();

        GrantedAuthority authority  = new SimpleGrantedAuthority(this.user.getRole().getDescription());

        authorityList.add(authority);

        return authorityList;
    }

    @Override
    public String getPassword() {//how i can access to password field of the user object
        return this.user.getPassWord();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
    public Long getId(){//this method is for baseEntityListener for unPreUpdate and onPredPersist
        return this.user.getId();

    }
}
