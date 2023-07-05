package model.dto;

public class ChamadoEditarDto {

    private Long idChamado;

    private String codChamado;

    private String titulo;

    private String descricao;

    private Long idCliente;

    private Boolean fechado;

    public ChamadoEditarDto(Long idChamado, String codChamado, String titulo, String descricao, Long idCliente, Boolean fechado) {
        this.idChamado = idChamado;
        this.codChamado = codChamado;
        this.titulo = titulo;
        this.descricao = descricao;
        this.idCliente = idCliente;
        this.fechado = fechado;
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

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Boolean getFechado() {
        return fechado;
    }

    public void setFechado(Boolean fechado) {
        this.fechado = fechado;
    }
}
