package br.com.unipatas.view.animal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AnimalBuscaView {

    // Método que devolve a "caixa" visual pronta para o TabPane
    public VBox getConteudo() {
        
        VBox layoutPrincipal = new VBox(20); 
        layoutPrincipal.setAlignment(Pos.CENTER); 
        layoutPrincipal.setPadding(new Insets(25)); 

        // --- ÁREA DE PESQUISA ---
        HBox hbBusca = new HBox(10); 
        hbBusca.setAlignment(Pos.CENTER);
        
        TextField txtIdBusca = new TextField(); 
        txtIdBusca.setPromptText("Digite o ID do Animal"); 
        
        Button btnBuscar = new Button("Buscar"); 
        
        hbBusca.getChildren().addAll(new Label("ID do Animal:"), txtIdBusca, btnBuscar);

        // --- ÁREA DE RESULTADOS ---
        
        GridPane gridResultados = new GridPane();
        gridResultados.setAlignment(Pos.CENTER);
        gridResultados.setHgap(10); 
        gridResultados.setVgap(10);

        Label lblNome = new Label("-");
        Label lblRaca = new Label("-");
        Label lblPeso = new Label("-");
        Label lblPorte = new Label("-");
        Label lblNascimento = new Label("-");
        Label lblAdocao = new Label("-");

        gridResultados.add(new Label("Nome:"), 0, 0); gridResultados.add(lblNome, 1, 0);
        gridResultados.add(new Label("Raça:"), 0, 1); gridResultados.add(lblRaca, 1, 1);
        gridResultados.add(new Label("Peso:"), 0, 2); gridResultados.add(lblPeso, 1, 2);
        gridResultados.add(new Label("Porte:"), 0, 3); gridResultados.add(lblPorte, 1, 3);
        gridResultados.add(new Label("Data Nascimento:"), 0, 4); gridResultados.add(lblNascimento, 1, 4);
        gridResultados.add(new Label("Data Adoção:"), 0, 5); gridResultados.add(lblAdocao, 1, 5);

        // --- AÇÃO DO BOTÃO ---
        
        // setOnAction é o comando que "escuta" o clique do mouse.
        // O "e -> {}" é uma função Lambda. Tudo dentro das chaves roda quando clica.
        btnBuscar.setOnAction(e -> {
            String idDigitado = txtIdBusca.getText();

            if (idDigitado.equals("1")) {
                lblNome.setText("Rex");
                lblRaca.setText("Labrador");
                lblPeso.setText("25.5 kg");
                lblPorte.setText("Grande");
                lblNascimento.setText("15/03/2020");
                lblAdocao.setText("Ainda não adotado");
                
                // Futuramente, aqui será: Animal a = controller.buscar(id); lblNome.setText(a.getNome());
            } else {
                // Se digitou qualquer outra coisa, mostramos um Alerta de erro
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText(null);
                alerta.setContentText("Animal não encontrado na base de dados!");
                alerta.showAndWait();
                
                // Limpa a tela
                lblNome.setText("-"); lblRaca.setText("-"); lblPeso.setText("-");
                lblPorte.setText("-"); lblNascimento.setText("-"); lblAdocao.setText("-");
            }
        });

        // Junta tudo no VBox Principal: Coloca a barra de busca EM CIMA da tabela de resultados
        layoutPrincipal.getChildren().addAll(hbBusca, gridResultados);
        
        return layoutPrincipal;
    }
}