package br.com.unipatas.view.abrigo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AbrigoAtualizarView {

  public VBox getConteudo() {
    VBox layoutPrincipal = new VBox(20);
    layoutPrincipal.setAlignment(Pos.CENTER);
    layoutPrincipal.setPadding(new Insets(25));

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdBusca = new TextField();
    txtIdBusca.setPromptText("ID para alterar");

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

    GridPane gridForm = new GridPane();
    gridForm.setAlignment(Pos.CENTER);
    gridForm.setHgap(10);
    gridForm.setVgap(10);
    gridForm.getStyleClass().add("form-grid");

    TextField txtNome = new TextField();
    TextField txtEndereco = new TextField();
    TextField txtTelefone = new TextField();
    TextField txtCusto = new TextField();

    gridForm.add(new Label("Nome:"), 0, 0);
    gridForm.add(txtNome, 1, 0);
    gridForm.add(new Label("Endereço:"), 0, 1);
    gridForm.add(txtEndereco, 1, 1);
    gridForm.add(new Label("Telefone:"), 0, 2);
    gridForm.add(txtTelefone, 1, 2);
    gridForm.add(new Label("Custo Mensal:"), 0, 3);
    gridForm.add(txtCusto, 1, 3);

    gridForm.setDisable(true);

    Button btnSalvar = new Button("Salvar Alterações");
    btnSalvar.getStyleClass().add("botao-principal");
    btnSalvar.setDisable(true);

    layoutPrincipal.getChildren().addAll(hbBusca, gridForm, btnSalvar);
    return layoutPrincipal;
  }
}
