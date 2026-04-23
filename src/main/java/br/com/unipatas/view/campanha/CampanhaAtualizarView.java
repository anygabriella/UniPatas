package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Campanha;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CampanhaAtualizarView {

  private CampanhaController controller;
  private int idAtual = -1;

  public CampanhaAtualizarView() {
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

    // 🔍 BUSCA
    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtId = new TextField();
    txtId.setPromptText("ID da campanha");

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-secundario");

    hbBusca.getChildren().addAll(new Label("ID:"), txtId, btnBuscar);

    // 📋 FORM
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

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

    grid.setDisable(true);

    // 💾 SALVAR
    Button btnSalvar = new Button("Salvar Alterações");
    btnSalvar.getStyleClass().add("botao-principal");
    btnSalvar.setDisable(true);

    // 🔎 BUSCAR
    btnBuscar.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());
        Campanha c = controller.buscar(id);

        if (c != null) {
          idAtual = c.getId();

          txtNome.setText(c.getNome());
          txtLocal.setText(c.getLocal());
          txtData.setText(c.getData());
          txtCusto.setText(String.valueOf(c.getCusto()));

          grid.setDisable(false);
          btnSalvar.setDisable(false);
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Campanha não encontrada!");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    // 💾 SALVAR
    btnSalvar.setOnAction(e -> {
      try {
        boolean sucesso = controller.atualizar(
            idAtual,
            txtNome.getText(),
            txtLocal.getText(),
            txtData.getText(),
            Double.parseDouble(txtCusto.getText())
        );

        if (sucesso) {
          mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Campanha atualizada!");
          grid.setDisable(true);
          btnSalvar.setDisable(true);
          txtId.clear();
          idAtual = -1;
        } else {
          mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao atualizar.");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Custo deve ser numérico.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    layout.getChildren().addAll(hbBusca, grid, btnSalvar);
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