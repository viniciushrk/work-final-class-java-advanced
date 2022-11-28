
import br.sapiens.configs.ConexaoSingleton;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class MainTest {

    @Test
    public void test() throws SQLException {
        var conexao = new ConexaoSingleton().getConnection();
        Assert.assertTrue(conexao != null);
    }
}