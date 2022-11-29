package br.sapiens.controllers;

import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ListagemDisciplinaController
{
    @FXML
    TableView<Disciplina> table;

    @FXML
    TableColumn<Disciplina, String> columnNome;

    DisciplinaDao disciplinaDao = new DisciplinaDao();

    public ListagemDisciplinaController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
       if (table != null) {
           TableColumn<Disciplina, Integer> idC = new TableColumn<>("Id");
           idC.setCellValueFactory(new PropertyValueFactory("id"));

           TableColumn<Disciplina, String> descricaoC = new TableColumn<Disciplina, String>("Descricao");
           descricaoC.setCellValueFactory(new PropertyValueFactory<Disciplina, String>("descricao"));

           TableColumn<Disciplina, String> cursoC = new TableColumn("Curso");
           cursoC.setCellValueFactory(new PropertyValueFactory("curso"));

           TableColumn<Disciplina, String> periodoC = new TableColumn("Periodo");
           periodoC.setCellValueFactory(new PropertyValueFactory("periodos"));

           table.getColumns().addAll(List.of(idC, descricaoC, cursoC, periodoC)); // adiciona as colunas na tabela
           table.getItems().addAll(disciplinaDao.findAll()); // insere as tuplas
       }
    }

    public void vincularAlunoDisciplina() throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/matriculas/matricular.fxml"));
        Button button = new Button("Ok");
        VBox rootPane = new VBox(10, button);
        Parent pane = loader.load();
        Scene newDialogScene = new Scene(pane);
        dialog.setScene(newDialogScene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
