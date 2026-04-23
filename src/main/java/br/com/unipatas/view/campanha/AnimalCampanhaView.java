package br.com.unipatas.view.campanha;

import java.util.List;
import br.com.unipatas.controller.AnimalCampanhaController;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AnimalCampanhaView {

  private AnimalCampanhaController controller;

  public AnimalCampanhaView() {
    try {
      controller = new AnimalCampanhaController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco.");
    }
  }

  public VBox getConteudo() {

    VBox layout = new VBox(22);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(25));

    Label titulo = new Label("Relacionar Animais e Campanhas");
    titulo.getStyleClass().add("label-boas-vindas-sub");

    GridPane gridVinculo = new GridPane();
    gridVinculo.setAlignment(Pos.CENTER);
    gridVinculo.setHgap(10);
    gridVinculo.setVgap(10);
    gridVinculo.getStyleClass().add("form-grid");

    TextField txtIdAnimal = new TextField();
    txtIdAnimal.setPromptText("ID do Animal");
    txtIdAnimal.setPrefWidth(140);

    TextField txtIdCampanha = new TextField();
    txtIdCampanha.setPromptText("ID da Campanha");
    txtIdCampanha.setPrefWidth(140);

    gridVinculo.add(new Label("ID Animal:"), 0, 0);
    gridVinculo.add(txtIdAnimal, 1, 0);

    gridVinculo.add(new Label("ID Campanha:"), 0, 1);
    gridVinculo.add(txtIdCampanha, 1, 1);

    Button btnVincular = new Button("Vincular");
    btnVincular.getStyleClass().add("botao-principal");

    Button btnDesvincular = new Button("Desvincular");
    btnDesvincular.getStyleClass().add("botao-perigo");

    HBox botoesVinculo = new HBox(10);
    botoesVinculo.setAlignment(Pos.CENTER);
    botoesVinculo.getChildren().addAll(btnVincular, btnDesvincular);

    Separator separador = new Separator();
    separador.setMaxWidth(520);

    Label subtitulo = new Label("Consultas do Relacionamento");
    subtitulo.getStyleClass().add("label-boas-vindas-sub");

    HBox areaBusca = new HBox(10);
    areaBusca.setAlignment(Pos.CENTER);

    TextField txtBuscaAnimal = new TextField();
    txtBuscaAnimal.setPromptText("ID Animal");
    txtBuscaAnimal.setPrefWidth(110);

    Button btnListarCampanhas = new Button("Campanhas do Animal");
    btnListarCampanhas.getStyleClass().add("botao-secundario");

    TextField txtBuscaCampanha = new TextField();
    txtBuscaCampanha.setPromptText("ID Campanha");
    txtBuscaCampanha.setPrefWidth(110);

    Button btnListarAnimais = new Button("Animais da Campanha");
    btnListarAnimais.getStyleClass().add("botao-secundario");

    areaBusca.getChildren().addAll(
        txtBuscaAnimal,
        btnListarCampanhas,
        txtBuscaCampanha,
        btnListarAnimais
    );

    TextArea resultado = new TextArea();
    resultado.setEditable(false);
    resultado.setPrefWidth(520);
    resultado.setPrefHeight(140);
    resultado.setPromptText("Os resultados aparecerão aqui...");

    btnVincular.setOnAction(e -> {
      try {
        int idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
        int idCampanha = Integer.parseInt(txtIdCampanha.getText().trim());

        boolean sucesso = controller.vincular(idAnimal, idCampanha);

        if (sucesso) {
          mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal vinculado à campanha.");
          txtIdAnimal.clear();
          txtIdCampanha.clear();
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Animal ou campanha inexistente, ou vínculo já cadastrado.");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite IDs numéricos válidos.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    btnDesvincular.setOnAction(e -> {
      try {
        int idAnimal = Integer.parseInt(txtIdAnimal.getText().trim());
        int idCampanha = Integer.parseInt(txtIdCampanha.getText().trim());

        boolean sucesso = controller.desvincular(idAnimal, idCampanha);

        if (sucesso) {
          mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Vínculo removido.");
          txtIdAnimal.clear();
          txtIdCampanha.clear();
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Vínculo não encontrado.");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite IDs numéricos válidos.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    btnListarCampanhas.setOnAction(e -> {
      try {
        int idAnimal = Integer.parseInt(txtBuscaAnimal.getText().trim());
        List<Integer> campanhas = controller.listarCampanhasDoAnimal(idAnimal);

        resultado.clear();
        resultado.appendText("Campanhas vinculadas ao animal ID " + idAnimal + ":\n\n");

        if (campanhas.isEmpty()) {
          resultado.appendText("Nenhuma campanha encontrada.");
        } else {
          for (int id : campanhas) {
            resultado.appendText("• Campanha ID: " + id + "\n");
          }
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID de animal válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    btnListarAnimais.setOnAction(e -> {
      try {
        int idCampanha = Integer.parseInt(txtBuscaCampanha.getText().trim());
        List<Integer> animais = controller.listarAnimaisDaCampanha(idCampanha);

        resultado.clear();
        resultado.appendText("Animais vinculados à campanha ID " + idCampanha + ":\n\n");

        if (animais.isEmpty()) {
          resultado.appendText("Nenhum animal encontrado.");
        } else {
          for (int id : animais) {
            resultado.appendText("• Animal ID: " + id + "\n");
          }
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID de campanha válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    layout.getChildren().addAll(
        titulo,
        gridVinculo,
        botoesVinculo,
        separador,
        subtitulo,
        areaBusca,
        resultado
    );

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