package br.com.unipatas.compressao;

import java.io.*;
import java.nio.file.Files;

public class GerenciadorBackup {

    private LZW lzw;

    public GerenciadorBackup() {
        this.lzw = new LZW();
    }

    // Método para empacotar e comprimir
    public void gerarBackup() throws Exception {
        File pastaData = new File("data");
        if (!pastaData.exists() || !pastaData.isDirectory()) {
            throw new Exception("Pasta de dados não encontrada!");
        }

        File[] arquivos = pastaData.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            throw new Exception("Nenhum arquivo para fazer backup na pasta data.");
        }

        // Junta todos os arquivos em um único "pacotão" de bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        // Anota quantos arquivos estamos guardando
        dos.writeInt(arquivos.length);

        for (File arq : arquivos) {
            if (arq.isFile()) {
                byte[] conteudo = Files.readAllBytes(arq.toPath());
                
                // Salva o nome, o tamanho e o conteúdo de cada arquivo
                dos.writeUTF(arq.getName());
                dos.writeInt(conteudo.length);
                dos.write(conteudo);
            }
        }
        dos.flush();

        byte[] dadosAgrupados = baos.toByteArray();

        byte[] dadosComprimidos = lzw.comprimir(dadosAgrupados);

        // Salva o resultado em uma pasta de backup
        File pastaBackup = new File("backup");
        if (!pastaBackup.exists()) {
            pastaBackup.mkdir();
        }

        FileOutputStream fos = new FileOutputStream("backup/unipatas_backup.lzw");
        fos.write(dadosComprimidos);
        fos.close();

        System.out.println("--- RELATÓRIO LZW ---");
        System.out.println("Tamanho Original: " + dadosAgrupados.length + " bytes");
        System.out.println("Tamanho Comprimido: " + dadosComprimidos.length + " bytes");
        System.out.println("Backup gerado em: backup/unipatas_backup.lzw");
    }

    // Método para descomprimir e desempacotar (Restaurar)
    public void restaurarBackup() throws Exception {
        File arquivoBackup = new File("backup/unipatas_backup.lzw");
        if (!arquivoBackup.exists()) {
            throw new Exception("Arquivo de backup não encontrado!");
        }

        // Lê o arquivo comprimido e passa no motor LZW para descomprimir
        byte[] dadosComprimidos = Files.readAllBytes(arquivoBackup.toPath());
        byte[] dadosDescomprimidos = lzw.descomprimir(dadosComprimidos);

        // Desempacota o resultado recriando os arquivos originais
        ByteArrayInputStream bais = new ByteArrayInputStream(dadosDescomprimidos);
        DataInputStream dis = new DataInputStream(bais);

        int qtdArquivos = dis.readInt();

        for (int i = 0; i < qtdArquivos; i++) {
            String nomeArquivo = dis.readUTF();
            int tamanho = dis.readInt();
            byte[] conteudo = new byte[tamanho];
            dis.readFully(conteudo);

            // Grava de volta na pasta data
            FileOutputStream fos = new FileOutputStream("data/" + nomeArquivo);
            fos.write(conteudo);
            fos.close();
        }

        System.out.println("Backup restaurado com sucesso na pasta data!");
    }
}
