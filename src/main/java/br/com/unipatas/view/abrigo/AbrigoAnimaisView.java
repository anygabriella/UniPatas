package br.com.unipatas.view.abrigo;

import java.util.List;

import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.model.Animal;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AbrigoAnimaisView {

  private AnimalController animalController;

  public AbrigoAnimaisView() {
    try {
      animalController = new AnimalController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco.");
    }
  }

  public VBox getConteudo() {

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(25));

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdAbrigo = new TextField();
    txtIdAbrigo.setPromptText("ID do Abrigo");
    txtIdAbrigo.setPrefWidth(130);

    Button btnBuscar = new Button("Listar Animais");
    btnBuscar.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID Abrigo:"), txtIdAbrigo, btnBuscar);

    TextArea resultado = new TextArea();
    resultado.setEditable(false);
    resultado.setPrefWidth(420);
    resultado.setPrefHeight(230);
    resultado.setPromptText("Os animais do abrigo aparecerão aqui...");

    btnBuscar.setOnAction(e -> {
      try {
        int idAbrigo = Integer.parseInt(txtIdAbrigo.getText().trim());

        List<Animal> animais = animalController.listarPorAbrigo(idAbrigo);
        animais.sort((a1, a2) -> Integer.compare(a1.getId(), a2.getId()));

        resultado.clear();
        resultado.appendText("Animais cadastrados no abrigo ID " + idAbrigo + ":\n\n");

        if (animais.isEmpty()) {
          resultado.appendText("Nenhum animal encontrado.");
        } else {
          for (Animal animal : animais) {
            resultado.appendText("ID: " + animal.getId() + " | Nome: " + animal.getNome() + "\n");
          }
          resultado.appendText("Total: " + animais.size() + " animais\n\n");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID de abrigo válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao listar: " + ex.getMessage());
      }
    });

    layout.getChildren().addAll(hbBusca, resultado);
    return layout;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
  }
}