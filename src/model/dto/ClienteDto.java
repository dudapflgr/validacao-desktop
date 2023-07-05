package model.dto;

import java.util.Objects;

public class ClienteDto {

    private Long idCliente;

    private String codCliente;

    private String nome;

    private String email;

    public ClienteDto() {
    }

    public ClienteDto(Long idCliente, String codCliente, String nome, String email) {
        this.idCliente = idCliente;
        this.codCliente = codCliente;
        this.nome = nome;
        this.email = email;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDto that = (ClienteDto) o;
        return Objects.equals(idCliente, that.idCliente) && Objects.equals(codCliente, that.codCliente) && Objects.equals(nome, that.nome) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, codCliente, nome, email);
    }
}
