package com.aiqfome.desafiotecnico.repository;

import com.aiqfome.desafiotecnico.entity.Cliente;
import com.aiqfome.desafiotecnico.entity.Favorito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito,Long> {
    boolean existsByClienteAndProdutoId(Cliente cliente, Long produtoId);
    Page<Favorito> findByCliente(Cliente cliente, Pageable pageable);
    void deleteByClienteAndProdutoId(Cliente cliente, Long produtoId);
}
