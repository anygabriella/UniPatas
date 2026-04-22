package br.com.unipatas.view.adocao;

import br.com.unipatas.controller.AdocaoController;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AdocaoCadastroView {

  private AdocaoController controller;

  public AdocaoCadastroView() {
    try {
      controller = new AdocaoController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro Crítico", "Não foi possível conectar ao banco de dados.");
    }
  }

  public GridPane getConteudo() {

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));
    grid.getStyleClass().add("form-grid");

    TextField txtAnimal = new TextField();
    TextField txtUsuario = new TextField();
    TextField txtData = new TextField();
    TextField txtStatus = new TextField();

    grid.add(new Label("ID Animal:"), 0, 0);
    grid.add(txtAnimal, 1, 0);
    grid.add(new Label("ID Usuário:"), 0, 1);
    grid.add(txtUsuario, 1, 1);
    grid.add(new Label("Data:"), 0, 2);
    grid.add(txtData, 1, 2);
    grid.add(new Label("Status:"), 0, 3);
    grid.add(txtStatus, 1, 3);

    Button btnSalvar = new Button("Salvar Adoção");
    btnSalvar.getStyleClass().add("botao-principal");

    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btnSalvar);
    grid.add(hbBtn, 1, 4);

    btnSalvar.setOnAction(e -> {
      try {
        int idAnimal = Integer.parseInt(txtAnimal.getText());
        int idUsuario = Integer.parseInt(txtUsuario.getText());

        int idGerado = controller.cadastrar(
            idAnimal,
            idUsuario,
            txtData.getText(),
            txtStatus.getText()
        );

        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
            "Adoção cadastrada com ID: " + idGerado);

        txtAnimal.clear();
        txtUsuario.clear();
        txtData.clear();
        txtStatus.clear();

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "IDs devem ser números válidos.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar: " + ex.getMessage());
      }
    });

    return grid;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
  }
}