package br.com.unipatas.view.campanha;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CampanhaCadastroView {

  public GridPane getConteudo() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25));
    grid.getStyleClass().add("form-grid"); // CSS

    TextField txtNome = new TextField();
    TextField txtLocal = new TextField();
    DatePicker dpData = new DatePicker();
    TextField txtCusto = new TextField();
    txtCusto.setPromptText("Ex: 500.00");

    grid.add(new Label("Nome da Campanha:"), 0, 0);
    grid.add(txtNome, 1, 0);
    grid.add(new Label("Local:"), 0, 1);
    grid.add(txtLocal, 1, 1);
    grid.add(new Label("Data:"), 0, 2);
    grid.add(dpData, 1, 2);
    grid.add(new Label("Custo (R$):"), 0, 3);
    grid.add(txtCusto, 1, 3);

    Button btnSalvar = new Button("Salvar Campanha");
    btnSalvar.getStyleClass().add("botao-principal");

    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btnSalvar);
    grid.add(hbBtn, 1, 4);

    btnSalvar.setOnAction(e -> {
      Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Campanha salva (mock)");
      alerta.showAndWait();
    });

    return grid;
  }
}
