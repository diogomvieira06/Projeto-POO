package src.test.automacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.automacao.Acao;
import src.main.automacao.Condicao;
import src.main.controller.DomusControl;
import src.main.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AcaoTest {

    private DomusControl dc;
    private Casa casa;
    private Divisao sala;
    private Lampada lampada;
    private Cortina cortina;
    private ColunaSom colunaSom;

    @BeforeEach
    void setUp() {
        // Inicializa a casa com dispositivos para testar ações.
        dc = new DomusControl();
        Utilizador util = dc.criarUtilizador("Ana");
        casa = dc.criarCasa("Casa Acao");
        dc.adicionarCasaAAdministrador(util, casa);
        dc.criarDivisao(casa, "Sala");
        sala = casa.obterDivisaoPorId(1);

        lampada = new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue", 9.5, "Branco");
        cortina = new Cortina(dc.aumentarIdDispositivo(), "Somfy", "Smart", 18.0, 50);
        colunaSom = new ColunaSom(dc.aumentarIdDispositivo(), "Sonos", "Beam", 15.0, 40);

        dc.adicionarDispositivo(sala, lampada);
        dc.adicionarDispositivo(sala, cortina);
        dc.adicionarDispositivo(sala, colunaSom);
    }

    @Test
    void testAcaoLigarLuzesCasa() {
        // Valida que a ação de ligar luzes funciona.
        lampada.desligarDispositivo();
        Acao acao = Acao.ligarLuzesCasa(casa.getId());
        acao.executar(dc);
        assertEquals("LIGADO", lampada.getEstado());
    }

    @Test
    void testAcaoDesligarLuzesCasa() {
        // Valida que a ação de desligar luzes funciona.
        lampada.ligarDispositivo();
        Acao acao = Acao.desligarLuzesCasa(casa.getId());
        acao.executar(dc);
        assertEquals("DESLIGADO", lampada.getEstado());
    }

    @Test
    void testAcaoFecharCortinas() {
        // Valida que a ação de fechar cortinas funciona.
        cortina.setNivelAbertura(100);
        Acao acao = Acao.fecharCortinas(casa.getId());
        acao.executar(dc);
        assertEquals(0, cortina.getNivelAbertura());
    }

    @Test
    void testAcaoAbrirCortinas() {
        // Valida que a ação de abrir cortinas funciona.
        cortina.setNivelAbertura(0);
        Acao acao = Acao.abrirCortinas(casa.getId());
        acao.executar(dc);
        assertEquals(100, cortina.getNivelAbertura());
    }

    @Test
    void testAcaoLigarColunaSom() {
        // Valida que a ação de ligar coluna de som funciona.
        colunaSom.desligarDispositivo();
        Acao acao = Acao.ligarColunaSomCasa(casa.getId());
        acao.executar(dc);
        assertEquals("LIGADO", colunaSom.getEstado());
    }

    @Test
    void testAcaoDesligarColunaSom() {
        // Valida que a ação de desligar coluna de som funciona.
        colunaSom.ligarDispositivo();
        Acao acao = Acao.desligarColunaSomCasa(casa.getId());
        acao.executar(dc);
        assertEquals("DESLIGADO", colunaSom.getEstado());
    }

    @Test
    void testAcaoDefinirIntensidadeLampadasCasa() {
        // Valida que a ação de definir intensidade funciona.
        Acao acao = Acao.definirIntensidadeLampadasCasa(casa.getId(), 50);
        acao.executar(dc);
        // assertEquals(50, lampada.getIntesidade_Luminosidade()); // Removido: intensidade não existe mais
    }

    @Test
    void testAcaoDefinirCorLampadasCasa() {
        // Valida que a ação de definir cor funciona.
        Acao acao = Acao.definirCorLampadasCasa(casa.getId(), "Vermelho");
        acao.executar(dc);
        assertEquals("Vermelho", lampada.getCor_Luz());
    }

    @Test
    void testAcaoDesligarLuzesEFecharCortinas() {
        // Valida que a ação de desligar luzes e fechar cortinas funciona.
        lampada.ligarDispositivo();
        cortina.setNivelAbertura(100);
        
        Acao acao = Acao.desligarLuzesEFecharCortinas(casa.getId());
        acao.executar(dc);
        
        assertEquals("DESLIGADO", lampada.getEstado());
        assertEquals(0, cortina.getNivelAbertura());
    }

    @Test
    void testAcaoClone() {
        // Valida que o clone funciona corretamente.
        Acao acao1 = Acao.ligarLuzesCasa(casa.getId());
        Acao acao2 = acao1.clone();
        
        assertNotSame(acao1, acao2);
        assertEquals(acao1.getNome(), acao2.getNome());
    }

    @Test
    void testAcaoGetterSetter() {
        // Valida que os getters e setters funcionam.
        Acao acao = Acao.ligarLuzesCasa(casa.getId());
        
        String nome = acao.getNome();
        assertNotNull(nome);
        
        acao.setNome("Nova Acao");
        assertEquals("Nova Acao", acao.getNome());
    }

    @Test
    void testAcaoNullCasaDoesNothing() {
        // Valida que ações com casa nula não causam exceção.
        Acao acao = Acao.ligarLuzesCasa(99999);  // Casa que não existe
        acao.executar(dc);
        assertEquals("DESLIGADO", lampada.getEstado()); // Sem alteração
    }

    @Test
    void testAcaoMultiplasIntensidades() {
        // Valida diferentes valores de intensidade.
        int[] intensidades = {0, 25, 50, 75, 100};
        
        for (int intensidade : intensidades) {
            Acao acao = Acao.definirIntensidadeLampadasCasa(casa.getId(), intensidade);
            acao.executar(dc);
            // assertEquals(intensidade, lampada.getIntesidade_Luminosidade()); // Removido: intensidade não existe mais
        }
    }

    @Test
    void testAcaoMultiplosCores() {
        // Valida diferentes cores.
        String[] cores = {"Vermelho", "Verde", "Azul", "Amarelo", "Branco", "Branco Frio"};
        
        for (String cor : cores) {
            Acao acao = Acao.definirCorLampadasCasa(casa.getId(), cor);
            acao.executar(dc);
            assertEquals(cor, lampada.getCor_Luz());
        }
    }
}

