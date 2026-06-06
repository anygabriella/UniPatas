package br.com.unipatas.view.campanha;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class CampanhaGerenciamentoView {

    public TabPane getPainelAbas() {
        TabPane tabPane = new TabPane();

        Tab tabObjetos = new Tab("Campanhas");
        tabObjetos.setClosable(false);

        Tab tabGerenciar = new Tab("Gerenciar");
        tabGerenciar.setClosable(false);

        Tab tabCadastro = new Tab("Cadastrar");
        tabCadastro.setContent(new CampanhaCadastroView().getConteudo());
        tabCadastro.setClosable(false);

        Tab tabAtualizar = new Tab("Atualizar");
        tabAtualizar.setContent(new CampanhaAtualizarView().getConteudo());
        tabAtualizar.setClosable(false);

        Tab tabDetalhes = new Tab("Detalhes");
        tabDetalhes.setContent(criarPlaceholderDetalhes());
        tabDetalhes.setClosable(false);

        CampanhaObjetosView objetosView = new CampanhaObjetosView(tabPane, tabCadastro, tabAtualizar, tabDetalhes);
        tabObjetos.setContent(objetosView);
        tabGerenciar.setContent(objetosView.getGerenciamento());

        tabPane.getTabs().addAll(tabObjetos, tabGerenciar, tabCadastro, tabAtualizar, tabDetalhes);
        return tabPane;
    }

    private VBox criarPlaceholderDetalhes() {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40));

        Label titulo = new Label("Selecione uma campanha");
        titulo.getStyleClass().add("objetos-titulo");

        Label texto = new Label("Clique em Ver detalhes em um card de campanha para gerenciar seus vínculos.");
        texto.getStyleClass().add("objetos-subtitulo");

        box.getChildren().addAll(titulo, texto);
        return box;
    }
}
