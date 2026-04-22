package br.com.unipatas.view.usuario;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class UsuarioGerenciamentoView {

  public TabPane getPainelAbas() {
    TabPane tabPane = new TabPane();

    // Aba 1: Cadastro
    Tab tabCadastro = new Tab("Cadastrar Usuário");
    UsuarioCadastroView cadastroView = new UsuarioCadastroView();
    tabCadastro.setContent(cadastroView.getConteudo());
    tabCadastro.setClosable(false);

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

    return tabPane;
  }
}
