package br.com.unipatas.view.campanha;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CampanhaGerenciamentoView {

    public TabPane getPainelAbas() {
        TabPane tabPane = new TabPane();

        Tab tabCadastro = new Tab("Cadastrar Campanha");
        CampanhaCadastroView cadastroView = new CampanhaCadastroView();
        tabCadastro.setContent(cadastroView.getConteudo());
        tabCadastro.setClosable(false);

        Tab tabBusca = new Tab("Pesquisar");
        CampanhaBuscaView buscaView = new CampanhaBuscaView();
        tabBusca.setContent(buscaView.getConteudo());
        tabBusca.setClosable(false);

        Tab tabAtualizar = new Tab("Atualizar");
        CampanhaAtualizarView atualizarView = new CampanhaAtualizarView();
        tabAtualizar.setContent(atualizarView.getConteudo());
        tabAtualizar.setClosable(false);

        Tab tabRemover = new Tab("Remover");
        CampanhaRemoverView removerView = new CampanhaRemoverView();
        tabRemover.setContent(removerView.getConteudo());
        tabRemover.setClosable(false);

        Tab tabRelacionamento = new Tab("Animais da Campanha");
        tabRelacionamento.setContent(new AnimalCampanhaView().getConteudo());
        tabRelacionamento.setClosable(false);

        tabPane.getTabs().addAll(
            tabCadastro,
            tabBusca,
            tabAtualizar,
            tabRemover,
            tabRelacionamento
        );

        return tabPane;
    }
}