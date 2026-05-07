package src.test.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import src.main.view.ConsoleUI;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConsoleUI Tests")
public class ConsoleUITest {

    /**
     * Test: Validação que desenharDashboard() existe
     */
    @Test
    @DisplayName("ConsoleUI deve ter método desenharDashboard()")
    public void testDesenharDashboardMethodExists() {
        assertDoesNotThrow(() -> {
            ConsoleUI.class.getDeclaredMethod("desenharDashboard", String.class, String.class, String.class);
        });
    }

    /**
     * Test: Validação que mostrarErro() existe
     */
    @Test
    @DisplayName("ConsoleUI deve ter método mostrarErro()")
    public void testMostrarErroMethodExists() {
        assertDoesNotThrow(() -> {
            ConsoleUI.class.getDeclaredMethod("mostrarErro", String.class);
        });
    }

    /**
     * Test: Verificar acesso a métodos privados utilitários
     */
    @Test
    @DisplayName("ConsoleUI deve ter método privado truncar()")
    public void testTruncarMethodExists() {
        assertDoesNotThrow(() -> {
            var method = ConsoleUI.class.getDeclaredMethod("truncar", String.class, int.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
        });
    }

    @Test
    @DisplayName("ConsoleUI deve ter método privado centerString()")
    public void testCenterStringMethodExists() {
        assertDoesNotThrow(() -> {
            var method = ConsoleUI.class.getDeclaredMethod("centerString", String.class, int.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
        });
    }

    @Test
    @DisplayName("ConsoleUI deve ter método privado getTerminalDimensions()")
    public void testGetTerminalDimensionsMethodExists() {
        assertDoesNotThrow(() -> {
            var method = ConsoleUI.class.getDeclaredMethod("getTerminalDimensions");
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
        });
    }

    @Test
    @DisplayName("ConsoleUI deve ter método privado imprimirLinha()")
    public void testImprimirLinhaMethodExists() {
        assertDoesNotThrow(() -> {
            var method = ConsoleUI.class.getDeclaredMethod("imprimirLinha", int.class, String.class, String.class, String.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
        });
    }

    /**
     * Test: Testar funcionalidade de truncar usando reflexão
     */
    @Test
    @DisplayName("truncar() deve encurtar strings maiores que o limite")
    public void testTruncarFunctionality() throws Exception {
        var method = ConsoleUI.class.getDeclaredMethod("truncar", String.class, int.class);
        method.setAccessible(true);

        // Teste 1: String menor que o limite não deve ser truncada
        String result1 = (String) method.invoke(null, "Hello", 10);
        assertEquals("Hello", result1, "String menor que limite não deve ser truncada");

        // Teste 2: String maior que o limite deve ser truncada com "..."
        String result2 = (String) method.invoke(null, "HelloWorld", 5);
        assertTrue(result2.endsWith("..."), "String truncada deve terminar com '...'");
        assertTrue(result2.length() <= 5, "String truncada não deve exceder o limite");

        // Teste 3: String exatamente no limite
        String result3 = (String) method.invoke(null, "Hello", 5);
        assertEquals("Hello", result3, "String com tamanho exato do limite não deve ser truncada");
    }

    /**
     * Test: Testar funcionalidade de centralizar usando reflexão
     */
    @Test
    @DisplayName("centerString() deve centralizar texto corretamente")
    public void testCenterStringFunctionality() throws Exception {
        var method = ConsoleUI.class.getDeclaredMethod("centerString", String.class, int.class);
        method.setAccessible(true);

        // Teste 1: Text centralizado deve ter espaços
        String result1 = (String) method.invoke(null, "Test", 10);
        assertEquals(10, result1.length(), "String centralizada deve ter tamanho exato");
        assertTrue(result1.contains("Test"), "String centralizada deve conter o texto original");

        // Teste 2: String maior que largura deve retornar substring
        String result2 = (String) method.invoke(null, "HelloWorld", 5);
        assertEquals(5, result2.length(), "Result deve estar limitado à largura especificada");

        // Teste 3: String vazia
        String result3 = (String) method.invoke(null, "", 5);
        assertEquals(5, result3.length(), "Espaços vazios devem preencher a largura");
    }

    /**
     * Test: ConsoleUI deve ter constantes de cor
     */
    @Test
    @DisplayName("ConsoleUI deve ter constantes de cores ANSI")
    public void testColorConstants() throws Exception {
        // Verificar campos estáticos de constantes
        assertDoesNotThrow(() -> {
            ConsoleUI.class.getDeclaredField("RESET");
            ConsoleUI.class.getDeclaredField("BOLD");
            ConsoleUI.class.getDeclaredField("PURPLE");
            ConsoleUI.class.getDeclaredField("CYAN");
            ConsoleUI.class.getDeclaredField("WHITE");
        });
    }

    /**
     * Test: Verificar que métodos públicos são estáticos
     */
    @Test
    @DisplayName("Métodos públicos de ConsoleUI devem ser estáticos")
    public void testPublicMethodsAreStatic() throws Exception {
        var method1 = ConsoleUI.class.getDeclaredMethod("desenharDashboard", String.class, String.class, String.class);
        var method2 = ConsoleUI.class.getDeclaredMethod("mostrarErro", String.class);

        assertTrue(java.lang.reflect.Modifier.isStatic(method1.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(method2.getModifiers()));
    }

    /**
     * Test: Verificar que ConsoleUI está no pacote correto
     */
    @Test
    @DisplayName("ConsoleUI deve estar no pacote correto")
    public void testPackageName() {
        assertEquals("src.main.view", ConsoleUI.class.getPackageName(),
                "ConsoleUI deve estar no pacote src.main.view");
    }

    /**
     * Test: ConsoleUI deve ser uma classe pública
     */
    @Test
    @DisplayName("ConsoleUI deve ser público")
    public void testConsoleUIIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(ConsoleUI.class.getModifiers()),
                "ConsoleUI deve ser uma classe pública");
    }

    /**
     * Test: Verificar que CLEAR_ALL constante existe
     */
    @Test
    @DisplayName("ConsoleUI deve ter constante CLEAR_ALL")
    public void testHasClearAllConstant() {
        assertDoesNotThrow(() -> {
            var field = ConsoleUI.class.getDeclaredField("CLEAR_ALL");
            assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isStatic(field.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isFinal(field.getModifiers()));
        });
    }

    /**
     * Test: Testar truncar com casos limites
     */
    @Test
    @DisplayName("truncar() deve lidar com casos extremos")
    public void testTruncarEdgeCases() throws Exception {
        var method = ConsoleUI.class.getDeclaredMethod("truncar", String.class, int.class);
        method.setAccessible(true);

        // Teste com string vazia
        String result1 = (String) method.invoke(null, "", 5);
        assertEquals("", result1, "String vazia deve retornar vazia");

        // Teste com limit muito pequeno
        String result2 = (String) method.invoke(null, "A", 1);
        assertEquals("A", result2, "String com tamanho 1 não deve ser truncada");

        // Teste com limit 2 (menos que "...")
        String result3 = (String) method.invoke(null, "ABC", 2);
        // Mesmo com limit pequeno, a função deve tentar truncar
        assertTrue(result3.length() <= 2 || result3.contains("..."));
    }

}

