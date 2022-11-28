package br.sapiens.controllers;

import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;
import br.sapiens.domain.utils.DataParse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class CadastroDisciplinaController
{

    DisciplinaDao disciplinaDao = new DisciplinaDao();

    @FXML
    TextField descricao;

    @FXML
    ChoiceBox<CursosEnum> cursos;

    @FXML
    ChoiceBox<PeriodosEnum> periodos;


    public CadastroDisciplinaController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if (cursos != null) { // estou na tela de cadastro
            ObservableList<CursosEnum> list = FXCollections.observableArrayList();
            list.addAll(CursosEnum.values());
            cursos.setItems(list);
        }

        if (periodos != null) { // estou na tela de cadastro
            ObservableList<PeriodosEnum> periodosEnums = FXCollections.observableArrayList();
            periodosEnums.addAll(PeriodosEnum.values());
            periodos.setItems(periodosEnums);
        }
    }

    public void salvar () throws SQLException
    {
        var disciplina = new Disciplina(
            descricao.getText(),
            (CursosEnum) cursos.getValue(),
            (PeriodosEnum) periodos.getValue()
        );

        Disciplina alunoSalvo = disciplinaDao.save(disciplina);

        System.out.println("Aluno salvo com sucesso.");
        new Alert(Alert.AlertType.CONFIRMATION, "Disciplina criada com sucesso.").show();
        descricao.setText("");
    }
}
