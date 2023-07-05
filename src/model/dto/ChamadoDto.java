package model.dto;

public class ChamadoDto {

    private Long idChamado;

    private String codChamado;

    private String titulo;

    private String descricao;

    private String nomeCliente;

    private String usuarioRequisitante;

    private Long idUsuarioAtendente;

    private String nomeUsuarioAtendente;

    private String dataHoraAbertura;

    private String solucao;

    private String dataHoraFechamento;

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

    public String getUsuarioRequisitante() {
        return usuarioRequisitante;
    }

    public void setUsuarioRequisitante(String usuarioRequisitante) {
        this.usuarioRequisitante = usuarioRequisitante;
    }

    public Long getIdUsuarioAtendente() {
        return idUsuarioAtendente;
    }

    public void setIdUsuarioAtendente(Long idUsuarioAtendente) {
        this.idUsuarioAtendente = idUsuarioAtendente;
    }

    public String getNomeUsuarioAtendente() {
        return nomeUsuarioAtendente;
    }

    public void setNomeUsuarioAtendente(String nomeUsuarioAtendente) {
        this.nomeUsuarioAtendente = nomeUsuarioAtendente;
    }

    public String getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(String dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(String dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
