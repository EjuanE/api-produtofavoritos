package com.aiqfome.desafiotecnico.controller;

import com.aiqfome.desafiotecnico.dto.AuthRequestDTO;
import com.aiqfome.desafiotecnico.dto.AuthResponseDTO;
import com.aiqfome.desafiotecnico.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.autenticar(request));
    }
}