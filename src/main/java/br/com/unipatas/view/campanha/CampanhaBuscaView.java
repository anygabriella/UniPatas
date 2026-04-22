package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Campanha;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CampanhaBuscaView {

  private CampanhaController controller;

  // ✅ SEM throws
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
    Label lblDesc = new Label("-");

    grid.add(new Label("ID:"), 0, 0);
    grid.add(lblId, 1, 0);
    grid.add(new Label("Nome:"), 0, 1);
    grid.add(lblNome, 1, 1);
    grid.add(new Label("Descrição:"), 0, 2);
    grid.add(lblDesc, 1, 2);

    btn.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());

        // ✅ método correto
        Campanha c = controller.buscar(id);

        if (c != null) {
          lblId.setText(String.valueOf(c.getId()));
          lblNome.setText(c.getNome());
          lblDesc.setText(c.getDescricao());
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Campanha não encontrada!");
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