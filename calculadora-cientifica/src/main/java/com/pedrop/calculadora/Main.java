package com.pedrop.calculadora;

import java.util.Scanner;

/**
 * Classe principal da aplicação de calculadora simples.
 * <p>
 * Esta classe gere o input do utilizador, interpreta comandos e invoca os
 * métodos da classe {@link Calculator} para realizar operações matemáticas.
 */
public class Main {

    /**
     * Método principal onde a aplicação inicia.
     *
     * @param args argumentos da linha de comandos (não utilizados)
     */
    public static void main(String[] args) {

        /** Cria uma instância da calculadora que contém as operações básicas */
        Calculator calc = new Calculator();

        /** Scanner usado para ler entradas do utilizador */
        Scanner in = new Scanner(System.in);
        /** Historico simples das operacoes */
        Historico historico = new Historico();

        /** Mensagem inicial apresentada ao utilizador */
        System.out.println("Calculadora simples: + - / *  |  hist | limpar | exit para sair");

        // Instancia Operacoes (centraliza chamadas e historico)
        Operacoes ops = new Operacoes(calc, historico);
        // Mock LLM (simula interpretação mais avançada)
        LLM llm = new LLM();
        // Cliente que usa uma LLM real (OpenAI) se houver OPENAI_API_KEY
        LLMClient llmClient = new LLMClient();

        // Loop principal que continua até o utilizador digitar "exit"
        while (true) {

            System.out.print("-> ");

            // Lê a linha completa inserida pelo utilizador
            String line = in.nextLine();

            // Se for null, termina o programa
            if (line == null) break;

            // Remove espaços desnecessários
            line = line.trim();

            // Ignora linhas vazias
            if (line.equals("")) continue;

            // Verifica se o comando é "exit"
            if (line.equals("exit") || line.equals("sair")) {
                System.out.println("Tchau!");
                break;
            }

            // Comandos sem operandos: historico e limpar
            if (line.equals("hist")) {
                System.out.print(historico.toString());
                continue;
            }
            if (line.equals("clearhist") || line.equals("limpar")) {
                historico.limpar();
                System.out.println("Historico limpo");
                continue;
            }

            // Comando 'llm' usa a classe LLM (mock) para interpretar frases
            if (line.startsWith("llm ")) {
                String frase = line.substring(line.indexOf(' ') + 1);
                String expr = null;
                if (llmClient.isAvailable()) {
                    expr = llmClient.interpretar(frase);
                    if (expr != null) System.out.println("LLM(remote) => " + expr);
                }
                if (expr == null) {
                    expr = llm.interpretar(frase); // fallback para mock
                    if (expr != null) System.out.println("LLM(mock) => " + expr);
                }
                if (expr == null) {
                    System.out.println("LLM: nao consegui interpretar a frase");
                    continue;
                }
                line = expr;
            }

            // Comando simples para traduzir frase natural usando regras (simula Tradutor)
            // Uso: "trad <frase>" por exemplo: "trad 2 mais 3" ou "trad quanto é 4 vezes 5"
            if (line.startsWith("trad ") || line.startsWith("traduz ")) {
                String frase = line.substring(line.indexOf(' ') + 1);
                Tradutor trad = new Tradutor();
                String expr = trad.traduz(frase);
                if (expr == null) {
                    System.out.println("Nao consegui traduzir a frase");
                    continue;
                }
                System.out.println("=> " + expr);
                line = expr; // substituir pela expressão traduzida e continuar o processamento
            }

            // Divide a linha pelos espaços (ex.: "add 5 7")
            String[] parts = line.split("\\s+");

            // Se não tem 3 partes, tenta usar a LLM (remote ou mock) para interpretar
            if (parts.length < 3) {
                String expr = null;
                if (llmClient.isAvailable()) {
                    expr = llmClient.interpretar(line);
                    if (expr != null) System.out.println("LLM(remote) => " + expr);
                }
                if (expr == null) {
                    expr = llm.interpretar(line);
                    if (expr != null) System.out.println("LLM(mock) => " + expr);
                }
                if (expr == null) {
                    System.out.println("Use:  num1 <comando> num2  (ou escreva uma frase natural que a LLM pode interpretar)");
                    continue;
                }
                // substitui a linha pela expressão retornada e recalcula partes
                line = expr;
                parts = line.split("\\s+");
            }

            /** Primeiro token representa o comando desejado */
            String operador = parts[0];

            double n1, n2;

            // Suporta dois formatos de entrada:
            // 1) operando comando operando   (ex: "2 add 3")
            // 2) comando operando operando   (ex: "add 2 3")
            try {
                // tenta o formato padrão: comando n1 n2
                n1 = Double.parseDouble(parts[1]);
                n2 = Double.parseDouble(parts[2]);
                // operador já definido como parts[0]
            } catch (NumberFormatException e) {
                // se falhar, tenta formato com operador no meio
                try {
                    n1 = Double.parseDouble(parts[0]);
                    operador = parts[1];
                    n2 = Double.parseDouble(parts[2]);
                } catch (NumberFormatException ex) {
                    // Se falhar ao tentar o formato com operador no meio, tenta a LLM
                    String expr = null;
                    if (llmClient.isAvailable()) {
                        expr = llmClient.interpretar(line);
                        if (expr != null) System.out.println("LLM(remote) => " + expr);
                    }
                    if (expr == null) {
                        expr = llm.interpretar(line);
                        if (expr != null) System.out.println("LLM(mock) => " + expr);
                    }
                    if (expr == null) {
                        System.out.println("Entrada inválida, use números ou formato correto");
                        continue;
                    }
                    // substitui pela expressão retornada
                    line = expr;
                    parts = line.split("\\s+");
                    try {
                        n1 = Double.parseDouble(parts[0]);
                        operador = parts[1];
                        n2 = Double.parseDouble(parts[2]);
                    } catch (Exception ex2) {
                        System.out.println("Entrada inválida mesmo após LLM");
                        continue;
                    }
                }
            }

            // Usa Operacoes para executar e registar
            String saida = ops.executar(operador, n1, n2);
            // Se o utilizador inseriu no formato "2 add 3", queremos gravar e mostrar nesse formato
            // Normalizamos a saída para mostrar: "n1 oper n2 = resultado" ou mensagem de erro
            System.out.println(saida);
        }

        // Fecha o scanner
        in.close();
    }
}
