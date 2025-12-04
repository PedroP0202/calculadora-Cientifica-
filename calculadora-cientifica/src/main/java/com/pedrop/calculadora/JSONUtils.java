package com.pedrop.calculadora;

/**
 * Utilitário simples para extrair valores de campos de uma string JSON.
 */
public class JSONUtils {
    // Extrai o valor de uma chave simples (ex: "text") de um JSON plano (sem arrays aninhados)
    public static String getJsonString(String json, String key) {
        if (json == null || key == null) return null;
        String marker = "\"" + key + "\":";
        int idx = json.indexOf(marker);
        if (idx < 0) return null;
        int start = json.indexOf('"', idx + marker.length());
        if (start < 0) return null;
        int end = json.indexOf('"', start + 1);
        if (end < 0) return null;
        return json.substring(start + 1, end);
    }
}
