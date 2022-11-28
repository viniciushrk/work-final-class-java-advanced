package br.sapiens.controllers;

import br.sapiens.daos.AlunoDao;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.models.Aluno;
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

public class CadastroAlunoController
{

    AlunoDao alunoDao = new AlunoDao();

    @FXML
    TextField nome;

    @FXML
    DatePicker dataNascimento;

    @FXML
    ChoiceBox<CursosEnum> cursos;

    public CadastroAlunoController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if (cursos != null) { // estou na tela de cadastro
            ObservableList<CursosEnum> list = FXCollections.observableArrayList();
            list.addAll(CursosEnum.values());
            cursos.setItems(list);
        }
    }

    public void salvar () throws SQLException
    {
        LocalDate localDate = dataNascimento.getValue();
        var aluno = new Aluno(
            nome.getText(),
            new DataParse().parse(localDate),
            (CursosEnum) cursos.getValue()
        );
        Aluno alunoSalvo = alunoDao.save(aluno);

        System.out.println("Aluno salvo com sucesso.");
        new Alert(Alert.AlertType.CONFIRMATION, "Aluno salvo com sucesso.").show();
        nome.setText("");
    }
}
