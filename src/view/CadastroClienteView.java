/*
 * Created by JFormDesigner on Sun Jul 02 14:11:31 BRT 2023
 */

package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ClienteController;
import model.dto.CepResponseDto;
import model.dto.ClienteCadastroDto;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Duda
 */
public class CadastroClienteView extends JFrame {

    private Long idUsuario;

    private Long idCliente;

    public CadastroClienteView(Long idUsuario, Long idCliente) {
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        initComponents();
        if(idCliente != null) {
            carregarCliente();
        }
    }

    private void carregarCliente() {
        try {
            ClienteController clienteController = new ClienteController();
            ClienteCadastroDto cliente = clienteController.buscarClientePorId(idCliente);
            if (cliente != null) {
                textCodigoUsuario.setText(cliente.getCodCliente());
                textNome.setText(cliente.getNome());
                textEmail.setText(cliente.getEmail());
                textCep.setText(cliente.getCep());
                textLogradouro.setText(cliente.getLogradouro());
                textNumero.setText(cliente.getNumero());
                textComplemento.setText(cliente.getComplemento());
                textBairro.setText(cliente.getBairro());
                textCidade.setText(cliente.getCidade());
                textUF.setText(cliente.getUf());
                okButton.setText("Atualizar");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar cliente!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cliente!");
        }
    }

    private void voltarIndex(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void carregaCepApi(FocusEvent e) {
        if (textCep.getText().length() > 8) {
            JOptionPane.showMessageDialog(this, "Informe um CEP válido!");
            textLogradouro.setText("");
            textBairro.setText("");
            textCidade.setText("");
            textUF.setText("");
        } else if (textCep.getText().length() == 8) {
            try {
                Integer.valueOf(textCep.getText());
                StringBuffer content = consultarViaCep();
                if (content.toString().contains("erro")) {
                    JOptionPane.showMessageDialog(this, "CEP não existe!");
                } else {
                    Gson gson = new GsonBuilder().create();
                    CepResponseDto response = gson.fromJson(content.toString(), CepResponseDto.class);
                    textLogradouro.setText(response.getLogradouro());
                    textBairro.setText(response.getBairro());
                    textCidade.setText(response.getLocalidade());
                    textUF.setText(response.getUf());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao ler CEP!");
                textLogradouro.setText("");
                textBairro.setText("");
                textCidade.setText("");
                textUF.setText("");
            }
        } else {
            textLogradouro.setText("");
            textBairro.setText("");
            textCidade.setText("");
            textUF.setText("");
        }
    }

    private StringBuffer consultarViaCep() throws IOException {
        URL url = new URL("https://viacep.com.br/ws/" + textCep.getText() + "/json/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content;
    }

    private void cadastrarAtualizarCliente(MouseEvent e) {
        String erros = "";

        if (textNome.getText().isBlank()) {
            erros += "Informe um nome para o cliente!\n";
        }
        if (textNome.getText().length() > 70) {
            erros += "Nome do cliente muito grande!\n";
        }
        if (textEmail.getText().isBlank()) {
            erros += "Informe um e-mail para o cliente!\n";
        }
        if (textEmail.getText().length() > 30) {
            erros += "Tamanho do e-mail inválido!\n";
        }
        if (!validaEmail(textEmail.getText())) {
            erros += "Informe um e-mail válido para o cliente!\n";
        }
        if (textCep.getText().isBlank()) {
            erros += "Informe um CEP!\n";
        }
        //SE O NOME DA RUA ESTÁ VAZIO, O CEP É INVÁLIDO
        if (textLogradouro.getText().isBlank()) {
            erros += "Informe um CEP válido!\n";
        }
        if (textNumero.getText().isBlank()) {
            erros += "Informe um número do endereço!";
        }
        if (textNumero.getText().length() > 20) {
            erros += "Tamanho do número inválido!";
        }
        if (textComplemento.getText().length() > 100) {
            erros += "Tamanho do complemento inválido!";
        }
        if (erros.isBlank()) {
            ClienteController clienteController = new ClienteController();
            if (idCliente == null) {
                String codCliente = null;
                try {
                    codCliente = "C"+idUsuario+clienteController.idUltimoClienteCadastrado();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String nome = textNome.getText();
                String email = textEmail.getText();
                String cep = textCep.getText();
                String logradouro = textLogradouro.getText();
                String numero = textNumero.getText();
                String complemento = textComplemento.getText();
                String bairro = textBairro.getText();
                String cidade = textCidade.getText();
                String uf = textUF.getText();
                ClienteCadastroDto cliente = new ClienteCadastroDto(codCliente, nome, email, cep, logradouro, numero, complemento, bairro, cidade, uf);
                boolean cadastrou = false;
                try {
                    cadastrou = clienteController.cadastrarCliente(cliente);
                    if (cadastrou) {
                        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                        IndexView mainView = new IndexView(idUsuario);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente não cadastrado!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente");
                }
            } else {
                String codCliente = textCodigoUsuario.getText();
                String nome = textNome.getText();
                String email = textEmail.getText();
                String cep = textCep.getText();
                String logradouro = textLogradouro.getText();
                String numero = textNumero.getText();
                String complemento = textComplemento.getText();
                String bairro = textBairro.getText();
                String cidade = textCidade.getText();
                String uf = textUF.getText();
                ClienteCadastroDto cliente = new ClienteCadastroDto(codCliente, nome, email, cep, logradouro, numero, complemento, bairro, cidade, uf);
                cliente.setIdCliente(idCliente);
                try {
                    boolean atualizou = clienteController.atualizarCliente(cliente);
                    if (atualizou) {
                        JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
                        ListaClientesView listaClientesView = new ListaClientesView(idUsuario);
                        listaClientesView.setVisible(true);
                        listaClientesView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, erros);
        }
    }

    private static boolean validaEmail(String email) {
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
        label2 = new JLabel();
        textCodigoUsuario = new JTextField();
        textNome = new JTextField();
        label3 = new JLabel();
        textEmail = new JTextField();
        label6 = new JLabel();
        separator1 = new JSeparator();
        label4 = new JLabel();
        label5 = new JLabel();
        textCep = new JTextField();
        textLogradouro = new JTextField();
        label7 = new JLabel();
        label8 = new JLabel();
        textNumero = new JTextField();
        textComplemento = new JTextField();
        label11 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        textBairro = new JTextField();
        textCidade = new JTextField();
        textUF = new JTextField();
        separator2 = new JSeparator();
        buttonBar = new JPanel();
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

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {121, 144, 97, 233, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00f3digo cliente");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("Nome");
                contentPanel.add(label2, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- textCodigoUsuario ----
                textCodigoUsuario.setEditable(false);
                contentPanel.add(textCodigoUsuario, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textNome, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label3 ----
                label3.setText("E-mail");
                label3.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textEmail, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label6 ----
                label6.setText("Endere\u00e7o");
                contentPanel.add(label6, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(separator1, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label4 ----
                label4.setText("CEP");
                label4.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(label4, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label5 ----
                label5.setText("Logradouro");
                contentPanel.add(label5, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textCep ----
                textCep.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        carregaCepApi(e);
                    }
                });
                contentPanel.add(textCep, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textLogradouro ----
                textLogradouro.setEditable(false);
                contentPanel.add(textLogradouro, new GridBagConstraints(1, 6, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label7 ----
                label7.setText("N\u00famero");
                contentPanel.add(label7, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label8 ----
                label8.setText("Complemento");
                contentPanel.add(label8, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textNumero, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textComplemento, new GridBagConstraints(1, 8, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label11 ----
                label11.setText("Bairro");
                contentPanel.add(label11, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label9 ----
                label9.setText("Cidade");
                contentPanel.add(label9, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label10 ----
                label10.setText("UF");
                contentPanel.add(label10, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textBairro ----
                textBairro.setEditable(false);
                contentPanel.add(textBairro, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textCidade ----
                textCidade.setEditable(false);
                contentPanel.add(textCidade, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textUF ----
                textUF.setEditable(false);
                contentPanel.add(textUF, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(separator2, new GridBagConstraints(0, 11, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 1.0};

                //---- okButton ----
                okButton.setText("Cadastrar");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cadastrarAtualizarCliente(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Voltar");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        voltarIndex(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
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
    private JTextField textCodigoUsuario;
    private JTextField textNome;
    private JLabel label3;
    private JTextField textEmail;
    private JLabel label6;
    private JSeparator separator1;
    private JLabel label4;
    private JLabel label5;
    private JTextField textCep;
    private JTextField textLogradouro;
    private JLabel label7;
    private JLabel label8;
    private JTextField textNumero;
    private JTextField textComplemento;
    private JLabel label11;
    private JLabel label9;
    private JLabel label10;
    private JTextField textBairro;
    private JTextField textCidade;
    private JTextField textUF;
    private JSeparator separator2;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
