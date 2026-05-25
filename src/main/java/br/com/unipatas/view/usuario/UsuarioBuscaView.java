package br.com.unipatas.view.usuario;

import br.com.unipatas.controller.UsuarioController;
import br.com.unipatas.model.Usuario;
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

public class UsuarioBuscaView {

    private UsuarioController controller;
    private TableView<Usuario> tabela;
    private TextField txtFiltro;

    public UsuarioBuscaView() {
        try {
            controller = new UsuarioController();
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro Crítico",
                    "Não foi possível conectar ao banco de usuários."
            );
        }
    }

    public VBox getConteudo() {
        VBox layout = new VBox(14);
        layout.setPadding(new Insets(25));
        layout.getStyleClass().add("tela-listagem");

        Label titulo = new Label("Pesquisar usuários");
        titulo.getStyleClass().add("label-secao");

        txtFiltro = new TextField();
        txtFiltro.setPromptText("Filtrar por ID, nome, CPF, email, telefone, cidade ou estado");
        HBox.setHgrow(txtFiltro, Priority.ALWAYS);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.getStyleClass().add("botao-secundario");

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("botao-principal");

        HBox barraAcoes = new HBox(10, txtFiltro, btnFiltrar, btnAtualizar);

        tabela = new TableView<>();
        tabela.setPrefHeight(420);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

        TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Usuario, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Usuario, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Usuario, String> colCidade = new TableColumn<>("Cidade");
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        TableColumn<Usuario, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tabela.getColumns().addAll(
                colId,
                colNome,
                colCpf,
                colEmail,
                colTelefone,
                colCidade,
                colEstado
        );

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnFiltrar.setOnAction(e -> carregarUsuarios());

        btnAtualizar.setOnAction(e -> {
            txtFiltro.clear();
            carregarUsuarios();
        });

        carregarUsuarios();

        layout.getChildren().addAll(titulo, barraAcoes, tabela);

        return layout;
    }

    private void carregarUsuarios() {
        try {
            List<Usuario> usuarios = controller.listarTodos();

            String filtro = txtFiltro == null
                    ? ""
                    : txtFiltro.getText().trim().toLowerCase();

            if (!filtro.isEmpty()) {
                usuarios = usuarios.stream()
                        .filter(usuario ->
                                String.valueOf(usuario.getId()).contains(filtro)
                                        || contem(usuario.getNome(), filtro)
                                        || contem(usuario.getCpf(), filtro)
                                        || contem(usuario.getEmail(), filtro)
                                        || contem(usuario.getTelefone(), filtro)
                                        || contem(usuario.getCidade(), filtro)
                                        || contem(usuario.getEstado(), filtro)
                        )
                        .toList();
            }

            tabela.setItems(FXCollections.observableArrayList(usuarios));

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao carregar usuários: " + e.getMessage()
            );
        }
    }

    private boolean contem(String texto, String filtro) {
        return texto != null && texto.toLowerCase().contains(filtro);
    }
}