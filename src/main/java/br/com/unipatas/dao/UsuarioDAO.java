package br.com.unipatas.dao;

import java.io.*;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.com.unipatas.model.Usuario;

public class UsuarioDAO {
    private RandomAccessFile arq;
    private UsuarioIndiceDAO indiceDAO;

    public UsuarioDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        arq = new RandomAccessFile("data/Usuario.db", "rw");

        // Cabeçalho: último ID gerado
        if (arq.length() == 0) {
            arq.writeInt(0);
        }

        indiceDAO = new UsuarioIndiceDAO();

    }

    // CREATE
    public int create(Usuario i) throws Exception {
        arq.seek(0); // cabeçalho
        int ultimoId = arq.readInt();
        ultimoId++; // le o ultimo id usado e atualiza para ser o novo
        arq.seek(0);
        arq.writeInt(ultimoId); // escreve o novo (e ultimo) id

        i.setId(ultimoId);

        arq.seek(arq.length()); // vai para a ultima posição

        byte[] ba = i.toBytes(); // transforma os dados em bytes

        long pos = arq.getFilePointer(); // posição da lápide (INÍCIO do registro)

        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);

        indiceDAO.create(i.getNome(), pos);

        return ultimoId;
    }

    // READ
    public Usuario read(String nome) throws Exception {
        long pos = indiceDAO.read(nome);

        if(pos == -1) {
            return null;
        }

        arq.seek(pos);

        byte lapide = arq.readByte();
        short tam = arq.readShort();

        if (lapide == 1) return null;

        byte[] by = new byte[tam];
        arq.readFully(by);

        Usuario u = new Usuario();
        u.fromBytes(by);

        return u;
        
    }

    // UPDATE
    public boolean update(Usuario novo, String nomeAntigo) throws Exception {
        long pos = indiceDAO.read(nomeAntigo);
        if (pos == -1) return false;

        arq.seek(pos);
        byte lapide = arq.readByte();

        if (lapide == 1) return false;

        // marca antigo como removido
        arq.seek(pos);
        arq.writeByte(1);

        // escreve novo no final
        arq.seek(arq.length());
        byte[] novoBa = novo.toBytes();

        long novaPos = arq.getFilePointer();
        arq.writeByte(0);
        arq.writeShort(novoBa.length);
        arq.write(novoBa);

        // atualiza índice
        indiceDAO.delete(nomeAntigo);
        indiceDAO.create(novo.getNome(), novaPos);

        return true;
    }

    // DELETE
    public boolean delete(String nome) throws Exception {
        arq.seek(4); // Pula o cabeçalho

        while (arq.getFilePointer() < arq.length()) {
            long pos = arq.getFilePointer(); // Guarda o início do registro (onde está a lápide)

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {
                byte[] by = new byte[tam]; 
                arq.readFully(by);

                Usuario u = new Usuario();
                u.fromBytes(by); 

                if (u.getNome().equals(nome)) {
                    arq.seek(pos); // Volta para o início deste registro
                    arq.writeByte(1); // Marca a lápide como excluída
                    indiceDAO.delete(nome);
                    return true;
                } 
            } else {
                // Se o registro já estiver excluído (lápide 1), pula o tamanho total dele
                arq.skipBytes(tam);
            }
        }
        return false;
    }

    public void ordenarPorNome() throws Exception {
        Path pastaTemp = Files.createTempDirectory("OrdenacaoTemp");
        Path[] caminhos = {
            pastaTemp.resolve("arq1.db"), pastaTemp.resolve("arq2.db"),
            pastaTemp.resolve("arq3.db"), pastaTemp.resolve("arq4.db")
        };

        // 1. DISTRIBUIÇÃO
        arq.seek(0);
        int ultimoId = arq.readInt();
        arq.seek(4);
        int totalRegistros = 0;

        try (RandomAccessFile out1 = new RandomAccessFile(caminhos[0].toFile(), "rw");
            RandomAccessFile out2 = new RandomAccessFile(caminhos[1].toFile(), "rw")) {

            int destino = 1;

            while (arq.getFilePointer() < arq.length()) {
                Usuario[] array = new Usuario[4];
                int preenchidos = 0;

                while (preenchidos < 4 && arq.getFilePointer() < arq.length()) {
                    byte lapide = arq.readByte();
                    short tam = arq.readShort();

                    if (lapide == 0) {
                        byte[] by = new byte[tam];
                        arq.readFully(by);

                        Usuario u = new Usuario();
                        u.fromBytes(by);

                        array[preenchidos++] = u;
                        totalRegistros++;
                    } else {
                        arq.skipBytes(tam);
                    }
                }

                if (preenchidos > 0) {
                    Arrays.sort(array, 0, preenchidos,
                        (u1, u2) -> u1.getNome().compareTo(u2.getNome()));

                    RandomAccessFile out = (destino == 1) ? out1 : out2;

                    for (int i = 0; i < preenchidos; i++) {
                        escreverUsuario(out, array[i]);
                    }

                    destino = (destino == 1) ? 2 : 1;
                }
            }
        }

        // 2. INTERCALAÇÃO
        int tamBloco = 4;
        boolean nosAuxiliares = true;

        while (tamBloco < totalRegistros) {
            if (nosAuxiliares) {
                mesclarArquivos(caminhos[0], caminhos[1], caminhos[2], caminhos[3], tamBloco);
            } else {
                mesclarArquivos(caminhos[2], caminhos[3], caminhos[0], caminhos[1], tamBloco);
            }

            tamBloco *= 2;
            nosAuxiliares = !nosAuxiliares;
        }

        // 3. RESULTADO FINAL
        Path resultadoFinal = nosAuxiliares ? caminhos[0] : caminhos[2];
        copiarParaArquivoOrdenado(resultadoFinal, ultimoId);
    }

    public void mesclarArquivos(Path arqA, Path arqB, Path arqC, Path arqD, int tamBloco) throws Exception {

        try (RandomAccessFile a = new RandomAccessFile(arqA.toFile(), "r");
            RandomAccessFile b = new RandomAccessFile(arqB.toFile(), "r");
            RandomAccessFile c = new RandomAccessFile(arqC.toFile(), "rw");
            RandomAccessFile d = new RandomAccessFile(arqD.toFile(), "rw")) {

            c.setLength(0);
            d.setLength(0);

            boolean destinoC = true;

            while (a.getFilePointer() < a.length() || b.getFilePointer() < b.length()) {

                RandomAccessFile out = destinoC ? c : d;

                int lidosA = 0;
                int lidosB = 0;

                Usuario uA = lerUsuario(a);
                Usuario uB = lerUsuario(b);

                while (lidosA < tamBloco || lidosB < tamBloco) {

                    if ((uA != null && lidosA < tamBloco) &&
                        (uB == null || lidosB >= tamBloco ||
                        uA.getNome().compareTo(uB.getNome()) <= 0)) {

                        escreverUsuario(out, uA);
                        lidosA++;

                        if (lidosA < tamBloco) {
                            uA = lerUsuario(a);
                        } else {
                            uA = null;
                        }

                    } else if (uB != null && lidosB < tamBloco) {

                        escreverUsuario(out, uB);
                        lidosB++;

                        if (lidosB < tamBloco) {
                            uB = lerUsuario(b);
                        } else {
                            uB = null;
                        }

                    } else {
                        break;
                    }
                }

                destinoC = !destinoC;
            }
        }
    }

    public Usuario lerUsuario(RandomAccessFile arq) throws Exception {
        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 1) {
                arq.skipBytes(tam);
                continue;
            }

            byte[] by = new byte[tam];
            arq.readFully(by);

            Usuario u = new Usuario();
            u.fromBytes(by);

            return u;
        }
        return null;
    }

    public void escreverUsuario(RandomAccessFile arq, Usuario u) throws Exception {
        byte[] by = u.toBytes();

        arq.writeByte(0); // lápide válida
        arq.writeShort(by.length);
        arq.write(by);
    }

    public void copiarParaArquivoOrdenado(Path origem, int ultimoId) throws Exception {

        Path destino = Paths.get("UsuarioOrdenado.db");

        // se já existir, apaga
        if (Files.exists(destino)) {
            Files.delete(destino);
        }

        try (RandomAccessFile in = new RandomAccessFile(origem.toFile(), "r");
            RandomAccessFile out = new RandomAccessFile(destino.toFile(), "rw")) {

            out.setLength(0);
            out.seek(0);

            out.writeInt(ultimoId);

            while (in.getFilePointer() < in.length()) {
                Usuario u = lerUsuario(in);
                if (u != null) {
                    escreverUsuario(out, u);
                }
            }
        }
    }
}
