import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaTest {

    // CT01 - Login válido
    @Test
    public void testLoginValido() {
        Login login = new Login("admin", "1234");
        assertTrue(login.authenticate());
    }

    // CT02 - Login inválido
    @Test
    public void testLoginInvalido() {
        Login login = new Login("admin", "senhaerrada");
        assertFalse(login.authenticate());
    }

    // CT03 - Login vazio
    @Test
    public void testLoginVazio() {
        Login login = new Login("", "");
        assertFalse(login.authenticate());
    }

    // CT04 - Cadastrar tarefa
    @Test
    public void testCadastrarTarefa() {
        Tarefa t = new Tarefa("Estudar Java", "Ana", "ALTA", "10/06/2025");
        assertEquals("Estudar Java", t.getDescricao());
        assertEquals("PENDENTE", t.getStatus());
    }

    // CT05 - Alterar status da tarefa
    @Test
    public void testAlterarStatus() {
        Tarefa t = new Tarefa("Estudar Java", "Ana", "ALTA", "10/06/2025");
        t.setStatus("CONCLUÍDA");
        assertEquals("CONCLUÍDA", t.getStatus());
    }
}