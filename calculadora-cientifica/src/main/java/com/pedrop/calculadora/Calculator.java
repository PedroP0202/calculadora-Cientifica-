package com.pedrop.calculadora;

public class Calculator {

    // Método para somar dois valores
    public double add(double a, double b) { 
        return a + b; 
    }

    // Método para subtrair o segundo valor do primeiro
    public double subtract(double a, double b) { 
        return a - b; 
    }

    // Método para multiplicar dois valores
    public double multiply(double a, double b) { 
        return a * b; 
    }

    // Método para dividir dois valores
    public double divide(double a, double b) {

        // Verifica se o divisor é zero, o que causaria erro
        if (b == 0) 
            throw new IllegalArgumentException("Divisão por zero");

        // Se estiver tudo ok, realiza a divisão
        return a / b;
    }
}
