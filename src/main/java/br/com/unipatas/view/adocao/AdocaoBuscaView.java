package br.com.unipatas.view.adocao;

import br.com.unipatas.controller.AdocaoController;
import br.com.unipatas.model.Adocao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdocaoBuscaView {

  private AdocaoController controller;

  public AdocaoBuscaView() {
    try {
      this.controller = new AdocaoController();
    } catch (Exception e) {
      mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco de dados.");
    }
  }

  public VBox getConteudo() {

    VBox layoutPrincipal = new VBox(20);
    layoutPrincipal.setAlignment(Pos.CENTER);
    layoutPrincipal.setPadding(new Insets(25));

    // 🔍 ÁREA DE BUSCA
    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdBusca = new TextField();
    txtIdBusca.setPromptText("ID da Adoção");
    txtIdBusca.setPrefWidth(120);

    Button btnBuscar = new Button("Buscar");
    btnBuscar.getStyleClass().add("botao-principal");

    hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

    // 📊 RESULTADOS
    GridPane gridResultados = new GridPane();
    gridResultados.setAlignment(Pos.CENTER);
    gridResultados.setHgap(10);
    gridResultados.setVgap(10);
    gridResultados.getStyleClass().add("form-grid");

    Label lblId = new Label("-");
    Label lblAnimal = new Label("-");
    Label lblUsuario = new Label("-");
    Label lblData = new Label("-");
    Label lblStatus = new Label("-");

    gridResultados.add(new Label("ID:"), 0, 0);
    gridResultados.add(lblId, 1, 0);

    gridResultados.add(new Label("ID Animal:"), 0, 1);
    gridResultados.add(lblAnimal, 1, 1);

    gridResultados.add(new Label("ID Usuário:"), 0, 2);
    gridResultados.add(lblUsuario, 1, 2);

    gridResultados.add(new Label("Data:"), 0, 3);
    gridResultados.add(lblData, 1, 3);

    gridResultados.add(new Label("Status:"), 0, 4);
    gridResultados.add(lblStatus, 1, 4);

    // 🔘 AÇÃO
    btnBuscar.setOnAction(e -> {
      try {
        int id = Integer.parseInt(txtIdBusca.getText().trim());
        Adocao a = controller.buscar(id);

        if (a != null) {
          lblId.setText(String.valueOf(a.getId()));
          lblAnimal.setText(String.valueOf(a.getIdAnimal()));
          lblUsuario.setText(String.valueOf(a.getIdUsuario()));
          lblData.setText(a.getData());
          lblStatus.setText(a.getStatus());

          mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Adoção encontrada!");
        } else {
          mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Adoção não encontrada!");

          // limpa
          lblId.setText("-");
          lblAnimal.setText("-");
          lblUsuario.setText("-");
          lblData.setText("-");
          lblStatus.setText("-");
        }

      } catch (NumberFormatException ex) {
        mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Digite um ID numérico válido.");
      } catch (Exception ex) {
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