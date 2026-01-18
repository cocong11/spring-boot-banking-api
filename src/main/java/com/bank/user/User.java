package com.bank.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // BigDecimal to avoid floating-point error
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    public User() {}

    public User(String username, String password, BigDecimal balance) {
        this.username = username;
        this.password = password;
        this.balance = (balance == null) ? BigDecimal.ZERO : balance;
    }

    public Long getId() { return id; }

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    public BigDecimal getBalance() { return balance; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public void setBalance(BigDecimal balance) {
        this.balance = (balance == null) ? BigDecimal.ZERO : balance;
    }

    // Spring Security 必需的方法：
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

