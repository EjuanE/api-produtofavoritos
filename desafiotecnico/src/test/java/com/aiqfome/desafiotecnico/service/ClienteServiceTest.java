package com.aiqfome.desafiotecnico.service;

import com.aiqfome.desafiotecnico.dto.ClienteDTO;
import com.aiqfome.desafiotecnico.entity.Cliente;
import com.aiqfome.desafiotecnico.exception.ClienteNaoEncontradoException;
import com.aiqfome.desafiotecnico.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNomeCompleto("João Silva");
        cliente.setEmail("joao@email.com");
    }

    @Test
    void listarTodos_RetornaPagina() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Cliente> pageCliente = new PageImpl<>(List.of(cliente));

        when(clienteRepository.findAll(pageable)).thenReturn(pageCliente);

        Page<ClienteDTO> resultado = clienteService.listarTodos(pageable);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        assertEquals("João Silva", resultado.getContent().get(0).getNomeCompleto());
    }

    @Test
    void buscarPorId_ClienteExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteDTO dto = clienteService.buscarPorId(1L);

        assertNotNull(dto);
        assertEquals(cliente.getNomeCompleto(), dto.getNomeCompleto());
        assertEquals(cliente.getEmail(), dto.getEmail());
    }

    @Test
    void buscarPorId_ClienteNaoExiste() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.buscarPorId(2L));
    }
}
