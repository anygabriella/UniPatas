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
    TextField txtLocal = new TextField();
    TextField txtData = new TextField();
    TextField txtCusto = new TextField();

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(txtNome, 1, 0);

    grid.add(new Label("Local:"), 0, 1);
    grid.add(txtLocal, 1, 1);

    grid.add(new Label("Data:"), 0, 2);
    grid.add(txtData, 1, 2);

    grid.add(new Label("Custo:"), 0, 3);
    grid.add(txtCusto, 1, 3);

    Button btn = new Button("Salvar");
    btn.getStyleClass().add("botao-principal");

    btn.setOnAction(e -> {
      try {

        double custo = Double.parseDouble(txtCusto.getText());

        int id = controller.cadastrar(
            txtNome.getText(),
            txtLocal.getText(),
            txtData.getText(),
            custo
        );

        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Campanha criada! ID: " + id);

        // limpar campos
        txtNome.clear();
        txtLocal.clear();
        txtData.clear();
        txtCusto.clear();

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Custo deve ser numérico.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    grid.add(btn, 1, 4);
    return grid;
  }

  private void mostrarAlerta(Alert.AlertType t, String titulo, String msg) {
    Alert a = new Alert(t);
    a.setTitle(titulo);
    a.setHeaderText(null);
    a.setContentText(msg);
    a.showAndWait();
  }
}