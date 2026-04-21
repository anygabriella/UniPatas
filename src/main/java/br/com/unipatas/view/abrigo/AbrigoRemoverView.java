package br.com.unipatas.view.abrigo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class AbrigoRemoverView {

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdDeletar = new TextField();
        txtIdDeletar.setPromptText("ID para excluir");
        
        Button btnDeletar = new Button("Excluir Abrigo");
        btnDeletar.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-font-weight: bold;");

        hbBusca.getChildren().addAll(new Label("ID:"), txtIdDeletar, btnDeletar);

        // AÇÃO DELETAR (Mocking)
        btnDeletar.setOnAction(e -> {
            if (txtIdDeletar.getText().equals("1")) {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação");
                confirmacao.setHeaderText("Atenção!");
                confirmacao.setContentText("Deseja realmente excluir o abrigo: Abrigo Esperança?");

                Optional<ButtonType> resultado = confirmacao.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    Alert sucesso = new Alert(Alert.AlertType.INFORMATION, "Simulação: Abrigo removido!");
                    sucesso.showAndWait();
                    txtIdDeletar.clear();
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Abrigo não encontrado!");
                alerta.showAndWait();
            }
        });

        layoutPrincipal.getChildren().add(hbBusca);
        return layoutPrincipal;
    }
}