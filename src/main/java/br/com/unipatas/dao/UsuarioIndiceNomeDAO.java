package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.UsuarioIndiceNome;

public class UsuarioIndiceNomeDAO {

    private RandomAccessFile arq;

    public UsuarioIndiceNomeDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        arq = new RandomAccessFile("data/UsuarioNome.idx", "rw");
    }

    // CREATE
    public void create(String nome, long posicao) throws Exception {

        arq.seek(arq.length());

        UsuarioIndiceNome idx = new UsuarioIndiceNome(nome, posicao);
        byte[] ba = idx.toBytes();

        arq.writeByte(0); // lápide válida
        arq.writeShort(ba.length);
        arq.write(ba);
    }

    // READ (busca sequencial)
    public long read(String nome) throws Exception {

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {

                byte[] ba = new byte[tam];
                arq.readFully(ba);

                UsuarioIndiceNome idx = new UsuarioIndiceNome();
                idx.fromBytes(ba);

                if (idx.getNome().equals(nome)) {
                    return idx.getPosicao();
                }

            } else {
                arq.skipBytes(tam);
            }
        }

        return -1;
    }

    // DELETE (marca lápide)
    public boolean delete(String nome) throws Exception {

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {

                byte[] ba = new byte[tam];
                arq.readFully(ba);

                UsuarioIndiceNome idx = new UsuarioIndiceNome();
                idx.fromBytes(ba);

                if (idx.getNome().equals(nome)) {

                    arq.seek(pos);
                    arq.writeByte(1); // marca como removido

                    return true;
                }

            } else {
                arq.skipBytes(tam);
            }
        }

        return false;
    }
}