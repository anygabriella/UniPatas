package br.com.unipatas.view.animal;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class AnimalGerenciamentoView {

    public TabPane getPainelAbas() {
        TabPane tabPane = new TabPane();

        // Aba 1: Cadastro
        Tab tabCadastro = new Tab("Cadastrar Animal");
        AnimalCadastroView cadastroView = new AnimalCadastroView();
        tabCadastro.setContent(cadastroView.getConteudo());
        tabCadastro.setClosable(false);

        // Aba 1: Pesquisa
        Tab tabBusca = new Tab("Pesquisar");
        AnimalBuscaView buscaView = new AnimalBuscaView();
        tabBusca.setContent(buscaView.getConteudo());
        tabBusca.setClosable(false);

        // Aba 3: Atualizar
        Tab tabAtualizar = new Tab("Atualizar");
        AnimalAtualizarView atualizarView = new AnimalAtualizarView();
        tabAtualizar.setContent(atualizarView.getConteudo());
        tabAtualizar.setClosable(false);

        // Aba 4: Remover
        Tab tabRemover = new Tab("Remover");
        AnimalRemoverView removerView = new AnimalRemoverView();
        tabRemover.setContent(removerView.getConteudo());
        tabRemover.setClosable(false);

        tabPane.getTabs().addAll(tabCadastro, tabBusca, tabAtualizar, tabRemover);
        return tabPane;
    }

}