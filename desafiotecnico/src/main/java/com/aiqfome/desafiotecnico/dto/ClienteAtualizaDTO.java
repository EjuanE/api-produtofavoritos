package com.aiqfome.desafiotecnico.dto;

public class ClienteAtualizaDTO {

    private String nomeCompleto;
    private String email;

    public ClienteAtualizaDTO() {
    }

    public ClienteAtualizaDTO(String nomeCompleto, String email) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
