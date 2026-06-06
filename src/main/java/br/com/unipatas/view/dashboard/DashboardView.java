package br.com.unipatas.view.dashboard;

import br.com.unipatas.controller.AbrigoController;
import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.controller.UsuarioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DashboardView extends BorderPane {

    private final Runnable abrirUsuarios;
    private final Runnable abrirAnimais;
    private final Runnable abrirAbrigos;
    private final Runnable abrirCampanhas;

    public DashboardView(
            Runnable abrirUsuarios,
            Runnable abrirAnimais,
            Runnable abrirAbrigos,
            Runnable abrirCampanhas
    ) {
        this.abrirUsuarios = abrirUsuarios;
        this.abrirAnimais = abrirAnimais;
        this.abrirAbrigos = abrirAbrigos;
        this.abrirCampanhas = abrirCampanhas;
        montarTela();
    }

    private void montarTela() {
        getStyleClass().add("dashboard");
        setPadding(new Insets(28));

        VBox conteudo = new VBox(22);
        conteudo.getStyleClass().add("dashboard-conteudo");

        VBox hero = criarHero();
        FlowPane cardsMetricas = criarCardsMetricas();
        HBox acoesRapidas = criarAcoesRapidas();

        conteudo.getChildren().addAll(hero, cardsMetricas, acoesRapidas);
        setCenter(conteudo);
    }

    private VBox criarHero() {
        VBox hero = new VBox(8);
        hero.getStyleClass().add("dashboard-hero");

        Label badge = new Label("Sistema de gestão para adoção responsável");
        badge.getStyleClass().add("dashboard-badge");

        Label titulo = new Label("Bem-vindo ao UniPatas");
        titulo.getStyleClass().add("dashboard-titulo");

        Label subtitulo = new Label("Centralize usuários, animais, abrigos e campanhas em uma interface mais clara, rápida e acolhedora.");
        subtitulo.getStyleClass().add("dashboard-subtitulo");
        subtitulo.setWrapText(true);

        hero.getChildren().addAll(badge, titulo, subtitulo);
        return hero;
    }

    private FlowPane criarCardsMetricas() {
        FlowPane cards = new FlowPane();
        cards.setHgap(16);
        cards.setVgap(16);
        cards.getStyleClass().add("dashboard-metricas");

        cards.getChildren().addAll(
                criarCardMetrica("Usuários", contarUsuarios(), "Pessoas cadastradas", "👥"),
                criarCardMetrica("Animais", contarAnimais(), "Pets no sistema", "🐶"),
                criarCardMetrica("Abrigos", contarAbrigos(), "Parceiros cadastrados", "🏠"),
                criarCardMetrica("Campanhas", contarCampanhas(), "Ações registradas", "🚀")
        );

        return cards;
    }

    private VBox criarCardMetrica(String titulo, int valor, String descricao, String icone) {
        VBox card = new VBox(8);
        card.getStyleClass().add("dashboard-card");
        card.setPrefWidth(210);
        card.setMinHeight(132);

        Label lblIcone = new Label(icone);
        lblIcone.getStyleClass().add("dashboard-card-icone");

        Label lblValor = new Label(String.valueOf(valor));
        lblValor.getStyleClass().add("dashboard-card-valor");

        Label lblTitulo = new Label(titulo);
        lblTitulo.getStyleClass().add("dashboard-card-titulo");

        Label lblDescricao = new Label(descricao);
        lblDescricao.getStyleClass().add("dashboard-card-descricao");

        card.getChildren().addAll(lblIcone, lblValor, lblTitulo, lblDescricao);
        return card;
    }

    private HBox criarAcoesRapidas() {
        HBox container = new HBox(16);
        container.getStyleClass().add("dashboard-acoes");
        container.setAlignment(Pos.CENTER_LEFT);

        VBox texto = new VBox(5);
        Label titulo = new Label("Ações rápidas");
        titulo.getStyleClass().add("dashboard-acoes-titulo");
        Label subtitulo = new Label("Acesse as áreas principais sem procurar nas abas.");
        subtitulo.getStyleClass().add("dashboard-acoes-subtitulo");
        texto.getChildren().addAll(titulo, subtitulo);
        HBox.setHgrow(texto, Priority.ALWAYS);

        Button btnAnimal = criarBotaoAcao("Novo animal", abrirAnimais);
        Button btnAbrigo = criarBotaoAcao("Abrigos", abrirAbrigos);
        Button btnCampanha = criarBotaoAcao("Campanhas", abrirCampanhas);
        Button btnUsuario = criarBotaoAcao("Usuários", abrirUsuarios);

        container.getChildren().addAll(texto, btnAnimal, btnAbrigo, btnCampanha, btnUsuario);
        return container;
    }

    private Button criarBotaoAcao(String texto, Runnable acao) {
        Button botao = new Button(texto);
        botao.getStyleClass().add("dashboard-botao-acao");
        botao.setOnAction(event -> acao.run());
        return botao;
    }

    private int contarUsuarios() {
        try {
            return new UsuarioController().listarTodos().size();
        } catch (Exception e) {
            return 0;
        }
    }

    private int contarAnimais() {
        try {
            return new AnimalController().listarTodos().size();
        } catch (Exception e) {
            return 0;
        }
    }

    private int contarAbrigos() {
        try {
            return new AbrigoController().listarTodos().size();
        } catch (Exception e) {
            return 0;
        }
    }

    private int contarCampanhas() {
        try {
            return new CampanhaController().listarTodos().size();
        } catch (Exception e) {
            return 0;
        }
    }
}
