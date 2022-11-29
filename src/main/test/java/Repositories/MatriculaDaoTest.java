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
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;

public class MatriculaDaoTest {
    @Test
    public void testShouldBeMatricula() throws SQLException {
        var conexao = new ConexaoSingleton().getConnection();
        new CriaEntidades(conexao).gerarEntidade();

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
        var resultMatricula = matriculaDao.save(matricula);

        Assert.assertNotNull(resultDisciplina);
        Assert.assertNotNull(resultAluno);

        Assert.assertNotNull(resultMatricula);
        Assert.assertEquals(matricula.getAlunoId(), resultMatricula.getAlunoId());
        Assert.assertEquals(matricula.getDisciplinaId(), resultMatricula.getDisciplinaId());
        Assert.assertEquals(matricula.getPeriodo(), resultMatricula.getPeriodo());
    }

    @Test
    public void testShouldBeFindAll() throws SQLException {
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
        var resultMatricula = matriculaDao.findAll();

        Assert.assertNotNull(resultMatricula);
        Assert.assertNotNull(resultMatricula.get(0).getAluno());
        Assert.assertNotNull(resultMatricula.get(0).getDisciplina());
        Assert.assertEquals(resultMatricula.get(0).getDisciplina().getDescricao(), disciplina.getDescricao());
        Assert.assertEquals(resultMatricula.get(0).getAluno().getNome(), aluno.getNome());
    }
}
