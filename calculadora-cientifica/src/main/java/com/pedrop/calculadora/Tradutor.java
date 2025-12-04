package com.pedrop.calculadora;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Tradutor simples que tenta transformar frases em expressões "n1 oper n2".
// Não é uma LLM real — usa regras básicas para reconhecer números e palavras-chave.
public class Tradutor {

    /**
     * Tenta traduzir uma frase natural para uma expressão simples como "2 + 3".
     * Retorna null se não conseguir traduzir.
     */
    public String traduz(String frase) {
        if (frase == null) return null;
        String s = frase.toLowerCase();

        // Extrai até dois números (inteiros ou com ponto)
        Pattern numP = Pattern.compile("(-?\\d+(?:\\.\\d+)?)");
        Matcher m = numP.matcher(s);
        List<String> nums = new ArrayList<>();
        while (m.find() && nums.size() < 2) {
            nums.add(m.group(1));
        }
        if (nums.size() < 2) return null;

        // Detecta operador por palavras ou símbolos
        String op;
        if (s.contains("mais") || s.contains("soma") || s.contains("add") || s.contains("+")) {
            op = "+";
        } else if (s.contains("menos") || s.contains("sub") || s.contains("-") || s.contains("menos")) {
            op = "-";
        } else if (s.contains("vezes") || s.contains("x") || s.contains("mult") || s.contains("*")) {
            op = "*";
        } else if (s.contains("divid") || s.contains("/") || s.contains("div")) {
            op = "/";
        } else {
            return null;
        }

        return nums.get(0) + " " + op + " " + nums.get(1);
    }
}
