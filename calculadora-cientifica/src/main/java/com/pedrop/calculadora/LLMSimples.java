package com.pedrop.calculadora;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe que centraliza toda a lógica de integração com a LLM customizada.
 * Traduz frases do utilizador em expressões matemáticas ou resolve operações complexas.
 * Usa fallback local para operações básicas se a LLM não estiver disponível.
 */
public class LLMSimples {
    private LLMInteractionEngine engine;

    public LLMSimples(String url, String apiKey, String model) {
        this.engine = new LLMInteractionEngine(url, apiKey, model, true);
    }

    /**
     * Processa entrada do utilizador.
     * Retorna um objeto com tipo (EXPRESSAO ou RESULTADO) e o valor processado.
     * 
     * Exemplos:
     * - "2 mais 3" → type=EXPRESSAO, value="2 + 3"
     * - "integral de x de 0 a 1" → type=RESULTADO, value="0.5"
     */
    public LLMResponse processar(String input) {
        if (input == null || input.isBlank()) {
            return new LLMResponse(LLMResponse.Type.ERRO, "Input vazio");
        }

        // Tenta usar a LLM real
        try {
            return processarComLLM(input);
        } catch (Exception e) {
            // Se a LLM real falhar, tenta regras locais simples
            System.out.println("[LLM] LLM real indisponível, usando regras locais...");
            return processarComFallback(input);
        }
    }

    /**
     * Processa com a LLM real (sua chave customizada).
     */
    private LLMResponse processarComLLM(String input) throws Exception {
        // Prompt que pede à LLM para traduzir ou resolver
      String prompt = 
    "Aja como um processador matemático. Analise a entrada do usuário e responda apenas com o resultado numérico ou a expressão matemática limpa, sem explicações ou formato especial.\n\n" +
    "Regras:\n" +
    "Se for aritmética básica (soma, subtração, multiplicação, divisão, potência), nao faca nada, deixa que o sistema resolva .\n" +
    "Se for cálculo avançado (integral, derivada, equações, fatorial, conversão) ou pergunta teórica, forneça o resultado final.\n\n" +
    "Exemplos:\n" +
    "Entrada: 'derivada de 2x' -> '2'\n" +
    "Entrada: 'resolva x + 2 = 0' -> 'x = -2'\n\n" +
    "Entrada: " + input;

        String jsonResponse = engine.sendPrompt(prompt);
        String resultado = JSONUtils.getJsonString(jsonResponse, "text");
        if (resultado == null) {
            resultado = JSONUtils.getJsonString(jsonResponse, "content");
        }

        if (resultado == null || resultado.isBlank()) {
            throw new RuntimeException("Resposta vazia da LLM");
        }
        

        resultado = resultado.trim();

        // Heurística: se parece uma expressão, é EXPRESSAO
        if (parecerExpressao(resultado)) {
            return new LLMResponse(LLMResponse.Type.EXPRESSAO, resultado);
        } else {
            // Senão, é RESULTADO de operação complexa
            return new LLMResponse(LLMResponse.Type.RESULTADO, resultado);
        }
    }

    /**
     * Fallback local simples: usa regras básicas quando LLM real não está disponível.
     */
    private LLMResponse processarComFallback(String input) {
        String s = input.toLowerCase().trim();

        // Operações complexas: se contém essas palavras, não conseguimos resolver localmente
        if (s.contains("integral") || s.contains("derivada") || s.contains("equação") ||
            s.contains("raiz") || s.contains("logaritmo") || s.contains("seno") ||
            s.contains("cosseno") || s.contains("tangente") || s.contains("limite")) {
            return new LLMResponse(LLMResponse.Type.ERRO, 
                "Operação complexa requer LLM. Chave ainda não disponível.");
        }

        // Operações simples: tenta extrair números e operadores
        Pattern p = Pattern.compile("(-?\\d+(?:\\.\\d+)?)\\s*(mais|menos|vezes|dividido|por|\\+|\\-|\\*|/)\\s*(-?\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            String n1 = m.group(1);
            String op = m.group(2);
            String n2 = m.group(3);

            String opSymbol;
            switch (op) {
                case "mais": case "+": opSymbol = "+"; break;
                case "menos": case "-": opSymbol = "-"; break;
                case "vezes": case "*": opSymbol = "*"; break;
                case "dividido": case "por": case "/": opSymbol = "/"; break;
                default: opSymbol = op;
            }

            String expr = n1 + " " + opSymbol + " " + n2;
            return new LLMResponse(LLMResponse.Type.EXPRESSAO, expr);
        }

        return new LLMResponse(LLMResponse.Type.ERRO, "Não consegui interpretar a entrada");
    }

    /**
     * Verifica se uma string parece uma expressão matemática simples.
     */
    private boolean parecerExpressao(String s) {
        return Pattern.compile("\\d").matcher(s).find() &&
               Pattern.compile("[+\\-*/]").matcher(s).find();
    }

    /**
     * Classe interna para encapsular a resposta da LLM.
     */
    public static class LLMResponse {
        public enum Type { EXPRESSAO, RESULTADO, ERRO }

        public final Type type;
        public final String value;

        public LLMResponse(Type type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return type + ": " + value;
        }
    }
}
