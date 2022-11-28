package Repositories;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DisciplinaTest {
    @Test
    public void testShouldBeDisciplina() throws SQLException {
        var conexao = new ConexaoSingleton().getConnection();
        new CriaEntidades(conexao).gerarEntidade();
        var disciplina = new Disciplina(
                "POO",
                CursosEnum.SISTEMAS,
                PeriodosEnum.PRIMEIRO
        );

        DisciplinaDao disciplinaDao = new DisciplinaDao();
        var result = disciplinaDao.save(disciplina);

        Assert.assertNotNull(result);
        Assert.assertEquals(disciplina.getDescricao(), result.getDescricao());
        Assert.assertEquals(disciplina.getId(), result.getId());
        Assert.assertEquals(disciplina.getCurso(), result.getCurso());
        Assert.assertEquals(disciplina.getPeriodos(), result.getPeriodos());
    }
}