package br.com.unipatas.view.abrigo;

import br.com.unipatas.controller.AbrigoController;
import br.com.unipatas.model.Abrigo;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AbrigoBuscaView {

  private AbrigoController controller;

  public AbrigoBuscaView() {
    try {
      controller = new AbrigoController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar.");
    }
  }

  public VBox getConteudo() {

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(25));


    HBox hb = new HBox(10);
    hb.setAlignment(Pos.CENTER);

    TextField txtId = new TextField();
    txtId.setPromptText("ID do abrigo");
    txtId.setPrefWidth(120);

    Button btn = new Button("Buscar");
    btn.getStyleClass().add("botao-principal");

    hb.getChildren().addAll(new Label("ID:"), txtId, btn);


    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.getStyleClass().add("form-grid");

    Label lblNome = new Label("-");
    Label lblEndereco = new Label("-");
    Label lblTelefone = new Label("-");
    Label lblCusto = new Label("-");

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(lblNome, 1, 0);

    grid.add(new Label("Endereço:"), 0, 1);
    grid.add(lblEndereco, 1, 1);

    grid.add(new Label("Telefone:"), 0, 2);
    grid.add(lblTelefone, 1, 2);

    grid.add(new Label("Custo Mensal:"), 0, 3);
    grid.add(lblCusto, 1, 3);


    btn.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());
        Abrigo a = controller.buscar(id);

        if (a != null) {
          lblNome.setText(a.getNome());
          lblEndereco.setText(a.getendereco());
          lblTelefone.setText(a.getTelefone());
          lblCusto.setText("R$ " + a.getCustoMensal());
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Abrigo não encontrado!");

    
          lblNome.setText("-");
          lblEndereco.setText("-");
          lblTelefone.setText("-");
          lblCusto.setText("-");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", ex.getMessage());
      }
    });

    layout.getChildren().addAll(hb, grid);
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