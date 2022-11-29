package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlunoDao implements CrudRepository<Aluno,Integer> {

    private final Connection conn;

    public AlunoDao() throws SQLException {
        this.conn =  new ConexaoSingleton().getConnection();
    }

    @Override
    public <S extends Aluno> S save(S entity) throws SQLException {
        if(entity.getId() == 0)
            return insertInto(entity);
        else
            return update(entity);
    }

    private <S extends Aluno> S update(S entity) throws SQLException {
        String sql = "UPDATE alunos SET nome = ?, dataNascimento = ?, curso = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, entity.getNome());
        pstmt.setDate(2, entity.getDataNascimentoSql());
        pstmt.setString(3, entity.getCursosEnum().toString());
        pstmt.setInt(4, entity.getId());
        pstmt.executeUpdate();
        return entity;
    }

    private <S extends Aluno> S insertInto(S entity) throws SQLException {

        String sql = "Insert into alunos(nome, dataNascimento, curso) values(?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,entity.getNome());
        pstmt.setDate(2,entity.getDataNascimentoSql());
        pstmt.setString(3,entity.getCursosEnum().toString());

        int affectedRows = pstmt.executeUpdate();

        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setId(generatedKeys.getInt(1));
        return entity;
    }

    @Override
    public <S extends Aluno> Iterable<Aluno> saveAll(Iterable<S> entities) throws SQLException {
        ArrayList lista = new ArrayList();
        for(S entity : entities) {
            lista.add(save(entity));
        }
        return lista;
    }

    @Override
    public Optional<Aluno> findById(Integer id) throws SQLException {
        List<Aluno> resultados = findAllById(List.of(id));

        if(resultados == null || resultados.size() != 1) {
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size " + resultados.size());
        }
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public List<Aluno> findAllById(Iterable<Integer> ids) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<Integer> interetor = ids.iterator();

        while(interetor.hasNext()){
            lista.add(interetor.next());
        }

        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select * from alunos where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<Aluno> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int alunoId = rs.getInt(1);


                var nome = rs.getString(3);
                var curso = rs.getString(5);
                var date =  rs.getDate(4);

                var aluno = new Aluno (
                        alunoId,
                        nome,
                        date,
                        CursosEnum.valueOf(rs.getString(5) != null ? rs.getString(5) : CursosEnum.SISTEMAS.toString())
                );

                resultado.add(aluno);
            }
        }
        return resultado;
    }

    public List<Aluno> findAll() throws SQLException {
        List<Integer> lista = new ArrayList();

        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select * from alunos";
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<Aluno> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int alunoId = rs.getInt(1);
                var nome = rs.getString(3);
                var date =  rs.getDate(4);

                var aluno = new Aluno (
                        alunoId,
                        nome,
                        date,
                        CursosEnum.valueOf(rs.getString(5) != null ? rs.getString(5) : CursosEnum.SISTEMAS.toString())
                );

                resultado.add(aluno);
            }
        }
        return resultado;
    }

    @Override
    public void delete(Aluno entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteAll(Iterable<? extends Aluno> entities) {

    }
}