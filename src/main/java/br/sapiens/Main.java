package br.sapiens;


import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/layout/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Index");
        stage.setScene(scene);
        stage.show();
    }

    private static Connection connectionDatabase() throws SQLException {
        ConexaoSingleton conection = new ConexaoSingleton();
        return conection.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        var conection = connectionDatabase();

        var entidade = new CriaEntidades(conection);

        entidade.gerarEntidade("Create table alunos (matricula int primary key, nome varchar(200))");
        entidade.inserirDadoNaEntidade(2012455, "Vini Delas");
        //minha conecao
        launch();
    }

}