package br.com.unipatas.dao;

import java.io.*;
import java.util.*;

public class AbrigoAnimalDAO {

    private RandomAccessFile arq;

    public AbrigoAnimalDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/AbrigoAnimal.idx", "rw");
    }

    // CREATE
    public void create(int idAbrigo, long posAnimal) throws Exception {

        arq.seek(arq.length());

        arq.writeByte(0);      // lápide (0 = ativo)
        arq.writeInt(idAbrigo);
        arq.writeLong(posAnimal);
    }

    // READ
    public List<Long> read(int idAbrigo) throws Exception {

        List<Long> lista = new ArrayList<>();

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            try {
                byte lapide = arq.readByte();
                int id = arq.readInt();
                long pos = arq.readLong();

                if (lapide == 0 && id == idAbrigo) {
                    lista.add(pos);
                }

            } catch (EOFException e) {
                break; // segurança contra corrupção
            }
        }

        return lista;
    }

    // DELETE por posição do animal (IMPORTANTE)
    public void deleteByPos(long posAnimal) throws Exception {

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {

            long posRegistro = arq.getFilePointer();

            try {
                byte lapide = arq.readByte();
                int id = arq.readInt();
                long pos = arq.readLong();

                if (lapide == 0 && pos == posAnimal) {

                    arq.seek(posRegistro);
                    arq.writeByte(1); // marca como removido
                    return;
                }

            } catch (EOFException e) {
                break;
            }
        }
    }
}