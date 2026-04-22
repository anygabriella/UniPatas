package br.com.unipatas.view.adocao;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AdocaoCadastroView {

  public GridPane getConteudo() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25));
    grid.getStyleClass().add("form-grid");

    ComboBox<String> cbUsuario = new ComboBox<>();
    cbUsuario.getItems().addAll("[ID 1] Guilherme", "[ID 2] Any", "[ID 3] Henrique", "[ID 4] Lucca");
    cbUsuario.setPromptText("Selecione o Adotante");

    ComboBox<String> cbAnimal = new ComboBox<>();
    cbAnimal.getItems().addAll("[ID 1] Rex (Labrador)", "[ID 2] Mia (Gato)", "[ID 3] Caramelo (SRD)");
    cbAnimal.setPromptText("Selecione o Animal");

    DatePicker dpDataAdocao = new DatePicker();

    grid.add(new Label("Usuário (Adotante):"), 0, 0);
    grid.add(cbUsuario, 1, 0);
    grid.add(new Label("Animal Adotado:"), 0, 1);
    grid.add(cbAnimal, 1, 1);
    grid.add(new Label("Data da Adoção:"), 0, 2);
    grid.add(dpDataAdocao, 1, 2);

    Button btnSalvar = new Button("Registrar Adoção");
    btnSalvar.getStyleClass().add("botao-principal");

    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btnSalvar);
    grid.add(hbBtn, 1, 3);

    btnSalvar.setOnAction(e -> {
      if (cbUsuario.getValue() == null || cbAnimal.getValue() == null || dpDataAdocao.getValue() == null) {
        new Alert(Alert.AlertType.ERROR, "Preencha todos os campos!").showAndWait();
        return;
      }

      new Alert(Alert.AlertType.INFORMATION, "Adoção registrada (mock)").showAndWait();
    });

    return grid;
  }
}
