package com.aiqfome.desafiotecnico.service;

import com.aiqfome.desafiotecnico.entity.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRACAO = 1000 * 60 * 60 * 24; // 24h
    private static final String SEGREDO = "umsegredobemfortepraassinarotokenjwt123!";

    private Key getChave() {
        return Keys.hmacShaKeyFor(SEGREDO.getBytes());
    }

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(getChave(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getChave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token, Usuario usuario) {
        String email = extrairEmail(token);
        return email.equals(usuario.getEmail()) && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        Date exp = Jwts.parserBuilder()
                .setSigningKey(getChave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return exp.before(new Date());
    }
}
