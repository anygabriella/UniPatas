package br.com.unipatas.view.animal;

import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.model.Animal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AnimalBuscaView {

  private AnimalController controller;

  public AnimalBuscaView() {
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

    // 🔎 BUSCA
    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdBusca = new TextField();
    txtIdBusca.setPromptText("ID do Animal");
    txtIdBusca.setPrefWidth(120);

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

    // 📋 RESULTADOS
    GridPane gridResultados = new GridPane();
    gridResultados.setAlignment(Pos.CENTER);
    gridResultados.setHgap(10);
    gridResultados.setVgap(10);
    gridResultados.getStyleClass().add("form-grid");

    Label lblId = new Label("-");
    Label lblNome = new Label("-");
    Label lblRaca = new Label("-");
    Label lblPorte = new Label("-");
    Label lblPeso = new Label("-");
    Label lblData = new Label("-");
    Label lblAbrigo = new Label("-");

    gridResultados.add(new Label("ID:"), 0, 0);
    gridResultados.add(lblId, 1, 0);

    gridResultados.add(new Label("Nome:"), 0, 1);
    gridResultados.add(lblNome, 1, 1);

    gridResultados.add(new Label("Raça:"), 0, 2);
    gridResultados.add(lblRaca, 1, 2);

    gridResultados.add(new Label("Porte:"), 0, 3);
    gridResultados.add(lblPorte, 1, 3);

    gridResultados.add(new Label("Peso:"), 0, 4);
    gridResultados.add(lblPeso, 1, 4);

    gridResultados.add(new Label("Data Adoção:"), 0, 5);
    gridResultados.add(lblData, 1, 5);

    gridResultados.add(new Label("ID Abrigo:"), 0, 6);
    gridResultados.add(lblAbrigo, 1, 6);

    // 🔍 AÇÃO BUSCAR
    btnBuscar.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtIdBusca.getText().trim());

        Animal a = controller.buscar(id);

        if (a != null) {
          lblId.setText(String.valueOf(a.getId()));
          lblNome.setText(a.getNome());
          lblRaca.setText(a.getRaca());
          lblPorte.setText(a.getPorte());
          lblPeso.setText(String.valueOf(a.getPeso()));
          lblData.setText(a.getDataAdocao());
          lblAbrigo.setText(String.valueOf(a.getIdAbrigo()));
        } else {

          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Animal não encontrado!");

          lblId.setText("-");
          lblNome.setText("-");
          lblRaca.setText("-");
          lblPorte.setText("-");
          lblPeso.setText("-");
          lblData.setText("-");
          lblAbrigo.setText("-");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID numérico válido.");
      } catch (Exception ex) {
        ex.printStackTrace();
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao buscar: " + ex.getMessage());
      }
    });

    layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
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