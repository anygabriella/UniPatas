package br.com.unipatas.view.abrigo; 

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AbrigoBuscaView {

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        // --- ÁREA DE BUSCA ---
        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdBusca = new TextField();
        txtIdBusca.setPromptText("Digite o ID do Abrigo");
        Button btnBuscar = new Button("Buscar");
        hbBusca.getChildren().addAll(new Label("ID do Abrigo:"), txtIdBusca, btnBuscar);

        // --- ÁREA DE RESULTADOS ---
        GridPane gridResultados = new GridPane();
        gridResultados.setAlignment(Pos.CENTER);
        gridResultados.setHgap(10);
        gridResultados.setVgap(10);

        Label lblNome = new Label("-");
        Label lblEndereco = new Label("-");
        Label lblTelefone = new Label("-");
        Label lblCusto = new Label("-");

        gridResultados.add(new Label("Nome:"), 0, 0); gridResultados.add(lblNome, 1, 0);
        gridResultados.add(new Label("Endereço:"), 0, 1); gridResultados.add(lblEndereco, 1, 1);
        gridResultados.add(new Label("Telefone:"), 0, 2); gridResultados.add(lblTelefone, 1, 2);
        gridResultados.add(new Label("Custo Mensal:"), 0, 3); gridResultados.add(lblCusto, 1, 3);

        // --- AÇÃO DO BOTÃO BUSCAR (Mocking) ---
        btnBuscar.setOnAction(e -> {
            if (txtIdBusca.getText().equals("1")) {
                // Simula que encontrou o abrigo 1
                lblNome.setText("Abrigo Esperança");
                lblEndereco.setText("Rua das Flores, 123");
                lblTelefone.setText("(31) 98765-4321");
                lblCusto.setText("R$ 2500.00");
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Abrigo não encontrado!");
                alerta.showAndWait();
                lblNome.setText("-"); lblEndereco.setText("-"); lblTelefone.setText("-"); lblCusto.setText("-");
            }
        });

        layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
        return layoutPrincipal;
    }
}