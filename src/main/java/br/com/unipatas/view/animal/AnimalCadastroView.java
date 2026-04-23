package br.com.unipatas.view.animal;

import br.com.unipatas.controller.AnimalController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AnimalCadastroView {

  private AnimalController controller;

  public AnimalCadastroView() {
    try {
      this.controller = new AnimalController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro Crítico", "Não foi possível conectar ao banco.");
    }
  }

  public GridPane getConteudo() {

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25));
    grid.getStyleClass().add("form-grid");

    TextField txtNome = new TextField();
    TextField txtRaca = new TextField();
    TextField txtPorte = new TextField();
    TextField txtPeso = new TextField();
    TextField txtData = new TextField();
    TextField txtIdAbrigo = new TextField();

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(txtNome, 1, 0);

    grid.add(new Label("Raça:"), 0, 1);
    grid.add(txtRaca, 1, 1);

    grid.add(new Label("Porte:"), 0, 2);
    grid.add(txtPorte, 1, 2);

    grid.add(new Label("Peso:"), 0, 3);
    grid.add(txtPeso, 1, 3);

    grid.add(new Label("Data Adoção:"), 0, 4);
    grid.add(txtData, 1, 4);

    grid.add(new Label("ID Abrigo:"), 0, 5);
    grid.add(txtIdAbrigo, 1, 5);

    Button btnSalvar = new Button("Salvar Animal");
    btnSalvar.getStyleClass().add("botao-principal");

    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btnSalvar);
    grid.add(hbBtn, 1, 6);

    btnSalvar.setOnAction(e -> {
      try {

        float peso = Float.parseFloat(txtPeso.getText());
        int idAbrigo = Integer.parseInt(txtIdAbrigo.getText());

        int idGerado = controller.salvar(
            txtNome.getText(),
            txtRaca.getText(),
            txtPorte.getText(),
            peso,
            txtData.getText(),
            idAbrigo
        );

        mostrarAlerta(Alert.AlertType.INFORMATION,
            "Sucesso",
            "Animal salvo com ID: " + idGerado);

        // limpar campos
        txtNome.clear();
        txtRaca.clear();
        txtPorte.clear();
        txtPeso.clear();
        txtData.clear();
        txtIdAbrigo.clear();

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Peso e ID do abrigo devem ser numéricos.");
      } catch (Exception ex) {
        ex.printStackTrace();
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar: " + ex.getMessage());
      }
    });

    return grid;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
  }
}