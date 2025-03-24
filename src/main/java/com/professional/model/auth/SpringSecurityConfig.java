package com.professional.model.auth;

import com.professional.model.auth.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/auth/login"),
                                AntPathRequestMatcher.antMatcher("/auth/registro"),
                                AntPathRequestMatcher.antMatcher("/up/**"),
                                AntPathRequestMatcher.antMatcher("/auth/confirmar-cuenta")).permitAll()
                        .requestMatchers("/trabajo-independiente/**").hasAnyRole("INDEPENDIENTE","CLIENTE","EMPRESA")
                        .requestMatchers("/trabajo-empresa/**").hasAnyRole("INDEPENDIENTE","CLIENTE","EMPRESA")
                        .requestMatchers("/empresa/**").hasAnyRole("INDEPENDIENTE","CLIENTE","EMPRESA")
                        .requestMatchers("/clientes/**").hasAnyRole("INDEPENDIENTE","CLIENTE","EMPRESA")
                        .requestMatchers("/independiente/**").hasAnyRole("INDEPENDIENTE","CLIENTE","EMPRESA")
                        .requestMatchers("/trabajo-emp-en-accion/**").hasAnyRole("INDEPENDIENTE","CLIENTE","EMPRESA")
                        .requestMatchers("/trabajo-ind-en-accion/**").hasAnyRole("INDEPENDIENTE","CLIENTE","EMPRESA")

                        .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                //.addFilter(new JwtValidationFilter(authenticationManager()))
                .addFilterBefore(new JwtValidationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/auth/login"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/auth/registro"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/up/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/auth/confirmar-cuenta"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/trabajo-independiente/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/trabajo-empresa/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/empresa/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/independiente/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/trabajo-emp-en-accion/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/trabajo-ind-en-accion/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/clientes/**"))
                )
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
