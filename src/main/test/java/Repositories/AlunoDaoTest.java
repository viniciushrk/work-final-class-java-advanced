package Repositories;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.CrudRepository;
import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.models.Aluno;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeMethod;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.sql.SQLException;
import java.util.Date;

public class AlunoDaoTest {

    @BeforeMethod
    public void setUp() throws SQLException {
        System.out.println("setup");
    }

    @Test
    public void testShouldBeCreateAluno() throws SQLException {
        var conexao = new ConexaoSingleton().getConnection();
        new CriaEntidades(conexao).gerarEntidade();
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

        Assert.assertEquals("Count.FindAllAluno", 3, alunosEncontrado.size());
    }
}
