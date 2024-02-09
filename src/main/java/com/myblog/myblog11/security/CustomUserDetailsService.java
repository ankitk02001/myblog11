package com.myblog.myblog11.security;

import com.myblog.myblog11.entity.Role;
import com.myblog.myblog11.entity.User;
import com.myblog.myblog11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//this class help to fetch the data from database  and method we not need to call it called automatically
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)//it search record in db is user or email present in db or not
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + username));//this eception class is build in class

        //setting the data which we get from db in User Class(Predefine class in spring security) object
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    //mapRolesToAuthorities()get set of user Roles it return back the content which user(Predefine class in spring security) class can can take it
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        // This method maps a set of Role objects to a collection of GrantedAuthority objects,// which are used by Spring Security to represent the authorities/roles granted to a user.// Stream through the set of roles and map each role to a SimpleGrantedAuthority object
        // SimpleGrantedAuthority is a Spring Security class representing a granted authority (role).// We extract the name of each role and create a SimpleGrantedAuthority object with that name.
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()); // Collect the mapped authorities into a list and return it.
    }


}
