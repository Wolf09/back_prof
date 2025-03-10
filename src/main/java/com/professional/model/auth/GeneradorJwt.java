package com.professional.model.auth;

import io.jsonwebtoken.Jwts;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.professional.model.auth.TokenJwtConfig.*;
public class GeneradorJwt {


    public static String generarToken(String correo, LocalDateTime fechaExpiracion, String tipoUsuario,String pais,String ciudad, Long id){

        // Convierte LocalDateTime a Date
        Date expirationDate = Date.from(fechaExpiracion.atZone(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                .subject(correo) // Establece el 'subject' del token como el correo del usuario
                .claim("tipoUsuario", tipoUsuario) // Añade 'tipoUsuario' como una reclamación personalizada
                .claim("pais",pais)
                .claim("ciudad",ciudad)
                .claim("id", id)
                .issuedAt(new Date()) // Fecha de emisión
                .expiration(expirationDate) // Fecha de expiración
                .signWith(SECRET_KEY) // Firma el token con la clave secreta
                .compact(); // Construye el token como una cadena compacta


        return token;
    }
}
