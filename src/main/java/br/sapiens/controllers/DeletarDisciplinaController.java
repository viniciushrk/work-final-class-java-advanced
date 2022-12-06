package br.sapiens.controllers;

import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.daos.MatriculaDao;
import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Matricula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.sql.SQLException;

public class DeletarDisciplinaController {

    @FXML
    ChoiceBox<String> alunos;

    @FXML
    ChoiceBox<String> disciplinas;

    @FXML
    ChoiceBox<PeriodosEnum> periodo;

    AlunoDao alunoDao = new AlunoDao();
    DisciplinaDao disciplinaDao = new DisciplinaDao();

    MatriculaDao matriculaDao = new MatriculaDao();

    public DeletarDisciplinaController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if (alunos != null) {
            alunos.setItems(getAlunos());
        }

        if (disciplinas != null) {
            disciplinas.setItems(getDisciplinas());
        }

        if (periodo != null) { // estou na tela de cadastro
            ObservableList<PeriodosEnum> periodosEnums = FXCollections.observableArrayList();
            periodosEnums.addAll(PeriodosEnum.values());
            periodo.setItems(periodosEnums);
        }
    }

    public ObservableList<String> getAlunos() throws SQLException {
        ObservableList<String> guestsList = FXCollections.observableArrayList();
        var alunos = alunoDao.findAll();

        alunos.forEach((aluno) -> {
            String s = aluno.getNome() + " - " + aluno.getId();
            guestsList.add(s);
        });

        return guestsList;
    }

    public ObservableList<String> getDisciplinas() throws SQLException {
        ObservableList<String> guestsList = FXCollections.observableArrayList();
        var disciplinasList = disciplinaDao.findAll();

        disciplinasList.forEach((disciplinasItem) -> {
            String s = disciplinasItem.getDescricao() + " - " + disciplinasItem.getId();
            guestsList.add(s);
        });

        return guestsList;
    }

    public void salvar() throws SQLException {
        try {
            var disciplinasSelecionado = disciplinas.getValue();

            var disciplinasSelecionadoSplit = disciplinasSelecionado.split(" - ");

            int disciplinaId = Integer.parseInt(disciplinasSelecionadoSplit[1]);

            disciplinaDao.deleteById(disciplinaId);

            System.out.println("Deletado com sucesso.");
            new Alert(Alert.AlertType.CONFIRMATION, "Deletado com sucesso.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possivel deletar, talvez essa disciplina esteja vinculada a um aluno.").show();
        }
    }
}
