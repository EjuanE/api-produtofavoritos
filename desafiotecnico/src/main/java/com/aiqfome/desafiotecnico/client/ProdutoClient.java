package com.aiqfome.desafiotecnico.client;

import com.aiqfome.desafiotecnico.dto.FavoritoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produtoClient", url = "https://fakestoreapi.com")
public interface ProdutoClient {

    @GetMapping("/products/{id}")
    FavoritoResponse buscarProdutoPorId(@PathVariable("id") Long id);
}
