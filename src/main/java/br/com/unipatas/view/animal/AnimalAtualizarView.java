package br.com.unipatas.view.animal;

import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.model.Animal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AnimalAtualizarView {

  private AnimalController controller;
  private int idAtual = -1;

  public AnimalAtualizarView() {
    try {
      this.controller = new AnimalController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco.");
    }
  }

  public VBox getConteudo() {
    VBox layoutPrincipal = new VBox(20);
    layoutPrincipal.setAlignment(Pos.CENTER);
    layoutPrincipal.setPadding(new Insets(25));

    // 🔍 BUSCA
    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdBusca = new TextField();
    txtIdBusca.setPromptText("ID do Animal");
    txtIdBusca.setPrefWidth(120);

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-secundario");

    hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

    // 📋 FORM
    GridPane gridForm = new GridPane();
    gridForm.setAlignment(Pos.CENTER);
    gridForm.setHgap(10);
    gridForm.setVgap(10);
    gridForm.getStyleClass().add("form-grid");

    TextField txtNome = new TextField();
    TextField txtIdade = new TextField();
    TextField txtEspecie = new TextField();
    TextField txtRaca = new TextField();
    TextField txtAbrigo = new TextField();

    gridForm.add(new Label("Nome:"), 0, 0);
    gridForm.add(txtNome, 1, 0);

    gridForm.add(new Label("Idade:"), 0, 1);
    gridForm.add(txtIdade, 1, 1);

    gridForm.add(new Label("Espécie:"), 0, 2);
    gridForm.add(txtEspecie, 1, 2);

    gridForm.add(new Label("Raça:"), 0, 3);
    gridForm.add(txtRaca, 1, 3);

    gridForm.add(new Label("ID Abrigo:"), 0, 4);
    gridForm.add(txtAbrigo, 1, 4);

    gridForm.setDisable(true);

    // 💾 SALVAR
    Button btnSalvar = new Button("Salvar Alterações");
    btnSalvar.getStyleClass().add("botao-principal");
    btnSalvar.setDisable(true);

    // 🔎 AÇÃO BUSCAR
    btnBuscar.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtIdBusca.getText().trim());
        Animal a = controller.buscarAnimal(id);

        if (a != null) {
          idAtual = a.getId();

          txtNome.setText(a.getNome());
          txtIdade.setText(String.valueOf(a.getIdade()));
          txtEspecie.setText(a.getEspecie());
          txtRaca.setText(a.getRaca());
          txtAbrigo.setText(String.valueOf(a.getIdAbrigo()));

          gridForm.setDisable(false);
          btnSalvar.setDisable(false);
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Animal não encontrado!");
          gridForm.setDisable(true);
          btnSalvar.setDisable(true);
        }

      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    // 💾 AÇÃO SALVAR
    btnSalvar.setOnAction(e -> {
      try {
        boolean sucesso = controller.atualizarAnimal(
            idAtual,
            txtNome.getText(),
            Integer.parseInt(txtIdade.getText()),
            txtEspecie.getText(),
            txtRaca.getText(),
            Integer.parseInt(txtAbrigo.getText())
        );

        if (sucesso) {
          mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal atualizado!");
          gridForm.setDisable(true);
          btnSalvar.setDisable(true);
          txtIdBusca.clear();
          idAtual = -1;
        } else {
          mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao atualizar.");
        }

      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    layoutPrincipal.getChildren().addAll(hbBusca, gridForm, btnSalvar);
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