package br.com.unipatas.compressao;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class GerenciadorBackupHuffman {

    // Gera backup comprimindo com Huffman
    public void gerarBackup() throws Exception {
        File pastaData = new File("data");
        if (!pastaData.exists() || !pastaData.isDirectory())
            throw new Exception("Pasta de dados não encontrada!");

        File[] arquivos = pastaData.listFiles();
        if (arquivos == null || arquivos.length == 0)
            throw new Exception("Nenhum arquivo para backup.");

        // Empacota todos os arquivos da pasta data em um único bloco de bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(arquivos.length);
        for (File arq : arquivos) {
            if (arq.isFile()) {
                byte[] conteudo = Files.readAllBytes(arq.toPath());
                dos.writeUTF(arq.getName());
                dos.writeInt(conteudo.length);
                dos.write(conteudo);
            }
        }
        dos.flush();

        byte[] dadosAgrupados = baos.toByteArray();

        // Gera a tabela de códigos Huffman e comprime
        HashMap<Byte, String> codigos = Huffman.codifica(dadosAgrupados);
        byte[] dadosComprimidos = Huffman.codificar(dadosAgrupados, codigos);

        // Precisa salvar a tabela de códigos junto com os dados
        // pois sem ela não é possível descomprimir depois
        File pastaBackup = new File("backup");
        if (!pastaBackup.exists()) pastaBackup.mkdir();

        DataOutputStream out = new DataOutputStream(
            new FileOutputStream("backup/unipatas_backup.huff")
        );

        // Salva o tamanho da tabela e cada entrada (byte -> codigo)
        out.writeInt(codigos.size());
        for (java.util.Map.Entry<Byte, String> entry : codigos.entrySet()) {
            out.writeByte(entry.getKey());
            out.writeUTF(entry.getValue());
        }

        // Salva os dados comprimidos
        out.writeInt(dadosComprimidos.length);
        out.write(dadosComprimidos);
        out.close();

        System.out.println("--- RELATÓRIO HUFFMAN ---");
        System.out.println("Tamanho original:   " + dadosAgrupados.length + " bytes");
        System.out.println("Tamanho comprimido: " + dadosComprimidos.length + " bytes");
        System.out.println("Backup gerado em:   backup/unipatas_backup.huff");
    }

    // Restaura o backup descomprimindo com Huffman
    public void restaurarBackup() throws Exception {
        File arquivoBackup = new File("backup/unipatas_backup.huff");
        if (!arquivoBackup.exists())
            throw new Exception("Arquivo de backup não encontrado!");

        DataInputStream in = new DataInputStream(
            new FileInputStream(arquivoBackup)
        );

        // Lê a tabela de códigos que foi salva junto
        int tamanhoTabela = in.readInt();
        HashMap<Byte, String> codigos = new HashMap<>();
        for (int i = 0; i < tamanhoTabela; i++) {
            byte b = in.readByte();
            String codigo = in.readUTF();
            codigos.put(b, codigo);
        }

        // Lê e descomprime os dados
        int tamanho = in.readInt();
        byte[] dadosComprimidos = new byte[tamanho];
        in.readFully(dadosComprimidos);
        in.close();

        byte[] dadosDescomprimidos = Huffman.decodificar(dadosComprimidos, codigos);

        // Desempacota os arquivos de volta na pasta data
        DataInputStream dis = new DataInputStream(
            new ByteArrayInputStream(dadosDescomprimidos)
        );

        int qtdArquivos = dis.readInt();
        for (int i = 0; i < qtdArquivos; i++) {
            String nome = dis.readUTF();
            int tam = dis.readInt();
            byte[] conteudo = new byte[tam];
            dis.readFully(conteudo);

            FileOutputStream fos = new FileOutputStream("data/" + nome);
            fos.write(conteudo);
            fos.close();
        }

        System.out.println("Backup Huffman restaurado com sucesso na pasta data!");
    }
}
