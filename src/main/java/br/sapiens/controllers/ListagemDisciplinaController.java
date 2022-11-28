package br.sapiens.controllers;

import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ListagemDisciplinaController
{
    @FXML
    TableView<Aluno> table;

    @FXML
    TableColumn<Disciplina, String> columnNome;

    DisciplinaDao disciplinaDao = new DisciplinaDao();

    public ListagemDisciplinaController() throws SQLException {
    }

    public ObservableList<Aluno> getAlunos() throws SQLException {
        ObservableList<Aluno> guestsList = FXCollections.observableArrayList();
        var alunos = disciplinaDao.findAll();

        alunos.forEach((aluno) -> {
            guestsList.add(aluno);
        });

        return guestsList;
    }

    @FXML
    public void initialize() throws IOException, SQLException {
       if (table != null) {
           TableColumn<Aluno, Integer> idC = new TableColumn<>("Id");
           idC.setCellValueFactory(new PropertyValueFactory("id"));
           TableColumn<Aluno, String> nomeC = new TableColumn<Aluno, String>("Descricao");
           nomeC.setCellValueFactory(new PropertyValueFactory<Aluno, String>("descricao"));
           TableColumn<Aluno, String> dataNascimentoC = new TableColumn("Curso");
           dataNascimentoC.setCellValueFactory(new PropertyValueFactory("curso"));
           table.getColumns().addAll(List.of(idC, nomeC, dataNascimentoC));
           table.getItems().addAll(disciplinaDao.findAll());
       }
    }
}
