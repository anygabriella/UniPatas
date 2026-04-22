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
    TextField txtIdade = new TextField();
    TextField txtEspecie = new TextField();
    TextField txtRaca = new TextField();
    TextField txtIdAbrigo = new TextField();

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(txtNome, 1, 0);

    grid.add(new Label("Idade:"), 0, 1);
    grid.add(txtIdade, 1, 1);

    grid.add(new Label("Espécie:"), 0, 2);
    grid.add(txtEspecie, 1, 2);

    grid.add(new Label("Raça:"), 0, 3);
    grid.add(txtRaca, 1, 3);

    grid.add(new Label("ID Abrigo:"), 0, 4);
    grid.add(txtIdAbrigo, 1, 4);

    Button btnSalvar = new Button("Salvar Animal");
    btnSalvar.getStyleClass().add("botao-principal");

    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btnSalvar);
    grid.add(hbBtn, 1, 5);

    // 🚨 AQUI ESTAVA O MOCK — AGORA É REAL
    btnSalvar.setOnAction(e -> {
      try {

        int idade = Integer.parseInt(txtIdade.getText());
        int idAbrigo = Integer.parseInt(txtIdAbrigo.getText());

        int idGerado = controller.salvarAnimal(
            txtNome.getText(),
            idade,
            txtEspecie.getText(),
            txtRaca.getText(),
            idAbrigo
        );

        mostrarAlerta(Alert.AlertType.INFORMATION,
            "Sucesso",
            "Animal salvo com ID: " + idGerado);

        // limpar campos
        txtNome.clear();
        txtIdade.clear();
        txtEspecie.clear();
        txtRaca.clear();
        txtIdAbrigo.clear();

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Idade e ID do abrigo devem ser números.");
      } catch (Exception ex) {
        ex.printStackTrace(); // 🔥 ESSENCIAL
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