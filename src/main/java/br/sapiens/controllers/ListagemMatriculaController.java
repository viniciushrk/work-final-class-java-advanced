package br.sapiens.controllers;

import br.sapiens.daos.DisciplinaDao;
import br.sapiens.daos.MatriculaDao;
import br.sapiens.domain.dtos.MatriculaDto;
import br.sapiens.domain.models.Disciplina;
import br.sapiens.domain.models.Matricula;
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
import java.util.ArrayList;
import java.util.List;


public class ListagemMatriculaController
{
    @FXML
    TableView<MatriculaDto> table;

    MatriculaDao matriculaDao = new MatriculaDao();

    public ListagemMatriculaController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
       if (table != null) {
           TableColumn<MatriculaDto, String> alunoC = new TableColumn<MatriculaDto, String>("Aluno");
           alunoC.setCellValueFactory(new PropertyValueFactory<MatriculaDto, String>("nomeAluno"));

           TableColumn<MatriculaDto, String> disciplinaC = new TableColumn("Disciplina");
           disciplinaC.setCellValueFactory(new PropertyValueFactory("descricaoDisciplina"));

           TableColumn<MatriculaDto, String> periodoC = new TableColumn("Periodo");
           periodoC.setCellValueFactory(new PropertyValueFactory("periodo"));

           table.getColumns().addAll(List.of(alunoC, disciplinaC, periodoC)); // adiciona as colunas na tabela
           table.getItems().addAll(matriculaDtos()); // insere as tuplas
       }
    }

    public List<MatriculaDto> matriculaDtos() throws SQLException {
        var matriculas = matriculaDao.findAll();
        List<MatriculaDto> matriculaDtos = new ArrayList<MatriculaDto>();
        matriculas.forEach((matricula) -> {
            matriculaDtos.add(
                    new MatriculaDto(
                            matricula.getAlunoId(),
                            matricula.getDisciplinaId(),
                            matricula.getPeriodo(),
                            matricula.getAluno().getNome(),
                            matricula.getDisciplina().getDescricao()
                    )
            );
        });

        return matriculaDtos;
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
