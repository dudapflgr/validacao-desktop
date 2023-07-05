/*
 * Created by JFormDesigner on Sat Jul 01 16:26:47 BRT 2023
 */

package view;

import controller.ChamadoController;
import controller.ClienteController;
import model.dto.ChamadoEditarDto;
import model.dto.ClienteDto;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author dudap
 */
public class ChamadoView extends JDialog {

    private Long idUsuario;
    private Long idChamado;

    private List<ClienteDto> listaClientes = new ArrayList<>();

    public ChamadoView(Window owner, Long idUsuario, Long idChamado) {
        super(owner);
        this.idUsuario = idUsuario;
        this.idChamado = idChamado;
        initComponents();
        carregarClientes();
        carregarChamado(idChamado);
    }

    private void carregarClientes() {
        ClienteController clienteController = new ClienteController();
        try {
            listaClientes = clienteController.buscarClientes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os clientes!");
        }
        for (ClienteDto cliente : listaClientes) {
            String infos = cliente.getCodCliente()+" - "+cliente.getNome();
            comboBoxCliente.addItem(infos);
        }
    }

    private void carregarChamado(Long idChamado) {
        if (idChamado != null) {
            okButton.setText("Editar");
            ChamadoController chamadoController = new ChamadoController();
            try {
                ChamadoEditarDto chamado = chamadoController.buscarChamadoPorId(idChamado);
                textCodigo.setText(chamado.getCodChamado());
                textTitulo.setText(chamado.getTitulo());
                textDescricao.setText(chamado.getDescricao());
                if (chamado.getFechado()) {
                    textTitulo.setEditable(false);
                    textDescricao.setEditable(false);
                    ClienteDto cliente = null;
                    for (ClienteDto c : listaClientes) {
                        if (c.getIdCliente().equals(chamado.getIdCliente())) {
                            cliente = c;
                        }
                    }
                    String infos = cliente.getCodCliente()+" - "+cliente.getNome();
                    comboBoxCliente.setSelectedItem(infos);
                    comboBoxCliente.setEditable(false);
                    comboBoxCliente.setEnabled(false);
                    okButton.setText("Chamado fechado!");
                    okButton.setEnabled(false);
                    okButton.setSelected(false);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void fecharModal(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void cadastrarEditarChamado(MouseEvent e) {
        if (okButton.isEnabled()) {
            String erros = "";

            if (textTitulo.getText().isBlank()) {
                erros += "Por favor insira um título!\n";
            }

            if (textTitulo.getText().length() > 45) {
                erros += "Tamanho do título maior que 45 caracteres!\n";
            }

            if(textDescricao.getText().isBlank()) {
                erros += "Por favor insira uma descrição!\n";
            }

            if (textDescricao.getText().length() > 100) {
                erros += "Tamanho da descrição maior que 100 caracteres!\n";
            }

            if (comboBoxCliente.getSelectedIndex() == 0) {
                erros += "Selecione um cliente!\n";
            }

            if (!erros.isBlank()) {
                JOptionPane.showMessageDialog(this, erros);
            } else {
                if (idChamado == null) {
                    try {
                        ChamadoController chamadoController = new ChamadoController();
                        Long idCliente = listaClientes.get(comboBoxCliente.getSelectedIndex()-1).getIdCliente();
                        boolean cadastrou = chamadoController.cadastrarChamado(idUsuario, textTitulo.getText(), textDescricao.getText(), idCliente);
                        if (cadastrou) {
                            JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!");
                            IndexView mainView = new IndexView(idUsuario);
                            mainView.setVisible(true);
                            mainView.setLocationRelativeTo(null);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Erro ao fazer o cadastro!");
                        }
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(this, "Erro ao cadastrar o chamado!");
                    }
                } else {
                    try {
                        ChamadoController chamadoController = new ChamadoController();
                        Long idCliente = listaClientes.get(comboBoxCliente.getSelectedIndex()-1).getIdCliente();
                        boolean atualizou = chamadoController.atualizarChamado(idUsuario, textTitulo.getText(), textDescricao.getText(), idCliente);
                        if (atualizou) {
                            JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
                            IndexView mainView = new IndexView(idUsuario);
                            mainView.setVisible(true);
                            mainView.setLocationRelativeTo(null);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Erro ao atualizar o chamado!");
                        }
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(this, "Erro ao editar o chamado!");
                    }
                }
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textCodigo = new JTextField();
        label3 = new JLabel();
        textTitulo = new JTextField();
        label2 = new JLabel();
        comboBoxCliente = new JComboBox<>();
        label4 = new JLabel();
        scrollPane1 = new JScrollPane();
        textDescricao = new JTextArea();
        buttonBar = new JPanel();
        hSpacer1 = new JPanel(null);
        okButton = new JButton();
        hSpacer3 = new JPanel(null);
        cancelButton = new JButton();
        hSpacer2 = new JPanel(null);

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {568, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 229, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00f3digo do chamado");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- textCodigo ----
                textCodigo.setEditable(false);
                contentPanel.add(textCodigo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- label3 ----
                label3.setText("T\u00edtulo");
                contentPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
                contentPanel.add(textTitulo, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- label2 ----
                label2.setText("Cliente");
                contentPanel.add(label2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- comboBoxCliente ----
                comboBoxCliente.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Selecione"
                }));
                comboBoxCliente.setMaximumRowCount(5);
                contentPanel.add(comboBoxCliente, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- label4 ----
                label4.setText("Descri\u00e7\u00e3o");
                contentPanel.add(label4, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(textDescricao);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 7, 1, 1, 0.0, 229.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 0, 85, 0};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0};
                buttonBar.add(hSpacer1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- okButton ----
                okButton.setText("Cadastrar");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cadastrarEditarChamado(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                buttonBar.add(hSpacer3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancelar");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        fecharModal(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                buttonBar.add(hSpacer2, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField textCodigo;
    private JLabel label3;
    private JTextField textTitulo;
    private JLabel label2;
    private JComboBox<String> comboBoxCliente;
    private JLabel label4;
    private JScrollPane scrollPane1;
    private JTextArea textDescricao;
    private JPanel buttonBar;
    private JPanel hSpacer1;
    private JButton okButton;
    private JPanel hSpacer3;
    private JButton cancelButton;
    private JPanel hSpacer2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
