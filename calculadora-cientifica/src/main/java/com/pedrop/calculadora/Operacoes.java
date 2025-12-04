package com.pedrop.calculadora;

// Classe simples que encapsula as operacoes disponiveis.
// Faz a chamada à Calculator e regista no Historico.
public class Operacoes {
    private final Calculator calc;
    private final Historico historico;

    public Operacoes(Calculator calc, Historico historico) {
        this.calc = calc;
        this.historico = historico;
    }

    // Executa a operação e retorna a string que deve ser mostrada ao utilizador.
    // Aceita operadores em texto ou símbolos (+ - * /). Grava no historico no formato: "n1 oper n2 = resultado".
    public String executar(String oper, double a, double b) {
        double res;
        String simbolo;

        // Normaliza o operador: aceita palavras e símbolos
        if (oper.equals("+") || oper.equalsIgnoreCase("add")) {
            res = calc.add(a, b);
            simbolo = "+";
        } else if (oper.equals("-") || oper.equalsIgnoreCase("sub")) {
            res = calc.subtract(a, b);
            simbolo = "-";
        } else if (oper.equals("*") || oper.equalsIgnoreCase("mul") || oper.equalsIgnoreCase("x")) {
            res = calc.multiply(a, b);
            simbolo = "*";
        } else if (oper.equals("/") || oper.equalsIgnoreCase("div")) {
            try {
                res = calc.divide(a, b);
                simbolo = "/";
            } catch (IllegalArgumentException ex) {
                String linha = a + " " + "/" + " " + b + " = Erro: " + ex.getMessage();
                historico.adicionar(linha);
                return "Erro: " + ex.getMessage();
            }
        } else {
            return "Comando nao encontrado: " + oper;
        }

        String registro = a + " " + simbolo + " " + b + " = " + res;
        historico.adicionar(registro);
        return String.valueOf(res);
    }
}
