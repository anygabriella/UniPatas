package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Campanha;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.List;

public class CampanhaObjetosView extends BorderPane {

    private final TabPane tabPane;
    private final Tab tabCadastro;
    private final Tab tabAtualizar;
    private final Tab tabDetalhes;

    private CampanhaController controller;
    private TilePane cards;
    private TableView<Campanha> tabela;
    private TextField txtFiltro;
    private Label lblResultados;

    public CampanhaObjetosView(TabPane tabPane, Tab tabCadastro, Tab tabAtualizar, Tab tabDetalhes) {
        this.tabPane = tabPane;
        this.tabCadastro = tabCadastro;
        this.tabAtualizar = tabAtualizar;
        this.tabDetalhes = tabDetalhes;
        getStyleClass().add("objetos-page");

        try {
            controller = new CampanhaController();
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco.");
        }

        montar();
    }

    private void montar() {
        VBox conteudo = new VBox(18);
        conteudo.setPadding(new Insets(26));

        Label badge = new Label("CAMPANHAS");
        badge.getStyleClass().add("objetos-badge");

        Label titulo = new Label("Campanhas cadastradas");
        titulo.getStyleClass().add("objetos-titulo");

        Label sub = new Label("Visualize campanhas por cards e acesse os vínculos pelo botão Ver detalhes.");
        sub.getStyleClass().add("objetos-subtitulo");

        cards = new TilePane();
        cards.setHgap(16);
        cards.setVgap(16);
        cards.setPrefColumns(3);

        ScrollPane scroll = new ScrollPane(cards);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.getStyleClass().add("cards-scroll");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        conteudo.getChildren().addAll(badge, titulo, sub, scroll);
        setCenter(conteudo);

        Button btnNovo = new Button("+");
        btnNovo.getStyleClass().add("fab");
        btnNovo.setOnAction(e -> tabPane.getSelectionModel().select(tabCadastro));

        StackPane fab = new StackPane(btnNovo);
        fab.setAlignment(Pos.BOTTOM_RIGHT);
        fab.setPadding(new Insets(0, 26, 26, 0));
        setBottom(fab);

        carregarCards();
    }

