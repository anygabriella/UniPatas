package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Campanha;
import br.com.unipatas.view.util.AlertaUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CampanhaCardsView extends BorderPane {

    private CampanhaController campanhaController;
    private TabPane tabPanePrincipal;

    public CampanhaCardsView(TabPane tabPanePrincipal) {
        this.tabPanePrincipal = tabPanePrincipal;

        try {
            this.campanhaController = new CampanhaController();
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Não foi possível conectar ao banco de campanhas."
            );
        }

        montarTela();
    }

    private void montarTela() {
        this.setPadding(new Insets(24));
        this.getStyleClass().add("tela-campanhas");

        VBox topo = new VBox(6);

        Label titulo = new Label("Campanhas");
        titulo.getStyleClass().add("titulo-pagina");

        Label subtitulo = new Label("Gerencie campanhas e vincule animais participantes.");
        subtitulo.getStyleClass().add("subtitulo-pagina");

        topo.getChildren().addAll(titulo, subtitulo);
        this.setTop(topo);

        carregarCampanhas();
    }

    private void carregarCampanhas() {
        try {
            List<Campanha> campanhas = campanhaController.listarTodos();

            if (campanhas == null || campanhas.isEmpty()) {
                mostrarEstadoVazio();
                return;
            }

            FlowPane painelCards = new FlowPane();
            painelCards.setHgap(18);
            painelCards.setVgap(18);
            painelCards.setPadding(new Insets(24, 0, 0, 0));

            for (Campanha campanha : campanhas) {
                VBox card = criarCardCampanha(campanha);
                painelCards.getChildren().add(card);
            }

            ScrollPane scroll = new ScrollPane(painelCards);
            scroll.setFitToWidth(true);
            scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scroll.getStyleClass().add("scroll-cards");

            this.setCenter(scroll);

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao carregar campanhas: " + e.getMessage()
            );
        }
    }

    private VBox criarCardCampanha(Campanha campanha) {
        VBox card = new VBox(10);
        card.setPrefWidth(245);
        card.setMinHeight(175);
        card.setPadding(new Insets(18));
        card.setCursor(Cursor.HAND);
        card.getStyleClass().add("campanha-card");

        Label badge = new Label("Campanha #" + campanha.getId());
        badge.getStyleClass().add("campanha-badge");

        Label nome = new Label(campanha.getNome());
        nome.getStyleClass().add("campanha-card-titulo");
        nome.setWrapText(true);

        Label local = new Label("📍 " + campanha.getLocal());
        local.getStyleClass().add("campanha-card-info");
        local.setWrapText(true);

        Label data = new Label("📅 " + campanha.getData());
        data.getStyleClass().add("campanha-card-info");

        Label custo = new Label("💰 R$ " + String.format("%.2f", campanha.getCusto()));
        custo.getStyleClass().add("campanha-card-info");

        Button btnDetalhes = new Button("Ver detalhes");
        btnDetalhes.getStyleClass().add("botao-secundario-pequeno");

        HBox rodape = new HBox(btnDetalhes);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(
                badge,
                nome,
                local,
                data,
                custo,
                rodape
        );

        card.setOnMouseClicked(e -> abrirDetalhes(campanha));
        btnDetalhes.setOnAction(e -> abrirDetalhes(campanha));

        return card;
    }

    private void abrirDetalhes(Campanha campanha) {
        CampanhaDetalhesView detalhesView = new CampanhaDetalhesView(
                campanha,
                () -> carregarCampanhas()
        );

        this.setCenter(detalhesView);
    }

    private void mostrarEstadoVazio() {
        VBox vazio = new VBox(14);
        vazio.setAlignment(Pos.CENTER);
        vazio.setPadding(new Insets(60));
        vazio.getStyleClass().add("estado-vazio");

        Label icone = new Label("📢");
        icone.getStyleClass().add("estado-vazio-icone");

        Label titulo = new Label("Nenhuma campanha cadastrada");
        titulo.getStyleClass().add("estado-vazio-titulo");

        Label texto = new Label("Crie uma campanha para começar a vincular animais.");
        texto.getStyleClass().add("estado-vazio-texto");

        Button btnCriar = new Button("Criar primeira campanha");
        btnCriar.getStyleClass().add("botao-principal");

        btnCriar.setOnAction(e -> {
            if (tabPanePrincipal != null && tabPanePrincipal.getTabs().size() > 1) {
                tabPanePrincipal.getSelectionModel().select(1);
            }
        });

        vazio.getChildren().addAll(icone, titulo, texto, btnCriar);
        this.setCenter(vazio);
    }
}