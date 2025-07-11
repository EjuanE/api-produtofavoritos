package com.aiqfome.desafiotecnico.service;

import com.aiqfome.desafiotecnico.dto.ClienteDTO;
import com.aiqfome.desafiotecnico.dto.ClienteAtualizaDTO;
import com.aiqfome.desafiotecnico.entity.Cliente;
import com.aiqfome.desafiotecnico.exception.ClienteNaoEncontradoException;
import com.aiqfome.desafiotecnico.exception.EmailJaCadastradoException;
import com.aiqfome.desafiotecnico.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO criarCliente(ClienteDTO dto) {
        validaEmailUnico(dto.getEmail());

        Cliente novoCliente = construirCliente(dto);
        Cliente clienteSalvo = clienteRepository.save(novoCliente);
        return paraDTO(clienteSalvo);
    }

    public Page<ClienteDTO> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(this::paraDTO);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = buscarClientePorId(id);
        return paraDTO(cliente);
    }

    public void deletar(Long id) {
        boolean exists = clienteRepository.existsById(id);
        if (!exists) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado para deleção!");
        }
        clienteRepository.deleteById(id);
    }


    public ClienteDTO atualizarCliente(Long id, ClienteAtualizaDTO dto) {
        Cliente clienteExistente = buscarClientePorId(id);
        validarEmailSeAlterado(dto.getEmail(), clienteExistente);
        atualizarDados(clienteExistente, dto);
        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return paraDTO(clienteAtualizado);
    }

    public ClienteDTO atualizarParcialmente(Long id, ClienteAtualizaDTO dto) {
        Cliente cliente = buscarClientePorId(id);

        if (emailDeveSerAtualizado(dto, cliente)) {
            validarEmailDisponivel(dto.getEmail());
            cliente.setEmail(dto.getEmail());
        }

        if (dto.getNomeCompleto() != null) {
            cliente.setNomeCompleto(dto.getNomeCompleto());
        }

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return paraDTO(clienteAtualizado);
    }

    private boolean emailDeveSerAtualizado(ClienteAtualizaDTO dto, Cliente clienteAtual) {
        return dto.getEmail() != null && !dto.getEmail().equals(clienteAtual.getEmail());
    }

    private void validarEmailDisponivel(String email) {
        if (clienteRepository.existsByEmail(email)) {
            throw new EmailJaCadastradoException("Email já cadastrado por outro cliente");
        }
    }

    private void atualizarDados(Cliente cliente, @Valid ClienteAtualizaDTO dto) {
        cliente.setNomeCompleto(dto.getNomeCompleto());
        cliente.setEmail(dto.getEmail());
    }

    private void validarEmailSeAlterado(String novoEmail, Cliente clienteExistente) {
        boolean emailFoiModificado = !clienteExistente.getEmail().equalsIgnoreCase(novoEmail);
        boolean emailJaExiste = clienteRepository.existsByEmail(novoEmail);
        if (emailFoiModificado && emailJaExiste) {
            throw new EmailJaCadastradoException("Email já foi cadastrado!");
        }
    }

    private Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado!"));
    }

    private ClienteDTO paraDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNomeCompleto(), cliente.getEmail());
    }

    private Cliente construirCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(dto.getNomeCompleto());
        cliente.setEmail(dto.getEmail());
        return cliente;
    }

    private void validaEmailUnico(String email) {
        if (clienteRepository.existsByEmail(email)) {
            throw new EmailJaCadastradoException("Email já cadastrado!");
        }
    }
}
