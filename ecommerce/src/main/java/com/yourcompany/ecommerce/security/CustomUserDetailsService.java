package com.yourcompany.ecommerce.security;

import com.yourcompany.ecommerce.model.User;
import com.yourcompany.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // On renvoie le UserDetails **fourni par Spring Security**
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")        // rôle simple
                .build();
    }
}
