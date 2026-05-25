package br.com.unipatas.view.campanha;

import br.com.unipatas.controller.AnimalCampanhaController;
import br.com.unipatas.controller.AnimalController;
import br.com.unipatas.model.Animal;
import br.com.unipatas.model.Campanha;
import br.com.unipatas.view.util.AlertaUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.List;

public class CampanhaDetalhesView extends VBox {

    private Campanha campanha;
    private Runnable acaoVoltar;

    private AnimalCampanhaController animalCampanhaController;
    private AnimalController animalController;

    private ComboBox<Animal> cbAnimais;
    private ListView<String> listaAnimaisVinculados;

    public CampanhaDetalhesView(Campanha campanha, Runnable acaoVoltar) {
        this.campanha = campanha;
        this.acaoVoltar = acaoVoltar;

        try {
            this.animalCampanhaController = new AnimalCampanhaController();
            this.animalController = new AnimalController();
        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Não foi possível carregar os dados de animais e campanhas."
            );
        }

        montarTela();
        carregarAnimaisComboBox();
        carregarAnimaisVinculados();
    }

    private void montarTela() {
        this.setSpacing(18);
        this.setPadding(new Insets(24, 0, 0, 0));

        Button btnVoltar = new Button("← Voltar para campanhas");
        btnVoltar.getStyleClass().add("botao-secundario");
        btnVoltar.setOnAction(e -> {
            if (acaoVoltar != null) {
                acaoVoltar.run();
            }
        });

        VBox cardInfo = new VBox(12);
        cardInfo.getStyleClass().add("campanha-detalhes-card");

        Label badge = new Label("Campanha #" + campanha.getId());
        badge.getStyleClass().add("campanha-badge");

        Label nome = new Label(campanha.getNome());
        nome.getStyleClass().add("campanha-detalhes-titulo");
        nome.setWrapText(true);

        Label local = new Label("📍 Local: " + campanha.getLocal());
        local.getStyleClass().add("campanha-detalhes-info");

        Label data = new Label("📅 Data: " + campanha.getData());
        data.getStyleClass().add("campanha-detalhes-info");

        Label custo = new Label("💰 Custo: R$ " + String.format("%.2f", campanha.getCusto()));
        custo.getStyleClass().add("campanha-detalhes-info");

        cardInfo.getChildren().addAll(badge, nome, local, data, custo);

        VBox cardVinculo = new VBox(12);
        cardVinculo.getStyleClass().add("campanha-detalhes-card");

        Label tituloVinculo = new Label("Vincular animal à campanha");
        tituloVinculo.getStyleClass().add("label-secao");

        cbAnimais = new ComboBox<>();
        cbAnimais.setPromptText("Selecione um animal");
        cbAnimais.setMaxWidth(Double.MAX_VALUE);

        cbAnimais.setConverter(new StringConverter<Animal>() {
            @Override
            public String toString(Animal animal) {
                if (animal == null) {
                    return "";
                }

                return "#" + animal.getId()
                        + " - " + animal.getNome()
                        + " (" + animal.getRaca() + ")";
            }

            @Override
            public Animal fromString(String string) {
                return null;
            }
        });

        Button btnVincular = new Button("Vincular");
        btnVincular.getStyleClass().add("botao-principal");
        btnVincular.setOnAction(e -> vincularAnimal());

        Button btnDesvincular = new Button("Desvincular");
        btnDesvincular.getStyleClass().add("botao-perigo");
        btnDesvincular.setOnAction(e -> desvincularAnimal());

        HBox botoes = new HBox(10, btnVincular, btnDesvincular);
        botoes.setAlignment(Pos.CENTER_LEFT);

        cardVinculo.getChildren().addAll(
                tituloVinculo,
                cbAnimais,
                botoes
        );

        VBox cardLista = new VBox(12);
        cardLista.getStyleClass().add("campanha-detalhes-card");

        Label tituloLista = new Label("Animais vinculados");
        tituloLista.getStyleClass().add("label-secao");

        listaAnimaisVinculados = new ListView<>();
        listaAnimaisVinculados.setPrefHeight(180);
        VBox.setVgrow(listaAnimaisVinculados, Priority.ALWAYS);

        Button btnAtualizar = new Button("Atualizar lista");
        btnAtualizar.getStyleClass().add("botao-secundario");
        btnAtualizar.setOnAction(e -> carregarAnimaisVinculados());

        cardLista.getChildren().addAll(tituloLista, listaAnimaisVinculados, btnAtualizar);

        this.getChildren().addAll(
                btnVoltar,
                cardInfo,
                cardVinculo,
                cardLista
        );
    }

    private void carregarAnimaisComboBox() {
        try {
            List<Animal> animais = animalController.listarTodos();

            cbAnimais.getItems().clear();
            cbAnimais.getItems().addAll(animais);

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao carregar animais: " + e.getMessage()
            );
        }
    }

    private void carregarAnimaisVinculados() {
        try {
            listaAnimaisVinculados.getItems().clear();

            List<Integer> idsAnimais = animalCampanhaController.buscarAnimaisDaCampanha(campanha.getId());

            if (idsAnimais.isEmpty()) {
                listaAnimaisVinculados.getItems().add("Nenhum animal vinculado a esta campanha.");
                return;
            }

            for (Integer idAnimal : idsAnimais) {
                Animal animal = animalController.buscar(idAnimal);

                if (animal != null) {
                    listaAnimaisVinculados.getItems().add(
                            "#" + animal.getId()
                                    + " - " + animal.getNome()
                                    + " | Raça: " + animal.getRaca()
                                    + " | Porte: " + animal.getPorte()
                    );
                } else {
                    listaAnimaisVinculados.getItems().add(
                            "#" + idAnimal + " - Animal não encontrado"
                    );
                }
            }

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao carregar vínculos: " + e.getMessage()
            );
        }
    }

    private void vincularAnimal() {
        Animal animalSelecionado = cbAnimais.getValue();

        if (animalSelecionado == null) {
            AlertaUtil.mostrar(
                    Alert.AlertType.WARNING,
                    "Aviso",
                    "Selecione um animal para vincular."
            );
            return;
        }

        try {
            boolean sucesso = animalCampanhaController.vincular(
                    animalSelecionado.getId(),
                    campanha.getId()
            );

            if (sucesso) {
                AlertaUtil.mostrar(
                        Alert.AlertType.INFORMATION,
                        "Sucesso",
                        "Animal vinculado à campanha com sucesso!"
                );
                carregarAnimaisVinculados();
            } else {
                AlertaUtil.mostrar(
                        Alert.AlertType.WARNING,
                        "Aviso",
                        "Este animal já está vinculado a esta campanha."
                );
            }

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao vincular animal: " + e.getMessage()
            );
        }
    }

    private void desvincularAnimal() {
        Animal animalSelecionado = cbAnimais.getValue();

        if (animalSelecionado == null) {
            AlertaUtil.mostrar(
                    Alert.AlertType.WARNING,
                    "Aviso",
                    "Selecione um animal para desvincular."
            );
            return;
        }

        try {
            boolean sucesso = animalCampanhaController.desvincular(
                    animalSelecionado.getId(),
                    campanha.getId()
            );

            if (sucesso) {
                AlertaUtil.mostrar(
                        Alert.AlertType.INFORMATION,
                        "Sucesso",
                        "Animal desvinculado da campanha."
                );
                carregarAnimaisVinculados();
            } else {
                AlertaUtil.mostrar(
                        Alert.AlertType.WARNING,
                        "Aviso",
                        "Esse vínculo não foi encontrado."
                );
            }

        } catch (Exception e) {
            AlertaUtil.mostrar(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Erro ao desvincular animal: " + e.getMessage()
            );
        }
    }
}