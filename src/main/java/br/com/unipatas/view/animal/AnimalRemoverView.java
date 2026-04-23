package br.com.unipatas.view.animal;

import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.model.Animal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class AnimalRemoverView {

  private AnimalController controller;

  public AnimalRemoverView() {
    try {
      this.controller = new AnimalController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco de dados.");
    }
  }

  public VBox getConteudo() {

    VBox layoutPrincipal = new VBox(20);
    layoutPrincipal.setAlignment(Pos.CENTER);
    layoutPrincipal.setPadding(new Insets(25));

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdDeletar = new TextField();
    txtIdDeletar.setPromptText("ID do Animal");
    txtIdDeletar.setPrefWidth(120);

    Button btnDeletar = new Button("Excluir Animal");
    btnDeletar.getStyleClass().add("botao-perigo");

    hbBusca.getChildren().addAll(new Label("ID:"), txtIdDeletar, btnDeletar);

    // 🔥 AÇÃO REAL (igual Usuario)
    btnDeletar.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtIdDeletar.getText().trim());

        Animal animal = controller.buscar(id);

        if (animal != null) {

          Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
          confirmacao.setTitle("Confirmação de Exclusão");
          confirmacao.setContentText(
              "Deseja realmente excluir o animal: " + animal.getNome() +
              " (ID: " + animal.getId() + ")?");

          Optional<ButtonType> resultado = confirmacao.showAndWait();

          if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            boolean sucesso = controller.deletar(id);

            if (sucesso) {
              mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal removido!");
              txtIdDeletar.clear();
            } else {
              mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível remover.");
            }
          }

        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Animal não encontrado!");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir: " + ex.getMessage());
      }
    });

    layoutPrincipal.getChildren().add(hbBusca);
    return layoutPrincipal;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
  }
}