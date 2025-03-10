package com.professional.model.auth.filter;

import com.professional.controller.exceptions.JwttException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.professional.model.auth.TokenJwtConfig.*;
public class JwtValidationFilter extends BasicAuthenticationFilter {
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header= request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }


        String token = header.replace(PREFIX_TOKEN, "");

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String username = claims.getSubject();
            String tipoUsuario = claims.get("tipoUsuario", String.class);

            // Crea la lista de autoridades; se asume que en Spring Security se espera "ROLE_INDEPENDIENTE" cuando tipoUsuario es "INDEPENDIENTE"
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (tipoUsuario != null) {
                // Convierte el tipo a mayúsculas y antepone "ROLE_" para que coincida con hasRole("INDEPENDIENTE")
                authorities.add(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.toUpperCase()));
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request,response);
        } catch (JwtException e){
            throw new JwttException("Token Invalido Acceso No autorizado");
        }

    }
}