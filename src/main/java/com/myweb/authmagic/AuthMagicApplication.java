package com.myweb.authmagic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.myweb.authmagic.db.DbConnection;

@SpringBootApplication
public class AuthMagicApplication {

	public static void main(String[] args) {
        DbConnection.start("hospital",
				"root",
				"");
		
		SpringApplication.run(AuthMagicApplication.class, args);
	}

	// @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //             .username("user")
    //             .password("password")
    //             .roles("USER")
    //             .build();

    //     UserDetails admin = User.withDefaultPasswordEncoder()
    //             .username("admin")
    //             .password("admin")
    //             .roles("ADMIN")
    //             .build();

    //     return new InMemoryUserDetailsManager(user, admin);
    // }

}
