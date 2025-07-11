package com.aiqfome.desafiotecnico.init;

import com.aiqfome.desafiotecnico.entity.Cliente;
import com.aiqfome.desafiotecnico.entity.Favorito;
import com.aiqfome.desafiotecnico.entity.Usuario;
import com.aiqfome.desafiotecnico.repository.ClienteRepository;
import com.aiqfome.desafiotecnico.repository.FavoritoRepository;
import com.aiqfome.desafiotecnico.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            FavoritoRepository favoritoRepository,
            PasswordEncoder encoder) {
        return args -> {

            if (usuarioRepository.findByEmail("admin@aiqfome.com").isEmpty()) {
                Usuario usuario = new Usuario(null, "admin@aiqfome.com", encoder.encode("123456"));
                usuarioRepository.save(usuario);
            }

            if (clienteRepository.findByEmail("jr1@email.com").isEmpty()) {
                Cliente cliente1 = new Cliente(null, "João Silva", "jr1@email.com");
                clienteRepository.save(cliente1);

                if (!favoritoRepository.existsByClienteAndProdutoId(cliente1, 1L)) {
                    favoritoRepository.save(new Favorito(null, 1L, cliente1));
                }
                if (!favoritoRepository.existsByClienteAndProdutoId(cliente1, 2L)) {
                    favoritoRepository.save(new Favorito(null, 2L, cliente1));
                }
            }

            if (clienteRepository.findByEmail("ma1@email.com").isEmpty()) {
                Cliente cliente2 = new Cliente(null, "Maria Souza", "ma1@email.com");
                clienteRepository.save(cliente2);

                if (!favoritoRepository.existsByClienteAndProdutoId(cliente2, 3L)) {
                    favoritoRepository.save(new Favorito(null, 3L, cliente2));
                }
            }
        };
    }
}
