package com.pedrop.calculadora;

public class TestLLM {
    public static void main(String[] args) {
        LLMSimples llm = new LLMSimples(
            "https://modelos.ai.ulusofona.pt/v1/completions",
            "sk-L88zR6OTZ1_5PN_cdJY3ag",
            "gpt-4-turbo"
        );

        String[] tests = {"2 mais 3", "qual é 2 + 3", "integral de x de 0 a 1"};

        for (String test : tests) {
            System.out.println("Input: " + test);
            LLMSimples.LLMResponse resp = llm.processar(test);
            System.out.println("Response: " + resp);
            System.out.println();
        }
    }
}