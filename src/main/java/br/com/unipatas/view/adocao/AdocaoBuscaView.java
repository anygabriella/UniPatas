package br.com.unipatas.view.adocao;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdocaoBuscaView {

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        // --- ÁREA DE BUSCA ---
        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdAnimal = new TextField();
        txtIdAnimal.setPromptText("ID do Animal");
        Button btnBuscar = new Button("Consultar Adoção");
        hbBusca.getChildren().addAll(new Label("ID do Animal:"), txtIdAnimal, btnBuscar);

        // --- ÁREA DE RESULTADOS ---
        GridPane gridResultados = new GridPane();
        gridResultados.setAlignment(Pos.CENTER);
        gridResultados.setHgap(10);
        gridResultados.setVgap(10);

        Label lblAnimal = new Label("-");
        Label lblAdotante = new Label("-");
        Label lblData = new Label("-");
        Label lblStatus = new Label("-");

        gridResultados.add(new Label("Animal:"), 0, 0); gridResultados.add(lblAnimal, 1, 0);
        gridResultados.add(new Label("Adotante:"), 0, 1); gridResultados.add(lblAdotante, 1, 1);
        gridResultados.add(new Label("Data da Adoção:"), 0, 2); gridResultados.add(lblData, 1, 2);
        gridResultados.add(new Label("Status:"), 0, 3); gridResultados.add(lblStatus, 1, 3);

        // --- AÇÃO DO BOTÃO (Mocking) ---
        btnBuscar.setOnAction(e -> {
            if (txtIdAnimal.getText().equals("1")) {
                lblAnimal.setText("Rex (Labrador)");
                lblAdotante.setText("Guilherme");
                lblData.setText("20/10/2023");
                lblStatus.setText("Adotado com sucesso ❤️");
                lblStatus.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Este animal ainda não possui registro de adoção.");
                alerta.showAndWait();
                lblAnimal.setText("-"); lblAdotante.setText("-"); lblData.setText("-"); 
                lblStatus.setText("Aguardando família");
                lblStatus.setStyle("-fx-text-fill: orange;");
            }
        });

        layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
        return layoutPrincipal;
    }
}