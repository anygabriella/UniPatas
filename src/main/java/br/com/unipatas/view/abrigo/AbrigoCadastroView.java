package br.com.unipatas.view.abrigo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AbrigoCadastroView {
    public GridPane getConteudo() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        // Campos do Abrigo
        TextField txtNome = new TextField();
        TextField txtEndereco = new TextField();
        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("(XX) XXXXX-XXXX");
        
        TextField txtCusto = new TextField();
        txtCusto.setPromptText("Ex: 1500.00");

        // Montando o Grid
        grid.add(new Label("Nome do Abrigo:"), 0, 0); grid.add(txtNome, 1, 0);
        grid.add(new Label("Endereço:"), 0, 1); grid.add(txtEndereco, 1, 1);
        grid.add(new Label("Telefone:"), 0, 2); grid.add(txtTelefone, 1, 2);
        grid.add(new Label("Custo Mensal (R$):"), 0, 3); grid.add(txtCusto, 1, 3);

        // Botão de Salvar
        Button btnSalvar = new Button("Salvar Abrigo");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnSalvar);
        grid.add(hbBtn, 1, 4);

        // AÇÃO "MOCKADA" (Simulação)
        btnSalvar.setOnAction(e -> {
            String resumo = "Nome: " + txtNome.getText() + "\n" +
                            "Endereço: " + txtEndereco.getText() + "\n" +
                            "Custo: R$ " + txtCusto.getText();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Simulação");
            alerta.setHeaderText("Abrigo cadastrado com sucesso!");
            alerta.setContentText(resumo);
            alerta.showAndWait();

            // Limpa os campos
            txtNome.clear(); txtEndereco.clear(); txtTelefone.clear(); txtCusto.clear();
        });

        return grid;
    }
}
