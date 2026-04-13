package br.com.unipatas.view;

import br.com.unipatas.controller.UsuarioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UsuarioCadastroView {

    private UsuarioController controller;

    public UsuarioCadastroView() {
        try {
            this.controller = new UsuarioController();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro Crítico", "Não foi possível conectar ao banco de dados.");
        }
    }

    // Método que constrói e devolve o visual
    public GridPane getConteudo() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        TextField txtNome = new TextField();
        TextField txtCpf = new TextField();
        TextField txtEmail = new TextField();
        PasswordField txtSenha = new PasswordField();
        TextField txtTelefone = new TextField();
        TextField txtCidade = new TextField();
        TextField txtEstado = new TextField();

        grid.add(new Label("Nome:"), 0, 0); grid.add(txtNome, 1, 0);
        grid.add(new Label("CPF:"), 0, 1); grid.add(txtCpf, 1, 1);
        grid.add(new Label("Email:"), 0, 2); grid.add(txtEmail, 1, 2);
        grid.add(new Label("Senha:"), 0, 3); grid.add(txtSenha, 1, 3);
        grid.add(new Label("Telefone:"), 0, 4); grid.add(txtTelefone, 1, 4);
        grid.add(new Label("Cidade:"), 0, 5); grid.add(txtCidade, 1, 5);
        grid.add(new Label("Estado:"), 0, 6); grid.add(txtEstado, 1, 6);

        Button btnSalvar = new Button("Salvar Usuário");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnSalvar);
        grid.add(hbBtn, 1, 7);

        // O clique do botão chama a lógica que está no Controller
        btnSalvar.setOnAction(e -> {
            try {
                int idGerado = controller.salvarUsuario(
                        txtNome.getText(), txtCpf.getText(), txtEmail.getText(),
                        txtSenha.getText(), txtTelefone.getText(), txtCidade.getText(), txtEstado.getText()
                );
                
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário salvo no arquivo com ID: " + idGerado);
                
                // Limpa a tela para o próximo cadastro
                txtNome.clear(); txtCpf.clear(); txtEmail.clear();
                txtSenha.clear(); txtTelefone.clear(); txtCidade.clear(); txtEstado.clear();
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar: " + ex.getMessage());
            }
        });

        return grid;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}