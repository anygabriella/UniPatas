package br.com.unipatas.view.animal;

import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.model.Animal;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AnimalAtualizarView {

  private AnimalController controller;
  private int idAtual = -1;
  private int idInicial = -1;

  public AnimalAtualizarView() {
    this(-1);
  }

  public AnimalAtualizarView(int idInicial) {
    this.idInicial = idInicial;
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

    
    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdBusca = new TextField();
    txtIdBusca.setPromptText("ID do Animal");

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-secundario");

    hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

    
    GridPane gridForm = new GridPane();
    gridForm.setAlignment(Pos.CENTER);
    gridForm.setHgap(10);
    gridForm.setVgap(10);
    gridForm.getStyleClass().add("form-grid");

    TextField txtNome = new TextField();
    TextField txtRaca = new TextField();
    TextField txtPorte = new TextField();
    TextField txtPeso = new TextField();
    TextField txtData = new TextField();
    TextField txtAbrigo = new TextField();

    gridForm.add(new Label("Nome:"), 0, 0);
    gridForm.add(txtNome, 1, 0);

    gridForm.add(new Label("Raça:"), 0, 1);
    gridForm.add(txtRaca, 1, 1);

    gridForm.add(new Label("Porte:"), 0, 2);
    gridForm.add(txtPorte, 1, 2);

    gridForm.add(new Label("Peso:"), 0, 3);
    gridForm.add(txtPeso, 1, 3);

    gridForm.add(new Label("Data Adoção:"), 0, 4);
    gridForm.add(txtData, 1, 4);

    gridForm.add(new Label("ID Abrigo:"), 0, 5);
    gridForm.add(txtAbrigo, 1, 5);

    gridForm.setDisable(true);

  
    Button btnSalvar = new Button("Salvar Alterações");
    btnSalvar.getStyleClass().add("botao-principal");
    btnSalvar.setDisable(true);

    
    btnBuscar.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtIdBusca.getText());
        Animal a = controller.buscar(id);

        if (a != null) {
          idAtual = a.getId();

          txtNome.setText(a.getNome());
          txtRaca.setText(a.getRaca());
          txtPorte.setText(a.getPorte());
          txtPeso.setText(String.valueOf(a.getPeso()));
          txtData.setText(a.getDataAdocao());
          txtAbrigo.setText(String.valueOf(a.getIdAbrigo()));

          gridForm.setDisable(false);
          btnSalvar.setDisable(false);

        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Animal não encontrado!");
        }

      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    
    btnSalvar.setOnAction(e -> {
      try {

        boolean sucesso = controller.atualizar(
            idAtual,
            txtNome.getText(),
            txtRaca.getText(),
            txtPorte.getText(),
            Float.parseFloat(txtPeso.getText()),
            txtData.getText(),
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

    if (idInicial > 0) {
      txtIdBusca.setText(String.valueOf(idInicial));
      btnBuscar.fire();
    }

    return layoutPrincipal;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
    Alert a = new Alert(tipo);
    a.setTitle(titulo);
    a.setHeaderText(null);
    a.setContentText(msg);
    a.showAndWait();
  }
}