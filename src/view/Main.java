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
        dc.criarUtilizador("uti1");
        dc.criarUtilizador("uti2");
        dc.criarCasa("Casa do uti1");
        dc.criarCasa("Casa do uti2");
        Casa c1 = dc.encontrarCasaPorId(1);
        Casa c2 = dc.encontrarCasaPorId(3);
        Utilizador u1 = dc.encontrarUtilizadorPorId(1);
        Utilizador u2 = dc.encontrarUtilizadorPorId(2);

        if (c1 != null) dc.criarDivisao(c1, "Sala");
        if (c2 != null) dc.criarDivisao(c2, "Quarto");

        dc.adicionarCasaAAdministrador(u1, c1);
        dc.adicionarCasaAUtilizador(u1, c2);
        dc.adicionarCasaAAdministrador(u2, c2);
        dc.adicionarCasaAUtilizador(u2, c1);
    }
}