package br.com.unipatas.casamentopadrao;

import java.util.HashMap;
import java.util.Map;

public class BoyerMoore {

    private final Map<Character, Integer> tabelaBadChar;
    private final String padrao;

    // O Construtor recebe a palavra que estamos procurando e já monta a tabela de pulos
    public BoyerMoore(String padrao) {
        this.padrao = padrao == null ? "" : padrao;
        this.tabelaBadChar = new HashMap<>();

        for (int i = 0; i < this.padrao.length(); i++) {
            tabelaBadChar.put(this.padrao.charAt(i), i);
        }
    }

    // Método que faz a busca do padrão dentro de um texto
    // Retorna true se encontrou o padrão, ou false se não encontrou
    public boolean buscar(String texto) {
        if (texto == null || padrao.isEmpty() || padrao.length() > texto.length()) {
            return false;
        }

        int tamanhoPadrao = padrao.length();
        int tamanhoTexto = texto.length();

        int s = 0;

        while (s <= (tamanhoTexto - tamanhoPadrao)) {
            int j = tamanhoPadrao - 1;

            while (j >= 0 && padrao.charAt(j) == texto.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                return true;
            } else {
                int ultimaPosicao = tabelaBadChar.getOrDefault(texto.charAt(s + j), -1);
                s += Math.max(1, j - ultimaPosicao);
            }
        }

        return false;
    }
}
