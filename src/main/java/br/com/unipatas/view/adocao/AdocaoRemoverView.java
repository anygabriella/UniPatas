package br.com.unipatas.view.adocao;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class AdocaoRemoverView {

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdAnimal = new TextField();
        txtIdAnimal.setPromptText("ID do Animal");
        
        Button btnDeletar = new Button("Cancelar Adoção");
        btnDeletar.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-font-weight: bold;");

        hbBusca.getChildren().addAll(new Label("ID do Animal:"), txtIdAnimal, btnDeletar);

        // AÇÃO CANCELAR (Mocking)
        btnDeletar.setOnAction(e -> {
            if (txtIdAnimal.getText().equals("1")) {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Atenção");
                confirmacao.setHeaderText("Cancelamento de Adoção");
                confirmacao.setContentText("Deseja realmente cancelar a adoção do animal Rex pelo adotante Guilherme?");

                Optional<ButtonType> resultado = confirmacao.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    Alert sucesso = new Alert(Alert.AlertType.INFORMATION, "Adoção cancelada. O animal voltou para a lista de adoção.");
                    sucesso.showAndWait();
                    txtIdAnimal.clear();
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Registro de adoção não encontrado para este animal!");
                alerta.showAndWait();
            }
        });

        layoutPrincipal.getChildren().add(hbBusca);
        return layoutPrincipal;
    }
}