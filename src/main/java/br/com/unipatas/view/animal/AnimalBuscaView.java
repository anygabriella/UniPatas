package br.com.unipatas.view.animal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AnimalBuscaView {

  public VBox getConteudo() {

    VBox layoutPrincipal = new VBox(20);
    layoutPrincipal.setAlignment(Pos.CENTER);
    layoutPrincipal.setPadding(new Insets(25));

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdBusca = new TextField();
    txtIdBusca.setPromptText("Digite o ID do Animal");

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID do Animal:"), txtIdBusca, btnBuscar);

    GridPane gridResultados = new GridPane();
    gridResultados.setAlignment(Pos.CENTER);
    gridResultados.setHgap(10);
    gridResultados.setVgap(10);
    gridResultados.getStyleClass().add("form-grid"); // padrão visual

    Label lblNome = new Label("-");
    Label lblRaca = new Label("-");
    Label lblPeso = new Label("-");
    Label lblPorte = new Label("-");
    Label lblNascimento = new Label("-");
    Label lblAdocao = new Label("-");

    gridResultados.add(new Label("Nome:"), 0, 0);
    gridResultados.add(lblNome, 1, 0);
    gridResultados.add(new Label("Raça:"), 0, 1);
    gridResultados.add(lblRaca, 1, 1);
    gridResultados.add(new Label("Peso:"), 0, 2);
    gridResultados.add(lblPeso, 1, 2);
    gridResultados.add(new Label("Porte:"), 0, 3);
    gridResultados.add(lblPorte, 1, 3);
    gridResultados.add(new Label("Data Nascimento:"), 0, 4);
    gridResultados.add(lblNascimento, 1, 4);
    gridResultados.add(new Label("Data Adoção:"), 0, 5);
    gridResultados.add(lblAdocao, 1, 5);

    layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);

    return layoutPrincipal;
  }
}
