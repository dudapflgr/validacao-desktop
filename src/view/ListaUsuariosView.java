/*
 * Created by JFormDesigner on Tue Jul 04 16:22:35 BRT 2023
 */

package view;

import java.awt.event.*;
import controller.UsuarioController;
import model.dto.ChamadoTabelaDto;
import model.dto.UsuarioTabelaDto;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author Duda
 */
public class ListaUsuariosView extends JFrame {

    private Long idUsuario;

    List<UsuarioTabelaDto> listaUsuarios = new ArrayList<>();
    public ListaUsuariosView(Long idUsuario) {
        this.idUsuario = idUsuario;
        initComponents();
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        UsuarioController usuarioController = new UsuarioController();
        listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios = usuarioController.buscarUsuarios();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar usuários!");
        }
        DefaultTableModel modeloTabela = (DefaultTableModel) tableUsuarios.getModel();
        modeloTabela.getDataVector().removeAllElements();
        for (UsuarioTabelaDto usuario : listaUsuarios) {
            modeloTabela.addRow(new Object[]{
                    usuario.getCodUsuario(), usuario.getNome(), usuario.getLogin(), usuario.getEmail()
            });
        }
        modeloTabela.fireTableDataChanged();
    }

    private void apagarUsuario(MouseEvent e) {
        int botaoClicado = e.getButton();
        if (botaoClicado == 3) {
            int i = tableUsuarios.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o usuário: " + listaUsuarios.get(i).getNome() + "?"
                    , "Deseja remover?", JOptionPane.YES_NO_OPTION);
            if (confirmacao == 0) {
                Long idUsuarioApagar = listaUsuarios.get(i).getIdUsuario();
                if (idUsuarioApagar.equals(idUsuario)) {
                    JOptionPane.showMessageDialog(null, "Você não pode remover o usuário que está logado!");
                } else {
                    try {
                        UsuarioController usuarioController = new UsuarioController();
                        boolean apagou = usuarioController.apagarUsuario(idUsuarioApagar);
                        if (apagou) {
                            JOptionPane.showMessageDialog(null, "Usuário apagado com sucesso!");
                            UsuarioTabelaDto usuarioApagar = listaUsuarios.get(i);
                            listaUsuarios.remove(usuarioApagar);
                            DefaultTableModel modeloTabela = (DefaultTableModel) tableUsuarios.getModel();
                            modeloTabela.getDataVector().removeAllElements();
                            for (UsuarioTabelaDto usuario : listaUsuarios) {
                                modeloTabela.addRow(new Object[]{
                                        usuario.getCodUsuario(), usuario.getNome(), usuario.getLogin(), usuario.getEmail()
                                });
                            }
                            modeloTabela.fireTableDataChanged();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao apagar usuário. Ele pode estar associado a um chamado!");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro inesperado!");
                    }
                }
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        tableUsuarios = new JTable();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

        //======== scrollPane1 ========
        {

            //---- tableUsuarios ----
            tableUsuarios.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "C\u00f3digo usu\u00e1rio", "Nome", "Login", "E-mail"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            tableUsuarios.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    apagarUsuario(e);
                }
            });
            scrollPane1.setViewportView(tableUsuarios);
        }
        contentPane.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        setSize(520, 310);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTable tableUsuarios;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
