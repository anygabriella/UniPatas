package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.Campanha;
import br.com.unipatas.index.HashExtensivel;

public class CampanhaDAO {

    private RandomAccessFile arq;
    private HashExtensivel indicePK;

    public CampanhaDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/Campanha.db", "rw");

        if (arq.length() == 0) {
            arq.writeInt(0);
        }

        indicePK = new HashExtensivel("CampanhaPK");
    }

    public boolean update(Campanha nova) throws Exception {

        long pos = indicePK.read(nova.getId());
        if (pos == -1) return false;

        arq.seek(pos);
        byte lapide = arq.readByte();

        if (lapide == 1) return false;

        // 🔥 marca antigo como removido
        arq.seek(pos);
        arq.writeByte(1);

        // 🔥 escreve novo no final do arquivo
        arq.seek(arq.length());
        long novaPos = arq.getFilePointer();

        byte[] ba = nova.toBytes();

        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);

        // 🔥 ATUALIZA O HASH (isso resolve seu bug)
        indicePK.delete(nova.getId());
        indicePK.create(nova.getId(), novaPos);

        return true;
    }

    public int create(Campanha c) throws Exception {

        arq.seek(0);
        int ultimoId = arq.readInt();
        ultimoId++;

        arq.seek(0);
        arq.writeInt(ultimoId);

        c.setId(ultimoId); // 🔥 ESSENCIAL

        arq.seek(arq.length());
        long pos = arq.getFilePointer();

        byte[] ba = c.toBytes();

        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);

        indicePK.create(c.getId(), pos);

        return c.getId();
    }

    public Campanha read(int id) throws Exception {

        long pos = indicePK.read(id);
        if (pos == -1) return null;

        arq.seek(pos);

        byte lapide = arq.readByte();
        short tam = arq.readShort();

        if (lapide == 1) return null;

        byte[] ba = new byte[tam];
        arq.readFully(ba);

        Campanha c = new Campanha();
        c.fromBytes(ba);

        return c;
    }

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