package br.com.unipatas.view.abrigo;

import br.com.unipatas.controller.AbrigoController;
import br.com.unipatas.model.Abrigo;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AbrigoAtualizarView {

  private AbrigoController controller;
  private int idAtual = -1;

  public AbrigoAtualizarView() {
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

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-secundario");

    hb.getChildren().addAll(new Label("ID:"), txtId, btnBuscar);

  
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.getStyleClass().add("form-grid");

    TextField txtNome = new TextField();
    TextField txtEndereco = new TextField();
    TextField txtTelefone = new TextField();
    TextField txtCusto = new TextField();
    txtCusto.setPromptText("Ex: 1500.00");

    grid.add(new Label("Nome:"), 0, 0);
    grid.add(txtNome, 1, 0);

    grid.add(new Label("Endereço:"), 0, 1);
    grid.add(txtEndereco, 1, 1);

    grid.add(new Label("Telefone:"), 0, 2);
    grid.add(txtTelefone, 1, 2);

    grid.add(new Label("Custo Mensal:"), 0, 3);
    grid.add(txtCusto, 1, 3);

    grid.setDisable(true);


    Button btnSalvar = new Button("Salvar Alterações");
    btnSalvar.getStyleClass().add("botao-principal");
    btnSalvar.setDisable(true);

 
    btnBuscar.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtId.getText().trim());
        Abrigo a = controller.buscar(id);

        if (a != null) {
          idAtual = a.getId();

          txtNome.setText(a.getNome());
          txtEndereco.setText(a.getendereco());
          txtTelefone.setText(a.getTelefone());
          txtCusto.setText(String.valueOf(a.getCustoMensal()));

          grid.setDisable(false);
          btnSalvar.setDisable(false);

        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Abrigo não encontrado!");
          grid.setDisable(true);
          btnSalvar.setDisable(true);
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID válido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao buscar: " + ex.getMessage());
      }
    });


    btnSalvar.setOnAction(e -> {
      try {

        double custo = Double.parseDouble(txtCusto.getText().trim());

        boolean sucesso = controller.atualizar(
            idAtual,
            txtNome.getText(),
            txtEndereco.getText(),
            txtTelefone.getText(),
            custo
        );

        if (sucesso) {
          mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Abrigo atualizado!");

          grid.setDisable(true);
          btnSalvar.setDisable(true);
          txtId.clear();
          idAtual = -1;

        } else {
          mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível atualizar.");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Custo mensal inválido.");
      } catch (Exception ex) {
        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar: " + ex.getMessage());
      }
    });

    layout.getChildren().addAll(hb, grid, btnSalvar);
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