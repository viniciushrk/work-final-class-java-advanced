package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;
import br.sapiens.domain.models.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DisciplinaDao implements CrudRepository<Disciplina, Integer>  {

    private final Connection conn;

    public DisciplinaDao() throws SQLException {
        this.conn =  new ConexaoSingleton().getConnection();
    }

    @Override
    public <S extends Disciplina> S save(S entity) throws SQLException
    {
        if(entity.getId() == 0)
            return insertInto(entity);
        else
            return update(entity);
    }

    private <S extends Disciplina> S update(S entity) throws SQLException {
        String sql = "UPDATE disciplina SET descricao = ?, curso = ?, periodo = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,entity.getId());
        pstmt.setString(2,entity.getDescricao());
        pstmt.setString(3, entity.getCurso().toString());
        pstmt.setString(3, entity.getCurso().toString());
        pstmt.executeUpdate();
        return entity;
    }

    private <S extends Disciplina> S insertInto(S entity) throws SQLException {
        String sql = "Insert into disciplinas (descricao, curso, periodo) values(?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, entity.getDescricao());
        pstmt.setString(2, entity.getCurso().toString());
        pstmt.setString(3, entity.getPeriodos().toString());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setId(generatedKeys.getInt(1));
        return entity;
    }

    @Override
    public <S extends Disciplina> Iterable<Disciplina> saveAll(Iterable<S> entities) throws SQLException {
        ArrayList lista = new ArrayList();
        for(S entity : entities) {
            lista.add(save(entity));
        }
        return lista;
    }

    @Override
    public Optional<Disciplina> findById(Integer id) throws SQLException {
        List<Disciplina> resultados = findAllById(List.of(id));
        if(resultados == null || resultados.size() != 1)
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size "+resultados.size());
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public List<Disciplina> findAllById(Iterable<Integer> ids) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<Integer> interetor = ids.iterator();

        while(interetor.hasNext()){
            lista.add(interetor.next());
        }

        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));

        String sql = "select * from disciplinas where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<Disciplina> resultado = new ArrayList();

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int disciplinaId = rs.getInt(1);
                var disciplina = new Disciplina (
                        rs.getInt(1),
                        rs.getString(2),
                        CursosEnum.valueOf(rs.getString(3)),
                        PeriodosEnum.valueOf(rs.getString(4))
                );

                resultado.add(disciplina);
            }
        }

        return resultado;
    }

    @Override
    public void delete(Disciplina entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteAll(Iterable<? extends Disciplina> entities) {

    }
}
