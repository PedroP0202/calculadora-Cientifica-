package com.pedrop.calculadora;

import java.util.Scanner;

/**
 * Classe principal da aplicação. Responsável por ler o input do utilizador,
 * orquestrar as operações, histórico e integração com a LLM.
 */
public class Main {
    /**
     * Ponto de entrada da aplicação.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Historico historico = new Historico();
        Calculator calc = new Calculator();
        Operacoes ops = new Operacoes(calc, historico);
        
        // LLM com sua chave
        LLMSimples llm = new LLMSimples(
            "https://modelos.ai.ulusofona.pt/v1/completions",
            "sk-L88zR6OTZ1_5PN_cdJY3ag",
            "gpt-4-turbo"
        );

        System.out.println("Calculadora");
        System.out.println("Comandos: hist | limpar | exit");
        System.out.println();

        while (true) {
            System.out.print("-> ");
            String input = in.nextLine().trim();

            if (input.isEmpty()) continue;

            if (input.equals("exit") || input.equals("sair") || input.equals("quit") || input.equals("byebye")) {
                System.out.println("Tchau!");
                break;
            }
            if (input.equals("hist")) {
                System.out.print(historico.toString());
                continue;
            }
            if (input.equals("limpar") || input.equals("clearhist")) {
                historico.limpar();
                System.out.println("Histórico limpo");
                continue;
            }

            // Tenta como expressão direta
            try {
                String[] parts = input.split("\\s+");
                if (parts.length >= 3) {
                    double n1, n2;
                    String op;
                    try {
                        n1 = Double.parseDouble(parts[0]);
                        op = parts[1];
                        n2 = Double.parseDouble(parts[2]);
                    } catch (NumberFormatException e) {
                        n1 = Double.parseDouble(parts[1]);
                        op = parts[0];
                        n2 = Double.parseDouble(parts[2]);
                    }
                    double resultado = Double.parseDouble(ops.executar(op, n1, n2));
                    System.out.println("= " + resultado);
                    continue;
                }
            } catch (Exception e) {
                // Não é expressão direta, vai tentar com LLM
            }

            // Processa com LLM
            LLMSimples.LLMResponse resposta = llm.processar(input);
            
            if (resposta.type == LLMSimples.LLMResponse.Type.EXPRESSAO) {
                try {
                    String[] parts = resposta.value.split("\\s+");
                    double n1 = Double.parseDouble(parts[0]);
                    String op = parts[1];
                    double n2 = Double.parseDouble(parts[2]);
                    double resultado = Double.parseDouble(ops.executar(op, n1, n2));
                    System.out.println("= " + resultado);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else if (resposta.type == LLMSimples.LLMResponse.Type.RESULTADO) {
                System.out.println("= " + resposta.value);
            } else {
                System.out.println("[Erro] " + resposta.value);
            }
        }

        in.close();
    }
}
