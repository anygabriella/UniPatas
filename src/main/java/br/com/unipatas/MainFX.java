package br.com.unipatas;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import br.com.unipatas.view.abrigo.AbrigoGerenciamentoView;
import br.com.unipatas.view.animal.AnimalGerenciamentoView;
import br.com.unipatas.view.campanha.CampanhaGerenciamentoView;
import br.com.unipatas.view.usuario.UsuarioGerenciamentoView;
import br.com.unipatas.view.campanha.AnimalCampanhaView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFX extends Application {


  private Button criarBotaoMenuComIcone(String texto, String caminhoIcone) {
    Image imagem = new Image(getClass().getResourceAsStream(caminhoIcone));

    ImageView icone = new ImageView(imagem);
    icone.setFitWidth(18);
    icone.setFitHeight(18);
    icone.setPreserveRatio(true);
    icone.setSmooth(true);

    Button botao = new Button(texto, icone);
    botao.setGraphicTextGap(10);
    botao.getStyleClass().add("menu-botao");

    return botao;
}

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("UniPatas - Sistema de Adoção");
    primaryStage.setResizable(false);

    BorderPane layoutPrincipal = new BorderPane();

    VBox menuLateral = new VBox();
    menuLateral.setPrefWidth(210);
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

    Region spacer = new Region();
    spacer.setPrefHeight(8);

    Button btnUsuarios = criarBotaoMenuComIcone("Usuarios","/br/icons/users-round.png");
    Button btnAnimais = criarBotaoMenuComIcone("Animais","/br/icons/dog.png");
    Button btnAbrigos = criarBotaoMenuComIcone("Abrigos","/br/icons/house-heart.png");
    Button btnCampanhas = criarBotaoMenuComIcone("Campanha","/br/icons/rocket.png");

    for (Button btn : new Button[] { btnUsuarios, btnAnimais, btnAbrigos, btnCampanhas}) {
      btn.setMaxWidth(Double.MAX_VALUE);
      btn.getStyleClass().add("menu-botao");
    }

    menuLateral.getChildren().addAll(
        lblTitulo,
        spacer,
        btnUsuarios,
        btnAnimais,
        btnAbrigos,
        btnCampanhas
    );

    layoutPrincipal.setLeft(menuLateral);

    VBox telaBemVindo = new VBox(10);
    telaBemVindo.setAlignment(Pos.CENTER);
    telaBemVindo.getStyleClass().add("tela-boas-vindas");

    Label lblBemVindo = new Label("Bem-vindo ao UniPatas 🐾");
    lblBemVindo.getStyleClass().add("label-boas-vindas");

    Label lblSub = new Label("Selecione uma opção no menu lateral para começar.");
    lblSub.getStyleClass().add("label-boas-vindas-sub");

    telaBemVindo.getChildren().addAll(lblBemVindo, lblSub);
    layoutPrincipal.setCenter(telaBemVindo);

    btnUsuarios.setOnAction(e -> layoutPrincipal.setCenter(new UsuarioGerenciamentoView().getPainelAbas()));
    btnAnimais.setOnAction(e -> layoutPrincipal.setCenter(new AnimalGerenciamentoView().getPainelAbas()));
    btnAbrigos.setOnAction(e -> layoutPrincipal.setCenter(new AbrigoGerenciamentoView().getPainelAbas()));
    btnCampanhas.setOnAction(e -> layoutPrincipal.setCenter(new CampanhaGerenciamentoView().getPainelAbas()));
    
    Scene scene = new Scene(layoutPrincipal, 860, 620);
    scene.getStylesheets().add(
        getClass().getResource("/br/com/unipatas/unipatas.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}