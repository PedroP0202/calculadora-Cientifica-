package com.pedrop.calculadora;

/**
 * A classe {@code Calculator} fornece métodos para realizar operações matemáticas básicas
 * como adição, subtração, multiplicação e divisão.
 * <p>
 * Esta classe é o núcleo da lógica de negócio para cálculos na aplicação.
 * </p>
 *
 * @author Pedro Piedede.
 * @version 1.0
 * @since 2023-11-29
 */
/**
 * Classe que implementa as operações matemáticas básicas da calculadora.
 */
public class Calculator {

    /**
     * Realiza a operação de adição entre dois números.
     *
     * @param a O primeiro valor (parcela).
     * @param b O segundo valor (parcela).
     * @return O resultado da soma: $a + b$.
     */
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * Realiza a operação de subtração, subtraindo o segundo valor do primeiro.
     *
     * @param a O primeiro valor (minuendo).
     * @param b O segundo valor (subtraendo).
     * @return O resultado da subtração: $a - b$.
     */
    public double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Realiza a operação de multiplicação entre dois números.
     *
     * @param a O primeiro valor (fator).
     * @param b O segundo valor (fator).
     * @return O resultado da multiplicação: $a \times b$.
     */
    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Realiza a operação de divisão do primeiro número pelo segundo.
     *
     * @param a O numerador (dividendo).
     * @param b O denominador (divisor).
     * @return O resultado da divisão: $a / b$.
     * @throws IllegalArgumentException Se o divisor $b$ for igual a zero.
     */
    public double divide(double a, double b) {

        // Verifica se o divisor é zero, o que causaria erro
        if (b == 0)
            throw new IllegalArgumentException("Divisão por zero");

        // Se estiver tudo ok, realiza a divisão
        return a / b;
    }
}