package Repositories;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;

import br.sapiens.daos.DisciplinaDao;
import br.sapiens.daos.MatriculaDao;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Aluno;

import br.sapiens.domain.models.Disciplina;
import br.sapiens.domain.models.Matricula;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class AlunoDaoTest {
    ConexaoSingleton conexao = null;
    CriaEntidades entidade = null;
    @BeforeEach
    public void init() throws SQLException {
        System.out.println("setup");
        conexao = new ConexaoSingleton();

        entidade = new CriaEntidades(conexao.getConnection());
        entidade.gerarEntidade();
    }

    @AfterEach
    public void setTearDown() throws SQLException {
        if (conexao != null) {
            entidade.removerEntidade();
        }
        System.out.println("setTearDown");
    }

    @Test
    public void testShouldBeCreateAluno() throws SQLException {
        var aluno = new Aluno(
                "jorge",
                new Date(),
                CursosEnum.SISTEMAS
        );

        AlunoDao alunoRespository = new AlunoDao();
        var result = alunoRespository.save(aluno);

        Assert.assertNotNull(result);
        Assert.assertEquals(aluno.getNome(), result.getNome());
        Assert.assertEquals(aluno.getId(), result.getId());
        Assert.assertEquals(aluno.getCursosEnum(), result.getCursosEnum());
    }

    @Test
    public void testShouldBeCreateAlunoAndGetAlunos() throws SQLException
    {
        var aluno = new Aluno(
                "jorge",
                new Date(),
                CursosEnum.SISTEMAS
        );
        var aluno2 = new Aluno(
                "Cara",
                new Date(),
                CursosEnum.ENGENHARIA
        );

        AlunoDao alunoRespository = new AlunoDao();
        alunoRespository.save(aluno);
        alunoRespository.save(aluno2);

        var alunoEncontrado = alunoRespository.findById(aluno.id);

        Assert.assertEquals(aluno.getNome(), alunoEncontrado.get().getNome());
        Assert.assertEquals(aluno.getId(), alunoEncontrado.get().getId());
        Assert.assertEquals(aluno.getCursosEnum(), alunoEncontrado.get().getCursosEnum());
    }

    @Test
    public void testShouldBeThrowSQLException() throws SQLException
    {
        AlunoDao alunoRespository = new AlunoDao();
        assertThrows(
                SQLException.class,
                () -> {
                    alunoRespository.findById((int) Math.random());
                },
                "Erro ao buscar valores, não existe somente um resultado! Size 0"
        );
    }

    @Test
    public void testShouldBeGetAlunos() throws SQLException
    {
        AlunoDao alunoRespository = new AlunoDao();
        var alunosEncontrado = alunoRespository.findAll();

        Assert.assertEquals("Count.FindAllAluno", 0, alunosEncontrado.size());
    }

    @Test
    public void testShouldBeDeleteAlunoById() throws SQLException
    {
        var aluno = new Aluno(
                "jorge",
                new Date(),
                CursosEnum.SISTEMAS
        );

        AlunoDao alunoRespository = new AlunoDao();
        var result = alunoRespository.save(aluno);

        Assert.assertNotNull(result);

        alunoRespository.deleteById(result.id);
        Assertions.assertThrows(
            SQLException.class,
            () -> {
                alunoRespository.findById(result.id);
            }, "Erro ao buscar valores, não existe somente um resultado! Size 0"
        );
    }

    @Test
    public void testShouldBeDeleteThrowsExceptionByIdWithAlunoMatricula() throws SQLException {
        var aluno = new Aluno(
                "jorge",
                new Date(),
                CursosEnum.SISTEMAS
        );


        var disciplina = new Disciplina(
                "POO",
                CursosEnum.SISTEMAS,
                PeriodosEnum.PRIMEIRO
        );

        DisciplinaDao disciplinaDao = new DisciplinaDao();
        var resultDisciplina = disciplinaDao.save(disciplina);

        AlunoDao alunoRespository = new AlunoDao();
        var resultAluno = alunoRespository.save(aluno);

        var matricula = new Matricula(resultAluno.getId(), resultDisciplina.getId(), PeriodosEnum.PRIMEIRO);
        MatriculaDao matriculaDao = new MatriculaDao();
        matriculaDao.save(matricula);

        Assertions.assertThrows(
                JdbcSQLIntegrityConstraintViolationException.class,
                () -> {
                    alunoRespository.deleteById(resultAluno.id);
                }
        );
    }

    @Test
    public void testShouldBeDeleteAllAluno() throws SQLException
    {
        var aluno = new Aluno(
                "jorge",
                new Date(),
                CursosEnum.SISTEMAS
        );

        AlunoDao alunoRespository = new AlunoDao();
        alunoRespository.save(aluno);

        var result = alunoRespository.findAll();
        Assert.assertNotNull(result);

        alunoRespository.deleteAll(result);
        Assertions.assertEquals(0, alunoRespository.findAll().size());
    }
}
