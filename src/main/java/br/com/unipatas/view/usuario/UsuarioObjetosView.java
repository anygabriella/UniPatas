package br.com.unipatas.view.usuario;

import br.com.unipatas.controller.UsuarioController;
import br.com.unipatas.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.util.List;

public class UsuarioObjetosView extends BorderPane {
  private final TabPane tabPane;
  private final Tab tabCadastro;
  private final Tab tabAtualizar;
  private UsuarioController controller;
  private TilePane cards;
  private TableView<Usuario> tabela;

  public UsuarioObjetosView(TabPane tabPane, Tab tabCadastro, Tab tabAtualizar) {
    this.tabPane = tabPane;
    this.tabCadastro = tabCadastro;
    this.tabAtualizar = tabAtualizar;
    getStyleClass().add("objetos-page");
    try { controller = new UsuarioController(); } catch (Exception e) { alerta(Alert.AlertType.ERROR, "Erro", "Erro ao conectar ao banco."); }
    montar();
  }

  private void montar() {
    VBox conteudo = new VBox(18);
    conteudo.setPadding(new Insets(26));
    conteudo.getStyleClass().add("objetos-conteudo");

    Label badge = new Label("USUÁRIOS");
    badge.getStyleClass().add("objetos-badge");
    Label titulo = new Label("Usuários cadastrados");
    titulo.getStyleClass().add("objetos-titulo");
    Label subtitulo = new Label("Visualize os usuários em cards e use o botão + para cadastrar um novo registro.");
    subtitulo.getStyleClass().add("objetos-subtitulo");

    cards = new TilePane();
    cards.setHgap(16); cards.setVgap(16); cards.setPrefColumns(3);

    ScrollPane scroll = new ScrollPane(cards);
    scroll.setFitToWidth(true);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.getStyleClass().add("cards-scroll");
    VBox.setVgrow(scroll, Priority.ALWAYS);

    conteudo.getChildren().addAll(badge, titulo, subtitulo, scroll);
    setCenter(conteudo);

    Button btnNovo = new Button("+");
    btnNovo.getStyleClass().add("fab");
    btnNovo.setOnAction(e -> tabPane.getSelectionModel().select(tabCadastro));
    StackPane fabLayer = new StackPane(btnNovo);
    fabLayer.setPadding(new Insets(0, 26, 26, 0));
    fabLayer.setAlignment(Pos.BOTTOM_RIGHT);
    setBottom(fabLayer);
    carregarCards();
  }

  private void carregarCards() {
    cards.getChildren().clear();
    try {
      List<Usuario> lista = controller.listarTodos();
      if (lista.isEmpty()) { cards.getChildren().add(estadoVazio("Nenhum usuário cadastrado", "Clique no + para criar o primeiro usuário.")); return; }
      for (Usuario u : lista) cards.getChildren().add(card("#" + u.getId(), u.getNome(), u.getEmail(), u.getCidade() + " - " + u.getEstado()));
    } catch (Exception e) { alerta(Alert.AlertType.ERROR, "Erro", "Erro ao carregar usuários: " + e.getMessage()); }
  }

  private VBox card(String id, String titulo, String info1, String info2) {
    VBox c = new VBox(8); c.getStyleClass().add("objeto-card"); c.setPrefWidth(250);
    Label lid = new Label(id); lid.getStyleClass().add("objeto-card-id");
    Label lt = new Label(titulo); lt.getStyleClass().add("objeto-card-titulo");
    Label l1 = new Label(info1); l1.getStyleClass().add("objeto-card-info");
    Label l2 = new Label(info2); l2.getStyleClass().add("objeto-card-info");
    c.getChildren().addAll(lid, lt, l1, l2); return c;
  }

  private VBox estadoVazio(String titulo, String texto) { VBox v = new VBox(8, new Label("🐾"), new Label(titulo), new Label(texto)); v.setAlignment(Pos.CENTER); v.getStyleClass().add("estado-vazio-card"); return v; }

  public BorderPane getGerenciamento() {
    BorderPane painel = new BorderPane(); painel.getStyleClass().add("objetos-page"); painel.setPadding(new Insets(24));
    VBox topo = new VBox(6); Label titulo = new Label("Gerenciar usuários"); titulo.getStyleClass().add("objetos-titulo"); Label sub = new Label("Pesquise, edite ou exclua usuários sem precisar abrir uma página separada de remoção."); sub.getStyleClass().add("objetos-subtitulo"); topo.getChildren().addAll(titulo, sub);
    tabela = new TableView<>(); tabela.getStyleClass().add("tabela-gerenciamento");
    TableColumn<Usuario, Void> acoes = colunaAcoes(); acoes.setPrefWidth(170);
    TableColumn<Usuario, Integer> id = new TableColumn<>("ID"); id.setCellValueFactory(new PropertyValueFactory<>("id")); id.setPrefWidth(70);
    TableColumn<Usuario, String> nome = new TableColumn<>("Nome"); nome.setCellValueFactory(new PropertyValueFactory<>("nome")); nome.setPrefWidth(180);
    TableColumn<Usuario, String> email = new TableColumn<>("Email"); email.setCellValueFactory(new PropertyValueFactory<>("email")); email.setPrefWidth(220);
    TableColumn<Usuario, String> cidade = new TableColumn<>("Cidade"); cidade.setCellValueFactory(new PropertyValueFactory<>("cidade")); cidade.setPrefWidth(140);
    tabela.getColumns().addAll(acoes, id, nome, email, cidade);
    atualizarTabela();
    VBox box = new VBox(16, topo, tabela); VBox.setVgrow(tabela, Priority.ALWAYS); painel.setCenter(box); return painel;
  }

  private TableColumn<Usuario, Void> colunaAcoes() {
    TableColumn<Usuario, Void> col = new TableColumn<>("Ações");
    col.setCellFactory(param -> new TableCell<>() {
      private final Button editar = new Button("Editar");
      private final Button excluir = new Button("Excluir");
      private final HBox box = new HBox(8, editar, excluir);
      { editar.getStyleClass().add("botao-tabela-editar"); excluir.getStyleClass().add("botao-tabela-excluir"); editar.setOnAction(e -> editar(getTableView().getItems().get(getIndex()))); excluir.setOnAction(e -> excluir(getTableView().getItems().get(getIndex()))); }
      @Override protected void updateItem(Void item, boolean empty) { super.updateItem(item, empty); setGraphic(empty ? null : box); }
    }); return col;
  }
  private void editar(Usuario u) { tabAtualizar.setContent(new UsuarioAtualizarView(u.getId()).getConteudo()); tabPane.getSelectionModel().select(tabAtualizar); }
  private void excluir(Usuario u) { if (confirmar("Excluir usuário", "Deseja excluir " + u.getNome() + "?")) { try { controller.deletarUsuarioPorId(u.getId()); atualizarTabela(); carregarCards(); } catch (Exception e) { alerta(Alert.AlertType.ERROR, "Erro", e.getMessage()); } } }
  private void atualizarTabela() { try { tabela.getItems().setAll(controller.listarTodos()); } catch (Exception e) { alerta(Alert.AlertType.ERROR, "Erro", e.getMessage()); } }
  private boolean confirmar(String titulo, String texto) { Alert a = new Alert(Alert.AlertType.CONFIRMATION, texto, ButtonType.CANCEL, ButtonType.OK); a.setTitle(titulo); a.setHeaderText(null); return a.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK; }
  private void alerta(Alert.AlertType tipo, String titulo, String texto) { Alert a = new Alert(tipo); a.setTitle(titulo); a.setHeaderText(null); a.setContentText(texto); a.showAndWait(); }
}
