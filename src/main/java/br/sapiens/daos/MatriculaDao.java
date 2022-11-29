package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;
import br.sapiens.domain.models.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaDao implements CrudRepository<Matricula, Integer>{

    private final Connection conn;

    public MatriculaDao() throws SQLException {
        this.conn =  new ConexaoSingleton().getConnection();
    }
    @Override
    public <S extends Matricula> S save(S entity) throws SQLException {
        return insertInto(entity);
    }

    private <S extends Matricula> S insertInto(S entity) throws SQLException {

        String sql = "Insert into matriculas(disciplinaId, alunoId, periodo) values(?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, entity.getAlunoId());
        pstmt.setInt(2, entity.getDisciplinaId());
        pstmt.setString(3, entity.getPeriodo().toString());

        int affectedRows = pstmt.executeUpdate();

        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setId(generatedKeys.getInt(1));
        return entity;
    }

    @Override
    public Iterable saveAll(Iterable entities) throws SQLException {
        return null;
    }

    @Override
    public Optional findById(Integer o) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void delete(Matricula entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteAll(Iterable<? extends Matricula> entities) {

    }

    @Override
    public Iterable findAllById(Iterable iterable) throws SQLException {
        return null;
    }

    public List<Matricula> findAll() throws SQLException {
        String sql = "select * from matriculas";
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<Matricula> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int alunoId = rs.getInt(2);
                Optional<Aluno> alunoDao = new AlunoDao().findById(alunoId);

                var disciplinaId = rs.getInt(3);
                Optional<Disciplina> disciplinaDao = new DisciplinaDao().findById(disciplinaId);

                var periodo = rs.getString(4);

                var matricula = new Matricula (
                        alunoId,
                        disciplinaId,
                        PeriodosEnum.valueOf(periodo)
                );

                if (disciplinaDao.isPresent()) {
                    matricula.setAluno(alunoDao.get());
                }

                if (disciplinaDao.isPresent()) {
                    matricula.setDisciplina(disciplinaDao.get());
                }

                resultado.add(matricula);
            }
        }

        return resultado;
    }
}
