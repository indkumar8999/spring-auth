package com.myweb.authmagic.service;

import com.myweb.authmagic.model.MyUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check for admin user
        if (username.equals("admin")) {
            return User.withUsername("admin")
                .password(passwordEncoder.encode("admin123")) // Hardcoded admin password
                .roles("ADMIN")
                .build();
        }

        // return userService.getUsers().stream()
        //     .filter(user -> user.getUsername().equals(username))
        //     .findFirst()
        //     .map(user -> User.withUsername(user.getUsername())
        //         .password(user.getPassword()) // Already encrypted
        //         .roles("USER") // Assign roles dynamically if needed
        //         .build())
        //     .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        MyUser user = userService.getUserFromDb(username);
        if (user != null) {
            return User.withUsername(user.getUsername())
                .password(user.getPassword()) // Already encrypted
                .roles("USER") // Assign roles dynamically if needed
                .build();
        }

        return null;
    }
}
