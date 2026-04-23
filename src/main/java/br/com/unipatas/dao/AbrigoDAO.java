package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.Abrigo;
import br.com.unipatas.index.HashExtensivel;

public class AbrigoDAO {

    private RandomAccessFile arq;
    private HashExtensivel indicePK;

    public AbrigoDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/Abrigo.db", "rw");

        if (arq.length() == 0) {
            arq.writeInt(0);
        }

        indicePK = new HashExtensivel("AbrigoPK");
    }

    // UPDATE
    public boolean update(Abrigo novo) throws Exception {

        long pos = indicePK.read(novo.getId());
        if (pos == -1) return false;

        arq.seek(pos);

        byte lapide = arq.readByte();
        short tam = arq.readShort();

        if (lapide == 1) return false;

        byte[] baNovo = novo.toBytes();

        
        if (baNovo.length <= tam) {

            arq.seek(pos + 3); 
            arq.write(baNovo);

        } 
        
        else {

            arq.seek(pos);
            arq.writeByte(1); // marca como removido

            arq.seek(arq.length());
            long novaPos = arq.getFilePointer();

            arq.writeByte(0);
            arq.writeShort(baNovo.length);
            arq.write(baNovo);

            
            indicePK.delete(novo.getId());
            indicePK.create(novo.getId(), novaPos);
        }

        return true;
    }

    // CREATE
    public int create(Abrigo a) throws Exception {

        arq.seek(0);
        int ultimoId = arq.readInt();
        ultimoId++;

        arq.seek(0);
        arq.writeInt(ultimoId);

        a.setId(ultimoId);

        arq.seek(arq.length());
        long pos = arq.getFilePointer();

        byte[] ba = a.toBytes();

        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);

        indicePK.create(a.getId(), pos);

        return a.getId();
    }

    // READ
    public Abrigo read(int id) throws Exception {

        long pos = indicePK.read(id);
        if (pos == -1) return null;

        arq.seek(pos);

        byte lapide = arq.readByte();
        short tam = arq.readShort();

        if (lapide == 1) return null;

        byte[] ba = new byte[tam];
        arq.readFully(ba);

        Abrigo a = new Abrigo();
        a.fromBytes(ba);

        return a;
    }

    // DELETE
    public boolean delete(int id) throws Exception {

        long pos = indicePK.read(id);
        if (pos == -1) return false;

        arq.seek(pos);

        byte lapide = arq.readByte();
        if (lapide == 1) return false;

        arq.seek(pos);
        arq.writeByte(1);

        
        indicePK.delete(id);

        return true;
    }
}