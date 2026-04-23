package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.AbrigoIndice;

public class AbrigoIndiceDAO {

    private RandomAccessFile arq;

    public AbrigoIndiceDAO() throws Exception {
        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/Abrigo.idx", "rw");
    }

    // CREATE
    public void create(int id, long pos) throws Exception {
        arq.seek(arq.length());

        AbrigoIndice idx = new AbrigoIndice(id, pos);
        byte[] ba = idx.toBytes();

        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);
    }

    // READ
    public long read(int id) throws Exception {
        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {

                byte[] ba = new byte[tam];
                arq.readFully(ba);

                AbrigoIndice idx = new AbrigoIndice();
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

    // DELETE
    public boolean delete(int id) throws Exception {
        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {

                byte[] ba = new byte[tam];
                arq.readFully(ba);

                AbrigoIndice idx = new AbrigoIndice();
                idx.fromBytes(ba);

                if (idx.getId() == id) {
                    arq.seek(pos);
                    arq.writeByte(1);
                    return true;
                }

            } else {
                arq.skipBytes(tam);
            }
        }

        return false;
    }
}