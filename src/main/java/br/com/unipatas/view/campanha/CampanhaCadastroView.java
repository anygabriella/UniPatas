package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CampanhaCadastroView {

  private CampanhaController controller;

  public CampanhaCadastroView() {
    try {
      controller = new CampanhaController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar.");
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
    TextField txtDescricao = new TextField();
    TextField txtInicio = new TextField();
    TextField txtFim = new TextField();

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(txtNome, 1, 0);
    grid.add(new Label("Descrição:"), 0, 1);
    grid.add(txtDescricao, 1, 1);
    grid.add(new Label("Data Início:"), 0, 2);
    grid.add(txtInicio, 1, 2);
    grid.add(new Label("Data Fim:"), 0, 3);
    grid.add(txtFim, 1, 3);

    Button btn = new Button("Salvar");
    btn.getStyleClass().add("botao-principal");

    btn.setOnAction(e -> {
      try {
        int id = controller.cadastrar(
            txtNome.getText(),
            txtDescricao.getText(),
            txtInicio.getText(),
            txtFim.getText());

        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "ID: " + id);
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    grid.add(btn, 1, 4);
    return grid;
  }

  private void mostrarAlerta(Alert.AlertType t, String ti, String m) {
    new Alert(t, m).showAndWait();
  }
}