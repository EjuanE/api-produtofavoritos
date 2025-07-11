package com.aiqfome.desafiotecnico.controller;

import com.aiqfome.desafiotecnico.dto.FavoritoRequest;
import com.aiqfome.desafiotecnico.dto.FavoritoResponse;
import com.aiqfome.desafiotecnico.service.FavoritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping
    public ResponseEntity<Void> adicionarFavorito(@PathVariable @Valid Long clienteId, @RequestBody FavoritoRequest favoritoRequest) {
        favoritoService.adicionarFavorito(clienteId, favoritoRequest.getProdutoId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<FavoritoResponse>> listarFavoritos(@PathVariable Long clienteId, Pageable pageable) {
        Page<FavoritoResponse> favoritos = favoritoService.listarFavoritos(clienteId, pageable);
        return ResponseEntity.ok(favoritos);
    }

    @DeleteMapping("/{produtoId}")
    @Transactional
    public ResponseEntity<List<Object>> removerFavorito(@PathVariable Long clienteId, @PathVariable Long produtoId) {
            favoritoService.removerFavorito(clienteId,produtoId);
        return ResponseEntity.noContent().build();
    }


}
