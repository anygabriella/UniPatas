package br.com.unipatas.view.adocao;

import br.com.unipatas.controller.AdocaoController;
import br.com.unipatas.model.Adocao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class AdocaoRemoverView {

  private AdocaoController controller;

  public AdocaoRemoverView() {
    try {
      controller = new AdocaoController();
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

    TextField txtId = new TextField();
    txtId.setPromptText("ID da adoção");
    txtId.setPrefWidth(120);

    Button btnExcluir = new Button("Excluir Adoção");
    btnExcluir.getStyleClass().add("botao-perigo");

    hbBusca.getChildren().addAll(new Label("ID:"), txtId, btnExcluir);

    btnExcluir.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());

        // ⚠️ IMPORTANTE: precisa existir no controller
        Adocao adocao = controller.buscar(id);

        if (adocao != null) {

          Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
          confirmacao.setTitle("Confirmação de Exclusão");
          confirmacao.setContentText(
              "Deseja realmente excluir a adoção ID: " + adocao.getId() + "?"
          );

          Optional<ButtonType> resultado = confirmacao.showAndWait();

          if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            boolean sucesso = controller.deletar(id);

            if (sucesso) {
              mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Adoção removida!");
              txtId.clear();
            } else {
              mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível remover.");
            }
          }

        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Adoção não encontrada!");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID numérico válido.");
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