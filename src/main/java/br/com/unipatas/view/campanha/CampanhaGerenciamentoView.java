package br.com.unipatas.view.campanha;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CampanhaGerenciamentoView {

    public TabPane getPainelAbas() {
        TabPane tabPane = new TabPane();

        Tab tabCampanhas = new Tab("Campanhas");
        tabCampanhas.setClosable(false);

        Tab tabCadastro = new Tab("Cadastrar");
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

        tabPane.getTabs().addAll(
                tabCampanhas,
                tabCadastro,
                tabBusca,
                tabAtualizar,
                tabRemover
        );

        CampanhaCardsView cardsView = new CampanhaCardsView(tabPane);
        tabCampanhas.setContent(cardsView);

        return tabPane;
    }
}