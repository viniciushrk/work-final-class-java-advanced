package br.sapiens.controllers;

import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.daos.MatriculaDao;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;
import br.sapiens.domain.models.Matricula;
import br.sapiens.domain.utils.DataParse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.sql.SQLException;

public class MatricularController {

    @FXML
    ChoiceBox<String> alunos;
    
    @FXML
    ChoiceBox<String> disciplinas;
    
    @FXML
    ChoiceBox<PeriodosEnum> periodo;

    AlunoDao alunoDao = new AlunoDao();
    DisciplinaDao disciplinaDao = new DisciplinaDao();

    MatriculaDao matriculaDao = new MatriculaDao();

    public MatricularController() throws SQLException {
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
        var alunoSelecionado = alunos.getValue();
        var disciplinasSelecionado = disciplinas.getValue();

        var alunoSelecionadoSplit = alunoSelecionado.split(" - ");
        var disciplinasSelecionadoSplit = disciplinasSelecionado.split(" - ");

        int alunoId = Integer.parseInt(alunoSelecionadoSplit[1]);
        int disciplinaId = Integer.parseInt(disciplinasSelecionadoSplit[1]);

        var matricula = new Matricula(
                alunoId,
                disciplinaId,
                (PeriodosEnum) periodo.getValue()
        );

        Matricula matriculaSalva = matriculaDao.save(matricula);

        System.out.println("Matricula realizada com sucesso.");
        new Alert(Alert.AlertType.CONFIRMATION, "Matricula realizada com sucesso.").show();
    }
}
