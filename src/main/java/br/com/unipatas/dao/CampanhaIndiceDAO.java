package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.CampanhaIndice;

public class CampanhaIndiceDAO {

    private RandomAccessFile arq;

    public CampanhaIndiceDAO() throws Exception {
        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/Campanha.idx", "rw");
    }

    // CREATE
    public void create(int id, long pos) throws Exception {
        arq.seek(arq.length());

        CampanhaIndice idx = new CampanhaIndice(id, pos);
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

                CampanhaIndice idx = new CampanhaIndice();
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

                CampanhaIndice idx = new CampanhaIndice();
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