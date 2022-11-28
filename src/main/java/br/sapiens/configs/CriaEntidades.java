package br.sapiens.configs;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public class CriaEntidades {

    private Connection connection;
    public CriaEntidades(Connection connection) {
        this.connection = connection;
    }

    public void gerarEntidade() throws SQLException
    {
        Statement statement = connection.createStatement();

        String Disciplina = "Create table disciplinas (" +
                "id int primary key AUTO_INCREMENT, " +
                "descricao varchar(200)," +
                "curso varchar(200)," +
                "periodo varchar(200)" +
                ");";

        String Aluno = "Create table alunos (" +
                "id int primary key AUTO_INCREMENT,"+
                "matricula varchar(200)," +
                "nome varchar(200)," +
                "dataNascimento DATE," +
                "curso varchar(200)" +
                ") ;";

        statement.execute(Disciplina);
        statement.execute(Aluno);

        System.out.println("Tabela criada com sucesso");
    }
}
