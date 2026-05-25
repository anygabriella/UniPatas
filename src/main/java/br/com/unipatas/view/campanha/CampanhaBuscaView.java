package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Campanha;
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

public class CampanhaBuscaView {

    private CampanhaController controller;
    private TableView<Campanha> tabela;
    private TextField txtFiltro;

    public CampanhaBuscaView() {
        try {
            controller = new CampanhaController();
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro Crítico",
                    "Não foi possível conectar ao banco de campanhas."
            );
        }
    }

    public VBox getConteudo() {
        VBox layout = new VBox(14);
        layout.setPadding(new Insets(25));
        layout.getStyleClass().add("tela-listagem");

        Label titulo = new Label("Pesquisar campanhas");
        titulo.getStyleClass().add("label-secao");

        txtFiltro = new TextField();
        txtFiltro.setPromptText("Filtrar por ID, nome, local, data ou custo");
        HBox.setHgrow(txtFiltro, Priority.ALWAYS);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.getStyleClass().add("botao-secundario");

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("botao-principal");

        HBox barraAcoes = new HBox(10, txtFiltro, btnFiltrar, btnAtualizar);

        tabela = new TableView<>();
        tabela.setPrefHeight(420);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        TableColumn<Campanha, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

        TableColumn<Campanha, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Campanha, String> colLocal = new TableColumn<>("Local");
        colLocal.setCellValueFactory(new PropertyValueFactory<>("local"));

        TableColumn<Campanha, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Campanha, Double> colCusto = new TableColumn<>("Custo");
        colCusto.setCellValueFactory(new PropertyValueFactory<>("custo"));

        tabela.getColumns().addAll(
                colId,
                colNome,
                colLocal,
                colData,
                colCusto
        );

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnFiltrar.setOnAction(e -> carregarCampanhas());

        btnAtualizar.setOnAction(e -> {
            txtFiltro.clear();
            carregarCampanhas();
        });

        carregarCampanhas();

        layout.getChildren().addAll(titulo, barraAcoes, tabela);

        return layout;
    }

    private void carregarCampanhas() {
        try {
            List<Campanha> campanhas = controller.listarTodos();

            String filtro = txtFiltro == null
                    ? ""
                    : txtFiltro.getText().trim().toLowerCase();

            if (!filtro.isEmpty()) {
                campanhas = campanhas.stream()
                        .filter(campanha ->
                                String.valueOf(campanha.getId()).contains(filtro)
                                        || contem(campanha.getNome(), filtro)
                                        || contem(campanha.getLocal(), filtro)
                                        || contem(campanha.getData(), filtro)
                                        || String.valueOf(campanha.getCusto()).contains(filtro)
                        )
                        .toList();
            }

            tabela.setItems(FXCollections.observableArrayList(campanhas));

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao carregar campanhas: " + e.getMessage()
            );
        }
    }

    private boolean contem(String texto, String filtro) {
        return texto != null && texto.toLowerCase().contains(filtro);
    }
}