package com.aiqfome.desafiotecnico.repository;

import com.aiqfome.desafiotecnico.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<Cliente> findAll(Pageable pageable);

}
