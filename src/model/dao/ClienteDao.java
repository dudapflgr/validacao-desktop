package model.dao;

import model.dto.ClienteCadastroDto;
import model.dto.ClienteDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    public Long idUltimoClienteCadastrado() throws SQLException {
        long ultimoId = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_cliente FROM cliente ORDER BY id_cliente DESC LIMIT 1");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ultimoId = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return ultimoId;
    }

    public boolean cadastrarCliente(ClienteCadastroDto cliente) throws SQLException {
        boolean cadastrou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("INSERT INTO cliente (cod_cliente, nome, email, cep, logradouro, numero, complemento, bairro, cidade, uf) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, cliente.getCodCliente());
            pstmt.setString(2, cliente.getNome());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getCep());
            pstmt.setString(5, cliente.getLogradouro());
            pstmt.setString(6, cliente.getNumero());
            pstmt.setString(7, cliente.getComplemento());
            pstmt.setString(8, cliente.getBairro());
            pstmt.setString(9, cliente.getCidade());
            pstmt.setString(10, cliente.getUf());
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

    public List<ClienteDto> buscarClientes() throws SQLException {
        List<ClienteDto> listaClientes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_cliente, cod_cliente, nome, email FROM cliente");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idCliente = rs.getLong(1);
                String codCliente = rs.getString(2);
                String nome = rs.getString(3);
                String email = rs.getString(4);
                ClienteDto cliente = new ClienteDto(idCliente, codCliente, nome, email);
                listaClientes.add(cliente);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return listaClientes;
    }

    public boolean removerClientePorId(long idCliente) throws SQLException {
        boolean apagou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("DELETE FROM cliente WHERE id_cliente = ?");
            pstmt.setLong(1, idCliente);
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

    public ClienteCadastroDto buscarClientePorId(Long idCliente) throws SQLException {
        ClienteCadastroDto cliente = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_cliente, cod_cliente, nome, email, cep, logradouro, numero, complemento, bairro, cidade, uf FROM cliente WHERE id_cliente = ?");
            pstmt.setLong(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idClienteBanco = rs.getLong(1);
                String codCliente = rs.getString(2);
                String nome = rs.getString(3);
                String email = rs.getString(4);
                String cep = rs.getString(5);
                String logradouro = rs.getString(6);
                String numero = rs.getString(7);
                String complemento = rs.getString(8);
                String bairro = rs.getString(9);
                String cidade = rs.getString(10);
                String uf = rs.getString(11);
                cliente = new ClienteCadastroDto(codCliente, nome, email, cep, logradouro, numero, complemento, bairro, cidade, uf);
                cliente.setIdCliente(idClienteBanco);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return cliente;
    }

    public boolean atualizarCliente(ClienteCadastroDto cliente) throws SQLException {
        boolean atualizou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_validacao_desktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE cliente SET nome = ?, email = ?, cep = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? WHERE id_cliente = ?");
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getCep());
            pstmt.setString(4, cliente.getLogradouro());
            pstmt.setString(5, cliente.getNumero());
            pstmt.setString(6, cliente.getComplemento());
            pstmt.setString(7, cliente.getBairro());
            pstmt.setString(8, cliente.getCidade());
            pstmt.setString(9, cliente.getUf());
            pstmt.setLong(10, cliente.getIdCliente());
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
}
