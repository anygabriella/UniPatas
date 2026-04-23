package br.com.unipatas.view.abrigo;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AbrigoGerenciamentoView {

    public TabPane getPainelAbas() {

        TabPane tabPane = new TabPane();

   
        Tab tabCadastro = new Tab("Cadastrar Abrigo");
        AbrigoCadastroView cadastroView = new AbrigoCadastroView();
        tabCadastro.setContent(cadastroView.getConteudo());
        tabCadastro.setClosable(false);

     
        Tab tabBusca = new Tab("Pesquisar");
        AbrigoBuscaView buscaView = new AbrigoBuscaView();
        tabBusca.setContent(buscaView.getConteudo());
        tabBusca.setClosable(false);

     
        Tab tabAtualizar = new Tab("Atualizar");
        AbrigoAtualizarView atualizarView = new AbrigoAtualizarView();
        tabAtualizar.setContent(atualizarView.getConteudo());
        tabAtualizar.setClosable(false);

       
        Tab tabAnimais = new Tab("Animais do Abrigo");
        tabAnimais.setContent(new AbrigoAnimaisView().getConteudo());
        tabAnimais.setClosable(false);

     
        Tab tabRemover = new Tab("Remover");
        AbrigoRemoverView removerView = new AbrigoRemoverView();
        tabRemover.setContent(removerView.getConteudo());
        tabRemover.setClosable(false);

      
        tabPane.getTabs().addAll(
            tabCadastro,
            tabBusca,
            tabAtualizar,
            tabAnimais,
            tabRemover
        );

        return tabPane;
    }
}