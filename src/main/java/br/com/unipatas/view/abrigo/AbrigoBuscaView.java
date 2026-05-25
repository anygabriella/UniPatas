package br.com.unipatas.view.abrigo;

import br.com.unipatas.controller.AbrigoController;
import br.com.unipatas.model.Abrigo;
import br.com.unipatas.view.util.AlertaUtil;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class AbrigoBuscaView {

    private AbrigoController controller;
    private TableView<Abrigo> tabela;
    private TextField txtFiltro;

    public AbrigoBuscaView() {
        try {
            controller = new AbrigoController();
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro Crítico",
                    "Não foi possível conectar ao banco de abrigos."
            );
        }
    }

    public VBox getConteudo() {
        VBox layout = new VBox(14);
        layout.setPadding(new Insets(25));
        layout.getStyleClass().add("tela-listagem");

        Label titulo = new Label("Pesquisar abrigos");
        titulo.getStyleClass().add("label-secao");

        txtFiltro = new TextField();
        txtFiltro.setPromptText("Filtrar por ID, nome, endereço, telefone ou custo mensal");
        HBox.setHgrow(txtFiltro, Priority.ALWAYS);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.getStyleClass().add("botao-secundario");

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("botao-principal");

        HBox barraAcoes = new HBox(10, txtFiltro, btnFiltrar, btnAtualizar);

        tabela = new TableView<>();
        tabela.setPrefHeight(420);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        TableColumn<Abrigo, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cell ->
                new SimpleIntegerProperty(cell.getValue().getId())
        );
        colId.setPrefWidth(60);

        TableColumn<Abrigo, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getNome())
        );

        TableColumn<Abrigo, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getendereco())
        );

        TableColumn<Abrigo, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTelefone())
        );

        TableColumn<Abrigo, Number> colCusto = new TableColumn<>("Custo Mensal");
        colCusto.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getCustoMensal())
        );

        tabela.getColumns().addAll(
                colId,
                colNome,
                colEndereco,
                colTelefone,
                colCusto
        );

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnFiltrar.setOnAction(e -> carregarAbrigos());

        btnAtualizar.setOnAction(e -> {
            txtFiltro.clear();
            carregarAbrigos();
        });

        carregarAbrigos();

        layout.getChildren().addAll(titulo, barraAcoes, tabela);

        return layout;
    }

    private void carregarAbrigos() {
        try {
            List<Abrigo> abrigos = controller.listarTodos();

            String filtro = txtFiltro == null
                    ? ""
                    : txtFiltro.getText().trim().toLowerCase();

            if (!filtro.isEmpty()) {
                abrigos = abrigos.stream()
                        .filter(abrigo ->
                                String.valueOf(abrigo.getId()).contains(filtro)
                                        || contem(abrigo.getNome(), filtro)
                                        || contem(abrigo.getendereco(), filtro)
                                        || contem(abrigo.getTelefone(), filtro)
                                        || String.valueOf(abrigo.getCustoMensal()).contains(filtro)
                        )
                        .toList();
            }

            tabela.setItems(FXCollections.observableArrayList(abrigos));

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao carregar abrigos: " + e.getMessage()
            );
        }
    }

    private boolean contem(String texto, String filtro) {
        return texto != null && texto.toLowerCase().contains(filtro);
    }
}