package com.example.authentication.services;

import com.example.authentication.models.dtos.AuthRequest;
import com.example.authentication.models.User;
import com.example.authentication.exceptions.InvalidCredentialsException;
import com.example.authentication.exceptions.UsernameAlreadyExistsException;
import com.example.authentication.repositories.UserRepository;
import com.example.authentication.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Transactional
    public User register(AuthRequest request) {

        validateUser(request.getUsername());

        User newUser = User.builder()
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(newUser);
        log.info("User {} successfully registered", savedUser.getUsername());
        return savedUser;
    }

    public String login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) customUserDetailsService.loadUserByUsername(authentication.getName());
            String token = jwtTokenUtil.generateAccessToken(user);
            log.info("User {} logged in successfully with token {}", user.getUsername(), token);
            return token;
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException();
        }
    }

    public void validateUser (String username){
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new UsernameAlreadyExistsException(username);
                });
    }
}
