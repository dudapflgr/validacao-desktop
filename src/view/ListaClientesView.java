/*
 * Created by JFormDesigner on Tue Jul 04 16:52:26 BRT 2023
 */

package view;

import java.awt.event.*;

import controller.ChamadoController;
import controller.ClienteController;
import model.dto.ChamadoTabelaDto;
import model.dto.ClienteDto;
import model.dto.UsuarioTabelaDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Duda
 */
public class ListaClientesView extends JFrame {

    private Long idUsuario;

    private List<ClienteDto> listaClientes;
    public ListaClientesView(Long idUsuario) {
        this.idUsuario = idUsuario;
        initComponents();
        carregarClientes();
    }

    private void carregarClientes() {
        ClienteController clienteController = new ClienteController();
        try {
            listaClientes = clienteController.buscarClientes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes!");
        }
        DefaultTableModel modeloTabela = (DefaultTableModel) tabelaClientes.getModel();
        modeloTabela.getDataVector().removeAllElements();
        for (ClienteDto cliente : listaClientes) {
            modeloTabela.addRow(new Object[]{
                    cliente.getCodCliente(), cliente.getNome(), cliente.getEmail()
            });
        }
        modeloTabela.fireTableDataChanged();
    }

    private void atualizarDeletar(MouseEvent e) {
        int botaoClicado = e.getButton();

        if (botaoClicado == 1) {
            int i = tabelaClientes.rowAtPoint(e.getPoint());
            Long idCliente = listaClientes.get(i).getIdCliente();
            CadastroClienteView cadastroClienteView = new CadastroClienteView(idUsuario, idCliente);
            cadastroClienteView.setVisible(true);
            cadastroClienteView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 3) {
            int i = tabelaClientes.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o cliente: " + listaClientes.get(i).getNome() + "?"
                    , "Deseja remover?", JOptionPane.YES_NO_OPTION);
            if (confirmacao == 0) {
                ClienteController clienteController = new ClienteController();
                try {
                    long idCliente = listaClientes.get(i).getIdCliente();
                    boolean removido = clienteController.removerClientePorId(idCliente);
                    if (removido) {
                        JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
                        ClienteDto chamadoTabelaDto = listaClientes.get(i);
                        listaClientes.remove(chamadoTabelaDto);
                        DefaultTableModel modeloTabela = (DefaultTableModel) tabelaClientes.getModel();
                        modeloTabela.getDataVector().removeAllElements();
                        for (ClienteDto cliente : listaClientes) {
                            modeloTabela.addRow(new Object[]{
                                    cliente.getCodCliente(), cliente.getNome(), cliente.getEmail()
                            });
                        }
                        modeloTabela.fireTableDataChanged();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cliente não removido. Cliente associado algum chamado!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void voltarIndex(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        tabelaClientes = new JTable();
        buttonVoltar = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

                //======== scrollPane1 ========
                {

                    //---- tabelaClientes ----
                    tabelaClientes.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "C\u00f3digo cliente", "Nome", "E-mail"
                        }
                    ) {
                        boolean[] columnEditable = new boolean[] {
                            false, false, false
                        };
                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    tabelaClientes.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            atualizarDeletar(e);
                        }
                    });
                    scrollPane1.setViewportView(tabelaClientes);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- buttonVoltar ----
                buttonVoltar.setText("Voltar");
                buttonVoltar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        voltarIndex(e);
                    }
                });
                contentPanel.add(buttonVoltar, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTable tabelaClientes;
    private JButton buttonVoltar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
