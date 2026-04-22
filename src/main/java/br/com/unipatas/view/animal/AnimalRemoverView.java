package br.com.unipatas.view.animal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class AnimalRemoverView {

  public VBox getConteudo() {
    VBox layoutPrincipal = new VBox(20);
    layoutPrincipal.setAlignment(Pos.CENTER);
    layoutPrincipal.setPadding(new Insets(25));

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdDeletar = new TextField();
    txtIdDeletar.setPromptText("ID para excluir");

    Button btnDeletar = new Button("Excluir Animal");
    btnDeletar.getStyleClass().add("botao-perigo"); // padrão correto

    hbBusca.getChildren().addAll(new Label("ID:"), txtIdDeletar, btnDeletar);

    layoutPrincipal.getChildren().add(hbBusca);
    return layoutPrincipal;
  }
}
