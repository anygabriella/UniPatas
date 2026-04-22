package br.com.unipatas.view.adocao;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class AdocaoRemoverView {

  public VBox getConteudo() {
    VBox layoutPrincipal = new VBox(20);
    layoutPrincipal.setAlignment(Pos.CENTER);
    layoutPrincipal.setPadding(new Insets(25));

    HBox hbBusca = new HBox(10);
    hbBusca.setAlignment(Pos.CENTER);

    TextField txtIdAnimal = new TextField();
    txtIdAnimal.setPromptText("ID do Animal");

    Button btnDeletar = new Button("Cancelar Adoção");
    btnDeletar.getStyleClass().add("botao-perigo");

    hbBusca.getChildren().addAll(new Label("ID do Animal:"), txtIdAnimal, btnDeletar);

    layoutPrincipal.getChildren().add(hbBusca);
    return layoutPrincipal;
  }
}
