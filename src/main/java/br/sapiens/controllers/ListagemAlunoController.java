package br.sapiens.controllers;

import br.sapiens.Main;
import br.sapiens.daos.AlunoDao;

import br.sapiens.domain.models.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ListagemAlunoController
{
    @FXML
    TableView<Aluno> table;

    @FXML
    TableColumn<Aluno, String> columnNome;

    AlunoDao alunoDao = new AlunoDao();

    public ListagemAlunoController() throws SQLException {
    }

    public ObservableList<Aluno> getAlunos() throws SQLException {
        ObservableList<Aluno> guestsList = FXCollections.observableArrayList();
        var alunos = alunoDao.findAll();

        alunos.forEach((aluno) -> {
            guestsList.add(aluno);
        });

        return guestsList;
    }

    @FXML
    public void initialize() throws IOException, SQLException {
       if (table != null) {
           TableColumn<Aluno, Integer> idC = new TableColumn<>("Matricula");
           idC.setCellValueFactory(new PropertyValueFactory("id"));
           TableColumn<Aluno, String> nomeC = new TableColumn<Aluno, String>("Nome");
           nomeC.setCellValueFactory(new PropertyValueFactory<Aluno, String>("nome"));
           TableColumn<Aluno, String> dataNascimentoC = new TableColumn("DataNascimento");
           dataNascimentoC.setCellValueFactory(new PropertyValueFactory("dataNascimento"));
           table.getColumns().addAll(List.of(idC, nomeC, dataNascimentoC));
           table.getItems().addAll(alunoDao.findAll());
       }
    }

    private void addButtonToTable() {
        TableColumn<Aluno, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Aluno, Void>, TableCell<Aluno, Void>> cellFactory = new Callback<TableColumn<Aluno, Void>, TableCell<Aluno, Void>>() {
            @Override
            public TableCell<Aluno, Void> call(final TableColumn<Aluno, Void> param) {
                final TableCell<Aluno, Void> cell = new TableCell<Aluno, Void>() {

                    private final Button btn = new Button("Pagar Parcela");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Aluno data = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Pagamento");
                            alert.setHeaderText("Pagamento Parcela");
                            alert.setContentText("referencia ID = " + data.getId() );

                            alert.showAndWait();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if( empty ) {
                            setGraphic(null);
                        } else {
                            Aluno data = getTableView().getItems().get(getIndex());
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

    public void deletarAlunoDisciplina() throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/alunos/DeletarAluno.fxml"));
        Button button = new Button("Ok");
        VBox rootPane = new VBox(10, button);
        Parent pane = loader.load();
        Scene newDialogScene = new Scene(pane);
        dialog.setScene(newDialogScene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
