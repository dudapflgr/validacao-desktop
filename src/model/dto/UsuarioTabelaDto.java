package model.dto;

import java.util.Objects;

public class UsuarioTabelaDto {

    private Long idUsuario;

    private String codUsuario;

    private String nome;

    private String login;

    private String email;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        UsuarioTabelaDto that = (UsuarioTabelaDto) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(codUsuario, that.codUsuario) && Objects.equals(nome, that.nome) && Objects.equals(login, that.login) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, codUsuario, nome, login, email);
    }
}
