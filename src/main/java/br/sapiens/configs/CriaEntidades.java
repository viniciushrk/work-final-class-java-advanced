package br.sapiens.configs;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public class CriaEntidades {

    private Connection connection;
    private Statement statement;
    public CriaEntidades(Connection connection) {
        this.connection = connection;
    }

    public void gerarEntidade() throws SQLException
    {
        statement = connection.createStatement();

        String Disciplina = "Create table disciplinas (" +
                "id int primary key AUTO_INCREMENT, " +
                "descricao varchar(200)," +
                "curso varchar(200)," +
                "periodo varchar(200)" +
                ");";

        String Matricula = "" +
                "CREATE TABLE `matriculas` (" +
                "  `id` int primary key AUTO_INCREMENT," +
                "  `disciplinaId` int," +
                "  `alunoId` int," +
                "  `periodo` varchar(200)," +
                "  FOREIGN KEY (`alunoId`) REFERENCES alunos(`id`)," +
                "  FOREIGN KEY (`disciplinaId`) REFERENCES disciplinas(`id`)" +
                ");\n";

        String Aluno = "Create table alunos (" +
                "id int primary key AUTO_INCREMENT,"+
                "matricula varchar(200)," +
                "nome varchar(200)," +
                "dataNascimento DATE," +
                "curso varchar(200)" +
                ") ;";

        statement.execute(Disciplina);
        statement.execute(Aluno);
        statement.execute(Matricula);

        System.out.println("Tabela criada com sucesso");

        //statement.close();
    }

    public void removerEntidade() throws SQLException
    {
        statement.executeUpdate("DROP TABLE matriculas;");
        statement.executeUpdate("DROP TABLE disciplinas;");
        statement.executeUpdate("DROP TABLE alunos;");
    }
}
