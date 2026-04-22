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

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdAnimal = new TextField();
    txtIdAnimal.setPromptText("ID do Animal");

    Button btnBuscar = new Button("Consultar Adoção");
    btnBuscar.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID do Animal:"), txtIdAnimal, btnBuscar);

    GridPane gridResultados = new GridPane();
    gridResultados.setAlignment(Pos.CENTER);
    gridResultados.setHgap(10);
    gridResultados.setVgap(10);
    gridResultados.getStyleClass().add("form-grid");

    Label lblAnimal = new Label("-");
    Label lblAdotante = new Label("-");
    Label lblData = new Label("-");
    Label lblStatus = new Label("-");

    gridResultados.add(new Label("Animal:"), 0, 0);
    gridResultados.add(lblAnimal, 1, 0);
    gridResultados.add(new Label("Adotante:"), 0, 1);
    gridResultados.add(lblAdotante, 1, 1);
    gridResultados.add(new Label("Data da Adoção:"), 0, 2);
    gridResultados.add(lblData, 1, 2);
    gridResultados.add(new Label("Status:"), 0, 3);
    gridResultados.add(lblStatus, 1, 3);

    layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
    return layoutPrincipal;
  }
}
