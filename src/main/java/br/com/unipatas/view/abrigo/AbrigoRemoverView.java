package br.com.unipatas.view.abrigo;

import br.com.unipatas.controller.AbrigoController;
import br.com.unipatas.model.Abrigo;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Optional;

public class AbrigoRemoverView {

  private AbrigoController controller;

  public AbrigoRemoverView() {
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
    Button btn = new Button("Excluir");
    btn.getStyleClass().add("botao-perigo");

    hb.getChildren().addAll(new Label("ID:"), txtId, btn);

    btn.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText());

        Abrigo a = controller.buscar(id);

        if (a != null) {

          Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
          confirm.setContentText("Excluir " + a.getNome() + "?");

          Optional<ButtonType> res = confirm.showAndWait();

          if (res.isPresent() && res.get() == ButtonType.OK) {
            controller.remover(id);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Removido!");
          }
        }

      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    layout.getChildren().add(hb);
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