package br.com.unipatas.dao;

import java.io.*;
import java.util.*;
import br.com.unipatas.model.Animal;
import br.com.unipatas.index.HashExtensivel;

public class AnimalDAO {

    private RandomAccessFile arq;
    private HashExtensivel indicePK;
    private AbrigoAnimalDAO relDAO;

    public AnimalDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/Animal.db", "rw");

        if (arq.length() == 0) {
            arq.writeInt(0);
        }

        indicePK = new HashExtensivel("AnimalPK");
        relDAO = new AbrigoAnimalDAO();
    }

    // CREATE (CORRETO)
    public int create(Animal a) throws Exception {

        arq.seek(0);
        int ultimoId = arq.readInt();
        ultimoId++;

        arq.seek(0);
        arq.writeInt(ultimoId);

        a.setId(ultimoId); // 🔥 ESSENCIAL

        arq.seek(arq.length());
        long pos = arq.getFilePointer();

        byte[] ba = a.toBytes();

        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);

        indicePK.create(a.getId(), pos);

        // relacionamento 1:N
        relDAO.create(a.getIdAbrigo(), pos);

        return a.getId();
    }

    public boolean update(Animal novo) throws Exception {

    long pos = indicePK.read(novo.getId());

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

    byte[] ba = novo.toBytes();

    arq.writeByte(0);
    arq.writeShort(ba.length);
    arq.write(ba);

    // 🔥 atualiza índice hash
    indicePK.delete(novo.getId());
    indicePK.create(novo.getId(), novaPos);

  
    // remove relação antiga
    relDAO.deleteByPos(pos);

    // cria nova
    relDAO.create(novo.getIdAbrigo(), novaPos);

    return true;
}

    public Animal read(int id) throws Exception {

        long pos = indicePK.read(id);
        if (pos == -1) return null;

        arq.seek(pos);

        byte lapide = arq.readByte();
        short tam = arq.readShort();

        if (lapide == 1) return null;

        byte[] ba = new byte[tam];
        arq.readFully(ba);

        Animal a = new Animal();
        a.fromBytes(ba);

        return a;
    }

    public List<Animal> readByAbrigo(int idAbrigo) throws Exception {

        List<Animal> lista = new ArrayList<>();
        List<Long> posicoes = relDAO.read(idAbrigo);

        for (long pos : posicoes) {

            arq.seek(pos);

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 1) continue;

            byte[] ba = new byte[tam];
            arq.readFully(ba);

            Animal a = new Animal();
            a.fromBytes(ba);

            lista.add(a);
        }

        return lista;
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