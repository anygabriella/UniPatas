package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.AnimalCampanhaController;
import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.controller.CampanhaController;
import br.com.unipatas.model.Animal;
import br.com.unipatas.model.Campanha;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CampanhaDetalhesView extends BorderPane {

    private final int idCampanha;
    private CampanhaController campanhaController;
    private AnimalController animalController;
    private AnimalCampanhaController vinculoController;

    private ComboBox<Animal> cbAnimais;
    private ListView<Animal> listaAnimais;
    private Label lblResumo;

    public CampanhaDetalhesView(int idCampanha) {
        this.idCampanha = idCampanha;
        getStyleClass().add("objetos-page");
        try {
            campanhaController = new CampanhaController();
            animalController = new AnimalController();
            vinculoController = new AnimalCampanhaController();
            montarTela();
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os detalhes da campanha: " + e.getMessage());
        }
    }

    private void montarTela() throws Exception {
        Campanha campanha = campanhaController.buscar(idCampanha);
        if (campanha == null) {
            setCenter(criarEstadoErro());
            return;
        }

        VBox conteudo = new VBox(18);
        conteudo.setPadding(new Insets(26));

        Label badge = new Label("DETALHES DA CAMPANHA");
        badge.getStyleClass().add("objetos-badge");

        Label titulo = new Label(campanha.getNome());
        titulo.getStyleClass().add("objetos-titulo");

        Label subtitulo = new Label("Gerencie os animais vinculados a esta campanha sem sair do card.");
        subtitulo.getStyleClass().add("objetos-subtitulo");

        GridPane dados = new GridPane();
        dados.getStyleClass().add("campanha-detalhes-card");
        dados.setHgap(16);
        dados.setVgap(10);
        dados.add(criarRotulo("ID"), 0, 0);
        dados.add(criarValor("#" + campanha.getId()), 1, 0);
        dados.add(criarRotulo("Local"), 0, 1);
        dados.add(criarValor(campanha.getLocal()), 1, 1);
        dados.add(criarRotulo("Data"), 0, 2);
        dados.add(criarValor(campanha.getData()), 1, 2);
        dados.add(criarRotulo("Custo"), 0, 3);
        dados.add(criarValor("R$ " + campanha.getCusto()), 1, 3);

        VBox painelVinculos = new VBox(14);
        painelVinculos.getStyleClass().add("campanha-detalhes-card");

        Label tituloVinculos = new Label("Animais vinculados");
        tituloVinculos.getStyleClass().add("campanha-detalhes-titulo");

        lblResumo = new Label();
        lblResumo.getStyleClass().add("campanha-detalhes-info");

        listaAnimais = new ListView<>();
        listaAnimais.setPrefHeight(230);
        VBox.setVgrow(listaAnimais, Priority.ALWAYS);

        cbAnimais = new ComboBox<>();
        cbAnimais.setPromptText("Selecione um animal para vincular");
        cbAnimais.setPrefWidth(320);

        Button btnVincular = new Button("Vincular animal");
        btnVincular.getStyleClass().add("botao-principal");
        btnVincular.setOnAction(e -> vincularAnimal());

        Button btnDesvincular = new Button("Desvincular selecionado");
        btnDesvincular.getStyleClass().add("botao-perigo");
        btnDesvincular.setOnAction(e -> desvincularAnimalSelecionado());

        HBox acoes = new HBox(10, cbAnimais, btnVincular, btnDesvincular);
        acoes.setAlignment(Pos.CENTER_LEFT);

        painelVinculos.getChildren().addAll(tituloVinculos, lblResumo, listaAnimais, new Separator(), acoes);
        conteudo.getChildren().addAll(badge, titulo, subtitulo, dados, painelVinculos);

        setCenter(conteudo);
        carregarDadosVinculos();
    }

    private Label criarRotulo(String texto) {
        Label label = new Label(texto + ":");
        label.getStyleClass().add("objeto-card-id");
        return label;
    }

    private Label criarValor(String texto) {
        Label label = new Label(texto == null || texto.isBlank() ? "Não informado" : texto);
        label.getStyleClass().add("campanha-detalhes-info");
        return label;
    }

    private VBox criarEstadoErro() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40));
        Label titulo = new Label("Campanha não encontrada");
        titulo.getStyleClass().add("objetos-titulo");
        Label texto = new Label("Volte para a tela de campanhas e selecione um card válido.");
        texto.getStyleClass().add("objetos-subtitulo");
        box.getChildren().addAll(titulo, texto);
        return box;
    }

    private void carregarDadosVinculos() {
        try {
            listaAnimais.getItems().clear();
            cbAnimais.getItems().clear();

            List<Integer> idsVinculados = vinculoController.buscarAnimaisDaCampanha(idCampanha);
            Set<Integer> idsSet = new HashSet<>(idsVinculados);

            for (Integer idAnimal : idsVinculados) {
                Animal animal = animalController.buscar(idAnimal);
                if (animal != null) {
                    listaAnimais.getItems().add(animal);
                }
            }

            for (Animal animal : animalController.listarTodos()) {
                if (!idsSet.contains(animal.getId())) {
                    cbAnimais.getItems().add(animal);
                }
            }

            lblResumo.setText(listaAnimais.getItems().size() + " animal(is) vinculado(s) a esta campanha.");
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", "Erro ao carregar vínculos: " + e.getMessage());
        }
    }

    private void vincularAnimal() {
        Animal animal = cbAnimais.getValue();
        if (animal == null) {
            alerta(Alert.AlertType.WARNING, "Aviso", "Selecione um animal para vincular.");
            return;
        }

        try {
            boolean sucesso = vinculoController.vincular(animal.getId(), idCampanha);
            if (sucesso) {
                alerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal vinculado à campanha.");
                carregarDadosVinculos();
            } else {
                alerta(Alert.AlertType.WARNING, "Aviso", "Esse animal já está vinculado à campanha.");
            }
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    private void desvincularAnimalSelecionado() {
        Animal animal = listaAnimais.getSelectionModel().getSelectedItem();
        if (animal == null) {
            alerta(Alert.AlertType.WARNING, "Aviso", "Selecione um animal vinculado na lista.");
            return;
        }

        try {
            boolean sucesso = vinculoController.desvincular(animal.getId(), idCampanha);
            if (sucesso) {
                alerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal desvinculado da campanha.");
                carregarDadosVinculos();
            } else {
                alerta(Alert.AlertType.WARNING, "Aviso", "Vínculo não encontrado.");
            }
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Erro", e.getMessage());
        }
    }

    private void alerta(Alert.AlertType tipo, String titulo, String texto) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }
}
