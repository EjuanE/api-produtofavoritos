package com.aiqfome.desafiotecnico.mapper;

import com.aiqfome.desafiotecnico.dto.ClienteDTO;
import com.aiqfome.desafiotecnico.entity.Cliente;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClienteMapper {

    public Cliente paraCliente(ClienteDTO dto){
        Objects.requireNonNull(dto,"ClienteDTO nao pode ser null!");

        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(dto.getNomeCompleto());
        cliente.setEmail(dto.getEmail());
        return cliente;
    }

    public ClienteDTO paraClienteDTO(Cliente cliente) {
        Objects.requireNonNull(cliente,"Cliente nao pode ser null");

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNomeCompleto(cliente.getNomeCompleto());
        dto.setEmail(cliente.getEmail());
        return dto;
    }

}
