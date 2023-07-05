package controller;

import model.dao.ClienteDao;
import model.dto.ClienteCadastroDto;
import model.dto.ClienteDto;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    private ClienteDao clienteDao;

    public ClienteController() {
        this.clienteDao = new ClienteDao();
    }

    public Long idUltimoClienteCadastrado() throws SQLException {
        return clienteDao.idUltimoClienteCadastrado();
    }

    public boolean cadastrarCliente(ClienteCadastroDto cliente) throws SQLException {
        return clienteDao.cadastrarCliente(cliente);
    }

    public List<ClienteDto> buscarClientes() throws SQLException {
        return clienteDao.buscarClientes();
    }

    public boolean removerClientePorId(long idCliente) throws SQLException {
        return clienteDao.removerClientePorId(idCliente);
    }

    public ClienteCadastroDto buscarClientePorId(Long idCliente) throws SQLException {
        return clienteDao.buscarClientePorId(idCliente);
    }

    public boolean atualizarCliente(ClienteCadastroDto cliente) throws SQLException {
        return clienteDao.atualizarCliente(cliente);
    }
}
