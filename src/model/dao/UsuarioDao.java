package model.dao;

import model.dto.UsuarioEditarDto;
import model.dto.UsuarioTabelaDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public Long logar(String login, String senha) throws SQLException {
        Long idUsuario = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_usuario FROM usuario WHERE login LIKE ? AND senha LIKE ?");
            pstmt.setString(1, login);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idUsuario = rs.getLong("id_usuario");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return idUsuario;
    }

    public boolean cadastrarUsuario(String codUsuario, String nome, String email, String login, String senhaHash) throws SQLException {
        boolean cadastrou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("INSERT INTO usuario (cod_usuario, nome, email, senha, login) " +
                    "VALUES (?,?,?,?,?)");
            pstmt.setString(1, codUsuario);
            pstmt.setString(2, nome);
            pstmt.setString(3, email);
            pstmt.setString(4, senhaHash);
            pstmt.setString(5, login);
            pstmt.executeUpdate();
            cadastrou = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return cadastrou;
    }

    public UsuarioEditarDto carregarUsuarioPorId(Long idUsuarioEditar) throws SQLException {
        UsuarioEditarDto usuarioEditarDto = new UsuarioEditarDto();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_usuario, cod_usuario, nome, login, email FROM usuario WHERE id_usuario = ?");
            pstmt.setLong(1, idUsuarioEditar);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idUsuario = rs.getLong("id_usuario");
                String codUsuario = rs.getString("cod_usuario");
                String nome = rs.getString("nome");
                String login = rs.getString("login");
                String email = rs.getString("email");
                usuarioEditarDto.setIdUsuario(idUsuario);
                usuarioEditarDto.setCodUsuario(codUsuario);
                usuarioEditarDto.setNome(nome);
                usuarioEditarDto.setLogin(login);
                usuarioEditarDto.setEmail(email);
                usuarioEditarDto.setSenha("");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return usuarioEditarDto;
    }

    public boolean atualizarSenha(Long idUsuarioEditar, String senhaHash) throws SQLException {
        boolean atualizou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE usuario SET senha = ? WHERE id_usuario = ? ");
            pstmt.setString(1, senhaHash);
            pstmt.setLong(2, idUsuarioEditar);
            pstmt.executeUpdate();
            atualizou = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return atualizou;
    }

    public List<UsuarioTabelaDto> buscarUsuarios() throws SQLException {
        List<UsuarioTabelaDto> usuarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_usuario, cod_usuario, nome, login, email FROM usuario");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idUsuario = rs.getLong("id_usuario");
                String codUsuario = rs.getString("cod_usuario");
                String nome = rs.getString("nome");
                String login = rs.getString("login");
                String email = rs.getString("email");
                UsuarioTabelaDto usuario = new UsuarioTabelaDto();
                usuario.setIdUsuario(idUsuario);
                usuario.setCodUsuario(codUsuario);
                usuario.setNome(nome);
                usuario.setLogin(login);
                usuario.setEmail(email);
                usuarios.add(usuario);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return usuarios;
    }

    public boolean apagarUsuario(Long idUsuarioApagar) throws SQLException {
        boolean apagou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
            pstmt.setLong(1, idUsuarioApagar);
            pstmt.executeUpdate();
            apagou = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return apagou;
    }
}
