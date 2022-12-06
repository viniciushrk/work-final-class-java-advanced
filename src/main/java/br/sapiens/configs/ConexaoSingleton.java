package br.sapiens.configs;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSingleton {
    private static Connection conn = null;

    public ConexaoSingleton() throws SQLException {
        String jdbcURL = "jdbc:h2:mem:test";
        if(conn == null){
            conn = DriverManager.getConnection(jdbcURL);
            System.out.println("Uma conexao esta sendo estabelecida");
        }

    }
    public Connection getConnection() {
        return conn;
    }
}
