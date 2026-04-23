package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Campanha;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CampanhaBuscaView {

  private CampanhaController controller;

  public CampanhaBuscaView() {
    try {
      controller = new CampanhaController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco.");
    }
  }

  public VBox getConteudo() {

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(25));

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtId = new TextField();
    txtId.setPromptText("ID da Campanha");
    txtId.setPrefWidth(120);

    Button btn = new Button("Buscar");
    btn.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID:"), txtId, btn);

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.getStyleClass().add("form-grid");

    Label lblId = new Label("-");
    Label lblNome = new Label("-");
    Label lblLocal = new Label("-");
    Label lblData = new Label("-");
    Label lblCusto = new Label("-");

    grid.add(new Label("ID:"), 0, 0);
    grid.add(lblId, 1, 0);

    grid.add(new Label("Nome:"), 0, 1);
    grid.add(lblNome, 1, 1);

    grid.add(new Label("Local:"), 0, 2);
    grid.add(lblLocal, 1, 2);

    grid.add(new Label("Data:"), 0, 3);
    grid.add(lblData, 1, 3);

    grid.add(new Label("Custo:"), 0, 4);
    grid.add(lblCusto, 1, 4);

    btn.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());

        Campanha c = controller.buscar(id);

        if (c != null) {
          lblId.setText(String.valueOf(c.getId()));
          lblNome.setText(c.getNome());
          lblLocal.setText(c.getLocal());
          lblData.setText(c.getData());
          lblCusto.setText(String.valueOf(c.getCusto()));
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Campanha não encontrada!");

          // limpar tela
          lblId.setText("-");
          lblNome.setText("-");
          lblLocal.setText("-");
          lblData.setText("-");
          lblCusto.setText("-");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    layout.getChildren().addAll(hbBusca, grid);
    return layout;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
  }
}