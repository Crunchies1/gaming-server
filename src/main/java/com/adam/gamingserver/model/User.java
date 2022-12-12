package com.adam.gamingserver.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Getter long id;
    private @Getter @Setter String email;
    private @Getter @Setter String encryptedPassword;
    private @Getter String accessToken;
    private @Getter float netWorth;

    @ElementCollection 
    private @Getter @Setter Set<String> roles;
    
    public User() {}

    public User(String email, String password) {
        this.email = email;
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.encryptedPassword = encoder.encode(password);
        this.accessToken = "noaccess";
        this.netWorth = 0;
        this.roles = new HashSet<>();
    }

    public User(long id, String email, String password) {
        this.id = id;
        this.email = email;
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.encryptedPassword = encoder.encode(password);
        this.accessToken = "noaccess";
        this.netWorth = 0;
        this.roles = new HashSet<>();
    }
    public float addNet(float value) {
        this.netWorth += value;
        return this.netWorth;
    }

    public float decreaseNet(float value) {
        this.netWorth -= value;
        return this.netWorth;
    }

    public String login(String email, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (email != this.email || encoder.encode(password) != this.encryptedPassword) {
            return "noaccess";
        }
        this.accessToken = "access";
        return this.accessToken;
    }
}
