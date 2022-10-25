package br.sapiens.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane painel;

    public void initialize() throws IOException {
        var label = new Label("Sapiens");
        painel.setBottom(label);
    }
}