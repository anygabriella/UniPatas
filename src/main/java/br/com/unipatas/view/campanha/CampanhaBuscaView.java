package br.com.unipatas.view.campanha;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CampanhaBuscaView {

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        // --- ÁREA DE BUSCA ---
        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdBusca = new TextField();
        txtIdBusca.setPromptText("Digite o ID da Campanha");
        Button btnBuscar = new Button("Buscar");
        hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

        // --- ÁREA DE RESULTADOS ---
        GridPane gridResultados = new GridPane();
        gridResultados.setAlignment(Pos.CENTER);
        gridResultados.setHgap(10);
        gridResultados.setVgap(10);

        Label lblNome = new Label("-");
        Label lblLocal = new Label("-");
        Label lblData = new Label("-");
        Label lblCusto = new Label("-");

        gridResultados.add(new Label("Nome:"), 0, 0); gridResultados.add(lblNome, 1, 0);
        gridResultados.add(new Label("Local:"), 0, 1); gridResultados.add(lblLocal, 1, 1);
        gridResultados.add(new Label("Data:"), 0, 2); gridResultados.add(lblData, 1, 2);
        gridResultados.add(new Label("Custo:"), 0, 3); gridResultados.add(lblCusto, 1, 3);

        // --- AÇÃO DO BOTÃO BUSCAR (Mocking) ---
        btnBuscar.setOnAction(e -> {
            if (txtIdBusca.getText().equals("1")) {
                lblNome.setText("Feira de Adoção de Inverno");
                lblLocal.setText("Praça Central");
                lblData.setText("15/07/2026");
                lblCusto.setText("R$ 800.00");
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Campanha não encontrada!");
                alerta.showAndWait();
                lblNome.setText("-"); lblLocal.setText("-"); lblData.setText("-"); lblCusto.setText("-");
            }
        });

        layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
        return layoutPrincipal;
    }
}