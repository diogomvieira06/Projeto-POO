package src.view;

import java.util.*;
import src.controller.DomusControl;
import src.model.*;

public class Main {
    public static void main(String[] args) {
        DomusControl domusControl = new DomusControl();

        // 1. CARREGAMENTO OU SEED
        DomusControl carregado = DomusControl.carregarEstado("estado_projeto.dat");
        if (carregado != null) {
            domusControl = carregado;
            System.out.println("Estado anterior carregado com sucesso!\n");
        } else {
            System.out.println("Iniciando de raiz. Criando configuração de teste...");
            inicializarDadosTeste(domusControl); // A REMOVER NO FINAL, APENAS PARA TESTES RÁPIDOS
        }

        System.out.println("Bem-vindo ao DomusControl!");


        // 2. LOGIN COM CICLO INFINITO (ROBUSTEZ)
        Utilizador utilizador_atual = null;
        while (utilizador_atual == null) {
            System.out.println("Escolha o Utilizador:");
            domusControl.listarUtilizadores();
            System.out.print("Introduza o seu ID de Utilizador (ou 0 para encerrar): ");
            int idLogin = InputValidator.lerInteiro();

            if (idLogin == 0) {
                System.out.println("A encerrar o programa...");
                return;
            }

            utilizador_atual = domusControl.encontrarUtilizadorPorId(idLogin);

            if (utilizador_atual == null) {
                System.out.println("Erro: Utilizador com ID " + idLogin + " não encontrado. Tente novamente.");
            }
        }
        System.out.println("Sessão iniciada como: " + utilizador_atual.getNome());

        // 3. MENU PRINCIPAL
        boolean sair = false;
        while (!sair) {
            Menu.exibirMenuPrincipal();
            int opcao = InputValidator.lerInteiro();

            switch (opcao) {
                case 1 -> Menu.submenuCasas(utilizador_atual, domusControl);
                case 2 -> {
                    System.out.print("Alcunha da nova casa: ");
                    String alcunha = InputValidator.lerLinha();
                    Casa nova = domusControl.criarCasa(alcunha);
                    domusControl.adicionarCasaAAdministrador(utilizador_atual, nova);
                    System.out.println("Casa criada com sucesso!");
                }
                case 3 -> Menu.menuAutomacao(utilizador_atual, domusControl);
                case 0 -> sair = true;
                default -> System.out.println("Erro: Opção '" + opcao + "' inválida.");
            }
        }

        // 4. PERSISTÊNCIA
        try {
            domusControl.guardarEstado("estado_projeto.dat");
            System.out.println("Estado guardado com sucesso. Até à próxima!");
        } catch (Exception e) {
            System.out.println("Erro crítico ao gravar dados.");
        }
    }


    
    private static void inicializarDadosTeste(DomusControl dc) { // APENAS PARA TESTES RÁPIDOS, A REMOVER NO FINAL
        // ===== CRIAÇÃO DE UTILIZADORES =====
        dc.criarUtilizador("Joao");
        dc.criarUtilizador("Antonio");
        dc.criarUtilizador("Luísa");
        
        // ===== CRIAÇÃO DE CASAS =====
        dc.criarCasa("Casa do Joao");
        dc.criarCasa("Casa do Antonio");
        dc.criarCasa("Casa do Luísa");
        
        Casa c1 = dc.encontrarCasaPorId(1);
        Casa c2 = dc.encontrarCasaPorId(2);
        Casa c3 = dc.encontrarCasaPorId(3);

        Utilizador u1 = dc.encontrarUtilizadorPorId(1);
        Utilizador u2 = dc.encontrarUtilizadorPorId(2);
        Utilizador u3 = dc.encontrarUtilizadorPorId(3);

        // ===== CRIAÇÃO DE DIVISÕES =====
        if (c1 != null) dc.criarDivisao(c1, "Sala");
        if (c1 != null) dc.criarDivisao(c1, "Quarto");
        if (c1 != null) dc.criarDivisao(c1, "Cozinha");
        if (c2 != null) dc.criarDivisao(c2, "Sala");
        if (c2 != null) dc.criarDivisao(c2, "Quarto");
        if (c2 != null) dc.criarDivisao(c2, "Cozinha");
        if (c3 != null) dc.criarDivisao(c3, "Cozinha");
        if (c3 != null) dc.criarDivisao(c3, "Sala");
        if (c3 != null) dc.criarDivisao(c3, "Quarto");

        // ===== ADIÇÃO DE DISPOSITIVOS POR DIVISÃO =====
        // CASA 1 - SALA
        Divisao sala1 = dc.encontrarDivisaoPorId(c1, 1);
        if (sala1 != null) {
            sala1.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue White", 15.0, 80, "Branco"));
            sala1.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "IKEA", "TRADFRI", 12.0, 60, "Quente"));
            sala1.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "TP-Link", "Smart Plug", 8.0));
            sala1.adicionarDispositivo(new Curtina(dc.aumentarIdDispositivo(), "Somfy", "Motorizada", 5.0, 50));
        }
        
        // CASA 1 - QUARTO
        Divisao quarto1 = dc.encontrarDivisaoPorId(c1, 2);
        if (quarto1 != null) {
            quarto1.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue White", 15.0, 70, "Branco"));
            quarto1.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "Sonoff", "S31", 10.0));
            quarto1.adicionarDispositivo(new ColunaSom(dc.aumentarIdDispositivo(), "JBL", "Link 20", 20.0, 50));
        }
        
        // CASA 1 - COZINHA
        Divisao cozinha1 = dc.encontrarDivisaoPorId(c1, 3);
        if (cozinha1 != null) {
            cozinha1.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "IKEA", "TRADFRI", 12.0, 100, "Branco"));
            cozinha1.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "TP-Link", "Smart Plug", 8.0));
            cozinha1.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "Meross", "Smart Plug", 9.0));
        }

        // CASA 2 - SALA
        Divisao sala2 = dc.encontrarDivisaoPorId(c2, 4);
        if (sala2 != null) {
            sala2.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue Color", 18.0, 80, "RGB"));
            sala2.adicionarDispositivo(new Curtina(dc.aumentarIdDispositivo(), "Somfy", "Motorizada", 5.0, 75));
            sala2.adicionarDispositivo(new ColunaSom(dc.aumentarIdDispositivo(), "Google", "Home Mini", 2.0, 70));
        }
        
        // CASA 2 - QUARTO
        Divisao quarto2 = dc.encontrarDivisaoPorId(c2, 5);
        if (quarto2 != null) {
            quarto2.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "IKEA", "TRADFRI", 12.0, 50, "Quente"));
            quarto2.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "Sonoff", "S31", 10.0));
        }
        
        // CASA 2 - COZINHA
        Divisao cozinha2 = dc.encontrarDivisaoPorId(c2, 6);
        if (cozinha2 != null) {
            cozinha2.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue White", 15.0, 90, "Branco"));
            cozinha2.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "TP-Link", "Smart Plug", 8.0));
            cozinha2.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "Sonoff", "S31", 10.0));
        }

        // CASA 3 - COZINHA
        Divisao cozinha3 = dc.encontrarDivisaoPorId(c3, 7);
        if (cozinha3 != null) {
            cozinha3.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "IKEA", "TRADFRI", 12.0, 100, "Branco"));
            cozinha3.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "TP-Link", "Smart Plug", 8.0));
        }
        
        // CASA 3 - SALA
        Divisao sala3 = dc.encontrarDivisaoPorId(c3, 8);
        if (sala3 != null) {
            sala3.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue White", 15.0, 75, "Branco"));
            sala3.adicionarDispositivo(new Curtina(dc.aumentarIdDispositivo(), "Somfy", "Motorizada", 5.0, 30));
            sala3.adicionarDispositivo(new PortaoGaragem(dc.aumentarIdDispositivo(), "Genie", "Garage Door Opener", 25.0, 0));
        }
        
        // CASA 3 - QUARTO
        Divisao quarto3 = dc.encontrarDivisaoPorId(c3, 9);
        if (quarto3 != null) {
            quarto3.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue White", 15.0, 60, "Quente"));
            quarto3.adicionarDispositivo(new ColunaSom(dc.aumentarIdDispositivo(), "Amazon", "Echo Dot", 2.0, 50));
            quarto3.adicionarDispositivo(new Tomada(dc.aumentarIdDispositivo(), "Meross", "Smart Plug", 9.0));
        }

        // ===== ASSOCIAÇÃO DE CASAS AOS UTILIZADORES =====
        dc.adicionarCasaAAdministrador(u1, c1);
        dc.adicionarCasaAUtilizador(u1, c2);
        dc.adicionarCasaAUtilizador(u1, c3);
        dc.adicionarCasaAAdministrador(u2, c2);
        dc.adicionarCasaAUtilizador(u2, c1);
        dc.adicionarCasaAUtilizador(u2, c3);
        dc.adicionarCasaAAdministrador(u3, c3);
        dc.adicionarCasaAUtilizador(u3, c1);
        dc.adicionarCasaAUtilizador(u3, c2);
        
        System.out.println("\n✓ Dados de teste inicializados com sucesso!");
        System.out.println("  - 3 Utilizadores criados");
        System.out.println("  - 3 Casas criadas com 3 divisões cada");
        System.out.println("  - 23 Dispositivos distribuídos por todas as divisões\n");
    }
}