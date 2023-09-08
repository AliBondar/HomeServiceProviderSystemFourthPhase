package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.security.CustomAuthProvider;
import org.example.security.CustomUserDetailsService;
import org.example.service.impl.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((requests) -> requests

                        .requestMatchers("/signup/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/expert/**").hasAuthority("EXPERT")
                        .requestMatchers("/client/**").hasAuthority("CLIENT")
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {});
//                .httpBasic(withDefaults())
//                .authenticationProvider(new CustomAuthProvider((CustomUserDetailsService) userDetailsService(),passwordEncoder()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailService();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
