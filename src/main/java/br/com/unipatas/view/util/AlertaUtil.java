package br.com.unipatas.view.util;

import javafx.scene.control.Alert;

public class AlertaUtil {

    public static void mostrar(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}