/*
 * Created by JFormDesigner on Sun Jun 25 00:26:22 BRT 2023
 */

package view;

import controller.UsuarioController;
import util.CriptografiaUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Duda
 */
public class LoginView extends JFrame {
    public LoginView() {
        initComponents();
    }

    public static void initialize() {
        LoginView mainView = new LoginView();
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
    }

    private void okButtonMouseClicked(MouseEvent e) {
        boolean erro = false;
        if (textUsuario.getText().isBlank()) {
            messageUsuario.setText("Informe um usuário!");
            erro = true;
        }
        if (textSenha.getPassword().length == 0) {
            messageSenha.setText("Informe uma senha!");
            erro = true;
        }
        if(!erro) {
            try {
                String senha = Arrays.toString(textSenha.getPassword());
                String hash = CriptografiaUtils.changeToHash(senha);
                String login = textUsuario.getText();
                UsuarioController usuarioController = new UsuarioController();
                Long idUsuario = usuarioController.logar(login, hash);
                if (idUsuario != null) {
                    IndexView mainView = new IndexView(idUsuario);
                    mainView.setVisible(true);
                    mainView.setLocationRelativeTo(null);
                    dispose();
                } else {
                    messageUsuario.setText("Usuário/senha incorretos!");
                    messageSenha.setText("");
                }
            } catch (Exception ex) {
                System.out.println("Erro ao fazer login.");
            }
        }
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        buttonBar = new JPanel();
        label1 = new JLabel();
        textUsuario = new JTextField();
        label2 = new JLabel();
        textSenha = new JPasswordField();
        messageUsuario = new JLabel();
        messageSenha = new JLabel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 85, 0};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

                //---- label1 ----
                label1.setText("Usuario");
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(label1, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                buttonBar.add(textUsuario, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label2 ----
                label2.setText("Senha");
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(label2, new GridBagConstraints(0, 2, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                buttonBar.add(textSenha, new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- messageUsuario ----
                messageUsuario.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(messageUsuario, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- messageSenha ----
                messageSenha.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(messageSenha, new GridBagConstraints(0, 5, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- okButton ----
                okButton.setText("Autenticar");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Sair");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 6, 2, 1, 0.0, 0.0,
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
    private JPanel buttonBar;
    private JLabel label1;
    private JTextField textUsuario;
    private JLabel label2;
    private JPasswordField textSenha;
    private JLabel messageUsuario;
    private JLabel messageSenha;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
