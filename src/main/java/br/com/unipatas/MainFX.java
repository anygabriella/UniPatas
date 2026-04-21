package br.com.unipatas;

import br.com.unipatas.view.abrigo.AbrigoGerenciamentoView;
import br.com.unipatas.view.adocao.AdocaoGerenciamentoView;
import br.com.unipatas.view.animal.AnimalGerenciamentoView;
import br.com.unipatas.view.campanha.CampanhaGerenciamentoView;
import br.com.unipatas.view.usuario.UsuarioGerenciamentoView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UniPatas - Sistema de Adoção");

        primaryStage.setResizable(false);
        // BorderPane divide a tela em: Topo, Esquerda, Centro, Direita e Baixo
        BorderPane layoutPrincipal = new BorderPane();

        // --- MENU LATERAL (Lado Esquerdo) ---
        VBox menuLateral = new VBox(15); // Espaçamento de 15 entre os botões
        menuLateral.setPadding(new Insets(20));
        menuLateral.setStyle("-fx-background-color: #2b3e50;"); // Azul escuro
        menuLateral.setPrefWidth(200);

        Label lblTitulo = new Label("MENU PRINCIPAL");
        lblTitulo.setTextFill(javafx.scene.paint.Color.WHITE);
        lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Estilo padrão para os botões do menu
        String estiloBotao = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: center-left;";

        Button btnUsuarios = new Button("👥 Gerenciar Usuários");
        btnUsuarios.setMaxWidth(Double.MAX_VALUE);
        btnUsuarios.setStyle(estiloBotao);

        Button btnAnimais = new Button("🐾 Gerenciar Animais");
        btnAnimais.setMaxWidth(Double.MAX_VALUE);
        btnAnimais.setStyle(estiloBotao);

        Button btnAbrigos = new Button("🏠 Gerenciar Abrigos");
        btnAbrigos.setMaxWidth(Double.MAX_VALUE);
        btnAbrigos.setStyle(estiloBotao);

        Button btnCampanhas = new Button("📢 Gerenciar Campanhas");
        btnCampanhas.setMaxWidth(Double.MAX_VALUE);
        btnCampanhas.setStyle(estiloBotao);

        Button btnAdocoes = new Button("♡ Registrar Adoções");
        btnAdocoes.setMaxWidth(Double.MAX_VALUE);
        btnAdocoes.setStyle(estiloBotao);

        menuLateral.getChildren().addAll(lblTitulo, btnUsuarios, btnAnimais, btnAbrigos, btnCampanhas, btnAdocoes);
        layoutPrincipal.setLeft(menuLateral);

        // --- ÁREA CENTRAL (Começa com mensagem de boas-vindas) ---
        VBox telaBemVindo = new VBox();
        telaBemVindo.setAlignment(Pos.CENTER);
        Label lblBemVindo = new Label("Selecione uma opção no menu lateral");
        lblBemVindo.setFont(Font.font(20));
        telaBemVindo.getChildren().add(lblBemVindo);

        layoutPrincipal.setCenter(telaBemVindo);

        // --- AÇÕES DOS BOTÕES ---
        btnUsuarios.setOnAction(e -> {
            // Puxa o "pacote" de abas que criamos no Passo 1 e joga no Centro
            UsuarioGerenciamentoView viewUsuarios = new UsuarioGerenciamentoView();
            layoutPrincipal.setCenter(viewUsuarios.getPainelAbas());
        });

        btnAnimais.setOnAction(e -> {
            AnimalGerenciamentoView viewAnimais = new AnimalGerenciamentoView();
            layoutPrincipal.setCenter(viewAnimais.getPainelAbas());
        });

        btnAbrigos.setOnAction(e -> {
            AbrigoGerenciamentoView viewAbrigos = new AbrigoGerenciamentoView();
            layoutPrincipal.setCenter(viewAbrigos.getPainelAbas());
        });

        btnCampanhas.setOnAction(e -> {
            CampanhaGerenciamentoView viewCampanhas = new CampanhaGerenciamentoView();
            layoutPrincipal.setCenter(viewCampanhas.getPainelAbas());
        });

        btnAdocoes.setOnAction(e -> {
            AdocaoGerenciamentoView viewAdocoes = new AdocaoGerenciamentoView();
            layoutPrincipal.setCenter(viewAdocoes.getPainelAbas());
        });

        Scene scene = new Scene(layoutPrincipal, 800, 600); // Tela maior para caber tudo
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}