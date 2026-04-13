package br.com.unipatas;

import br.com.unipatas.view.UsuarioCadastroView;
import br.com.unipatas.view.UsuarioRemoverView;
import br.com.unipatas.view.UsuarioAtualizarView;
import br.com.unipatas.view.UsuarioBuscaView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UniPatas - Gerenciamento de Usuários");

        TabPane tabPane = new TabPane();

        // Aba 1: Cadastro
        Tab tabCadastro = new Tab("Cadastrar Usuários");
        UsuarioCadastroView cadastroView = new UsuarioCadastroView();
        tabCadastro.setContent(cadastroView.getConteudo());
        tabCadastro.setClosable(false); // Impede o utilizador de fechar a aba

        // Aba 2: Busca
        Tab tabBusca = new Tab("Pesquisar");
        UsuarioBuscaView buscaView = new UsuarioBuscaView();
        tabBusca.setContent(buscaView.getConteudo());
        tabBusca.setClosable(false);

        // Aba 3: Atualizar
        Tab tabAtualizar = new Tab("Atualizar");
        UsuarioAtualizarView atualizarView = new UsuarioAtualizarView();
        tabAtualizar.setContent(atualizarView.getConteudo());
        tabAtualizar.setClosable(false);

        // Aba 4: Remover
        Tab tabRemover = new Tab("Remover");
        UsuarioRemoverView removerView = new UsuarioRemoverView();
        tabRemover.setContent(removerView.getConteudo());
        tabRemover.setClosable(false);

        // Adiciona as abas ao painel principal
        tabPane.getTabs().addAll(tabCadastro, tabBusca, tabAtualizar, tabRemover);  

        Scene scene = new Scene(tabPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}