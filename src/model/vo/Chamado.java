package model.vo;

import java.time.LocalDateTime;

public class Chamado {

    private Long idChamado;

    private String codChamado;

    private Cliente cliente;

    private String titulo;

    private String descricao;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    private String solucao;

    private Usuario usuarioRequisitante;

    private Usuario usuarioTecnico;

    public Chamado(String codChamado, Cliente cliente, String titulo, String descricao, LocalDateTime dataAbertura, Usuario usuarioRequisitante) {
        this.codChamado = codChamado;
        this.cliente = cliente;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataAbertura = dataAbertura;
        this.usuarioRequisitante = usuarioRequisitante;
    }

    public Long getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Long idChamado) {
        this.idChamado = idChamado;
    }

    public String getCodChamado() {
        return codChamado;
    }

    public void setCodChamado(String codChamado) {
        this.codChamado = codChamado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public Usuario getUsuarioRequisitante() {
        return usuarioRequisitante;
    }

    public void setUsuarioRequisitante(Usuario usuarioRequisitante) {
        this.usuarioRequisitante = usuarioRequisitante;
    }

    public Usuario getUsuarioTecnico() {
        return usuarioTecnico;
    }

    public void setUsuarioTecnico(Usuario usuarioTecnico) {
        this.usuarioTecnico = usuarioTecnico;
    }
}
