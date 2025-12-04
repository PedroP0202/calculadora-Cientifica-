package com.pedrop.calculadora;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Mock LLM: não chama serviços externos, apenas simula comportamento
// usando regras e delegando ao Tradutor quando apropriado.
public class LLM {

    /**
     * Interpreta uma frase do utilizador e tenta devolver uma expressão simples
     * no formato: "n1 op n2" (ex: "2 + 3"). Retorna null se não conseguir.
     */
    public String interpretar(String frase) {
        if (frase == null) return null;
        String s = frase.toLowerCase().trim();

        // delegado simples: tenta o Tradutor primeiro
        Tradutor trad = new Tradutor();
        String r = trad.traduz(s);
        if (r != null) return r;

        // Tentativa por palavras em inglês (caso o utilizador escreva em inglês)
        // Ex.: "what is 2 plus 3" -> 2 + 3
        Pattern p = Pattern.compile("(-?\\d+(?:\\.\\d+)?)\\s*(plus|minus|times|multiplied by|divided by|over)\\s*(-?\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            String a = m.group(1);
            String opWord = m.group(2);
            String b = m.group(3);
            String op;
            switch (opWord) {
                case "plus": op = "+"; break;
                case "minus": op = "-"; break;
                case "times": case "multiplied by": op = "*"; break;
                case "divided by": case "over": op = "/"; break;
                default: return null;
            }
            return a + " " + op + " " + b;
        }

        // Outra heurística: procurar duas ocorrências numéricas e uma palavra chave entre elas
        Pattern twoNums = Pattern.compile("(-?\\d+(?:\\.\\d+)?)");
        Matcher m2 = twoNums.matcher(s);
        String first = null, second = null;
        while (m2.find()) {
            if (first == null) first = m2.group(1);
            else if (second == null) { second = m2.group(1); break; }
        }
        if (first != null && second != null) {
            // tenta identificar operador por palavras chave
            if (s.contains("add") || s.contains("plus") || s.contains("mais") ) return first + " + " + second;
            if (s.contains("sub") || s.contains("minus") || s.contains("menos")) return first + " - " + second;
            if (s.contains("mul") || s.contains("times") || s.contains("vezes") || s.contains("x")) return first + " * " + second;
            if (s.contains("div") || s.contains("over") || s.contains("divid")) return first + " / " + second;
        }

        return null;
    }
}
