package br.com.unipatas;

import br.com.unipatas.view.abrigo.AbrigoGerenciamentoView;
import br.com.unipatas.view.animal.AnimalGerenciamentoView;
import br.com.unipatas.view.campanha.CampanhaGerenciamentoView;
import br.com.unipatas.view.dashboard.DashboardView;
import br.com.unipatas.view.pesquisa.PesquisaPadraoView;
import br.com.unipatas.view.usuario.UsuarioGerenciamentoView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFX extends Application {

  private BorderPane layoutPrincipal;
  private Button btnInicio;
  private Button btnUsuarios;
  private Button btnAnimais;
  private Button btnAbrigos;
  private Button btnCampanhas;
  private Button btnPesquisaPadrao;

  private Button criarBotaoMenuComIcone(String texto, String caminhoIcone) {
    Image imagem = new Image(getClass().getResourceAsStream(caminhoIcone));

    ImageView icone = new ImageView(imagem);
    icone.setFitWidth(18);
    icone.setFitHeight(18);
    icone.setPreserveRatio(true);
    icone.setSmooth(true);

    Button botao = new Button(texto, icone);
    botao.setGraphicTextGap(10);
    botao.setWrapText(true);
    botao.getStyleClass().add("menu-botao");
    botao.setMaxWidth(Double.MAX_VALUE);

    return botao;
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("UniPatas - Sistema de Adoção");
    primaryStage.setResizable(true);
    primaryStage.setMinWidth(980);
    primaryStage.setMinHeight(640);

    layoutPrincipal = new BorderPane();

    VBox menuLateral = new VBox();
    menuLateral.setPrefWidth(224);
    menuLateral.getStyleClass().add("menu-lateral");

    ImageView logoTitulo = new ImageView(
        new Image(getClass().getResourceAsStream("/br/icons/pata.png"))
    );
    logoTitulo.setFitWidth(24);
    logoTitulo.setFitHeight(24);
    logoTitulo.setPreserveRatio(true);
    logoTitulo.setSmooth(true);

    Label lblTitulo = new Label("UniPatas", logoTitulo);
    lblTitulo.setGraphicTextGap(10);
    lblTitulo.getStyleClass().add("menu-titulo");
    lblTitulo.setMaxWidth(Double.MAX_VALUE);

    Label lblSubtitulo = new Label("Gestão de adoções");
    lblSubtitulo.getStyleClass().add("menu-subtitulo");
    lblSubtitulo.setMaxWidth(Double.MAX_VALUE);

    Region spacer = new Region();
    spacer.setPrefHeight(8);

    btnInicio = criarBotaoMenuComIcone("Início", "/br/icons/pata.png");
    btnUsuarios = criarBotaoMenuComIcone("Usuários", "/br/icons/users-round.png");
    btnAnimais = criarBotaoMenuComIcone("Animais", "/br/icons/dog.png");
    btnAbrigos = criarBotaoMenuComIcone("Abrigos", "/br/icons/house-heart.png");
    btnCampanhas = criarBotaoMenuComIcone("Campanhas", "/br/icons/rocket.png");
    btnPesquisaPadrao = criarBotaoMenuComIcone("Pesquisar por padrão (KMP / BM)", "/br/icons/pata.png");

    menuLateral.getChildren().addAll(
        lblTitulo,
        lblSubtitulo,
        spacer,
        btnInicio,
        btnUsuarios,
        btnAnimais,
        btnAbrigos,
        btnCampanhas,
        btnPesquisaPadrao
    );

    layoutPrincipal.setLeft(menuLateral);

    btnInicio.setOnAction(e -> mostrarDashboard());
    btnUsuarios.setOnAction(e -> mostrarUsuarios());
    btnAnimais.setOnAction(e -> mostrarAnimais());
    btnAbrigos.setOnAction(e -> mostrarAbrigos());
    btnCampanhas.setOnAction(e -> mostrarCampanhas());
    btnPesquisaPadrao.setOnAction(e -> mostrarPesquisaPadrao());

    mostrarDashboard();

    Scene scene = new Scene(layoutPrincipal, 1080, 680);
    scene.getStylesheets().add(
        getClass().getResource("/br/com/unipatas/unipatas.css").toExternalForm()
    );

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void mostrarDashboard() {
    layoutPrincipal.setCenter(new DashboardView(
        this::mostrarUsuarios,
        this::mostrarAnimais,
        this::mostrarAbrigos,
        this::mostrarCampanhas
    ));
    ativarBotao(btnInicio);
  }

  private void mostrarUsuarios() {
    layoutPrincipal.setCenter(new UsuarioGerenciamentoView().getPainelAbas());
    ativarBotao(btnUsuarios);
  }

  private void mostrarAnimais() {
    layoutPrincipal.setCenter(new AnimalGerenciamentoView().getPainelAbas());
    ativarBotao(btnAnimais);
  }

  private void mostrarAbrigos() {
    layoutPrincipal.setCenter(new AbrigoGerenciamentoView().getPainelAbas());
    ativarBotao(btnAbrigos);
  }

  private void mostrarCampanhas() {
    layoutPrincipal.setCenter(new CampanhaGerenciamentoView().getPainelAbas());
    ativarBotao(btnCampanhas);
  }

  private void mostrarPesquisaPadrao() {
    layoutPrincipal.setCenter(new PesquisaPadraoView());
    ativarBotao(btnPesquisaPadrao);
  }

  private void ativarBotao(Button botaoAtivo) {
    for (Button botao : new Button[] { btnInicio, btnUsuarios, btnAnimais, btnAbrigos, btnCampanhas, btnPesquisaPadrao }) {
      botao.getStyleClass().remove("menu-botao-ativo");
    }
    botaoAtivo.getStyleClass().add("menu-botao-ativo");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
