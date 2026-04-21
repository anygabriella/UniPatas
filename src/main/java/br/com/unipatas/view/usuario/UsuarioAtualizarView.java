package br.com.unipatas.view.usuario;

import br.com.unipatas.controller.UsuarioController;
import br.com.unipatas.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UsuarioAtualizarView {

    private UsuarioController controller;
    private int idAtual = -1; // Guarda o ID do usuário que estamos editando

    public UsuarioAtualizarView() {
        try {
            this.controller = new UsuarioController();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco.");
        }
    }

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        // --- 1. Área de Busca ---
        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdBusca = new TextField();
        txtIdBusca.setPromptText("ID para alterar");
        Button btnBuscar = new Button("Buscar");
        hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

        // --- 2. Área do Formulário (Inicialmente Desativada) ---
        GridPane gridForm = new GridPane();
        gridForm.setAlignment(Pos.CENTER);
        gridForm.setHgap(10); gridForm.setVgap(10);

        TextField txtNome = new TextField();
        TextField txtCpf = new TextField();
        TextField txtEmail = new TextField();
        PasswordField txtSenha = new PasswordField();
        TextField txtTelefone = new TextField();
        TextField txtCidade = new TextField();
        TextField txtEstado = new TextField();

        gridForm.add(new Label("Nome:"), 0, 0); gridForm.add(txtNome, 1, 0);
        gridForm.add(new Label("CPF:"), 0, 1); gridForm.add(txtCpf, 1, 1);
        gridForm.add(new Label("Email:"), 0, 2); gridForm.add(txtEmail, 1, 2);
        gridForm.add(new Label("Senha:"), 0, 3); gridForm.add(txtSenha, 1, 3);
        gridForm.add(new Label("Telefone:"), 0, 4); gridForm.add(txtTelefone, 1, 4);
        gridForm.add(new Label("Cidade:"), 0, 5); gridForm.add(txtCidade, 1, 5);
        gridForm.add(new Label("Estado:"), 0, 6); gridForm.add(txtEstado, 1, 6);

        // Desativa os campos até que um usuário seja encontrado
        gridForm.setDisable(true); 

        // --- 3. Botão de Salvar Alterações ---
        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setDisable(true); // Desativado até buscar

        // AÇÃO DO BOTÃO BUSCAR
        btnBuscar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdBusca.getText());
                Usuario user = controller.buscarUsuario(id);

                if (user != null) {
                    idAtual = user.getId(); 
                    // Preenche os campos com os dados antigos
                    txtNome.setText(user.getNome());
                    txtCpf.setText(user.getCpf());
                    txtEmail.setText(user.getEmail());
                    txtTelefone.setText(user.getTelefone());
                    txtCidade.setText(user.getCidade());
                    txtEstado.setText(user.getEstado());

                    // Libera os campos para edição
                    gridForm.setDisable(false);
                    btnSalvar.setDisable(false);
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Usuário não encontrado!");
                    gridForm.setDisable(true);
                    btnSalvar.setDisable(true);
                }
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "ID inválido.");
            }
        });

        // AÇÃO DO BOTÃO SALVAR
        btnSalvar.setOnAction(e -> {
            try {
                boolean sucesso = controller.atualizarUsuario(
                        idAtual, txtNome.getText(), txtCpf.getText(), txtEmail.getText(),
                        txtSenha.getText(), txtTelefone.getText(), txtCidade.getText(), txtEstado.getText()
                );

                if (sucesso) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário atualizado com sucesso!");
                    // Trava tudo de novo até a próxima busca
                    gridForm.setDisable(true);
                    btnSalvar.setDisable(true);
                    txtIdBusca.clear();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível atualizar.");
                }
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar: " + ex.getMessage());
            }
        });

        layoutPrincipal.getChildren().addAll(hbBusca, gridForm, btnSalvar);
        return layoutPrincipal;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo); alerta.setTitle(titulo); alerta.setHeaderText(null); alerta.setContentText(mensagem); alerta.showAndWait();
    }
}