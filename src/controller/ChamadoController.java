package controller;

import model.dao.ChamadoDao;
import model.dto.ChamadoDto;
import model.dto.ChamadoEditarDto;
import model.dto.ChamadoTabelaDto;

import java.sql.SQLException;
import java.util.List;

public class ChamadoController {

    private ChamadoDao chamadoDao;

    public ChamadoController() {
        chamadoDao = new ChamadoDao();
    }

    public boolean cadastrarChamado(Long idUsuario, String titulo, String descricao, Long idCliente) throws Exception {
        return chamadoDao.cadastrarChamado(idUsuario, titulo, descricao, idCliente);
    }

    public List<ChamadoTabelaDto> carregarChamadosAbertos() throws SQLException {
        return chamadoDao.carregarChamadosAbertos();
    }

    public List<ChamadoTabelaDto> carregarChamados() throws SQLException {
        return chamadoDao.carregarChamados();
    }

    public boolean removerChamadoPorId(Long idChamado) throws SQLException {
        return chamadoDao.removerChamadoPorId(idChamado);
    }

    public ChamadoEditarDto buscarChamadoPorId(Long idChamado) throws SQLException {
        return chamadoDao.buscarChamadoPorId(idChamado);
    }

    public boolean atualizarChamado(Long idUsuario, String titulo, String descricao, Long idCliente) throws SQLException {
        return chamadoDao.atualizarChamado(idUsuario, titulo, descricao, idCliente);
    }

    public ChamadoDto carregarChamadoAtendimentoPorId(Long idChamado) throws SQLException {
        return chamadoDao.carregarChamadoAtendimentoPorId(idChamado);
    }

    public boolean receberChamado(Long idChamado, Long idUsuario) throws SQLException {
        return chamadoDao.receberChamado(idChamado, idUsuario);
    }

    public boolean resolverChamado(Long idChamado, String solucao) throws SQLException {
        return chamadoDao.resolverChamado(idChamado, solucao);
    }

    public List<ChamadoTabelaDto> carregarChamadosAbertosPorMim(Long idUsuario) throws SQLException {
        return chamadoDao.carregarChamadosAbertosPorMim(idUsuario);
    }

    public List<ChamadoTabelaDto> carregarChamadosEmAtendimentoPorMim(Long idUsuario) throws SQLException {
        return chamadoDao.carregarChamadosEmAtendimentoPorMim(idUsuario);
    }
}
