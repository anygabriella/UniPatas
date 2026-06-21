package br.com.unipatas.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Classe utilitária responsável por aplicar criptografia simétrica do tipo
 * XOR em campos considerados sensíveis (neste sistema, a senha do usuário).
 *
 * Como o XOR é uma operação reversível (A XOR B XOR B = A), o mesmo
 * algoritmo é usado tanto para criptografar quanto para descriptografar,
 * bastando aplicar a operação novamente com a mesma chave.
 *
 * Fluxo:
 *  1) O texto original é convertido para bytes (UTF-8).
 *  2) Cada byte do texto é combinado, via XOR, com um byte da chave
 *     (a chave se repete ciclicamente caso seja menor que o texto).
 *  3) O resultado (que pode conter bytes não imprimíveis) é convertido
 *     para Base64, garantindo que possa ser gravado/lido com segurança
 *     pelo DataOutputStream/DataInputStream (writeUTF/readUTF) usados
 *     na persistência em arquivo binário (Usuario.toBytes/fromBytes).
 */
public final class CriptografiaXOR {

    // Chave utilizada na operação XOR. Em um cenário real, isso viria de
    // uma configuração externa/segura, mas para fins acadêmicos ela é
    // mantida fixa aqui.
    private static final String CHAVE_PADRAO = "UniPatas#2025@Seguranca";

    private CriptografiaXOR() {
        // classe utilitária: não deve ser instanciada
    }

    /** Criptografa um texto usando a chave padrão do sistema. */
    public static String criptografar(String textoOriginal) {
        return criptografar(textoOriginal, CHAVE_PADRAO);
    }

    /** Descriptografa um texto usando a chave padrão do sistema. */
    public static String descriptografar(String textoCriptografado) {
        return descriptografar(textoCriptografado, CHAVE_PADRAO);
    }

    /**
     * Aplica XOR byte a byte entre o texto e a chave e devolve o
     * resultado codificado em Base64 (texto seguro para armazenamento).
     */
    public static String criptografar(String textoOriginal, String chave) {
        if (textoOriginal == null) {
            return null;
        }

        byte[] dados = textoOriginal.getBytes(StandardCharsets.UTF_8);
        byte[] resultado = aplicarXOR(dados, chave);

        return Base64.getEncoder().encodeToString(resultado);
    }

    /**
     * Decodifica o Base64 e aplica XOR novamente com a mesma chave,
     * recuperando o texto original.
     */
    public static String descriptografar(String textoCriptografado, String chave) {
        if (textoCriptografado == null) {
            return null;
        }

        byte[] dados = Base64.getDecoder().decode(textoCriptografado);
        byte[] resultado = aplicarXOR(dados, chave);

        return new String(resultado, StandardCharsets.UTF_8);
    }

    /** Núcleo do algoritmo: XOR de cada byte do dado com um byte da chave (cíclico). */
    private static byte[] aplicarXOR(byte[] dados, String chave) {
        byte[] chaveBytes = chave.getBytes(StandardCharsets.UTF_8);
        byte[] resultado = new byte[dados.length];

        for (int i = 0; i < dados.length; i++) {
            resultado[i] = (byte) (dados[i] ^ chaveBytes[i % chaveBytes.length]);
        }

        return resultado;
    }
}
