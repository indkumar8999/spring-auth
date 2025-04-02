package com.myweb.authmagic.service;

import com.myweb.authmagic.model.MyUser;
import com.myweb.authmagic.db.DbConnection;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserService {

    private final List<MyUser> users = new ArrayList<>(); // Store registered users
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        // users.add(user);
        saveUserToDb(user);
    }

    public List<MyUser> getUsers() {
        return users;
    }

    public MyUser getUserFromDb(String username) {
        
        try {
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
            if (rs.next()) {
                return new MyUser(rs.getString("username"), rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveUserToDb(MyUser user) {
        System.out.println("-------------------");
        System.out.println("Saving user to database");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println("-------------------");
        try {
            PreparedStatement stmt = DbConnection.getConnection().prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
