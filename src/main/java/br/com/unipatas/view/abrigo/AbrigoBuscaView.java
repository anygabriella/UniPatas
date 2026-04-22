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

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdBusca = new TextField();
    txtIdBusca.setPromptText("Digite o ID do Abrigo");

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID do Abrigo:"), txtIdBusca, btnBuscar);

    GridPane gridResultados = new GridPane();
    gridResultados.setAlignment(Pos.CENTER);
    gridResultados.setHgap(10);
    gridResultados.setVgap(10);
    gridResultados.getStyleClass().add("form-grid");

    Label lblNome = new Label("-");
    Label lblEndereco = new Label("-");
    Label lblTelefone = new Label("-");
    Label lblCusto = new Label("-");

    gridResultados.add(new Label("Nome:"), 0, 0);
    gridResultados.add(lblNome, 1, 0);
    gridResultados.add(new Label("Endereço:"), 0, 1);
    gridResultados.add(lblEndereco, 1, 1);
    gridResultados.add(new Label("Telefone:"), 0, 2);
    gridResultados.add(lblTelefone, 1, 2);
    gridResultados.add(new Label("Custo Mensal:"), 0, 3);
    gridResultados.add(lblCusto, 1, 3);

    layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
    return layoutPrincipal;
  }
}
