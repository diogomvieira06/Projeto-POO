package src.test.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import src.main.view.InputValidator;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InputValidator Tests")
public class InputValidatorTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private PrintStream capturedOutput;

    @BeforeEach
    public void setUp() {
        capturedOutput = new PrintStream(new ByteArrayOutputStream());
        System.setOut(capturedOutput);
        System.setErr(capturedOutput);
    }

    /**
     * Test: Validação que lerInteiro() existe e é acessível
     */
    @Test
    @DisplayName("InputValidator deve ter método lerInteiro()")
    public void testLerInteiroMethodExists() {
        assertDoesNotThrow(() -> {
            // Verifica que o método existe através de reflexão
            InputValidator.class.getDeclaredMethod("lerInteiro");
        });
    }

    /**
     * Test: Validação que lerDouble() existe e é acessível
     */
    @Test
    @DisplayName("InputValidator deve ter método lerDouble()")
    public void testLerDoubleMethodExists() {
        assertDoesNotThrow(() -> {
            // Verifica que o método existe através de reflexão
            InputValidator.class.getDeclaredMethod("lerDouble");
        });
    }

    /**
     * Test: Validação que lerLinha() existe e é acessível
     */
    @Test
    @DisplayName("InputValidator deve ter método lerLinha()")
    public void testLerLinhaMethodExists() {
        assertDoesNotThrow(() -> {
            // Verifica que o método existe através de reflexão
            InputValidator.class.getDeclaredMethod("lerLinha");
        });
    }

    /**
     * Test: InputValidator deve ser uma classe pública
     */
    @Test
    @DisplayName("InputValidator deve ser público")
    public void testInputValidatorIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(InputValidator.class.getModifiers()),
                "InputValidator deve ser uma classe pública");
    }

    /**
     * Test: Verificar que métodos de leitura são estáticos
     */
    @Test
    @DisplayName("Métodos de leitura devem ser estáticos")
    public void testMethodsAreStatic() {
        assertDoesNotThrow(() -> {
            var method1 = InputValidator.class.getDeclaredMethod("lerInteiro");
            var method2 = InputValidator.class.getDeclaredMethod("lerDouble");
            var method3 = InputValidator.class.getDeclaredMethod("lerLinha");

            assertTrue(java.lang.reflect.Modifier.isStatic(method1.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isStatic(method2.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isStatic(method3.getModifiers()));
        });
    }

    /**
     * Test: Verificar retorno de tipos corretos
     */
    @Test
    @DisplayName("lerInteiro() deve retornar int")
    public void testLerInteiroReturnType() {
        assertDoesNotThrow(() -> {
            var method = InputValidator.class.getDeclaredMethod("lerInteiro");
            assertEquals(int.class, method.getReturnType(), "lerInteiro() deve retornar int");
        });
    }

    @Test
    @DisplayName("lerDouble() deve retornar double")
    public void testLerDoubleReturnType() {
        assertDoesNotThrow(() -> {
            var method = InputValidator.class.getDeclaredMethod("lerDouble");
            assertEquals(double.class, method.getReturnType(), "lerDouble() deve retornar double");
        });
    }

    @Test
    @DisplayName("lerLinha() deve retornar String")
    public void testLerLinhaReturnType() {
        assertDoesNotThrow(() -> {
            var method = InputValidator.class.getDeclaredMethod("lerLinha");
            assertEquals(String.class, method.getReturnType(), "lerLinha() deve retornar String");
        });
    }

    /**
     * Test: Verificar que há um Scanner estático em InputValidator
     */
    @Test
    @DisplayName("InputValidator deve ter um Scanner estático")
    public void testHasScannerField() {
        assertDoesNotThrow(() -> {
            var field = InputValidator.class.getDeclaredField("sc");
            assertTrue(java.lang.reflect.Modifier.isStatic(field.getModifiers()),
                    "Campo sc deve ser estático");
            assertEquals(java.util.Scanner.class, field.getType(),
                    "Campo sc deve ser do tipo Scanner");
        });
    }

    /**
     * Test: Verificar que o Scanner é privado
     */
    @Test
    @DisplayName("Scanner deve ser privado")
    public void testScannerIsPrivate() {
        assertDoesNotThrow(() -> {
            var field = InputValidator.class.getDeclaredField("sc");
            assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()),
                    "Campo sc deve ser privado");
        });
    }

    /**
     * Test: Validação de estrutura básica da classe
     */
    @Test
    @DisplayName("InputValidator deve estar no pacote correto")
    public void testPackageName() {
        assertEquals("src.main.view", InputValidator.class.getPackageName(),
                "InputValidator deve estar no pacote src.main.view");
    }

}

