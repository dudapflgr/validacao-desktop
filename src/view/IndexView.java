/*
 * Created by JFormDesigner on Sun Jun 25 01:13:20 BRT 2023
 */

package view;

import controller.ChamadoController;
import model.dto.ChamadoTabelaDto;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author Duda
 */
public class IndexView extends JFrame {

    private Long idUsuario;

    private List<ChamadoTabelaDto> listaChamados = new ArrayList<>();
    private List<ChamadoTabelaDto> listaChamadosAbertos = new ArrayList<>();
    private List<ChamadoTabelaDto> listaChamadosEmAtendimento = new ArrayList<>();

    public IndexView(Long idUsuario) {
        this.idUsuario = idUsuario;
        initComponents();
        carregarTodosChamadosAbertos();
        carregarChamadosAbertosPorMim();
        carregarChamadosEmAtendimentoPorMim();
    }

    private void carregarChamadosEmAtendimentoPorMim() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listaChamadosEmAtendimento = chamadoController.carregarChamadosEmAtendimentoPorMim(idUsuario);
            DefaultTableModel modeloTabela = (DefaultTableModel) tabelaChamadosAtendidos.getModel();
            for (ChamadoTabelaDto chamado : listaChamadosEmAtendimento) {
                modeloTabela.addRow(new Object[]{
                        chamado.getCodChamado(), chamado.getNomeCliente(), chamado.getTitulo(), chamado.getDataHoraChamado()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados por mim!");
        }
    }

    private void carregarChamadosAbertosPorMim() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listaChamadosAbertos = chamadoController.carregarChamadosAbertosPorMim(idUsuario);
            DefaultTableModel modeloTabela = (DefaultTableModel) tabelaChamadosAbertos.getModel();
            for (ChamadoTabelaDto chamado : listaChamadosAbertos) {
                modeloTabela.addRow(new Object[]{
                        chamado.getCodChamado(), chamado.getNomeCliente(), chamado.getTitulo(), chamado.getDataHoraChamado()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados por mim!");
        }
    }

    private void carregarTodosChamadosAbertos() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listaChamados = chamadoController.carregarChamadosAbertos();
            DefaultTableModel modeloTabela = (DefaultTableModel) tabelaChamados.getModel();
            for (ChamadoTabelaDto chamado : listaChamados) {
                modeloTabela.addRow(new Object[]{
                        chamado.getCodChamado(), chamado.getNomeCliente(), chamado.getTitulo(), chamado.getDataHoraChamado()
                });
            }
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTabela);
            tabelaChamados.setRowSorter(sorter);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
        }
    }

    private void atualizarTabelaTodosOsChamados(MouseEvent e) {
        boolean selecionado = botaoTodosChamados.isSelected();
        ChamadoController chamadoController = new ChamadoController();
        if (selecionado) {
            try {
                listaChamados = chamadoController.carregarChamados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
            }
        } else {
            try {
                listaChamados = chamadoController.carregarChamadosAbertos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
            }
        }
        atualizaTabela(tabelaChamados, listaChamados);
    }

    private void editarApagarChamado(MouseEvent e) {
        int botaoClicado = e.getButton();

        if (botaoClicado == 1) {
            int i = tabelaChamados.rowAtPoint(e.getPoint());
            Long idChamado = listaChamados.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUsuario, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 2) {
            int i = tabelaChamados.rowAtPoint(e.getPoint());
            Long idChamado = listaChamados.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 3) {
            int i = tabelaChamados.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o chamado: " + listaChamados.get(i).getTitulo() + "?"
                    , "Deseja remover?", JOptionPane.YES_NO_OPTION);
            if (confirmacao == 0) {
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean removido = chamadoController.removerChamadoPorId(listaChamados.get(i).getIdChamado());
                    if (removido) {
                        JOptionPane.showMessageDialog(this, "Chamado removido com sucesso!");
                        ChamadoTabelaDto chamadoTabelaDto = listaChamados.get(i);
                        listaChamados.remove(chamadoTabelaDto);
                        listaChamadosAbertos.remove(chamadoTabelaDto);
                        listaChamadosEmAtendimento.remove(chamadoTabelaDto);
                        atualizaTabela(tabelaChamados, listaChamados);
                        atualizaTabela(tabelaChamadosAbertos, listaChamadosAbertos);
                        atualizaTabela(tabelaChamadosAtendidos, listaChamadosEmAtendimento);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void atualizaTabela(JTable tabelaChamados, List<ChamadoTabelaDto> listaChamados) {
        DefaultTableModel modeloTabela = (DefaultTableModel) tabelaChamados.getModel();
        modeloTabela.getDataVector().removeAllElements();
        for (ChamadoTabelaDto chamado : listaChamados) {
            modeloTabela.addRow(new Object[]{
                    chamado.getCodChamado(), chamado.getNomeCliente(), chamado.getTitulo(), chamado.getDataHoraChamado()
            });
        }
        modeloTabela.fireTableDataChanged();
    }

    private void cadastrarChamado(MouseEvent e) {
        ChamadoView chamadoView = new ChamadoView(null, idUsuario, null);
        chamadoView.setVisible(true);
        chamadoView.setLocationRelativeTo(null);
        dispose();
    }

    private void abrirModalCadastroChamado(ActionEvent e) {
        ChamadoView chamadoView = new ChamadoView(null, idUsuario, null);
        chamadoView.setVisible(true);
        chamadoView.setLocationRelativeTo(null);
        dispose();
    }

    private void acessarCadastroCliente(ActionEvent e) {
        CadastroClienteView cadastroClienteView = new CadastroClienteView(idUsuario, null);
        cadastroClienteView.setVisible(true);
        cadastroClienteView.setLocationRelativeTo(null);
        dispose();
    }

    private void editarApagarChamadoAbertoPorMim(MouseEvent e) {
        int botaoClicado = e.getButton();

        if (botaoClicado == 1) {
            int i = tabelaChamadosAbertos.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAbertos.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUsuario, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 2) {
            int i = tabelaChamadosAbertos.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAbertos.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 3) {
            int i = tabelaChamadosAbertos.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o chamado: " + listaChamadosAbertos.get(i).getTitulo() + "?"
                    , "Deseja remover?", JOptionPane.YES_NO_OPTION);
            if (confirmacao == 0) {
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean removido = chamadoController.removerChamadoPorId(listaChamadosAbertos.get(i).getIdChamado());
                    if (removido) {
                        JOptionPane.showMessageDialog(this, "Chamado removido com sucesso!");
                        ChamadoTabelaDto chamadoTabelaDto = listaChamadosAbertos.get(i);
                        listaChamados.remove(chamadoTabelaDto);
                        listaChamadosAbertos.remove(chamadoTabelaDto);
                        listaChamadosEmAtendimento.remove(chamadoTabelaDto);
                        atualizaTabela(tabelaChamados, listaChamados);
                        atualizaTabela(tabelaChamadosAbertos, listaChamadosAbertos);
                        atualizaTabela(tabelaChamadosAtendidos, listaChamadosEmAtendimento);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void editarAtualizarChamadosAtendidos(MouseEvent e) {
        int botaoClicado = e.getButton();

        if (botaoClicado == 1) {
            int i = tabelaChamadosAtendidos.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosEmAtendimento.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUsuario, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 2) {
            int i = tabelaChamadosAtendidos.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosEmAtendimento.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 3) {
            int i = tabelaChamadosAtendidos.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o chamado: " + listaChamadosEmAtendimento.get(i).getTitulo() + "?"
                    , "Deseja remover?", JOptionPane.YES_NO_OPTION);
            if (confirmacao == 0) {
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean removido = chamadoController.removerChamadoPorId(listaChamadosEmAtendimento.get(i).getIdChamado());
                    if (removido) {
                        JOptionPane.showMessageDialog(this, "Chamado removido com sucesso!");
                        ChamadoTabelaDto chamadoTabelaDto = listaChamadosEmAtendimento.get(i);
                        listaChamados.remove(chamadoTabelaDto);
                        listaChamadosAbertos.remove(chamadoTabelaDto);
                        listaChamadosEmAtendimento.remove(chamadoTabelaDto);
                        atualizaTabela(tabelaChamados, listaChamados);
                        atualizaTabela(tabelaChamadosAbertos, listaChamadosAbertos);
                        atualizaTabela(tabelaChamadosAtendidos, listaChamadosEmAtendimento);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void cadastrarUsuario(ActionEvent e) {
        CadastroUsuarioView cadastroUsuarioView = new CadastroUsuarioView(idUsuario, null);
        cadastroUsuarioView.setVisible(true);
        cadastroUsuarioView.setLocationRelativeTo(null);
        dispose();
    }

    private void atualizarSenha(MouseEvent e) {
        CadastroUsuarioView cadastroUsuarioView = new CadastroUsuarioView(idUsuario, idUsuario);
        cadastroUsuarioView.setVisible(true);
        cadastroUsuarioView.setLocationRelativeTo(null);
        dispose();
    }

    private void atualizarSenha(ActionEvent e) {
        CadastroUsuarioView cadastroUsuarioView = new CadastroUsuarioView(idUsuario, idUsuario);
        cadastroUsuarioView.setVisible(true);
        cadastroUsuarioView.setLocationRelativeTo(null);
        dispose();
    }

    private void visualizarUsuarios(ActionEvent e) {
        ListaUsuariosView usuariosView = new ListaUsuariosView(idUsuario);
        usuariosView.setVisible(true);
        usuariosView.setLocationRelativeTo(null);
    }

    private void abrirListaClientes(ActionEvent e) {
        ListaClientesView clientesView = new ListaClientesView(idUsuario);
        clientesView.setVisible(true);
        clientesView.setLocationRelativeTo(null);
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        content = new JPanel();
        panel2 = new JPanel();
        panel1 = new JPanel();
        menuBar1 = new JMenuBar();
        menu = new JMenu();
        menuCadastrarChamado = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItemAtualizarSenha = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        scrollPane1 = new JScrollPane();
        tabelaChamados = new JTable();
        desktopPane1 = new JDesktopPane();
        internalFrame1 = new JInternalFrame();
        scrollPane2 = new JScrollPane();
        tabelaChamadosAbertos = new JTable();
        desktopPane2 = new JDesktopPane();
        internalFrame2 = new JInternalFrame();
        scrollPane3 = new JScrollPane();
        tabelaChamadosAtendidos = new JTable();
        botaoTodosChamados = new JRadioButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1366, 768));
        setPreferredSize(new Dimension(1366, 768));
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(1, 1));

        //======== content ========
        {
            content.setLayout(new GridLayout());

            //======== panel2 ========
            {
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 918, 68};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 0.0};

                //======== panel1 ========
                {
                    panel1.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {62, 579, 0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0E-4};
                    ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0E-4};

                    //======== menuBar1 ========
                    {

                        //======== menu ========
                        {
                            menu.setText("A\u00e7\u00f5es");

                            //---- menuCadastrarChamado ----
                            menuCadastrarChamado.setText("Cadastrar chamado");
                            menuCadastrarChamado.addActionListener(e -> abrirModalCadastroChamado(e));
                            menu.add(menuCadastrarChamado);

                            //---- menuItem2 ----
                            menuItem2.setText("Cadastrar usu\u00e1rio");
                            menuItem2.addActionListener(e -> cadastrarUsuario(e));
                            menu.add(menuItem2);

                            //---- menuItem3 ----
                            menuItem3.setText("Visualizar usu\u00e1rios");
                            menuItem3.addActionListener(e -> visualizarUsuarios(e));
                            menu.add(menuItem3);

                            //---- menuItemAtualizarSenha ----
                            menuItemAtualizarSenha.setText("Atualizar senha");
                            menuItemAtualizarSenha.addActionListener(e -> atualizarSenha(e));
                            menu.add(menuItemAtualizarSenha);

                            //---- menuItem4 ----
                            menuItem4.setText("Cadastrar cliente");
                            menuItem4.addActionListener(e -> acessarCadastroCliente(e));
                            menu.add(menuItem4);

                            //---- menuItem5 ----
                            menuItem5.setText("Visualizar clientes");
                            menuItem5.addActionListener(e -> abrirListaClientes(e));
                            menu.add(menuItem5);
                        }
                        menuBar1.add(menu);
                    }
                    panel1.add(menuBar1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //======== scrollPane1 ========
                    {

                        //---- tabelaChamados ----
                        tabelaChamados.setModel(new DefaultTableModel(
                            new Object[][] {
                            },
                            new String[] {
                                "C\u00f3digo do chamado", "Nome do cliente", "T\u00edtulo", "Data e hora abertura"
                            }
                        ) {
                            boolean[] columnEditable = new boolean[] {
                                true, false, false, false
                            };
                            @Override
                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return columnEditable[columnIndex];
                            }
                        });
                        tabelaChamados.setEnabled(false);
                        tabelaChamados.setColumnSelectionAllowed(true);
                        tabelaChamados.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                editarApagarChamado(e);
                            }
                        });
                        scrollPane1.setViewportView(tabelaChamados);
                    }
                    panel1.add(scrollPane1, new GridBagConstraints(1, 1, 1, 4, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //======== desktopPane1 ========
                    {

                        //======== internalFrame1 ========
                        {
                            internalFrame1.setVisible(true);
                            internalFrame1.setTitle("Chamados abertos por mim");
                            internalFrame1.setMaximizable(true);
                            internalFrame1.setIconifiable(true);
                            internalFrame1.setClosable(true);
                            var internalFrame1ContentPane = internalFrame1.getContentPane();
                            internalFrame1ContentPane.setLayout(new GridBagLayout());
                            ((GridBagLayout)internalFrame1ContentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
                            ((GridBagLayout)internalFrame1ContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                            ((GridBagLayout)internalFrame1ContentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                            ((GridBagLayout)internalFrame1ContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};

                            //======== scrollPane2 ========
                            {

                                //---- tabelaChamadosAbertos ----
                                tabelaChamadosAbertos.setModel(new DefaultTableModel(
                                    new Object[][] {
                                    },
                                    new String[] {
                                        "C\u00f3digo do chamado", "Nome do cliente", "T\u00edtulo", "Data e hora abertura"
                                    }
                                ) {
                                    boolean[] columnEditable = new boolean[] {
                                        false, false, false, true
                                    };
                                    @Override
                                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                                        return columnEditable[columnIndex];
                                    }
                                });
                                tabelaChamadosAbertos.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        editarApagarChamadoAbertoPorMim(e);
                                    }
                                });
                                scrollPane2.setViewportView(tabelaChamadosAbertos);
                            }
                            internalFrame1ContentPane.add(scrollPane2, new GridBagConstraints(0, 0, 2, 3, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        desktopPane1.add(internalFrame1, JLayeredPane.DEFAULT_LAYER);
                        internalFrame1.setBounds(0, 0, 345, 195);
                    }
                    panel1.add(desktopPane1, new GridBagConstraints(3, 1, 2, 2, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //======== desktopPane2 ========
                    {

                        //======== internalFrame2 ========
                        {
                            internalFrame2.setVisible(true);
                            internalFrame2.setClosable(true);
                            internalFrame2.setIconifiable(true);
                            internalFrame2.setMaximizable(true);
                            internalFrame2.setResizable(true);
                            internalFrame2.setTitle("Chamados em atendimento por mim");
                            var internalFrame2ContentPane = internalFrame2.getContentPane();
                            internalFrame2ContentPane.setLayout(new GridBagLayout());
                            ((GridBagLayout)internalFrame2ContentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
                            ((GridBagLayout)internalFrame2ContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                            ((GridBagLayout)internalFrame2ContentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                            ((GridBagLayout)internalFrame2ContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};

                            //======== scrollPane3 ========
                            {

                                //---- tabelaChamadosAtendidos ----
                                tabelaChamadosAtendidos.setModel(new DefaultTableModel(
                                    new Object[][] {
                                    },
                                    new String[] {
                                        "C\u00f3digo do chamado", "Nome do cliente", "T\u00edtulo", "Data e hora abertura"
                                    }
                                ) {
                                    boolean[] columnEditable = new boolean[] {
                                        false, true, false, false
                                    };
                                    @Override
                                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                                        return columnEditable[columnIndex];
                                    }
                                });
                                tabelaChamadosAtendidos.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        editarAtualizarChamadosAtendidos(e);
                                    }
                                });
                                scrollPane3.setViewportView(tabelaChamadosAtendidos);
                            }
                            internalFrame2ContentPane.add(scrollPane3, new GridBagConstraints(0, 0, 2, 3, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        desktopPane2.add(internalFrame2, JLayeredPane.DEFAULT_LAYER);
                        internalFrame2.setBounds(0, 0, 345, 175);
                    }
                    panel1.add(desktopPane2, new GridBagConstraints(3, 4, 2, 2, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- botaoTodosChamados ----
                    botaoTodosChamados.setText("Todos os chamados");
                    botaoTodosChamados.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            atualizarTabelaTodosOsChamados(e);
                        }
                    });
                    panel1.add(botaoTodosChamados, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                }
                panel2.add(panel1, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
            }
            content.add(panel2);
        }
        contentPane.add(content);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel content;
    private JPanel panel2;
    private JPanel panel1;
    private JMenuBar menuBar1;
    private JMenu menu;
    private JMenuItem menuCadastrarChamado;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItemAtualizarSenha;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JScrollPane scrollPane1;
    private JTable tabelaChamados;
    private JDesktopPane desktopPane1;
    private JInternalFrame internalFrame1;
    private JScrollPane scrollPane2;
    private JTable tabelaChamadosAbertos;
    private JDesktopPane desktopPane2;
    private JInternalFrame internalFrame2;
    private JScrollPane scrollPane3;
    private JTable tabelaChamadosAtendidos;
    private JRadioButton botaoTodosChamados;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
