package br.com.unipatas.casamentopadrao;

public class KMP {

    private String padrao;
    private int[] tabelaFalha; // também chamada de "tabela de prefixos"

    // O construtor recebe o padrão e já monta a tabela de falhas (LPS)
    public KMP(String padrao) {
        this.padrao = padrao;
        this.tabelaFalha = construirTabelaFalha(padrao);
    }

    // Constrói a tabela de prefixos próprios que também são sufixos (LPS)
    private int[] construirTabelaFalha(String padrao) {
        int m = padrao.length();
        int[] lps = new int[m];
        int tamanho = 0; // tamanho do maior prefixo-sufixo encontrado até agora
        int i = 1;

        lps[0] = 0; // o primeiro caractere nunca tem prefixo-sufixo

        while (i < m) {
            if (padrao.charAt(i) == padrao.charAt(tamanho)) {
                tamanho++;
                lps[i] = tamanho;
                i++;
            } else {
                if (tamanho != 0) {
                    // não avança i, tenta um prefixo menor
                    tamanho = lps[tamanho - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // Método que faz a busca do padrão dentro de um texto
    // Retorna true se encontrou o padrão, ou false se não encontrou
    public boolean buscar(String texto) {
        int n = texto.length();
        int m = padrao.length();

        if (m == 0) return false;

        int i = 0; // índice no texto
        int j = 0; // índice no padrão

        while (i < n) {
            if (texto.charAt(i) == padrao.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    return true; // padrão encontrado
                }
            } else {
                if (j != 0) {
                    // usa a tabela de falhas para não recomeçar do zero
                    j = tabelaFalha[j - 1];
                } else {
                    i++;
                }
            }
        }

        return false;
    }
}