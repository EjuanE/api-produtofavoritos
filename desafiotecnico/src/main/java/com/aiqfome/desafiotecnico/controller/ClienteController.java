package com.aiqfome.desafiotecnico.controller;

import com.aiqfome.desafiotecnico.dto.ClienteAtualizaDTO;
import com.aiqfome.desafiotecnico.dto.ClienteDTO;
import com.aiqfome.desafiotecnico.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody @Valid ClienteDTO dto) {
        ClienteDTO novoCliente = clienteService.criarCliente(dto);
        URI location = URI.create("/api/clientes/" + novoCliente.getId());
        return ResponseEntity.created(location).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listarClientes(Pageable pageable) {
        Page<ClienteDTO> clientes = clienteService.listarTodos(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id,
                                                       @RequestBody @Valid ClienteAtualizaDTO dto) {
        ClienteDTO clienteAtualizado = clienteService.atualizarCliente(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarParcialmente(@PathVariable Long id,
                                                            @RequestBody ClienteAtualizaDTO dto) {
        return ResponseEntity.ok(clienteService.atualizarParcialmente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
