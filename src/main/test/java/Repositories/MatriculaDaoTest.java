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
import junit.framework.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MatriculaDaoTest {

    ConexaoSingleton conexao = null;
    CriaEntidades entidade = null;

    Aluno aluno = null;
    Disciplina disciplina  = null;

    Disciplina resultDisciplina = null;
    Aluno resultAluno = null;
    @BeforeEach
    public void init() throws SQLException {
        System.out.println("setup");
        conexao = new ConexaoSingleton();

        entidade = new CriaEntidades(conexao.getConnection());
        entidade.gerarEntidade();

        aluno = new Aluno(
                "jorge",
                new Date(),
                CursosEnum.SISTEMAS
        );

        disciplina = new Disciplina(
                "POO",
                CursosEnum.SISTEMAS,
                PeriodosEnum.PRIMEIRO
        );


        DisciplinaDao disciplinaDao = new DisciplinaDao();
        resultDisciplina = disciplinaDao.save(disciplina);

        AlunoDao alunoRespository = new AlunoDao();
        resultAluno = alunoRespository.save(aluno);
    }

    @AfterEach
    public void setTearDown() throws SQLException {
        if (conexao != null) {
            entidade.removerEntidade();
        }
        System.out.println("setTearDown");
    }

    @Test
    public void testShouldBeMatricula() throws SQLException {
        var matricula = new Matricula(resultAluno.getId(), resultDisciplina.getId(), PeriodosEnum.PRIMEIRO);
        MatriculaDao matriculaDao = new MatriculaDao();
        var resultMatricula = matriculaDao.save(matricula);

        Assert.assertNotNull(resultMatricula);
        Assert.assertEquals(matricula.getAlunoId(), resultMatricula.getAlunoId());
        Assert.assertEquals(matricula.getDisciplinaId(), resultMatricula.getDisciplinaId());
        Assert.assertEquals(matricula.getPeriodo(), resultMatricula.getPeriodo());
    }

    @Test
    public void testShouldBeFindAll() throws SQLException {
        var matricula = new Matricula(resultAluno.getId(), resultDisciplina.getId(), PeriodosEnum.PRIMEIRO);
        MatriculaDao matriculaDao = new MatriculaDao();
        matriculaDao.save(matricula);
        var resultMatricula = matriculaDao.findAll();

        Assert.assertNotNull(resultMatricula);
        Assert.assertNotNull(resultMatricula.get(0).getAluno());
        Assert.assertNotNull(resultMatricula.get(0).getDisciplina());
        Assert.assertEquals(resultMatricula.get(0).getDisciplina().getDescricao(), disciplina.getDescricao());
        Assert.assertEquals(resultMatricula.get(0).getAluno().getNome(), aluno.getNome());
    }

    @Test
    public void testShouldBeFindAllById() throws SQLException {
        var matricula = new Matricula(resultAluno.getId(), resultDisciplina.getId(), PeriodosEnum.PRIMEIRO);
        MatriculaDao matriculaDao = new MatriculaDao();

        var resultMatricula = matriculaDao.findAllById(List.of(matriculaDao.save(matricula).getId()));

        Assert.assertNotNull(resultMatricula);
        Assert.assertNotNull(resultMatricula.get(0).getAluno());
        Assert.assertNotNull(resultMatricula.get(0).getDisciplina());
        Assert.assertEquals(resultMatricula.get(0).getDisciplina().getDescricao(), disciplina.getDescricao());
        Assert.assertEquals(resultMatricula.get(0).getAluno().getNome(), aluno.getNome());
    }

    @Test
    public void testShouldBeDeleteMatriculaById() throws SQLException
    {
        var matricula = new Matricula(resultAluno.getId(), resultDisciplina.getId(), PeriodosEnum.PRIMEIRO);
        MatriculaDao matriculaDao = new MatriculaDao();
        var resultMatricula = matriculaDao.save(matricula);

        matriculaDao.deleteById(resultMatricula.getId());
        Assertions.assertThrows(
                SQLException.class,
                () -> {
                    matriculaDao.findById(resultMatricula.getId());
                }, "Erro ao buscar valores, não existe somente um resultado! Size 0"
        );
    }

    @Test
    public void testShouldBeDeleteAllMatricula() throws SQLException
    {
        var aluno = new Aluno(
                "jorge",
                new Date(),
                CursosEnum.SISTEMAS
        );

        AlunoDao alunoRespository = new AlunoDao();
        alunoRespository.save(aluno);

        var result = alunoRespository.findAll();
        org.junit.Assert.assertNotNull(result);

        alunoRespository.deleteAll(result);
        Assertions.assertEquals(0, alunoRespository.findAll().size());
    }
}
