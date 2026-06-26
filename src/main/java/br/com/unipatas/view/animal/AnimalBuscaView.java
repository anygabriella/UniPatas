package br.com.unipatas.view.animal;

import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.model.Animal;
import br.com.unipatas.view.util.AlertaUtil;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class AnimalBuscaView {

    private AnimalController controller;
    private TableView<Animal> tabela;
    private TextField txtFiltro;

    public AnimalBuscaView() {
        try {
            controller = new AnimalController();
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro Crítico",
                    "Não foi possível conectar ao banco de animais."
            );
        }
    }

    public VBox getConteudo() {
        VBox layout = new VBox(14);
        layout.setPadding(new Insets(25));
        layout.getStyleClass().add("tela-listagem");

        Label titulo = new Label("Pesquisar animais");
        titulo.getStyleClass().add("label-secao");

        txtFiltro = new TextField();
        txtFiltro.setPromptText("Filtrar por ID, nome, raça, porte ou ID do abrigo");
        HBox.setHgrow(txtFiltro, Priority.ALWAYS);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.getStyleClass().add("botao-secundario");

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("botao-principal");

        HBox barraAcoes = new HBox(10, txtFiltro, btnFiltrar, btnAtualizar);

        tabela = new TableView<>();
        tabela.setPrefHeight(420);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        TableColumn<Animal, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

        TableColumn<Animal, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Animal, String> colRaca = new TableColumn<>("Raça");
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));

        TableColumn<Animal, String> colPorte = new TableColumn<>("Porte");
        colPorte.setCellValueFactory(new PropertyValueFactory<>("porte"));

        TableColumn<Animal, Float> colPeso = new TableColumn<>("Peso");
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        TableColumn<Animal, String> colData = new TableColumn<>("Data Adoção");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataAdocao"));

        TableColumn<Animal, Integer> colAbrigo = new TableColumn<>("ID Abrigo");
        colAbrigo.setCellValueFactory(new PropertyValueFactory<>("idAbrigo"));

        tabela.getColumns().addAll(
                colId,
                colNome,
                colRaca,
                colPorte,
                colPeso,
                colData,
                colAbrigo
        );

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnFiltrar.setOnAction(e -> carregarAnimais());

        btnAtualizar.setOnAction(e -> {
            txtFiltro.clear();
            carregarAnimais();
        });

        carregarAnimais();

        layout.getChildren().addAll(titulo, barraAcoes, tabela);

        return layout;
    }

    private void carregarAnimais() {
        try {
            String filtro = txtFiltro == null
                    ? ""
                    : txtFiltro.getText().trim();

            List<Animal> animais;
            if (filtro.isEmpty()) {
                animais = controller.listarTodos();
            } else {
                animais = controller.buscarPorFiltro(filtro);
            }

            tabela.setItems(FXCollections.observableArrayList(animais));

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao carregar animais: " + e.getMessage()
            );
        }
    }
}
