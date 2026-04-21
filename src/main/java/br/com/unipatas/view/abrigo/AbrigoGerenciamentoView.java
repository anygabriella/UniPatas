package br.com.unipatas.view.abrigo;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class AbrigoGerenciamentoView {
    public TabPane getPainelAbas() {
        TabPane tabPane = new TabPane();

        // Aba 1: Cadastro
        Tab tabCadastro = new Tab("Cadastrar Abrigo");
        AbrigoCadastroView cadastroView = new AbrigoCadastroView();
        tabCadastro.setContent(cadastroView.getConteudo());
        tabCadastro.setClosable(false);

       // Aba 2: Busca
        Tab tabBusca = new Tab("Pesquisar");
        AbrigoBuscaView buscaView = new AbrigoBuscaView(); // Chama a view real
        tabBusca.setContent(buscaView.getConteudo());
        tabBusca.setClosable(false);

        // Aba 3: Atualizar
        Tab tabAtualizar = new Tab("Atualizar");
        AbrigoAtualizarView atualizarView = new AbrigoAtualizarView(); // Chama a view real
        tabAtualizar.setContent(atualizarView.getConteudo());
        tabAtualizar.setClosable(false);

        // Aba 4: Remover
        Tab tabRemover = new Tab("Remover");
        AbrigoRemoverView removerView = new AbrigoRemoverView(); // Chama a view real
        tabRemover.setContent(removerView.getConteudo());
        tabRemover.setClosable(false);

        tabPane.getTabs().addAll(tabCadastro, tabBusca, tabAtualizar, tabRemover);
        return tabPane;
    }

    private VBox criarAbaProvisoria(String mensagem) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(new Label(mensagem));
        return vbox;
    }
}
