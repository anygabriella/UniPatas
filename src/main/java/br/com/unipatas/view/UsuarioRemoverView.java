package br.com.unipatas.view;

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
        txtIdDeletar.setPromptText("ID para excluir");
        Button btnDeletar = new Button("Excluir Usuário");
        
        // Estiliza o botão
        btnDeletar.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-font-weight: bold;");

        hbBusca.getChildren().addAll(new Label("ID:"), txtIdDeletar, btnDeletar);

        // AÇÃO DO BOTÃO DELETAR
        btnDeletar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdDeletar.getText());
                
                // Buscar para ter certeza que existe e mostrar o nome
                Usuario user = controller.buscarUsuario(id);

                if (user != null) {
                    // Pedir confirmação 
                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacao.setTitle("Confirmação de Exclusão");
                    confirmacao.setHeaderText("Atenção!");
                    confirmacao.setContentText("Deseja realmente excluir o usuário: " + user.getNome() + "?");

                    // Espera a resposta do usuário
                    Optional<ButtonType> resultado = confirmacao.showAndWait();
                    
                    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                        // Se confirmou, deleta
                        boolean sucesso = controller.deletarUsuario(id);
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
            } catch (NumberFormatException ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Digite um ID numérico válido.");
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