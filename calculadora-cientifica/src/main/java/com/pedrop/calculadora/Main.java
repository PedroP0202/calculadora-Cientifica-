package com.pedrop.calculadora;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Cria uma instância da calculadora (onde estão as operações)
        Calculator calc = new Calculator();

        // Scanner para ler inputs do utilizador
        Scanner in = new Scanner(System.in);

        // Mensagem inicial
        System.out.println("Calculadora simples: add sub mul div  |  exit para sair");
        
        // Loop infinito até o utilizador escrever "exit"
        while (true) {

            System.out.print("-> ");

            // Lê a linha escrita pelo utilizador
            String line = in.nextLine();

            // Se for null, sai do programa
            if (line == null) break;

            // Remove espaços antes/depois
            line = line.trim();

            // Ignora linhas vazias
            if (line.equals("")) continue;

            // Caso o utilizador escreva "exit", termina o programa
            if (line.equals("exit")) {
                System.out.println("Tchau!");
                break;
            }

            // Divide a linha por espaços -> ['add', '5', '7'], por exemplo
            String[] parts = line.split("\\s+");

            // Necessário no mínimo comando + 2 números
            if (parts.length < 3) {
                System.out.println("Use: <comando> num1 num2");
                continue;
            }

            // Primeiro elemento é o comando (add/sub/mul/div)
            String operador = parts[0];

            double n1, n2;

            // Tenta converter as strings para números
            try {
                n1 = Double.parseDouble(parts[1]);
                n2 = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, use números");
                continue;
            }

            // Variável para guardar o resultado
            double res = 0;

            // Verifica que operação foi pedida
            if (operador.equals("add")) {
                res = calc.add(n1, n2);

            } else if (operador.equals("sub")) {
                res = calc.subtract(n1, n2);

            } else if (operador.equals("mul")) {
                res = calc.multiply(n1, n2);

            } else if (operador.equals("div")) {

                // Divisão pode causar erro (como divisão por zero)
                try {
                    res = calc.divide(n1, n2);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Erro: " + ex.getMessage());
                    continue;
                }

            } else {
                // Comando inválido
                System.out.println("Comando nao encontrado: " + operador);
                continue;
            }

            // Imprime o resultado da operação
            System.out.println(res);
        }

        // Fecha o scanner
        in.close();
    }
}
