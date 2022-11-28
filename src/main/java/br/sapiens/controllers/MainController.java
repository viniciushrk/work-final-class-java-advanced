package br.sapiens.controllers;

import br.sapiens.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    public void cadastrarAlunos() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/layout/alunos/CadastroAluno.fxml"));
        painel.setCenter(fxmlLoader.load());
    }

    public void listagemAlunos() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/layout/alunos/ListagemAluno.fxml"));
        painel.setCenter(fxmlLoader.load());
    }

    public void listagemDisciplinas() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/layout/disciplinas/ListagemDisciplinas.fxml"));
        painel.setCenter(fxmlLoader.load());
    }

    public void cadastrarDisciplina() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/layout/disciplinas/CadastroDisciplina.fxml"));
        painel.setCenter(fxmlLoader.load());
    }
}