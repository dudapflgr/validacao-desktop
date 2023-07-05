package model.dao;

import model.dto.ChamadoDto;
import model.dto.ChamadoEditarDto;
import model.dto.ChamadoTabelaDto;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDao {

    public boolean cadastrarChamado(Long idUsuario, String titulo, String descricao, Long idCliente) throws Exception {
        boolean cadastrou = false;
        Integer chamados = numeroChamadosSistema();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("INSERT INTO chamado (cod_chamado, id_cliente, titulo, descricao, data_abertura, id_usuario_requisitante) " +
                    "VALUES (?,?,?,?,?,?)");
            String codChamado = "CH"+idUsuario.toString()+""+chamados;
            pstmt.setString(1, codChamado);
            pstmt.setLong(2, idCliente);
            pstmt.setString(3, titulo);
            pstmt.setString(4, descricao);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            pstmt.setTimestamp(5, timestamp);
            pstmt.setLong(6, idUsuario);
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

    public int numeroChamadosSistema() throws SQLException {
        int numeroChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamado");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numeroChamados;
    }

    public List<ChamadoTabelaDto> carregarChamadosAbertos() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamadosAbertos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, cliente.nome, data_abertura, titulo FROM chamado INNER JOIN cliente ON chamado.id_cliente = cliente.id_cliente WHERE data_fechamento IS NULL ORDER BY data_abertura");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codChamado = rs.getString(2);
                String nomeCliente = rs.getString(3);
                Timestamp dataHoraAbertura = rs.getTimestamp(4);
                String dataFormatada = simpleDateFormat.format(dataHoraAbertura);
                String titulo = rs.getString(5);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codChamado, nomeCliente, dataFormatada, titulo);
                chamadosAbertos.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamadosAbertos;
    }

    public List<ChamadoTabelaDto> carregarChamados() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, cliente.nome, data_abertura, titulo FROM chamado INNER JOIN cliente ON chamado.id_cliente = cliente.id_cliente ORDER BY data_abertura");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codChamado = rs.getString(2);
                String nomeCliente = rs.getString(3);
                Timestamp dataHoraAbertura = rs.getTimestamp(4);
                String dataFormatada = simpleDateFormat.format(dataHoraAbertura);
                String titulo = rs.getString(5);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codChamado, nomeCliente, dataFormatada, titulo);
                chamados.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamados;
    }

    public boolean removerChamadoPorId(Long idChamado) throws SQLException {
        boolean apagou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("DELETE FROM chamado WHERE id_chamado = ?");
            pstmt.setLong(1, idChamado);
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

    public ChamadoEditarDto buscarChamadoPorId(Long idChamado) throws SQLException {
        ChamadoEditarDto chamado = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, titulo, descricao, id_cliente, data_fechamento IS NOT NULL FROM chamado WHERE id_chamado = ?");
            pstmt.setLong(1, idChamado);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong(1);
                String codChamado = rs.getString(2);
                String titulo = rs.getString(3);
                String descricao = rs.getString(4);
                Long idCliente = rs.getLong(5);
                boolean fechado = rs.getBoolean(6);
                chamado = new ChamadoEditarDto(id, codChamado, titulo, descricao, idCliente, fechado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamado;
    }

    public boolean atualizarChamado(Long idUsuario, String titulo, String descricao, Long idCliente) throws SQLException {
        boolean atualizou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamado SET titulo = ?, descricao = ?, id_cliente = ? WHERE id_chamado = ? ");
            pstmt.setString(1, titulo);
            pstmt.setString(2, descricao);
            pstmt.setLong(3, idCliente);
            pstmt.setLong(4, idUsuario);
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

    public ChamadoDto carregarChamadoAtendimentoPorId(Long idChamado) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ChamadoDto chamado = new ChamadoDto();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, titulo, descricao, data_abertura, nome, descricao_solucao, data_fechamento, id_usuario_tecnico, " +
                    "(SELECT nome FROM usuario WHERE usuario.id_usuario = ch.id_usuario_tecnico) AS nome_tecnico," +
                    "(SELECT nome FROM cliente WHERE cliente.id_cliente = ch.id_cliente) AS nome_cliente " +
                    "FROM chamado AS ch INNER JOIN usuario " +
                    "ON ch.id_usuario_requisitante = usuario.id_usuario WHERE id_chamado = ?");
            pstmt.setLong(1, idChamado);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong(1);
                String codChamado = rs.getString(2);
                String titulo = rs.getString(3);
                String descricao = rs.getString(4);
                Timestamp dataHoraAbertura = rs.getTimestamp(5);
                String dataAberturaFormatada = simpleDateFormat.format(dataHoraAbertura);
                String nomeUsuario = rs.getString(6);
                String descricaoSolucao = rs.getString(7);
                Timestamp dataHoraFechamento = rs.getTimestamp(8);
                String dataFechamentoFormatada = "";
                if (dataHoraFechamento != null) {
                    dataFechamentoFormatada = simpleDateFormat.format(dataHoraFechamento);
                }
                Long idUsuarioAtendente = rs.getLong(9);
                String nomeTecnico = rs.getString(10);
                String nomeCliente = rs.getString(11);
                chamado.setIdChamado(id);
                chamado.setCodChamado(codChamado);
                chamado.setTitulo(titulo);
                chamado.setDescricao(descricao);
                chamado.setDataHoraAbertura(dataAberturaFormatada);
                chamado.setUsuarioRequisitante(nomeUsuario);
                chamado.setSolucao(descricaoSolucao);
                chamado.setDataHoraFechamento(dataFechamentoFormatada);
                chamado.setIdUsuarioAtendente(idUsuarioAtendente);
                chamado.setNomeUsuarioAtendente(nomeTecnico);
                chamado.setNomeCliente(nomeCliente);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamado;
    }

    public boolean receberChamado(Long idChamado, Long idUsuario) throws SQLException {
        boolean recebido = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamado SET id_usuario_tecnico = ? WHERE id_chamado = ? ");
            pstmt.setLong(1, idUsuario);
            pstmt.setLong(2, idChamado);
            pstmt.executeUpdate();
            recebido = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return recebido;
    }

    public boolean resolverChamado(Long idChamado, String solucao) throws SQLException {
        boolean resolvido = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamado SET descricao_solucao = ?, data_fechamento = ? WHERE id_chamado = ? ");
            pstmt.setString(1, solucao);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            pstmt.setTimestamp(2, timestamp);
            pstmt.setLong(3, idChamado);
            pstmt.executeUpdate();
            resolvido = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return resolvido;
    }

    public List<ChamadoTabelaDto> carregarChamadosAbertosPorMim(Long idUsuario) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamadosAbertos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, cliente.nome, data_abertura, titulo FROM chamado INNER JOIN cliente ON chamado.id_cliente = cliente.id_cliente WHERE id_usuario_requisitante = ? ORDER BY data_abertura");
            pstmt.setLong(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codChamado = rs.getString(2);
                String nomeCliente = rs.getString(3);
                Timestamp dataHoraAbertura = rs.getTimestamp(4);
                String dataFormatada = simpleDateFormat.format(dataHoraAbertura);
                String titulo = rs.getString(5);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codChamado, nomeCliente, dataFormatada, titulo);
                chamadosAbertos.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamadosAbertos;
    }

    public List<ChamadoTabelaDto> carregarChamadosEmAtendimentoPorMim(Long idUsuario) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamadosAtendidos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, cliente.nome, data_abertura, titulo FROM chamado INNER JOIN cliente ON chamado.id_cliente = cliente.id_cliente WHERE id_usuario_tecnico = ? AND data_fechamento IS NULL ORDER BY data_abertura");
            pstmt.setLong(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codChamado = rs.getString(2);
                String nomeCliente = rs.getString(3);
                Timestamp dataHoraAbertura = rs.getTimestamp(4);
                String dataFormatada = simpleDateFormat.format(dataHoraAbertura);
                String titulo = rs.getString(5);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codChamado, nomeCliente, dataFormatada, titulo);
                chamadosAtendidos.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamadosAtendidos;
    }

    public int numeroChamadosEmAtendimento() throws SQLException {
        int numeroChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamado WHERE id_usuario_tecnico IS NOT NULL AND data_fechamento IS NULL");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numeroChamados;
    }

    public int numeroChamadosResolvidos() throws SQLException {
        int numeroChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamado WHERE data_fechamento IS NOT NULL");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numeroChamados;
    }

    public int numeroChamadosResolvidosHoje() throws SQLException {
        int numeroChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamado WHERE data_fechamento IS NOT NULL AND DATE(data_fechamento) = CURDATE() ");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numeroChamados;
    }

    public int buscarTotalChamadosAbertosPorUsuario(Long idUsuario) throws SQLException {
        int numeroChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamado WHERE id_usuario_requisitante = ?");
            pstmt.setLong(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numeroChamados;
    }

    public int buscarTotalChamadosAtendimentoPorUsuario(Long idUsuario) throws SQLException {
        int numeroChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamado WHERE id_usuario_tecnico = ? AND data_fechamento IS NULL");
            pstmt.setLong(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numeroChamados;
    }

    public int buscarTotalChamadosResolvidosPorUsuario(Long idUsuario) throws SQLException {
        int numeroChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamado WHERE id_usuario_tecnico = ? AND data_fechamento IS NOT NULL");
            pstmt.setLong(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numeroChamados;
    }
}
