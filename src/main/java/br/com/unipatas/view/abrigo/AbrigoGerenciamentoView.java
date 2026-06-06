package br.com.unipatas.view.abrigo;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AbrigoGerenciamentoView {

    public TabPane getPainelAbas() {
        TabPane tabPane = new TabPane();

        Tab tabObjetos = new Tab("Abrigos");
        tabObjetos.setClosable(false);

        Tab tabGerenciar = new Tab("Gerenciar");
        tabGerenciar.setClosable(false);

        Tab tabCadastro = new Tab("Cadastrar");
        tabCadastro.setContent(new AbrigoCadastroView().getConteudo());
        tabCadastro.setClosable(false);

        Tab tabAtualizar = new Tab("Atualizar");
        tabAtualizar.setContent(new AbrigoAtualizarView().getConteudo());
        tabAtualizar.setClosable(false);

        Tab tabAnimais = new Tab("Animais do Abrigo");
        tabAnimais.setContent(new AbrigoAnimaisView().getConteudo());
        tabAnimais.setClosable(false);

        AbrigoObjetosView objetosView = new AbrigoObjetosView(tabPane, tabCadastro, tabAtualizar);
        tabObjetos.setContent(objetosView);
        tabGerenciar.setContent(objetosView.getGerenciamento());

        tabPane.getTabs().addAll(tabObjetos, tabGerenciar, tabCadastro, tabAtualizar, tabAnimais);
        return tabPane;
    }
}
