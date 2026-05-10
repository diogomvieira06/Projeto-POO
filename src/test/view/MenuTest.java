package src.test.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import src.main.view.Menu;
import src.main.model.*;
import src.main.controller.DomusControl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Menu Tests")
public class MenuTest {

    private DomusControl dc;
    private Casa casa;
    private Divisao divisao;
    private Utilizador utilizador;

    @BeforeEach
    public void setUp() {
        dc = new DomusControl();
        utilizador = dc.criarUtilizador("João");
        casa = dc.criarCasa("HomeTest");
        dc.adicionarCasaAUtilizador(utilizador, casa);
        dc.adicionarCasaAAdministrador(utilizador, casa);
        divisao = dc.criarDivisao(casa, "Sala");
    }

    /**
     * Test: Menu classe deve estar no pacote correto
     */
    @Test
    @DisplayName("Menu deve estar no pacote src.main.view")
    public void testMenuPackageName() {
        assertEquals("src.main.view", Menu.class.getPackageName(),
                "Menu deve estar no pacote src.main.view");
    }

    /**
     * Test: Menu deve ser uma classe pública
     */
    @Test
    @DisplayName("Menu deve ser uma classe pública")
    public void testMenuIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(Menu.class.getModifiers()),
                "Menu deve ser uma classe pública");
    }

    /**
     * Test: Verificar que método montarEstadoGlobalCasa existe
     */
    @Test
    @DisplayName("Menu deve ter método montarEstadoGlobalCasa()")
    public void testMontarEstadoGlobalCasaMethodExists() {
        assertDoesNotThrow(() -> {
            var method = Menu.class.getDeclaredMethod("montarEstadoGlobalCasa", Casa.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        });
    }

    /**
     * Test: montarEstadoGlobalCasa com casa sem dispositivos
     */
    @Test
    @DisplayName("montarEstadoGlobalCasa() deve mostrar mensagem para casa vazia")
    public void testMontarEstadoGlobalCasaSemDispositivos() throws Exception {
        var method = Menu.class.getDeclaredMethod("montarEstadoGlobalCasa", Casa.class);
        method.setAccessible(true);

        String resultado = (String) method.invoke(null, casa);

        assertNotNull(resultado, "Resultado não deve ser nulo");
        assertTrue(resultado.contains("CASA:"), "Resultado deve conter título CASA");
        assertTrue(resultado.contains("HomeTest"), "Resultado deve conter nome da casa");
        assertTrue(resultado.toLowerCase().contains("sem"), "Resultado deve indicar ausência de dispositivos");
    }

    /**
     * Test: montarEstadoGlobalCasa com casa com dispositivos
     */
    @Test
    @DisplayName("montarEstadoGlobalCasa() deve listar dispositivos")
    public void testMontarEstadoGlobalCasaComDispositivos() throws Exception {
        // Adicionar uma lâmpada
        Lampada lampada = new Lampada(1, "Philips", "LED", 10.0, "Branco");
        divisao.adicionarDispositivo(lampada);

        var method = Menu.class.getDeclaredMethod("montarEstadoGlobalCasa", Casa.class);
        method.setAccessible(true);

        String resultado = (String) method.invoke(null, casa);

        assertNotNull(resultado, "Resultado não deve ser nulo");
        assertTrue(resultado.contains("CASA:"), "Resultado deve conter título CASA");
        assertTrue(resultado.contains("HomeTest"), "Resultado deve conter nome da casa");
        assertTrue(resultado.contains("Sala"), "Resultado deve conter nome da divisão");
        assertTrue(resultado.contains("Philips"), "Resultado deve conter marca do dispositivo");
        assertTrue(resultado.contains("LED"), "Resultado deve conter modelo do dispositivo");
    }

    /**
     * Test: montarEstadoGlobalCasa com múltiplos dispositivos
     */
    @Test
    @DisplayName("montarEstadoGlobalCasa() deve listar múltiplos dispositivos")
    public void testMontarEstadoGlobalCasaComMultiplosDispositivos() throws Exception {
        // Adicionar vários dispositivos
        Lampada lampada = new Lampada(1, "Philips", "LED", 10.0, "Branco");
        Tomada tomada = new Tomada(2, "Intelbras", "Smart", 15.0);
        divisao.adicionarDispositivo(lampada);
        divisao.adicionarDispositivo(tomada);

        var method = Menu.class.getDeclaredMethod("montarEstadoGlobalCasa", Casa.class);
        method.setAccessible(true);

        String resultado = (String) method.invoke(null, casa);

        assertTrue(resultado.contains("Philips"), "Resultado deve conter primeira marca");
        assertTrue(resultado.contains("Intelbras"), "Resultado deve conter segunda marca");
        assertTrue(resultado.contains("LED"), "Resultado deve conter primeiro modelo");
        assertTrue(resultado.contains("Smart"), "Resultado deve conter segundo modelo");
    }

    /**
     * Test: montarEstadoGlobalCasa com múltiplas divisões
     */
    @Test
    @DisplayName("montarEstadoGlobalCasa() deve listar múltiplas divisões")
    public void testMontarEstadoGlobalCasaComMultiplasDivisoes() throws Exception {
        // Adicionar segunda divisão
        Divisao divisao2 = dc.criarDivisao(casa, "Quarto");

        // Adicionar dispositivos
        Lampada lampada1 = new Lampada(1, "Philips", "LED", 10.0, "Branco");
        Lampada lampada2 = new Lampada(2, "Osram", "Hue", 12.0, "Branco");
        divisao.adicionarDispositivo(lampada1);
        divisao2.adicionarDispositivo(lampada2);

        var method = Menu.class.getDeclaredMethod("montarEstadoGlobalCasa", Casa.class);
        method.setAccessible(true);

        String resultado = (String) method.invoke(null, casa);

        assertTrue(resultado.contains("Sala"), "Resultado deve conter primeira divisão");
        assertTrue(resultado.contains("Quarto"), "Resultado deve conter segunda divisão");
        assertTrue(resultado.contains("Philips"), "Resultado deve conter primeiro dispositivo");
        assertTrue(resultado.contains("Osram"), "Resultado deve conter segundo dispositivo");
    }

    /**
     * Test: Verificar métodos submenu
     */
    @Test
    @DisplayName("Menu deve ter método submenuCasas()")
    public void testSubmenuCasasMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("submenuCasas", Utilizador.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método menuInternoCasa()")
    public void testMenuInternoCasaMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuInternoCasa", Casa.class, Utilizador.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método submenuGestaoUtilizadores()")
    public void testSubmenuGestaoUtilizadoresMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("submenuGestaoUtilizadores", Casa.class, Utilizador.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método menuDispositivos()")
    public void testMenuDispositivosMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuDispositivos", Divisao.class, boolean.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método adicionarDispositivoSubmenu()")
    public void testAdicionarDispositivoSubmenuMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("adicionarDispositivoSubmenu", Divisao.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método menuAutomacao()")
    public void testMenuAutomacaoMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuAutomacao", Utilizador.class, DomusControl.class);
        });
    }

    /**
     * Test: Verificar métodos de menu de topo
     */
    @Test
    @DisplayName("Menu deve ter método menuLigarDispositivo()")
    public void testMenuLigarDispositivoMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuLigarDispositivo", Utilizador.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método menuDesligarDispositivo()")
    public void testMenuDesligarDispositivoMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuDesligarDispositivo", Utilizador.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método menuEstatisticas()")
    public void testMenuEstatisticasMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuEstatisticas", Utilizador.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método menuEscalonamentos()")
    public void testMenuEscalonamentosMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuEscalonamentos", Utilizador.class, DomusControl.class);
        });
    }

    @Test
    @DisplayName("Menu deve ter método menuCenarios()")
    public void testMenuCenariosMethodExists() {
        assertDoesNotThrow(() -> {
            Menu.class.getDeclaredMethod("menuCenarios", Utilizador.class, DomusControl.class);
        });
    }

    /**
     * Test: Verificar métodos privados auxiliares
     */
    @Test
    @DisplayName("Menu deve ter método privado obterCasa()")
    public void testObterCasaMethodExists() {
        assertDoesNotThrow(() -> {
            var method = Menu.class.getDeclaredMethod("obterCasa", DomusControl.class, int.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        });
    }

    @Test
    @DisplayName("Menu deve ter método privado obterUtilizador()")
    public void testObterUtilizadorMethodExists() {
        assertDoesNotThrow(() -> {
            var method = Menu.class.getDeclaredMethod("obterUtilizador", DomusControl.class, int.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
        });
    }

    @Test
    @DisplayName("Menu deve ter método privado obterDivisao()")
    public void testObterDivisaoMethodExists() {
        assertDoesNotThrow(() -> {
            var method = Menu.class.getDeclaredMethod("obterDivisao", DomusControl.class, Casa.class, int.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
        });
    }

    /**
     * Test: montarEstadoGlobalCasa com estado de dispositivos
     */
    @Test
    @DisplayName("montarEstadoGlobalCasa() deve incluir estado dos dispositivos")
    public void testMontarEstadoGlobalCasaComEstadoDispositivos() throws Exception {
        Lampada lampada = new Lampada(1, "Philips", "LED", 10.0, "Branco");
        lampada.ligarDispositivo();
        divisao.adicionarDispositivo(lampada);

        var method = Menu.class.getDeclaredMethod("montarEstadoGlobalCasa", Casa.class);
        method.setAccessible(true);

        String resultado = (String) method.invoke(null, casa);

        assertTrue(resultado.contains("LIGADO"), "Resultado deve conter estado LIGADO");
    }

    /**
     * Test: All menu methods should be public
     */
    @Test
    @DisplayName("Todos os métodos de menu principal devem ser públicos")
    public void testMenuMethodsArePublic() throws Exception {
        var methods = new String[] {
            "submenuCasas",
            "menuInternoCasa",
            "submenuGestaoUtilizadores",
            "menuDispositivos",
            "adicionarDispositivoSubmenu",
            "menuAutomacao",
            "menuLigarDispositivo",
            "menuDesligarDispositivo",
            "menuEstatisticas",
            "menuEscalonamentos",
            "menuCenarios"
        };

        for (String methodName : methods) {
            boolean found = false;
            for (java.lang.reflect.Method m : Menu.class.getDeclaredMethods()) {
                if (m.getName().equals(methodName) && java.lang.reflect.Modifier.isPublic(m.getModifiers())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Método publico " + methodName + " deve existir");
        }
    }

}

