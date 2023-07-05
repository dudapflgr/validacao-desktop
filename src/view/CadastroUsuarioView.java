/*
 * Created by JFormDesigner on Sun Jul 02 13:15:11 BRT 2023
 */

package view;

import controller.UsuarioController;
import model.dto.UsuarioEditarDto;
import util.CriptografiaUtils;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Duda
 */
public class CadastroUsuarioView extends JFrame {

    private Long idUsuario;

    private Long idUsuarioEditar;

    public CadastroUsuarioView(Long idUsuario, Long idUsuarioEditar) {
        this.idUsuario = idUsuario;
        this.idUsuarioEditar = idUsuarioEditar;
        initComponents();
        if (idUsuarioEditar != null) {
            UsuarioController usuarioController = new UsuarioController();
            UsuarioEditarDto usuario = null;
            try {
                usuario = usuarioController.carregarUsuarioPorId(idUsuarioEditar);
                textCodUsuario.setText(usuario.getCodUsuario());
                textCodUsuario.setEditable(false);
                textLogin.setText(usuario.getLogin());
                textLogin.setEditable(false);
                textNome.setText(usuario.getNome());
                textNome.setEditable(false);
                textEmail.setText(usuario.getEmail());
                textEmail.setEditable(false);
                okButton.setText("Atualizar senha");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar usuário!");
            }
        }
    }

    private void cancelarCadastro(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void cadastrarUsuario(MouseEvent e) {
        if (idUsuarioEditar != null) {
            String erros = "";
            if (Arrays.toString(textSenha.getPassword()).isBlank()) {
                erros += "Informe uma senha para o usuário!\n";
            }
            if (Arrays.toString(textSenha.getPassword()).length() > 50) {
                erros += "Senha do usuário muito grande!\n";
            }
            if (!erros.isBlank()) {
                JOptionPane.showMessageDialog(null, erros);
            } else {
                UsuarioController usuarioController = new UsuarioController();
                String senhaHash = CriptografiaUtils.changeToHash(Arrays.toString(textSenha.getPassword()));
                try {
                    boolean atualizou = usuarioController.atualizarSenha(idUsuarioEditar, senhaHash);
                    if (atualizou) {
                        JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso!");
                        IndexView mainView = new IndexView(idUsuario);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar senha!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar senha!");
                }
            }
        } else {
            String erros = "";
            if (textCodUsuario.getText().isBlank()) {
                erros += "Informe um código para o usuário!\n";
            }
            if (textCodUsuario.getText().length() > 4) {
                erros += "Código do usuário muito grande!\n";
            }
            if (textNome.getText().isBlank()) {
                erros += "Informe um nome para o usuário!\n";
            }
            if (textCodUsuario.getText().length() > 50) {
                erros += "Nome do usuário muito grande!\n";
            }
            if (textLogin.getText().isBlank()) {
                erros += "Informe um login para o usuário!\n";
            }
            if (textLogin.getText().length() > 50) {
                erros += "Login do usuário muito grande!\n";
            }
            if (Arrays.toString(textSenha.getPassword()).isBlank()) {
                erros += "Informe uma senha para o usuário!\n";
            }
            if (Arrays.toString(textSenha.getPassword()).length() > 50) {
                erros += "Senha do usuário muito grande!\n";
            }
            if (textEmail.getText().isBlank()) {
                erros += "Informe um e-mail para o usuário!\n";
            }
            if (textEmail.getText().length() > 50) {
                erros += "E-mail do usuário muito grande!\n";
            }
            if (!validaEmail(textEmail.getText())) {
                erros += "Informe um e-mail válido para o usuário!";
            }
            if (!erros.isBlank()) {
                JOptionPane.showMessageDialog(null, erros);
            } else {
                UsuarioController usuarioController = new UsuarioController();
                String senhaHash = CriptografiaUtils.changeToHash(Arrays.toString(textSenha.getPassword()));
                String codUsuario = textCodUsuario.getText();
                String nome = textNome.getText();
                String email = textEmail.getText();
                String login = textLogin.getText();
                try {
                    boolean cadastrou = usuarioController.cadastrarUsuario(codUsuario, nome, email, login, senhaHash);
                    if (cadastrou) {
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        IndexView mainView = new IndexView(idUsuario);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário não cadastrado! Verificar dados inseridos!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário!");
                }
            }
        }
    }

    public static boolean validaEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textCodUsuario = new JTextField();
        label2 = new JLabel();
        textNome = new JTextField();
        label3 = new JLabel();
        textEmail = new JTextField();
        label5 = new JLabel();
        textLogin = new JTextField();
        label4 = new JLabel();
        textSenha = new JPasswordField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

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
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {264, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00f3digo usu\u00e1rio");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                contentPanel.add(textCodUsuario, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label2 ----
                label2.setText("Nome");
                contentPanel.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                contentPanel.add(textNome, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label3 ----
                label3.setText("E-mail");
                contentPanel.add(label3, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                contentPanel.add(textEmail, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label5 ----
                label5.setText("Login");
                contentPanel.add(label5, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                contentPanel.add(textLogin, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label4 ----
                label4.setText("Senha");
                contentPanel.add(label4, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                contentPanel.add(textSenha, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("Cadastrar");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cadastrarUsuario(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancelar");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelarCadastro(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
    private JTextField textCodUsuario;
    private JLabel label2;
    private JTextField textNome;
    private JLabel label3;
    private JTextField textEmail;
    private JLabel label5;
    private JTextField textLogin;
    private JLabel label4;
    private JPasswordField textSenha;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
