package com.pedrop.calculadora;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Classe simples de histórico: guarda strings com timestamp.
// Estilo amador: nomes em portugues e implementação direta.
public class Historico {
    private final List<String> itens = new ArrayList<>();

    // Adiciona uma operação ao histórico (ex: "add 2 3 = 5")
    public void adicionar(String texto) {
        String linha = LocalDateTime.now() + " - " + texto;
        itens.add(linha);
    }

    // Retorna cópia do histórico
    public List<String> listar() {
        return new ArrayList<>(itens);
    }

    // Limpa o histórico
    public void limpar() {
        itens.clear();
    }

    // Representação textual simples
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : itens) {
            sb.append(s).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
