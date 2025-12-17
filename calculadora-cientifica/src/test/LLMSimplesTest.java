package com.pedrop.calculadora;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de testes unitários para a classe {@link LLMSimples}.
 * <p>
 * Esta classe utiliza JUnit 5 para validar o funcionamento dos métodos
 * de cálculo matemático, como derivadas e raízes quadradas, fornecidos
 * pela classe {@code LLMSimples}.
 * </p>
 */
public class LLMSimplesTest {

    /**
     * Instância da classe {@link LLMSimples} utilizada nos testes.
     */
    private LLMSimples llm;

    /**
     * Método executado antes de cada teste.
     * <p>
     * Inicializa a instância de {@code LLMSimples} com os parâmetros
     * necessários para comunicação com a API de modelos.
     * </p>
     */
    @BeforeEach
    void setup() {
        llm = new LLMSimples(
            "https://modelos.ai.ulusofona.pt/v1/completions",
            "sk-TDYYkqfy9CTTEMzW9KjmKg",
            "gpt-4-turbo"
        );
    }

    /**
     * Testa se o método {@link LLMSimples#derivar(String)} devolve corretamente
     * a derivada da expressão matemática "2x".
     * <p>
     * A derivada esperada de 2x é 2.
     * </p>
     */
    void deveRetornarADerivaDe2x() {

        int resultado = llm.derivar("2x");

        assertEquals(2, resultado, "A derivada de 2x deve ser 2");
    }

    /**
     * Testa se o método {@link LLMSimples#raiz(String)} devolve corretamente
     * a raiz quadrada da expressão matemática "4".
     * <p>
     * A raiz quadrada esperada de 4 é 2.
     * </p>
     */
    @Test
    void deveRetornarARaizQuadradaDe4() {

        int resultado = llm.raiz("4");

        assertEquals(2, resultado, "A raiz de 4 deve ser 2");
    }
}
