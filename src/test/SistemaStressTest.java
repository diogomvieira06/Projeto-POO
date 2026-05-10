package src.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.controller.DomusControl;
import src.main.model.*;
import src.main.Exceptions.*;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SistemaStressTest {

    private DomusControl dc;

    @BeforeEach
    void setUp() {
        dc = new DomusControl();
    }

    @Test
    void testStressCargaExtrema() {
        // Criar 1000 utilizadores
        Utilizador admin = dc.criarUtilizador("AdminMaster");
        for (int i = 0; i < 999; i++) {
            dc.criarUtilizador("User" + i);
        }

        // Criar 500 casas e 5000 dispositivos
        for (int i = 0; i < 500; i++) {
            Casa casa = dc.criarCasa("Casa " + i);
            dc.adicionarCasaAAdministrador(admin, casa);
            
            Divisao sala = dc.criarDivisao(casa, "Sala " + i);
            Divisao quarto = dc.criarDivisao(casa, "Quarto " + i);

            for (int j = 0; j < 5; j++) {
                Lampada l = new Lampada(dc.aumentarIdDispositivo(), "MarcaX", "ModeloY", 10.0, "Branco");
                Tomada t = new Tomada(dc.aumentarIdDispositivo(), "MarcaZ", "ModeloW", 50.0);
                dc.adicionarDispositivo(sala, l);
                dc.adicionarDispositivo(quarto, t);
            }
        }

        assertEquals(1000, dc.getUtilizadores().size(), "Devem existir 1000 utilizadores");
        assertEquals(500, dc.getCasas().size(), "Devem existir 500 casas");

        // Passar muito tempo (1 ano)
        assertDoesNotThrow(() -> {
            dc.passarTempoGlobal(24 * 365); // 1 ano de horas
        }, "passarTempoGlobal não deve estoirar com 5000 dispositivos");
    }

    @Test
    void testFuzzingValoresExtremos() {
        // IDs inválidos
        assertThrows(UtilizadorNaoEncontradoException.class, () -> dc.encontrarUtilizadorPorId(-1));
        assertThrows(UtilizadorNaoEncontradoException.class, () -> dc.encontrarUtilizadorPorId(Integer.MAX_VALUE));
        
        assertThrows(CasaNaoEncontradaException.class, () -> dc.encontrarCasaPorId(-1));
        assertThrows(CasaNaoEncontradaException.class, () -> dc.encontrarCasaPorId(Integer.MAX_VALUE));

        // Nomes absurdamente grandes (500.000 caracteres)
        StringBuilder nomeGigante = new StringBuilder();
        nomeGigante.append("A".repeat(500000));
        
        assertDoesNotThrow(() -> {
            Utilizador u = dc.criarUtilizador(nomeGigante.toString());
            Casa c = dc.criarCasa(nomeGigante.toString());
            dc.criarDivisao(c, nomeGigante.toString());
            assertNotNull(u);
            assertNotNull(c);
        }, "A aplicação não deve crashar ao receber strings enormes, desde que tenha RAM suficiente");
    }

    @Test
    void testIOStressSerializacao() {
        // Criar estado básico
        Utilizador u = dc.criarUtilizador("TesteIO");
        Casa c = dc.criarCasa("CasaIO");
        dc.adicionarCasaAUtilizador(u, c);

        String tempFile = "stress_test_estado.dat";

        // Salvar e carregar 1000 vezes (verifica leak de file handles)
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 1000; i++) {
                dc.guardarEstado(tempFile);
                DomusControl carregado = DomusControl.carregarEstado(tempFile);
                assertNotNull(carregado);
            }
        }, "Não deve ocorrer erro de 'Too many open files' ou falha de leitura");

        // Limpeza
        File file = new File(tempFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testMemoryLeaksCriacaoRemocao() {
        // Forçamos a criação e remoção de milhares de objetos
        // e chamamos o Garbage Collector para tentar limpar tudo
        
        for (int i = 0; i < 5000; i++) {
            Utilizador u = dc.criarUtilizador("LeakTest " + i);
            dc.removerUtilizador(u);
        }
        
        // Verifica se as estruturas estão "limpas" de utilizadores
        assertEquals(0, dc.getUtilizadores().size(), "A lista de utilizadores deve estar limpa após remoção massiva");
        
        // Sugere limpeza à JVM (apenas um hint, não bloqueante, mas bom para testes locais)
        System.gc();
        
        assertTrue(true, "Chegou ao fim do teste de memória sem esgotar o heap.");
    }
}
