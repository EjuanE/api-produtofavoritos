package com.aiqfome.desafiotecnico.service;

import com.aiqfome.desafiotecnico.client.ProdutoClient;
import com.aiqfome.desafiotecnico.dto.FavoritoResponse;
import com.aiqfome.desafiotecnico.entity.Cliente;
import com.aiqfome.desafiotecnico.entity.Favorito;
import com.aiqfome.desafiotecnico.exception.FavoritoNaoEncontradoException;
import com.aiqfome.desafiotecnico.exception.ProdutoJaFavoritadoException;
import com.aiqfome.desafiotecnico.exception.ProdutoNaoEncontradoException;
import com.aiqfome.desafiotecnico.repository.ClienteRepository;
import com.aiqfome.desafiotecnico.repository.FavoritoRepository;
import feign.FeignException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FavoritoService {

    private final ClienteRepository clienteRepository;
    private final FavoritoRepository favoritoRepository;
    private final ProdutoClient produtoClient;

    public FavoritoService(ClienteRepository clienteRepository,
                           FavoritoRepository favoritoRepository,
                           ProdutoClient produtoClient) {
        this.clienteRepository = clienteRepository;
        this.favoritoRepository = favoritoRepository;
        this.produtoClient = produtoClient;
    }

    private Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

    private void validaProdutoExistente(Long produtoId) {
        try {
            produtoClient.buscarProdutoPorId(produtoId);
        } catch (FeignException.NotFound e) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado na API externa");
        }
    }

    public void adicionarFavorito(Long clienteId, Long produtoId) {
        Cliente cliente = buscarCliente(clienteId);

        if (favoritoRepository.existsByClienteAndProdutoId(cliente, produtoId)) {
            throw new ProdutoJaFavoritadoException("Produto já favoritado pelo cliente!");
        }

        validaProdutoExistente(produtoId);

        Favorito favorito = new Favorito();
        favorito.setCliente(cliente);
        favorito.setProdutoId(produtoId);
        favoritoRepository.save(favorito);
    }

    public Page<FavoritoResponse> listarFavoritos(Long clienteId, Pageable pageable) {
        Cliente cliente = buscarCliente(clienteId);
        Page<Favorito> favoritosPage = favoritoRepository.findByCliente(cliente, pageable);

        return favoritosPage.map(f -> produtoClient.buscarProdutoPorId(f.getProdutoId()));
    }

    public void removerFavorito(Long clienteId, Long produtoId) {
        Cliente cliente = buscarCliente(clienteId);
        boolean exists = favoritoRepository.existsByClienteAndProdutoId(cliente, produtoId);
        if (!exists) {
            throw new FavoritoNaoEncontradoException("Favorito não encontrado para deleção!");
        }
        favoritoRepository.deleteByClienteAndProdutoId(cliente, produtoId);
    }

}
