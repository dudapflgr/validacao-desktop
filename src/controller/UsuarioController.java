package controller;

import model.dao.UsuarioDao;
import model.dto.UsuarioEditarDto;
import model.dto.UsuarioTabelaDto;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {

    private UsuarioDao usuarioDao;

    public UsuarioController() {
        usuarioDao = new UsuarioDao();
    }

    public Long logar(String login, String senha) throws SQLException {
        return usuarioDao.logar(login, senha);
    }

    public boolean cadastrarUsuario(String codUsuario, String nome, String email, String login, String senhaHash) throws SQLException {
        return usuarioDao.cadastrarUsuario(codUsuario, nome, email, login, senhaHash);
    }

    public UsuarioEditarDto carregarUsuarioPorId(Long idUsuarioEditar) throws SQLException {
        return usuarioDao.carregarUsuarioPorId(idUsuarioEditar);
    }

    public boolean atualizarSenha(Long idUsuarioEditar, String senhaHash) throws SQLException {
        return usuarioDao.atualizarSenha(idUsuarioEditar, senhaHash);
    }

    public List<UsuarioTabelaDto> buscarUsuarios() throws SQLException {
        return usuarioDao.buscarUsuarios();
    }

    public boolean apagarUsuario(Long idUsuarioApagar) throws SQLException {
        return usuarioDao.apagarUsuario(idUsuarioApagar);
    }
}
