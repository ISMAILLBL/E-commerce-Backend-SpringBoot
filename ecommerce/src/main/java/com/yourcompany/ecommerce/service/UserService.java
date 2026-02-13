package com.yourcompany.ecommerce.service;

import com.yourcompany.ecommerce.dto.JwtResponseDTO;
import com.yourcompany.ecommerce.dto.UserLoginDTO;
import com.yourcompany.ecommerce.dto.UserRegisterDTO;
import com.yourcompany.ecommerce.model.User;
import com.yourcompany.ecommerce.repository.UserRepository;
import com.yourcompany.ecommerce.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwt;

    public void register(UserRegisterDTO dto) {
        if (repo.existsByEmail(dto.getEmail()))
            throw new RuntimeException("Email déjà utilisé");
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        repo.save(user);
    }

    public JwtResponseDTO login(UserLoginDTO dto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(), dto.getPassword()));
        String token = jwt.generate(auth.getName());
        return new JwtResponseDTO(token);
    }
}
