package br.com.unipatas.casamentopadrao;

import java.util.Arrays;

public class BoyerMoore {

    private int[] tabelaBadChar;
    private String padrao;

    // O Construtor recebe a palavra que estamos procurando e já monta a tabela de pulos
    public BoyerMoore(String padrao) {
        this.padrao = padrao; 
        this.tabelaBadChar = new int[256];
        
        Arrays.fill(tabelaBadChar, -1);
        
        for (int i = 0; i < padrao.length(); i++) {
            tabelaBadChar[(int) padrao.charAt(i)] = i;
        }
    }

    // Método que faz a busca do padrão dentro de um texto
    // Retorna true se encontrou o padrão, ou false se não encontrou
    public boolean buscar(String texto) {
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

                s += Math.max(1, j - tabelaBadChar[texto.charAt(s + j)]);
            }
        }
        
        return false;
    }
}