package com.pedrop.calculadora;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * Classe responsável por enviar prompts para a LLM customizada via HTTP.
 * Permite configuração de SSL inseguro para endpoints de teste.
 */
public class LLMInteractionEngine {
    private final String apiUrl;
    private final String apiKey;
    private final String model;
    private final boolean useHack;

    public LLMInteractionEngine(String apiUrl, String apiKey, String model, boolean useHack) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.model = model;
        this.useHack = useHack;
    }
    public String sendPrompt(String prompt) throws Exception {
        String body = "{\"model\":\"" + model + "\",\"prompt\":\"" + escapeJson(prompt) + "\",\"max_tokens\":200,\"temperature\":0}";

        HttpClient client;
        if (useHack) {
            SSLContext ssl = createInsecureSSLContext();
            client = HttpClient.newBuilder().sslContext(ssl).build();
        } else {
            client = HttpClient.newHttpClient();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int status = response.statusCode();
        if (status < 200 || status >= 300) {
            throw new IOException("Server returned HTTP response code: " + status + " for URL: " + apiUrl + " -> " + response.body());
        }

        return response.body();
    }

    private static SSLContext createInsecureSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAll = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        }};

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAll, new SecureRandom());
        return sc;
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
