package br.com.unipatas.view.animal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AnimalAtualizarView {

    public VBox getConteudo() {
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(25));

        // --- 1. ÁREA DE BUSCA ---
        HBox hbBusca = new HBox(10);
        hbBusca.setAlignment(Pos.CENTER);
        TextField txtIdBusca = new TextField();
        txtIdBusca.setPromptText("ID para alterar");
        Button btnBuscar = new Button("Buscar");
        hbBusca.getChildren().addAll(new Label("ID:"), txtIdBusca, btnBuscar);

        // --- 2. ÁREA DO FORMULÁRIO (Começa desativada) ---
        GridPane gridForm = new GridPane();
        gridForm.setAlignment(Pos.CENTER);
        gridForm.setHgap(10); gridForm.setVgap(10);

        TextField txtNome = new TextField();
        TextField txtRaca = new TextField();
        TextField txtPeso = new TextField();
        ComboBox<String> cbPorte = new ComboBox<>();
        cbPorte.getItems().addAll("Pequeno", "Médio", "Grande");
        DatePicker dpNascimento = new DatePicker();
        DatePicker dpAdocao = new DatePicker();

        gridForm.add(new Label("Nome:"), 0, 0); gridForm.add(txtNome, 1, 0);
        gridForm.add(new Label("Raça:"), 0, 1); gridForm.add(txtRaca, 1, 1);
        gridForm.add(new Label("Peso:"), 0, 2); gridForm.add(txtPeso, 1, 2);
        gridForm.add(new Label("Porte:"), 0, 3); gridForm.add(cbPorte, 1, 3);
        gridForm.add(new Label("Data Nascimento:"), 0, 4); gridForm.add(dpNascimento, 1, 4);
        gridForm.add(new Label("Data Adoção:"), 0, 5); gridForm.add(dpAdocao, 1, 5);

        gridForm.setDisable(true); // Trava os campos

        // --- 3. BOTÃO SALVAR ---
        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setDisable(true);

        // AÇÃO DO BOTÃO BUSCAR
        btnBuscar.setOnAction(e -> {
            if (txtIdBusca.getText().equals("1")) {
                // Preenche com dados falsos do ID 1
                txtNome.setText("Rex");
                txtRaca.setText("Labrador");
                txtPeso.setText("25.5");
                cbPorte.setValue("Grande");
                // Datas precisariam de formatação complexa no Mock, vamos deixar vazias por enquanto
                
                gridForm.setDisable(false); // Destrava o formulário
                btnSalvar.setDisable(false);
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Animal não encontrado!");
                alerta.showAndWait();
                gridForm.setDisable(true);
                btnSalvar.setDisable(true);
            }
        });

        // AÇÃO DO BOTÃO SALVAR 
        btnSalvar.setOnAction(e -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Simulação: Animal atualizado com sucesso!");
            alerta.showAndWait();
            gridForm.setDisable(true);
            btnSalvar.setDisable(true);
            txtIdBusca.clear();
        });

        layoutPrincipal.getChildren().addAll(hbBusca, gridForm, btnSalvar);
        return layoutPrincipal;
    }
}