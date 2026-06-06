package br.com.unipatas.view.usuario;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class UsuarioGerenciamentoView {

  public TabPane getPainelAbas() {
    TabPane tabPane = new TabPane();

    Tab tabObjetos = new Tab("Usuários");
    tabObjetos.setClosable(false);

    Tab tabGerenciar = new Tab("Gerenciar");
    tabGerenciar.setClosable(false);

    Tab tabCadastro = new Tab("Cadastrar");
    tabCadastro.setContent(new UsuarioCadastroView().getConteudo());
    tabCadastro.setClosable(false);

    Tab tabAtualizar = new Tab("Atualizar");
    tabAtualizar.setContent(new UsuarioAtualizarView().getConteudo());
    tabAtualizar.setClosable(false);

    UsuarioObjetosView objetosView = new UsuarioObjetosView(tabPane, tabCadastro, tabAtualizar);
    tabObjetos.setContent(objetosView);
    tabGerenciar.setContent(objetosView.getGerenciamento());

    tabPane.getTabs().addAll(tabObjetos, tabGerenciar, tabCadastro, tabAtualizar);
    return tabPane;
  }
}
