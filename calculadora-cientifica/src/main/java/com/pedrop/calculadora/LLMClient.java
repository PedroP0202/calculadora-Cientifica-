package com.pedrop.calculadora;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Cliente simples que usa `curl` para chamar a API OpenAI Chat Completions.
 *
 * Como é perigoso colocar chaves no código, este cliente lê a chave da
 * variável de ambiente `OPENAI_API_KEY`. Se não existir, o cliente é
 * considerado indisponível e o programa deve usar o mock `LLM`.
 *
 * Nota: Esta é uma integração simples e minimalista para fins educativos.
 */
public class LLMClient {
    private final String apiKey;
    private final String model;

    public LLMClient() {
        this.apiKey = System.getenv("OPENAI_API_KEY");
        String m = System.getenv("OPENAI_MODEL");
        this.model = (m == null || m.isBlank()) ? "gpt-4o-mini" : m;
    }

    public boolean isAvailable() {
        return apiKey != null && !apiKey.isBlank();
    }

    /**
     * Interpreta a frase usando a LLM remota. Retorna a string da LLM
     * (ex: "2 + 3") ou null em caso de falha.
     */
    public String interpretar(String frase) {
        if (!isAvailable()) return null;
        try {
            // Monta o JSON do corpo (simples, sem bibliotecas externas)
            String sys = "You are a small translator: convert user phrases to a simple math expression like '2 + 3' or '4 * 5'. Reply only the expression.'";
            String json = "{\"model\":\"" + model + "\",\"messages\":[{\"role\":\"system\",\"content\":\"" + escapeJson(sys) + "\"},{\"role\":\"user\",\"content\":\"" + escapeJson(frase) + "\"}],\"max_tokens\":60,\"temperature\":0}";

            List<String> cmd = new ArrayList<>();
            cmd.add("curl");
            cmd.add("-s");
            cmd.add("-X");
            cmd.add("POST");
            cmd.add("https://api.openai.com/v1/chat/completions");
            cmd.add("-H");
            cmd.add("Authorization: Bearer " + apiKey);
            cmd.add("-H");
            cmd.add("Content-Type: application/json");
            cmd.add("-d");
            cmd.add(json);

            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) out.append(line).append('\n');
            p.waitFor();

            String resp = out.toString();
            if (resp.isBlank()) return null;

            // Extrai o content simples: choices[0].message.content
            String marker = "\"content\":\"";
            int idx = resp.indexOf(marker);
            if (idx < 0) return null;
            int start = idx + marker.length();
            int end = resp.indexOf('\"', start);
            if (end < 0) end = resp.length();
            String content = resp.substring(start, end);
            // Des-escapa caracteres básicos
            content = content.replaceAll("\\\\n", "\\n").replaceAll("\\\\\"", "\"");
            return content.trim();

        } catch (Exception e) {
            return null;
        }
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
