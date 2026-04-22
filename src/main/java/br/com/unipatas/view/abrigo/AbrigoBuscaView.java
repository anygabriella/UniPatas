package br.com.unipatas.view.abrigo;

import br.com.unipatas.controller.AbrigoController;
import br.com.unipatas.model.Abrigo;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AbrigoBuscaView {

  private AbrigoController controller;

  public AbrigoBuscaView() {
    try {
      controller = new AbrigoController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar.");
    }
  }

  public VBox getConteudo() {

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(25));

    HBox hb = new HBox(10);
    hb.setAlignment(Pos.CENTER);

    TextField txtId = new TextField();
    txtId.setPromptText("ID do abrigo");

    Button btn = new Button("Buscar");
    btn.getStyleClass().add("botao-principal");

    hb.getChildren().addAll(new Label("ID:"), txtId, btn);

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    Label lblNome = new Label("-");
    Label lblCidade = new Label("-");
    Label lblTelefone = new Label("-");

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(lblNome, 1, 0);

    grid.add(new Label("Cidade:"), 0, 1);
    grid.add(lblCidade, 1, 1);

    grid.add(new Label("Telefone:"), 0, 2);
    grid.add(lblTelefone, 1, 2);

    btn.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());
        Abrigo a = controller.buscar(id);

        if (a != null) {
          lblNome.setText(a.getNome());
          lblCidade.setText(a.getCidade());
          lblTelefone.setText(a.getTelefone());
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Abrigo não encontrado!");
        }

      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    layout.getChildren().addAll(hb, grid);
    return layout;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
    Alert a = new Alert(tipo);
    a.setTitle(titulo);
    a.setHeaderText(null);
    a.setContentText(msg);
    a.showAndWait();
  }
}