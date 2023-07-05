/*
 * Created by JFormDesigner on Sun Jul 02 01:33:30 BRT 2023
 */

package view;

import controller.ChamadoController;
import model.dto.ChamadoDto;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author dudap
 */
public class AtendimentoChamadoView extends JFrame {

    private Long idChamado;

    private Long idUsuario;
    public AtendimentoChamadoView(Long idChamado, Long idUsuario) {
        this.idChamado = idChamado;
        this.idUsuario = idUsuario;
        initComponents();
        carregarChamado();
    }

    private void carregarChamado() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            ChamadoDto chamado = chamadoController.carregarChamadoAtendimentoPorId(idChamado);
            textCodigo.setText(chamado.getCodChamado());
            textTitulo.setText(chamado.getTitulo());
            textNomeUsuario.setText(chamado.getUsuarioRequisitante());
            textDataAbertura.setText(chamado.getDataHoraAbertura());
            textDescricao.setText(chamado.getDescricao());
            textCliente.setText(chamado.getNomeCliente());
            if (chamado.getIdUsuarioAtendente() != 0 ) {
                textDataFechamento.setVisible(true);
                scrollSolucao.setVisible(true);
                labelDataFechamento.setVisible(true);
                labelSolucao.setVisible(true);
                labelTecnico.setVisible(true);
                textTecnico.setVisible(true);
                textTecnico.setText(chamado.getNomeUsuarioAtendente());
                okButton.setText("Resolver");
                if (!chamado.getIdUsuarioAtendente().equals(idUsuario)) {
                    textSolucao.setEditable(false);
                    okButton.setEnabled(false);
                    okButton.setText("Recebido por outro atendente");
                }
                if (!chamado.getDataHoraFechamento().isBlank()) {
                    textSolucao.setEditable(false);
                    okButton.setEnabled(false);
                    okButton.setText("Resolvido");
                    textSolucao.setText(chamado.getSolucao());
                    labelDataFechamento.setVisible(true);
                    textDataFechamento.setVisible(true);
                    textDataFechamento.setText(chamado.getDataHoraFechamento());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void voltarTelaInicial(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void atenderChamado(MouseEvent e) {
        if (okButton.getText().equals("Resolver")) {
            if (textSolucao.getText().isBlank()) {
                JOptionPane.showMessageDialog(this,"Coloque uma descrição para solução");
            } else {
                if (textSolucao.getText().length() <= 256) {
                    ChamadoController chamadoController = new ChamadoController();
                    try {
                        boolean resolvido = chamadoController.resolverChamado(idChamado, textSolucao.getText());
                        if (resolvido) {
                            JOptionPane.showMessageDialog(this,"Chamado resolvido!");
                            IndexView indexView = new IndexView(idUsuario);
                            indexView.setVisible(true);
                            indexView.setLocationRelativeTo(null);
                            dispose();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,"Problema ao resolver o chamado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this,"Descrição da solução muito grande.");
                }
            }
        } else {
            if(okButton.isEnabled()) {
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean recebido = chamadoController.receberChamado(idChamado, idUsuario);
                    if (recebido) {
                        JOptionPane.showMessageDialog(this,"Chamado recebido!");
                        IndexView indexView = new IndexView(idUsuario);
                        indexView.setVisible(true);
                        indexView.setLocationRelativeTo(null);
                        dispose();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        textCodigo = new JTextField();
        textNomeUsuario = new JTextField();
        label3 = new JLabel();
        textTitulo = new JTextField();
        label4 = new JLabel();
        scrollPane1 = new JScrollPane();
        textDescricao = new JTextArea();
        label5 = new JLabel();
        textCliente = new JTextField();
        label6 = new JLabel();
        textDataAbertura = new JTextField();
        labelSolucao = new JLabel();
        scrollSolucao = new JScrollPane();
        textSolucao = new JTextArea();
        labelDataFechamento = new JLabel();
        textDataFechamento = new JTextField();
        labelTecnico = new JLabel();
        textTecnico = new JTextField();
        buttonBar = new JPanel();
        hSpacer2 = new JPanel(null);
        okButton = new JButton();
        hSpacer3 = new JPanel(null);
        cancelButton = new JButton();
        hSpacer1 = new JPanel(null);

        //======== this ========
        setMinimumSize(new Dimension(1000, 815));
        setPreferredSize(new Dimension(1000, 815));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 251, 0, 0, 0, 231, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00f3digo do chamado");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("Usu\u00e1rio requisitante");
                contentPanel.add(label2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- textCodigo ----
                textCodigo.setEditable(false);
                contentPanel.add(textCodigo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textNomeUsuario ----
                textNomeUsuario.setEditable(false);
                contentPanel.add(textNomeUsuario, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label3 ----
                label3.setText("T\u00edtulo");
                label3.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textTitulo ----
                textTitulo.setEditable(false);
                contentPanel.add(textTitulo, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label4 ----
                label4.setText("Descri\u00e7\u00e3o");
                label4.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(label4, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== scrollPane1 ========
                {

                    //---- textDescricao ----
                    textDescricao.setEditable(false);
                    scrollPane1.setViewportView(textDescricao);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label5 ----
                label5.setText("Cliente");
                contentPanel.add(label5, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textCliente ----
                textCliente.setEditable(false);
                contentPanel.add(textCliente, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label6 ----
                label6.setText("Data e hora de abertura");
                contentPanel.add(label6, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textDataAbertura ----
                textDataAbertura.setEditable(false);
                contentPanel.add(textDataAbertura, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- labelSolucao ----
                labelSolucao.setText("Solu\u00e7\u00e3o");
                labelSolucao.setHorizontalAlignment(SwingConstants.CENTER);
                labelSolucao.setVisible(false);
                contentPanel.add(labelSolucao, new GridBagConstraints(0, 7, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== scrollSolucao ========
                {
                    scrollSolucao.setVisible(false);
                    scrollSolucao.setViewportView(textSolucao);
                }
                contentPanel.add(scrollSolucao, new GridBagConstraints(0, 8, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- labelDataFechamento ----
                labelDataFechamento.setText("Data e hora de fechamento");
                labelDataFechamento.setVisible(false);
                contentPanel.add(labelDataFechamento, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textDataFechamento ----
                textDataFechamento.setEditable(false);
                textDataFechamento.setVisible(false);
                contentPanel.add(textDataFechamento, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- labelTecnico ----
                labelTecnico.setText("T\u00e9cnico");
                labelTecnico.setVisible(false);
                contentPanel.add(labelTecnico, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textTecnico ----
                textTecnico.setEditable(false);
                textTecnico.setVisible(false);
                contentPanel.add(textTecnico, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 0, 85, 0};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 1.0, 0.0, 1.0, 1.0};
                buttonBar.add(hSpacer2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- okButton ----
                okButton.setText("Atender");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        atenderChamado(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                buttonBar.add(hSpacer3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancelar");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        voltarTelaInicial(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                buttonBar.add(hSpacer1, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
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
    private JLabel label2;
    private JTextField textCodigo;
    private JTextField textNomeUsuario;
    private JLabel label3;
    private JTextField textTitulo;
    private JLabel label4;
    private JScrollPane scrollPane1;
    private JTextArea textDescricao;
    private JLabel label5;
    private JTextField textCliente;
    private JLabel label6;
    private JTextField textDataAbertura;
    private JLabel labelSolucao;
    private JScrollPane scrollSolucao;
    private JTextArea textSolucao;
    private JLabel labelDataFechamento;
    private JTextField textDataFechamento;
    private JLabel labelTecnico;
    private JTextField textTecnico;
    private JPanel buttonBar;
    private JPanel hSpacer2;
    private JButton okButton;
    private JPanel hSpacer3;
    private JButton cancelButton;
    private JPanel hSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
