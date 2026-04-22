package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.Adocao;
import br.com.unipatas.index.HashExtensivel;

public class AdocaoDAO {

    private RandomAccessFile arq;
    private HashExtensivel indicePK;

    public AdocaoDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/Adocao.db", "rw");

        if (arq.length() == 0) {
            arq.writeInt(0);
        }

        indicePK = new HashExtensivel("AdocaoPK");
    }

    // CREATE
    public int create(Adocao a) throws Exception {

        arq.seek(0);
        int id = arq.readInt();
        id++;

        arq.seek(0);
        arq.writeInt(id);

        a.setId(id);

        arq.seek(arq.length());
        long pos = arq.getFilePointer();

        byte[] ba = a.toBytes();

        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);

        indicePK.create(id, pos);

        return id;
    }

    // READ
    public Adocao read(int id) throws Exception {

        long pos = indicePK.read(id);
        if (pos == -1) return null;

        arq.seek(pos);

        if (arq.readByte() == 1) return null;

        short tam = arq.readShort();
        byte[] ba = new byte[tam];
        arq.readFully(ba);

        Adocao a = new Adocao();
        a.fromBytes(ba);

        return a;
    }

    // UPDATE 🔥 (padrão correto)
    public boolean update(Adocao novo) throws Exception {

        long pos = indicePK.read(novo.getId());
        if (pos == -1) return false;

        arq.seek(pos);
        byte lapide = arq.readByte();
        short tam = arq.readShort();

        if (lapide == 1) return false;

        byte[] novoBa = novo.toBytes();

        if (novoBa.length <= tam) {
            arq.seek(pos + 3);
            arq.write(novoBa);
        } else {
            arq.seek(pos);
            arq.writeByte(1);

            arq.seek(arq.length());
            long novaPos = arq.getFilePointer();

            arq.writeByte(0);
            arq.writeShort(novoBa.length);
            arq.write(novoBa);

            indicePK.create(novo.getId(), novaPos);
        }

        return true;
    }

    // DELETE
    public boolean delete(int id) throws Exception {

        long pos = indicePK.read(id);
        if (pos == -1) return false;

        arq.seek(pos);

        if (arq.readByte() == 1) return false;

        arq.seek(pos);
        arq.writeByte(1);

        indicePK.delete(id);

        return true;
    }
}