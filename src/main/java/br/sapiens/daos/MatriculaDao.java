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
    public Optional<Matricula> findById(Integer id) throws SQLException {
        List<Matricula> resultados = findAllById(List.of(id));

        if(resultados == null || resultados.size() != 1) {
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size " + resultados.size());
        }
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public void delete(Matricula matricula) throws SQLException {
        String sql = "DELETE FROM matriculas WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1 , matricula.getId());
        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi deletada.");
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM matriculas WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1 , id);
        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi deletada.");
    }

    @Override
    public void deleteAll(Iterable<? extends Matricula> matriculas) throws SQLException {
        for (var matricula: matriculas) {
            String sql = "DELETE FROM matriculas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1 , matricula.getId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0)
                throw new SQLException("Falha, nenhuma linha foi deletada.");
        }
    }

    @Override
    public List<Matricula> findAllById(Iterable<Integer> ids) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<Integer> interetor = ids.iterator();

        while(interetor.hasNext()){
            lista.add(interetor.next());
        }

        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select * from matriculas where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<Matricula> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int matriculaId = rs.getInt(1);
                var alunoId = rs.getInt(2);
                var disciplinaId = rs.getInt(3);
                var periodoEnum =  rs.getString(4);

                var matricula = new Matricula (
                        matriculaId,
                        alunoId,
                        disciplinaId,
                        PeriodosEnum.valueOf(periodoEnum != null ? periodoEnum : PeriodosEnum.PRIMEIRO.toString())
                );
                Optional<Aluno> alunoDao = new AlunoDao().findById(alunoId);
                Optional<Disciplina> disciplinaDao = new DisciplinaDao().findById(disciplinaId);
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
                        rs.getInt(1),
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
