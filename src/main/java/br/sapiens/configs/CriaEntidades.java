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

    public void gerarEntidade(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute(sql);

        System.out.println("Tabela criada comsucesso");
    }

    public void inserirDadoNaEntidade(int matricula, String nome) throws SQLException {
        Object[] params = new Object[]{matricula, nome};

        String sql = "Insert into alunos (matricula, nome) values ("+matricula+", '"+nome+"')";
        String msg = MessageFormat.format(sql, params);
        System.out.println(msg);

        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(sql);
        if (rows > 0) {
            System.out.println("Linha inserida com sucesso.");
        }
    }

}
