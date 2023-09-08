package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.entity.users.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordHash passwordHash = new PasswordHash();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("name: " + authentication.getName());
        System.out.println("details: " + authentication.getDetails());
        System.out.println("credentials: " + authentication.getCredentials());
        System.out.println("principal: " + authentication.getPrincipal());

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(authentication.getName());
            if (passwordEncoder.matches(((String) authentication.getCredentials()), userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
            }
        System.out.println("authorities: " + authentication.getAuthorities());

        return authentication;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
