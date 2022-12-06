package br.sapiens.controllers;

import br.sapiens.daos.DisciplinaDao;
import br.sapiens.daos.MatriculaDao;
import br.sapiens.domain.dtos.MatriculaDto;
import br.sapiens.domain.models.Disciplina;
import br.sapiens.domain.models.Matricula;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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

           TableColumn<MatriculaDto, String> disciplinaC = new TableColumn<MatriculaDto, String>("Disciplina");
           disciplinaC.setCellValueFactory(new PropertyValueFactory<MatriculaDto, String>("descricaoDisciplina"));

           TableColumn<MatriculaDto, String> periodoC = new TableColumn<MatriculaDto, String>("Periodo");
           periodoC.setCellValueFactory(new PropertyValueFactory<MatriculaDto, String>("periodo"));
           addButtonToTable();
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
                            matricula.getId(),
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

    private void addButtonToTable() {
        TableColumn<MatriculaDto, Void> colBtn = new TableColumn("Ação");
        Callback<TableColumn<MatriculaDto, Void>, TableCell<MatriculaDto, Void>> cellFactory = new Callback<TableColumn<MatriculaDto, Void>, TableCell<MatriculaDto, Void>>() {
            @Override
            public TableCell<MatriculaDto, Void> call(final TableColumn<MatriculaDto, Void> param) {
                final TableCell<MatriculaDto, Void> cell = new TableCell<MatriculaDto, Void>() {

                    private final Button btn = new Button("Remover matricula");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            MatriculaDto data = getTableView().getItems().get(getIndex());
                            try {
                                matriculaDao.deleteById(data.getId());
                                new Alert(Alert.AlertType.INFORMATION, "Deletado com sucesso.").show();
                                table.getItems().clear();
                                table.getItems().addAll(matriculaDtos());
                            } catch (SQLException e) {
                                new Alert(Alert.AlertType.ERROR, "Deletado com sucesso.").show();
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if( empty ) {
                            setGraphic(null);
                        } else {
                            MatriculaDto data = getTableView().getItems().get(getIndex());
                            btn.setText(btn.getText() + " " + data.getId() );
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }
}
