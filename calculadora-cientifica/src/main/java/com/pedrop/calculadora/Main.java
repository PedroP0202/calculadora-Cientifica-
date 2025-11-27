package com.pedrop.calculadora;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        Scanner in = new Scanner(System.in);
        System.out.println("Calculadora simples: add sub mul div  |  exit para sair");
        
        while (true) {
            System.out.print("-> ");
            String line = in.nextLine();
            if (line == null) break;
            line = line.trim();
            if (line.equals("")) continue;
            if (line.equals("exit")) {
                System.out.println("Tchau!");
                break;
            }

            String[] parts = line.split("\\s+");
            if (parts.length < 3) {
                System.out.println("Use: <comando> num1 num2");
                continue;
            }

            String operador = parts[0];
            double n1, n2;
            try {
                n1 = Double.parseDouble(parts[1]);
                n2 = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, use números");
                continue;
            }

            double res = 0;
            if (operador.equals("add")) {
                res = calc.add(n1, n2);
            } else if (operador.equals("sub")) {
                res = calc.subtract(n1, n2);
            } else if (operador.equals("mul")) {
                res = calc.multiply(n1, n2);
            } else if (operador.equals("div")) {
                try {
                    res = calc.divide(n1, n2);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Erro: " + ex.getMessage());
                    continue;
                }
            } else {
                System.out.println("Comando nao encontrado: " + operador);
                continue;
            }
            System.out.println(res);
        }
        in.close();
    }
}