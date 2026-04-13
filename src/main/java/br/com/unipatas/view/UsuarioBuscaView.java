package br.com.unipatas.view;

import br.com.unipatas.controller.UsuarioController;
import br.com.unipatas.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UsuarioBuscaView {

    private UsuarioController controller;

    public UsuarioBuscaView() {
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

        // --- Área de Pesquisa ---
        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdBusca = new TextField();
        txtIdBusca.setPromptText("Digite o ID");
        Button btnBuscar = new Button("Buscar");
        hbBusca.getChildren().addAll(new Label("ID do Usuário:"), txtIdBusca, btnBuscar);

        // --- Área de Resultados ---
        GridPane gridResultados = new GridPane();
        gridResultados.setAlignment(Pos.CENTER);
        gridResultados.setHgap(10);
        gridResultados.setVgap(10);

        Label lblResultadoNome = new Label("-");
        Label lblResultadoCpf = new Label("-");
        Label lblResultadoEmail = new Label("-");
        Label lblResultadoCidade = new Label("-");

        gridResultados.add(new Label("Nome:"), 0, 0); gridResultados.add(lblResultadoNome, 1, 0);
        gridResultados.add(new Label("CPF:"), 0, 1); gridResultados.add(lblResultadoCpf, 1, 1);
        gridResultados.add(new Label("Email:"), 0, 2); gridResultados.add(lblResultadoEmail, 1, 2);
        gridResultados.add(new Label("Cidade:"), 0, 3); gridResultados.add(lblResultadoCidade, 1, 3);

        // --- Ação do Botão Buscar ---
        btnBuscar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdBusca.getText());
                Usuario user = controller.buscarUsuario(id);

                if (user != null) {
                    lblResultadoNome.setText(user.getNome());
                    lblResultadoCpf.setText(user.getCpf());
                    lblResultadoEmail.setText(user.getEmail());
                    lblResultadoCidade.setText(user.getCidade());
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Usuário não encontrado!");
                    // Limpa os campos se não achar
                    lblResultadoNome.setText("-"); lblResultadoCpf.setText("-");
                    lblResultadoEmail.setText("-"); lblResultadoCidade.setText("-");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Por favor, digite um ID numérico válido.");
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao buscar: " + ex.getMessage());
            }
        });

        layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
        return layoutPrincipal;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}