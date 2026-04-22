package br.com.unipatas.view.animal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AnimalCadastroView {

  public GridPane getConteudo() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25));
    grid.getStyleClass().add("form-grid"); // CSS aplicado

    TextField txtNome = new TextField();
    TextField txtRaca = new TextField();
    TextField txtPeso = new TextField();
    txtPeso.setPromptText("Ex: 12.5");

    DatePicker dpNascimento = new DatePicker();
    DatePicker dpAdocao = new DatePicker();

    ComboBox<String> cbPorte = new ComboBox<>();
    cbPorte.getItems().addAll("Pequeno", "Médio", "Grande");
    cbPorte.setPromptText("Selecione o porte");

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(txtNome, 1, 0);
    grid.add(new Label("Raça:"), 0, 1);
    grid.add(txtRaca, 1, 1);
    grid.add(new Label("Peso (kg):"), 0, 2);
    grid.add(txtPeso, 1, 2);
    grid.add(new Label("Porte:"), 0, 3);
    grid.add(cbPorte, 1, 3);
    grid.add(new Label("Data Nascimento:"), 0, 4);
    grid.add(dpNascimento, 1, 4);
    grid.add(new Label("Data Adoção:"), 0, 5);
    grid.add(dpAdocao, 1, 5);

    Button btnSalvar = new Button("Salvar Animal");
    btnSalvar.getStyleClass().add("botao-principal"); // CSS

    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btnSalvar);
    grid.add(hbBtn, 1, 6);

    btnSalvar.setOnAction(e -> {
      Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Animal salvo (mock)");
      alerta.showAndWait();
    });

    return grid;
  }
}
