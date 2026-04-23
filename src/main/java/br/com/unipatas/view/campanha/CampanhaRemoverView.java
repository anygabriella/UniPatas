package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Campanha;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Optional;

public class CampanhaRemoverView {

  private CampanhaController controller;

  public CampanhaRemoverView() {
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

    HBox hb = new HBox(10);
    hb.setAlignment(Pos.CENTER);

    TextField txtId = new TextField();
    txtId.setPromptText("ID da campanha");
    txtId.setPrefWidth(120);

    Button btn = new Button("Excluir Campanha");
    btn.getStyleClass().add("botao-perigo");

    hb.getChildren().addAll(new Label("ID:"), txtId, btn);

    btn.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());

        Campanha c = controller.buscar(id);

        if (c != null) {

          Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
          confirm.setTitle("Confirmação");
          confirm.setHeaderText(null);
          confirm.setContentText(
              "Deseja excluir a campanha:\n\n" +
              c.getNome() +
              "\n(Local: " + c.getLocal() +
              ", Data: " + c.getData() + ")?"
          );

          Optional<ButtonType> res = confirm.showAndWait();

          if (res.isPresent() && res.get() == ButtonType.OK) {

            boolean sucesso = controller.remover(id);

            if (sucesso) {
              mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Campanha removida!");
              txtId.clear();
            } else {
              mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível remover.");
            }
          }

        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Campanha não encontrada!");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID válido.");
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