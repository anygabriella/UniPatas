package br.com.unipatas.compressao;

import java.io.*;
import java.util.*;

public class LZW {

    // tamanho máximo de 16 bits para os códigos
    private static final int TAMANHO_MAX_DICIONARIO = 65536;

    public byte[] comprimir(byte[] dados) throws IOException {
        if (dados == null || dados.length == 0) return new byte[0];

        // dicionário de compressão
        Map<String, Integer> dicionario = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dicionario.put(String.valueOf((char) i), i);
        }

        int proximoCodigo = 256;
        String w = "";
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        for (byte b : dados) {
            // Converte o byte para char
            char c = (char) (b & 0xFF);
            String wc = w + c;

            if (dicionario.containsKey(wc)) {
                w = wc;
            } else {
                out.writeShort(dicionario.get(w));
                if (proximoCodigo < TAMANHO_MAX_DICIONARIO) {
                    dicionario.put(wc, proximoCodigo++);
                }
                w = String.valueOf(c);
            }
        }

        // Escreve o código restante
        if (!w.isEmpty()) {
            out.writeShort(dicionario.get(w));
        }

        out.close();
        return baos.toByteArray();
    }

    public byte[] descomprimir(byte[] dadosComprimidos) throws IOException {
        if (dadosComprimidos == null || dadosComprimidos.length == 0) return new byte[0];

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(dadosComprimidos));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // O Dicionário de descompressão faz o oposto: mapeia Código (Integer) para Sequência (String)
        Map<Integer, String> dicionario = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dicionario.put(i, String.valueOf((char) i));
        }

        int proximoCodigo = 256;

        // Lê o primeiro código
        int codigoAnterior = in.readShort() & 0xFFFF; 
        String w = dicionario.get(codigoAnterior);
        
        // Escreve a tradução do primeiro código
        for (char c : w.toCharArray()) {
            out.write((byte) c);
        }

        // Continua a ler de 2 em 2 bytes até ao fim do ficheiro
        while (in.available() > 0) {
            int codigoAtual = in.readShort() & 0xFFFF;
            String entrada;

            if (dicionario.containsKey(codigoAtual)) {
                entrada = dicionario.get(codigoAtual);
            } else if (codigoAtual == proximoCodigo) {
                entrada = w + w.charAt(0);
            } else {
                throw new IOException("Ficheiro comprimido corrompido ou formato LZW inválido.");
            }

            // Escreve a tradução do código atual
            for (char c : entrada.toCharArray()) {
                out.write((byte) c);
            }

            // Adiciona a nova sequência ao dicionário
            if (proximoCodigo < TAMANHO_MAX_DICIONARIO) {
                dicionario.put(proximoCodigo++, w + entrada.charAt(0));
            }

            w = entrada;
        }

        out.close();
        return out.toByteArray();
    }
}