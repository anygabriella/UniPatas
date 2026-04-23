package br.com.unipatas.view.abrigo;

import br.com.unipatas.controller.AbrigoController;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AbrigoCadastroView {

  private AbrigoController controller;

  public AbrigoCadastroView() {
    try {
      controller = new AbrigoController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco.");
    }
  }

  public GridPane getConteudo() {

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25));
    grid.getStyleClass().add("form-grid");

    TextField txtNome = new TextField();
    TextField txtCidade = new TextField();
    TextField txtTelefone = new TextField();
    TextField txtCusto = new TextField(); 

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(txtNome, 1, 0);

    grid.add(new Label("Cidade:"), 0, 1);
    grid.add(txtCidade, 1, 1);

    grid.add(new Label("Telefone:"), 0, 2);
    grid.add(txtTelefone, 1, 2);

    grid.add(new Label("Custo Mensal:"), 0, 3);
    grid.add(txtCusto, 1, 3);

    Button btnSalvar = new Button("Salvar Abrigo");
    btnSalvar.getStyleClass().add("botao-principal");

    HBox hb = new HBox(10);
    hb.setAlignment(Pos.BOTTOM_RIGHT);
    hb.getChildren().add(btnSalvar);

    grid.add(hb, 1, 4);

    btnSalvar.setOnAction(e -> {
      try {

        long custo = Long.parseLong(txtCusto.getText()); 

        int id = controller.cadastrar(
            txtNome.getText(),
            txtCidade.getText(),
            txtTelefone.getText(),
            custo 
        );

        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Abrigo criado! ID: " + id);

        txtNome.clear();
        txtCidade.clear();
        txtTelefone.clear();
        txtCusto.clear(); 

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um custo válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    return grid;
  }

  private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
    Alert a = new Alert(tipo);
    a.setTitle(titulo);
    a.setHeaderText(null);
    a.setContentText(msg);
    a.showAndWait();
  }
}