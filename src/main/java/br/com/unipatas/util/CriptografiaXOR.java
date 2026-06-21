package br.com.unipatas.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class CriptografiaXOR {

  private static final String CHAVE_PADRAO = "UniPatas#2025@Seguranca";

  private CriptografiaXOR() {
  }

  public static String criptografar(String textoOriginal) {
    return criptografar(textoOriginal, CHAVE_PADRAO);
  }

  public static String descriptografar(String textoCriptografado) {
    return descriptografar(textoCriptografado, CHAVE_PADRAO);
  }

  public static String criptografar(String textoOriginal, String chave) {
    if (textoOriginal == null) {
      return null;
    }

    byte[] dados = textoOriginal.getBytes(StandardCharsets.UTF_8);
    byte[] resultado = aplicarXOR(dados, chave);

    return Base64.getEncoder().encodeToString(resultado);
  }

  public static String descriptografar(String textoCriptografado, String chave) {
    if (textoCriptografado == null) {
      return null;
    }

    byte[] dados = Base64.getDecoder().decode(textoCriptografado);
    byte[] resultado = aplicarXOR(dados, chave);

    return new String(resultado, StandardCharsets.UTF_8);
  }

  private static byte[] aplicarXOR(byte[] dados, String chave) {
    byte[] chaveBytes = chave.getBytes(StandardCharsets.UTF_8);
    byte[] resultado = new byte[dados.length];

    for (int i = 0; i < dados.length; i++) {
      resultado[i] = (byte) (dados[i] ^ chaveBytes[i % chaveBytes.length]);
    }

    return resultado;
  }
}
