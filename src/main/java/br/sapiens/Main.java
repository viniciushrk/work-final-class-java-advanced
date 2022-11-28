package br.sapiens;


import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.CrudRepository;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.utils.DataParse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/layout/principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Index");
        stage.setScene(scene);
        stage.show();
    }

    private static Connection connectionDatabase() throws SQLException {
        ConexaoSingleton connection = new ConexaoSingleton();
        return connection.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        var connection = connectionDatabase();

        var entidade = new CriaEntidades(connection);

        entidade.gerarEntidade();
        var aluno = new Aluno(
                "viniciusa",
                new Date(),
                CursosEnum.ENGENHARIA
        );

        Aluno alunoSalvo = new AlunoDao().save(aluno);
        //minha conecao
        launch();
    }

}