    private void carregarCards() {
        cards.getChildren().clear();

        try {
            List<Campanha> lista = controller.listarTodos();

            if (lista.isEmpty()) {
                cards.getChildren().add(estadoVazio("Nenhuma campanha cadastrada", "Clique no + para cadastrar."));
                return;
            }

            for (Campanha campanha : lista) {
                cards.getChildren().add(card(campanha));
            }
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    private VBox card(Campanha campanha) {
        VBox card = new VBox(10);
        card.getStyleClass().add("objeto-card");
        card.setPrefWidth(250);

        Label id = new Label("#" + campanha.getId());
        id.getStyleClass().add("objeto-card-id");

        Label titulo = new Label(campanha.getNome());
        titulo.getStyleClass().add("objeto-card-titulo");

        Label localData = new Label(campanha.getLocal() + " • " + campanha.getData());
        localData.getStyleClass().add("objeto-card-info");

        Label custo = new Label("Custo: R$ " + campanha.getCusto());
        custo.getStyleClass().add("objeto-card-info");

        Button detalhes = new Button("Ver detalhes");
        detalhes.getStyleClass().add("botao-secundario-pequeno");
        detalhes.setOnAction(e -> abrirDetalhes(campanha));

        card.getChildren().addAll(id, titulo, localData, custo, detalhes);
        return card;
    }

    private void abrirDetalhes(Campanha campanha) {
        tabDetalhes.setText("Detalhes");
        tabDetalhes.setContent(new CampanhaDetalhesView(campanha.getId()));
        tabPane.getSelectionModel().select(tabDetalhes);
    }

    private VBox estadoVazio(String titulo, String texto) {
        VBox vazio = new VBox(8, new Label("🚀"), new Label(titulo), new Label(texto));
        vazio.setAlignment(Pos.CENTER);
        vazio.getStyleClass().add("estado-vazio-card");
        return vazio;
    }

    public BorderPane getGerenciamento() {
        BorderPane painel = new BorderPane();
        painel.getStyleClass().add("objetos-page");
        painel.setPadding(new Insets(24));

        VBox topo = new VBox(6);
        Label titulo = new Label("Gerenciar campanhas");
        titulo.getStyleClass().add("objetos-titulo");
        Label sub = new Label("Busque, edite ou exclua campanhas na mesma tabela.");
        sub.getStyleClass().add("objetos-subtitulo");
        topo.getChildren().addAll(titulo, sub);

        tabela = new TableView<>();
        tabela.getStyleClass().add("tabela-gerenciamento");

        TableColumn<Campanha, Void> acoes = colunaAcoes();
        acoes.setPrefWidth(170);

        TableColumn<Campanha, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setPrefWidth(70);

        TableColumn<Campanha, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nome.setPrefWidth(190);

        TableColumn<Campanha, String> local = new TableColumn<>("Local");
        local.setCellValueFactory(new PropertyValueFactory<>("local"));
        local.setPrefWidth(170);

        TableColumn<Campanha, String> data = new TableColumn<>("Data");
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        data.setPrefWidth(130);

        tabela.getColumns().addAll(acoes, id, nome, local, data);

        HBox barraBusca = criarBarraBusca("Buscar por ID, nome, local, data ou custo");
        atualizarTabela();

        VBox box = new VBox(16, topo, barraBusca, tabela);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        painel.setCenter(box);
        return painel;
    }

    private HBox criarBarraBusca(String prompt) {
        txtFiltro = new TextField();
        txtFiltro.setPromptText(prompt);
        txtFiltro.getStyleClass().add("campo-busca");
        txtFiltro.setMaxWidth(Double.MAX_VALUE);
        txtFiltro.setOnAction(e -> filtrarTabela());

        Button buscar = new Button("Buscar");
        buscar.getStyleClass().add("botao-principal");
        buscar.setOnAction(e -> filtrarTabela());

        Button limpar = new Button("Limpar");
        limpar.getStyleClass().add("botao-secundario");
        limpar.setOnAction(e -> limparFiltro());

        lblResultados = new Label();
        lblResultados.getStyleClass().add("contador-resultados");

        HBox barra = new HBox(10, txtFiltro, buscar, limpar, lblResultados);
        barra.getStyleClass().add("barra-busca");
        barra.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(txtFiltro, Priority.ALWAYS);
        return barra;
    }

    private TableColumn<Campanha, Void> colunaAcoes() {
        TableColumn<Campanha, Void> coluna = new TableColumn<>("Ações");
        coluna.setCellFactory(param -> new TableCell<>() {
            Button editar = new Button("Editar");
            Button excluir = new Button("Excluir");
            HBox box = new HBox(8, editar, excluir);

            {
                editar.getStyleClass().add("botao-tabela-editar");
                excluir.getStyleClass().add("botao-tabela-excluir");
                editar.setOnAction(e -> editar(getTableView().getItems().get(getIndex())));
                excluir.setOnAction(e -> excluir(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
        return coluna;
    }

    private void editar(Campanha campanha) {
        tabAtualizar.setContent(new CampanhaAtualizarView(campanha.getId()).getConteudo());
        tabPane.getSelectionModel().select(tabAtualizar);
    }

    private void excluir(Campanha campanha) {
        if (confirmar("Excluir campanha", "Deseja excluir " + campanha.getNome() + "?")) {
            try {
                controller.remover(campanha.getId());
                filtrarTabela();
                carregarCards();
            } catch (Exception e) {
                alerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        }
    }

    private void atualizarTabela() {
        try {
            List<Campanha> campanhas = controller.listarTodos();
            tabela.getItems().setAll(campanhas);
            atualizarContador(campanhas.size());
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    private void filtrarTabela() {
        try {
            List<Campanha> campanhas = controller.buscarPorFiltro(txtFiltro.getText());
            tabela.getItems().setAll(campanhas);
            atualizarContador(campanhas.size());
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    private void limparFiltro() {
        txtFiltro.clear();
        atualizarTabela();
    }

    private void atualizarContador(int total) {
        if (lblResultados != null) {
            lblResultados.setText(total + (total == 1 ? " resultado" : " resultados"));
        }
    }

    private boolean confirmar(String titulo, String texto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, texto, ButtonType.CANCEL, ButtonType.OK);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        return alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void alerta(Alert.AlertType tipo, String titulo, String texto) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
