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
    txtId.setPromptText("ID do abrigo");
    txtId.setPrefWidth(120);

    Button btn = new Button("Excluir Abrigo");
    btn.getStyleClass().add("botao-perigo");

    hb.getChildren().addAll(new Label("ID:"), txtId, btn);

    btn.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());

        Abrigo a = controller.buscar(id);

        if (a != null) {

          Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
          confirm.setTitle("Confirmação de Exclusão");
          confirm.setHeaderText(null);
          confirm.setContentText(
              "Deseja excluir o abrigo: " + a.getNome() + " (ID: " + a.getId() + ")?"
          );

          Optional<ButtonType> res = confirm.showAndWait();

          if (res.isPresent() && res.get() == ButtonType.OK) {

            boolean sucesso = controller.remover(id);

            if (sucesso) {
              mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Abrigo removido!");
              txtId.clear();
            } else {
              mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível remover.");
            }
          }

        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Abrigo não encontrado!");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID numérico válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir: " + ex.getMessage());
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