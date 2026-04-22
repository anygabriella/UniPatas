package br.com.unipatas.view.animal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AnimalAtualizarView {

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
    gridForm.getStyleClass().add("form-grid"); // CSS aplicado

    TextField txtNome = new TextField();
    TextField txtRaca = new TextField();
    TextField txtPeso = new TextField();
    ComboBox<String> cbPorte = new ComboBox<>();
    cbPorte.getItems().addAll("Pequeno", "Médio", "Grande");

    DatePicker dpNascimento = new DatePicker();
    DatePicker dpAdocao = new DatePicker();

    gridForm.add(new Label("Nome:"), 0, 0);
    gridForm.add(txtNome, 1, 0);
    gridForm.add(new Label("Raça:"), 0, 1);
    gridForm.add(txtRaca, 1, 1);
    gridForm.add(new Label("Peso:"), 0, 2);
    gridForm.add(txtPeso, 1, 2);
    gridForm.add(new Label("Porte:"), 0, 3);
    gridForm.add(cbPorte, 1, 3);
    gridForm.add(new Label("Data Nascimento:"), 0, 4);
    gridForm.add(dpNascimento, 1, 4);
    gridForm.add(new Label("Data Adoção:"), 0, 5);
    gridForm.add(dpAdocao, 1, 5);

    gridForm.setDisable(true);

    Button btnSalvar = new Button("Salvar Alterações");
    btnSalvar.getStyleClass().add("botao-principal");
    btnSalvar.setDisable(true);

    layoutPrincipal.getChildren().addAll(hbBusca, gridForm, btnSalvar);

    return layoutPrincipal;
  }
}
