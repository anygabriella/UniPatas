package br.com.unipatas.view.adocao;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class AdocaoGerenciamentoView {

    public TabPane getPainelAbas() {
        TabPane tabPane = new TabPane();

        // Aba 1: Cadastro
        Tab tabCadastro = new Tab("Registrar Adoção");
        AdocaoCadastroView cadastroView = new AdocaoCadastroView();
        tabCadastro.setContent(cadastroView.getConteudo());
        tabCadastro.setClosable(false);

        // Aba 2: Busca
        Tab tabBusca = new Tab("Consultar Adoção");
        AdocaoBuscaView buscaView = new AdocaoBuscaView();
        tabBusca.setContent(buscaView.getConteudo());
        tabBusca.setClosable(false);

        // Aba 3: Alterar
        Tab tabAtualizar = new Tab("Alterar Registro");
        tabAtualizar.setContent(
                criarAbaProvisoria("Adoções não podem ser alteradas. Caso necessário, cancele e registre novamente."));
        tabAtualizar.setClosable(false);

        // Aba 4: Cancelar
        Tab tabRemover = new Tab("Cancelar Adoção");
        AdocaoRemoverView removerView = new AdocaoRemoverView();
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