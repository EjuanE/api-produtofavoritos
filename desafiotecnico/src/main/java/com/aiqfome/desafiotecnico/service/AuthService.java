package com.aiqfome.desafiotecnico.service;

import com.aiqfome.desafiotecnico.dto.AuthRequestDTO;
import com.aiqfome.desafiotecnico.dto.AuthResponseDTO;
import com.aiqfome.desafiotecnico.entity.Usuario;
import com.aiqfome.desafiotecnico.exception.EmailJaCadastradoException;
import com.aiqfome.desafiotecnico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String registrar(AuthRequestDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        Usuario usuario = new Usuario(null, request.getEmail(), passwordEncoder.encode(request.getSenha()));
        usuarioRepository.save(usuario);
        return "Usuário registrado com sucesso";
    }

    public AuthResponseDTO autenticar(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getSenha()
                    )
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        String token = jwtService.gerarToken(usuario);
        return new AuthResponseDTO(token);
    }
}
