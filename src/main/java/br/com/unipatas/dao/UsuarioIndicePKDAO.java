package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.UsuarioIndicePK;

public class UsuarioIndicePKDAO {

    private RandomAccessFile arq;

    public UsuarioIndicePKDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        arq = new RandomAccessFile("data/UsuarioPK.idx", "rw");
    }

    // CREATE
    public void create(int id, long posicao) throws Exception {

        arq.seek(arq.length());

        UsuarioIndicePK idx = new UsuarioIndicePK(id, posicao);
        byte[] ba = idx.toBytes();

        arq.writeByte(0); // lápide válida
        arq.writeShort(ba.length);
        arq.write(ba);
    }

    // READ (busca sequencial)
    public long read(int id) throws Exception {

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {

                byte[] ba = new byte[tam];
                arq.readFully(ba);

                UsuarioIndicePK idx = new UsuarioIndicePK();
                idx.fromBytes(ba);

                if (idx.getId() == id) {
                    return idx.getPosicao();
                }

            } else {
                arq.skipBytes(tam);
            }
        }

        return -1;
    }

    // DELETE (marca lápide)
    public boolean delete(int id) throws Exception {

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {

                byte[] ba = new byte[tam];
                arq.readFully(ba);

                UsuarioIndicePK idx = new UsuarioIndicePK();
                idx.fromBytes(ba);

                if (idx.getId() == id) {

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