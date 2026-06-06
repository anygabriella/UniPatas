package br.com.unipatas.view.animal;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AnimalGerenciamentoView {

  public TabPane getPainelAbas() {
    TabPane tabPane = new TabPane();

    Tab tabObjetos = new Tab("Animais");
    tabObjetos.setClosable(false);

    Tab tabGerenciar = new Tab("Gerenciar");
    tabGerenciar.setClosable(false);

    Tab tabCadastro = new Tab("Cadastrar");
    tabCadastro.setContent(new AnimalCadastroView().getConteudo());
    tabCadastro.setClosable(false);

    Tab tabAtualizar = new Tab("Atualizar");
    tabAtualizar.setContent(new AnimalAtualizarView().getConteudo());
    tabAtualizar.setClosable(false);

    AnimalObjetosView objetosView = new AnimalObjetosView(tabPane, tabCadastro, tabAtualizar);
    tabObjetos.setContent(objetosView);
    tabGerenciar.setContent(objetosView.getGerenciamento());

    tabPane.getTabs().addAll(tabObjetos, tabGerenciar, tabCadastro, tabAtualizar);
    return tabPane;
  }
}
