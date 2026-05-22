package br.com.unipatas;

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

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("UniPatas - Sistema de Adoção");
    primaryStage.setResizable(false);

    BorderPane layoutPrincipal = new BorderPane();

    VBox menuLateral = new VBox();
    menuLateral.setPrefWidth(210);
    menuLateral.getStyleClass().add("menu-lateral");

    Label lblTitulo = new Label("🐾 UniPatas");
    lblTitulo.getStyleClass().add("menu-titulo");
    lblTitulo.setMaxWidth(Double.MAX_VALUE);

    Region spacer = new Region();
    spacer.setPrefHeight(8);

    Button btnUsuarios = new Button("👥  Usuários");
    Button btnAnimais = new Button("🐶  Animais");
    Button btnAbrigos = new Button("🏠  Abrigos");
    Button btnCampanhas = new Button("📢  Campanhas");
    Button btnVinculos = new Button("🔗  Vincular N:N");

    for (Button btn : new Button[] { btnUsuarios, btnAnimais, btnAbrigos, btnCampanhas, btnVinculos }) {
      btn.setMaxWidth(Double.MAX_VALUE);
      btn.getStyleClass().add("menu-botao");
    }

    menuLateral.getChildren().addAll(
        lblTitulo,
        spacer,
        btnUsuarios,
        btnAnimais,
        btnAbrigos,
        btnCampanhas,
        btnVinculos);

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
    btnVinculos.setOnAction(e -> layoutPrincipal.setCenter(new br.com.unipatas.view.campanha.AnimalCampanhaView()));

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