package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.AnimalCampanhaController;
import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Animal;
import br.com.unipatas.model.Campanha;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class AnimalCampanhaView extends VBox {

  private AnimalCampanhaController controller;
  private AnimalController animalController;
  private CampanhaController campanhaController;

  private ComboBox<Integer> cbAnimalId;
  private ComboBox<Integer> cbCampanhaId;

  public AnimalCampanhaView() {

    try {
      controller = new AnimalCampanhaController();
      animalController = new AnimalController();
      campanhaController = new CampanhaController();
    } catch (Exception e) {
      e.printStackTrace();
      mostrarAlerta(Alert.AlertType.ERROR, "Erro Fatal", "Não foi possível carregar os bancos de dados.");
    }

    // Configuração do Layout Principal da Tela (VBox)
    this.setPadding(new Insets(30));
    this.setSpacing(20);
    this.setAlignment(Pos.TOP_CENTER);

    // Título
    Label titulo = new Label("Gerenciamento de Vínculos: Animais e Campanhas");
    titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    // Formulário Invisível (GridPane) para alinhar os campos
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(15);

    // --- Configurando o ComboBox de Animais ---
    grid.add(new Label("ID do Animal:"), 0, 0);
    cbAnimalId = new ComboBox<>();
    try {
      List<Animal> animais = animalController.listarTodos();
      for (Animal a : animais) {
        cbAnimalId.getItems().add(a.getId());
      }
    } catch (Exception e) {
      System.out.println("Erro ao carregar animais: ");
      e.printStackTrace(); 
    }
    cbAnimalId.setPromptText("Selecione o Animal");
    grid.add(cbAnimalId, 1, 0);

    // --- Configurando o ComboBox de Campanhas ---
    grid.add(new Label("ID da Campanha:"), 0, 1);
    cbCampanhaId = new ComboBox<>();
    try {
      List<Campanha> campanhas = campanhaController.listarTodos();
      for (Campanha c : campanhas) {
        cbCampanhaId.getItems().add(c.getId());
      }
    } catch (Exception e) {
      System.out.println("Erro ao carregar campanhas: " + e.getMessage());
    }
    cbCampanhaId.setPromptText("Selecione a Campanha");
    grid.add(cbCampanhaId, 1, 1);

    // Botões de Ação (HBox)
    HBox boxBotoesPrincipais = new HBox(15);
    boxBotoesPrincipais.setAlignment(Pos.CENTER);

    Button btnVincular = new Button("Vincular");
    btnVincular.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
    btnVincular.setOnAction(e -> acaoVincular());

    Button btnDesvincular = new Button("Desvincular");
    btnDesvincular.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
    btnDesvincular.setOnAction(e -> acaoDesvincular());

    boxBotoesPrincipais.getChildren().addAll(btnVincular, btnDesvincular);

    // Botão de Consulta Separado
    Button btnConsultar = new Button("Ver Campanhas deste Animal");
    btnConsultar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
    btnConsultar.setOnAction(e -> acaoConsultar());

    // Adiciona tudo na tela
    this.getChildren().addAll(titulo, grid, boxBotoesPrincipais, new Separator(), btnConsultar);
  }

  // --- Métodos de Ação dos Botões ---

  private void acaoVincular() {
    if (cbAnimalId.getValue() == null || cbCampanhaId.getValue() == null) {
      mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione um Animal e uma Campanha.");
      return;
    }

    try {
      boolean sucesso = controller.vincular(cbAnimalId.getValue(), cbCampanhaId.getValue());
      if (sucesso) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "O Animal foi vinculado à Campanha com sucesso!");
      } else {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Este vínculo já existe no sistema.");
      }
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao vincular: " + e.getMessage());
    }
  }

  private void acaoDesvincular() {
    if (cbAnimalId.getValue() == null || cbCampanhaId.getValue() == null) {
      mostrarAlerta(Alert.AlertType.WARNING, "Aviso",
          "Por favor, selecione um Animal e uma Campanha para desvincular.");
      return;
    }

    try {
      boolean sucesso = controller.desvincular(cbAnimalId.getValue(), cbCampanhaId.getValue());
      if (sucesso) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "O vínculo foi removido.");
      } else {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Este vínculo não foi encontrado no sistema.");
      }
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao desvincular: " + e.getMessage());
    }
  }

  private void acaoConsultar() {
    if (cbAnimalId.getValue() == null) {
      mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Selecione um Animal para ver suas campanhas.");
      return;
    }

    try {
      List<Integer> lista = controller.buscarCampanhasDoAnimal(cbAnimalId.getValue());
      if (lista.isEmpty()) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Resultado", "Este animal não participa de nenhuma campanha.");
      } else {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Resultado",
            "Este animal participa das Campanhas IDs: " + lista.toString());
      }
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao buscar as campanhas: " + e.getMessage());
    }
  }

  // Método utilitário para gerar pop-ups rápidos
  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
  }
}