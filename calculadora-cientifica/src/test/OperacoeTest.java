package com.pedrop.calculadora;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de testes unitários para a classe {@link Operacoes}.
 * <p>
 * Esta classe testa as funcionalidades da classe Operacoes, incluindo operações matemáticas
 * básicas e tratamento de erros, utilizando instâncias de {@link Calculator} e {@link Historico}.
 * </p>
 *
 * @author Pedro Piedade
 * @version 1.0
 * @since 2025-12-17
 */
class OperacoesTest {

    /**
     * Testa a operação de adição utilizando o operador "+".
     * <p>
     * Verifica se a operação retorna o resultado correto e adiciona ao histórico.
     * </p>
     */
    @Test
    void testAdicaoComSoma() {
        Calculator calc = new Calculator();
        Historico historico = new Historico();
        Operacoes operacoes = new Operacoes(calc, historico);

        String resultado = operacoes.executar("+", 5.0, 3.0);
        assertEquals("8.0", resultado);
        assertEquals(1, historico.listar().size());
    }

    /**
     * Testa a operação de adição utilizando a palavra "add".
     * <p>
     * Verifica se a operação aceita sinônimos e funciona corretamente.
     * </p>
     */
    @Test
    void testAdicaoComAdd() {
        Calculator calc = new Calculator();
        Historico historico = new Historico();
        Operacoes operacoes = new Operacoes(calc, historico);

        String resultado = operacoes.executar("add", 5.0, 3.0);
        assertEquals("8.0", resultado);
        assertEquals(1, historico.listar().size());
    }

    /**
     * Testa a operação de subtração utilizando o operador "-".
     * <p>
     * Verifica o resultado da subtração e o registro no histórico.
     * </p>
     */
    @Test
    void testSubtracao() {
        Calculator calc = new Calculator();
        Historico historico = new Historico();
        Operacoes operacoes = new Operacoes(calc, historico);

        String resultado = operacoes.executar("-", 10.0, 4.0);
        assertEquals("6.0", resultado);
        assertEquals(1, historico.listar().size());
    }

    /**
     * Testa a operação de multiplicação utilizando o operador "*".
     * <p>
     * Verifica o resultado da multiplicação e o histórico.
     * </p>
     */
    @Test
    void testMultiplicacao() {
        Calculator calc = new Calculator();
        Historico historico = new Historico();
        Operacoes operacoes = new Operacoes(calc, historico);

        String resultado = operacoes.executar("*", 6.0, 7.0);
        assertEquals("42.0", resultado);
        assertEquals(1, historico.listar().size());
    }

    /**
     * Testa a operação de divisão utilizando o operador "/".
     * <p>
     * Verifica o resultado da divisão válida.
     * </p>
     */
    @Test
    void testDivisao() {
        Calculator calc = new Calculator();
        Historico historico = new Historico();
        Operacoes operacoes = new Operacoes(calc, historico);

        String resultado = operacoes.executar("/", 20.0, 4.0);
        assertEquals("5.0", resultado);
        assertEquals(1, historico.listar().size());
    }

    /**
     * Testa a operação de divisão por zero.
     * <p>
     * Verifica se retorna erro apropriado e registra no histórico.
     * </p>
     */
    @Test
    void testDivisaoPorZero() {
        Calculator calc = new Calculator();
        Historico historico = new Historico();
        Operacoes operacoes = new Operacoes(calc, historico);

        String resultado = operacoes.executar("/", 10.0, 0.0);
        assertEquals("Erro: Divisão por zero", resultado);
        assertEquals(1, historico.listar().size());
    }

    /**
     * Testa um operador inválido.
     * <p>
     * Verifica se retorna mensagem de erro e não adiciona ao histórico.
     * </p>
     */
    @Test
    void testOperadorInvalido() {
        Calculator calc = new Calculator();
        Historico historico = new Historico();
        Operacoes operacoes = new Operacoes(calc, historico);

        String resultado = operacoes.executar("invalid", 1.0, 2.0);
        assertEquals("Comando nao encontrado: invalid", resultado);
        assertEquals(0, historico.listar().size()); // Não deve adicionar ao histórico
    }
}
