package br.com.unipatas.view.pesquisa;

import br.com.unipatas.casamentopadrao.BoyerMoore;
import br.com.unipatas.casamentopadrao.KMP;
import br.com.unipatas.controller.AbrigoController;
import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.controller.UsuarioController;
import br.com.unipatas.model.Abrigo;
import br.com.unipatas.model.Animal;
import br.com.unipatas.model.Campanha;
import br.com.unipatas.model.Usuario;
import br.com.unipatas.view.util.AlertaUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PesquisaPadraoView extends VBox {

    private static final String ENTIDADE_USUARIOS = "Usuários";
    private static final String ENTIDADE_ANIMAIS = "Animais";
    private static final String ENTIDADE_ABRIGOS = "Abrigos";
    private static final String ENTIDADE_CAMPANHAS = "Campanhas";
    private static final String ALGORITMO_KMP = "KMP";
    private static final String ALGORITMO_BOYER_MOORE = "Boyer-Moore";

    private UsuarioController usuarioController;
    private AnimalController animalController;
    private AbrigoController abrigoController;
    private CampanhaController campanhaController;

    private ComboBox<String> cmbEntidade;
    private ComboBox<String> cmbAlgoritmo;
    private TextField txtPadrao;
    private TableView<PesquisaResultado> tabela;
    private Label lblResumo;

    public PesquisaPadraoView() {
        inicializarControllers();
        montarTela();
        pesquisar();
    }

    private void inicializarControllers() {
        try {
            usuarioController = new UsuarioController();
            animalController = new AnimalController();
            abrigoController = new AbrigoController();
            campanhaController = new CampanhaController();
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro Crítico",
                    "Não foi possível conectar aos dados da pesquisa: " + e.getMessage()
            );
        }
    }

    private void montarTela() {
        setSpacing(14);
        setPadding(new Insets(25));
        getStyleClass().add("tela-listagem");

        Label titulo = new Label("Pesquisar por padrão (KMP / BM)");
        titulo.getStyleClass().add("titulo-pagina");

        cmbEntidade = new ComboBox<>(FXCollections.observableArrayList(
                ENTIDADE_USUARIOS,
                ENTIDADE_ANIMAIS,
                ENTIDADE_ABRIGOS,
                ENTIDADE_CAMPANHAS
        ));
        cmbEntidade.setValue(ENTIDADE_USUARIOS);

        cmbAlgoritmo = new ComboBox<>(FXCollections.observableArrayList(
                ALGORITMO_KMP,
                ALGORITMO_BOYER_MOORE
        ));
        cmbAlgoritmo.setValue(ALGORITMO_KMP);

        txtPadrao = new TextField();
        txtPadrao.setPromptText("Digite o padrão pesquisado");
        txtPadrao.getStyleClass().add("campo-busca");
        HBox.setHgrow(txtPadrao, Priority.ALWAYS);

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.getStyleClass().add("botao-principal");

        Button btnLimpar = new Button("Limpar");
        btnLimpar.getStyleClass().add("botao-secundario");

        HBox barraPesquisa = new HBox(
                10,
                criarCampo("Onde buscar", cmbEntidade),
                criarCampo("Algoritmo", cmbAlgoritmo),
                criarCampo("Padrão", txtPadrao),
                btnPesquisar,
                btnLimpar
        );
        barraPesquisa.setAlignment(Pos.BOTTOM_LEFT);
        barraPesquisa.getStyleClass().add("barra-busca");

        tabela = criarTabelaResultados();
        VBox.setVgrow(tabela, Priority.ALWAYS);

        lblResumo = new Label();
        lblResumo.getStyleClass().add("contador-resultados");

        btnPesquisar.setOnAction(e -> pesquisar());
        btnLimpar.setOnAction(e -> limpar());
        txtPadrao.setOnAction(e -> pesquisar());
        cmbEntidade.setOnAction(e -> pesquisar());
        cmbAlgoritmo.setOnAction(e -> pesquisar());

        getChildren().addAll(titulo, barraPesquisa, lblResumo, tabela);
    }

    private VBox criarCampo(String rotulo, javafx.scene.Node controle) {
        Label label = new Label(rotulo);
        label.getStyleClass().add("label-secao");

        VBox grupo = new VBox(6, label, controle);
        HBox.setHgrow(grupo, controle == txtPadrao ? Priority.ALWAYS : Priority.NEVER);
        return grupo;
    }

    private TableView<PesquisaResultado> criarTabelaResultados() {
        TableView<PesquisaResultado> tableView = new TableView<>();
        tableView.setPrefHeight(430);
        tableView.setPlaceholder(new Label("Nenhum resultado encontrado."));

        TableColumn<PesquisaResultado, String> colEntidade = new TableColumn<>("Entidade");
        colEntidade.setCellValueFactory(new PropertyValueFactory<>("entidade"));
        colEntidade.setPrefWidth(110);

        TableColumn<PesquisaResultado, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(70);

        TableColumn<PesquisaResultado, String> colRegistro = new TableColumn<>("Registro");
        colRegistro.setCellValueFactory(new PropertyValueFactory<>("registro"));
        colRegistro.setPrefWidth(190);

        TableColumn<PesquisaResultado, String> colDetalhes = new TableColumn<>("Campos pesquisados");
        colDetalhes.setCellValueFactory(new PropertyValueFactory<>("detalhes"));
        colDetalhes.setPrefWidth(520);

        tableView.getColumns().addAll(colEntidade, colId, colRegistro, colDetalhes);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tableView;
    }

    private void pesquisar() {
        if (!controllersDisponiveis()) {
            return;
        }

        String entidade = cmbEntidade.getValue();
        String algoritmo = cmbAlgoritmo.getValue();
        String padrao = txtPadrao.getText();

        try {
            List<PesquisaResultado> resultados = switch (entidade) {
                case ENTIDADE_ANIMAIS -> pesquisarAnimais(padrao, algoritmo);
                case ENTIDADE_ABRIGOS -> pesquisarAbrigos(padrao, algoritmo);
                case ENTIDADE_CAMPANHAS -> pesquisarCampanhas(padrao, algoritmo);
                default -> pesquisarUsuarios(padrao, algoritmo);
            };

            tabela.setItems(FXCollections.observableArrayList(resultados));
            atualizarResumo(resultados.size(), entidade, algoritmo, padrao);
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao pesquisar registros: " + e.getMessage()
            );
        }
    }

    private boolean controllersDisponiveis() {
        return usuarioController != null
                && animalController != null
                && abrigoController != null
                && campanhaController != null;
    }

    private void limpar() {
        txtPadrao.clear();
        pesquisar();
    }

    private void atualizarResumo(int total, String entidade, String algoritmo, String padrao) {
        String termo = normalizar(padrao).isEmpty()
                ? "todos os registros"
                : "padrão \"" + padrao.trim() + "\"";

        lblResumo.setText(total + " resultado(s) em " + entidade + " usando " + algoritmo + " para " + termo + ".");
    }

    private List<PesquisaResultado> pesquisarUsuarios(String padrao, String algoritmo) throws Exception {
        List<PesquisaResultado> resultados = new ArrayList<>();

        for (Usuario usuario : usuarioController.listarTodos()) {
            if (algumCampoContem(padrao, algoritmo,
                    String.valueOf(usuario.getId()),
                    usuario.getNome(),
                    usuario.getCpf(),
                    usuario.getEmail(),
                    usuario.getTelefone(),
                    usuario.getCidade(),
                    usuario.getEstado())) {
                resultados.add(new PesquisaResultado(
                        ENTIDADE_USUARIOS,
                        String.valueOf(usuario.getId()),
                        valorSeguro(usuario.getNome()),
                        "CPF: " + valorSeguro(usuario.getCpf())
                                + " | Email: " + valorSeguro(usuario.getEmail())
                                + " | Telefone: " + valorSeguro(usuario.getTelefone())
                                + " | Cidade: " + valorSeguro(usuario.getCidade())
                                + " | Estado: " + valorSeguro(usuario.getEstado())
                ));
            }
        }

        return resultados;
    }

    private List<PesquisaResultado> pesquisarAnimais(String padrao, String algoritmo) throws Exception {
        List<PesquisaResultado> resultados = new ArrayList<>();

        for (Animal animal : animalController.listarTodos()) {
            if (algumCampoContem(padrao, algoritmo,
                    String.valueOf(animal.getId()),
                    animal.getNome(),
                    animal.getRaca(),
                    animal.getPorte(),
                    String.valueOf(animal.getPeso()),
                    animal.getDataAdocao(),
                    String.valueOf(animal.getIdAbrigo()))) {
                resultados.add(new PesquisaResultado(
                        ENTIDADE_ANIMAIS,
                        String.valueOf(animal.getId()),
                        valorSeguro(animal.getNome()),
                        "Raça: " + valorSeguro(animal.getRaca())
                                + " | Porte: " + valorSeguro(animal.getPorte())
                                + " | Peso: " + animal.getPeso()
                                + " | Adoção: " + valorSeguro(animal.getDataAdocao())
                                + " | ID do abrigo: " + animal.getIdAbrigo()
                ));
            }
        }

        return resultados;
    }

    private List<PesquisaResultado> pesquisarAbrigos(String padrao, String algoritmo) throws Exception {
        List<PesquisaResultado> resultados = new ArrayList<>();

        for (Abrigo abrigo : abrigoController.listarTodos()) {
            if (algumCampoContem(padrao, algoritmo,
                    String.valueOf(abrigo.getId()),
                    abrigo.getNome(),
                    abrigo.getendereco(),
                    abrigo.getTelefone(),
                    String.valueOf(abrigo.getCustoMensal()))) {
                resultados.add(new PesquisaResultado(
                        ENTIDADE_ABRIGOS,
                        String.valueOf(abrigo.getId()),
                        valorSeguro(abrigo.getNome()),
                        "Endereço: " + valorSeguro(abrigo.getendereco())
                                + " | Telefone: " + valorSeguro(abrigo.getTelefone())
                                + " | Custo mensal: " + abrigo.getCustoMensal()
                ));
            }
        }

        return resultados;
    }

    private List<PesquisaResultado> pesquisarCampanhas(String padrao, String algoritmo) throws Exception {
        List<PesquisaResultado> resultados = new ArrayList<>();

        for (Campanha campanha : campanhaController.listarTodos()) {
            if (algumCampoContem(padrao, algoritmo,
                    String.valueOf(campanha.getId()),
                    campanha.getNome(),
                    campanha.getLocal(),
                    campanha.getData(),
                    String.valueOf(campanha.getCusto()))) {
                resultados.add(new PesquisaResultado(
                        ENTIDADE_CAMPANHAS,
                        String.valueOf(campanha.getId()),
                        valorSeguro(campanha.getNome()),
                        "Local: " + valorSeguro(campanha.getLocal())
                                + " | Data: " + valorSeguro(campanha.getData())
                                + " | Custo: " + campanha.getCusto()
                ));
            }
        }

        return resultados;
    }

    private boolean algumCampoContem(String padrao, String algoritmo, String... campos) {
        if (normalizar(padrao).isEmpty()) {
            return true;
        }

        for (String campo : campos) {
            if (contemPadrao(campo, padrao, algoritmo)) {
                return true;
            }
        }

        return false;
    }

    private boolean contemPadrao(String texto, String padrao, String algoritmo) {
        String textoNormalizado = normalizar(texto);
        String padraoNormalizado = normalizar(padrao);

        if (padraoNormalizado.isEmpty()) {
            return true;
        }

        if (ALGORITMO_KMP.equals(algoritmo)) {
            return new KMP(padraoNormalizado).buscar(textoNormalizado);
        }

        return new BoyerMoore(padraoNormalizado).buscar(textoNormalizado);
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase(Locale.ROOT);
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }

    public static class PesquisaResultado {
        private final String entidade;
        private final String id;
        private final String registro;
        private final String detalhes;

        public PesquisaResultado(String entidade, String id, String registro, String detalhes) {
            this.entidade = entidade;
            this.id = id;
            this.registro = registro;
            this.detalhes = detalhes;
        }

        public String getEntidade() {
            return entidade;
        }

        public String getId() {
            return id;
        }

        public String getRegistro() {
            return registro;
        }

        public String getDetalhes() {
            return detalhes;
        }
    }
}
