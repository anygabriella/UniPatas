package br.com.unipatas.view.usuario;

import br.com.unipatas.controller.UsuarioController;
import br.com.unipatas.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class UsuarioRemoverView {

    private UsuarioController controller;

    public UsuarioRemoverView() {
        try {
            this.controller = new UsuarioController();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco de dados.");
        }
    }

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        // --- Área de Busca ---
        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdDeletar = new TextField();
        txtIdDeletar.setPromptText("Nome para excluir");
        Button btnDeletar = new Button("Excluir Usuário");
        
        // Estiliza o botão
        btnDeletar.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-font-weight: bold;");

        hbBusca.getChildren().addAll(new Label("Nome:"), txtIdDeletar, btnDeletar);

        // AÇÃO DO BOTÃO DELETAR
        btnDeletar.setOnAction(e -> {
            try {
                String nomeDeletar = txtIdDeletar.getText(); // Pega o nome
                
                Usuario user = controller.buscarUsuario(nomeDeletar);

                if (user != null) {
                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacao.setTitle("Confirmação de Exclusão");
                    confirmacao.setContentText("Deseja realmente excluir o usuário: " + user.getNome() + "?");

                    Optional<ButtonType> resultado = confirmacao.showAndWait();
                    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                        
                        // Passa a STRING para o controller deletar
                        boolean sucesso = controller.deletarUsuario(nomeDeletar); 
                        
                        if (sucesso) {
                            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário removido!");
                            txtIdDeletar.clear();
                        } else {
                            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível remover.");
                        }
                    }
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Usuário não encontrado!");
                }
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir: " + ex.getMessage());
            }
        });

        layoutPrincipal.getChildren().addAll(hbBusca);
        return layoutPrincipal;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo); alerta.setTitle(titulo); alerta.setHeaderText(null); alerta.setContentText(mensagem); alerta.showAndWait();
    }
}