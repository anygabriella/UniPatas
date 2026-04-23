package br.com.unipatas.dao;

import java.io.*;
import java.util.*;
import br.com.unipatas.model.AnimalCampanha;

public class AnimalCampanhaDAO {

    private RandomAccessFile arq;

    public AnimalCampanhaDAO() throws Exception {
        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/AnimalCampanha.db", "rw");
    }

    public boolean create(int idAnimal, int idCampanha) throws Exception {

        if (exists(idAnimal, idCampanha)) {
            return false;
        }

        AnimalCampanha ac = new AnimalCampanha(idAnimal, idCampanha);
        byte[] ba = ac.toBytes();

        arq.seek(arq.length());
        arq.writeByte(0);
        arq.writeShort(ba.length);
        arq.write(ba);

        return true;
    }

    public boolean exists(int idAnimal, int idCampanha) throws Exception {

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {
            byte lapide = arq.readByte();
            short tam = arq.readShort();

            byte[] ba = new byte[tam];
            arq.readFully(ba);

            if (lapide == 0) {
                AnimalCampanha ac = new AnimalCampanha();
                ac.fromBytes(ba);

                if (ac.getIdAnimal() == idAnimal && ac.getIdCampanha() == idCampanha) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Integer> readByAnimal(int idAnimal) throws Exception {

        List<Integer> campanhas = new ArrayList<>();

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {
            byte lapide = arq.readByte();
            short tam = arq.readShort();

            byte[] ba = new byte[tam];
            arq.readFully(ba);

            if (lapide == 0) {
                AnimalCampanha ac = new AnimalCampanha();
                ac.fromBytes(ba);

                if (ac.getIdAnimal() == idAnimal) {
                    campanhas.add(ac.getIdCampanha());
                }
            }
        }

        return campanhas;
    }

    public List<Integer> readByCampanha(int idCampanha) throws Exception {

        List<Integer> animais = new ArrayList<>();

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {
            byte lapide = arq.readByte();
            short tam = arq.readShort();

            byte[] ba = new byte[tam];
            arq.readFully(ba);

            if (lapide == 0) {
                AnimalCampanha ac = new AnimalCampanha();
                ac.fromBytes(ba);

                if (ac.getIdCampanha() == idCampanha) {
                    animais.add(ac.getIdAnimal());
                }
            }
        }

        return animais;
    }

    public boolean delete(int idAnimal, int idCampanha) throws Exception {

        arq.seek(0);

        while (arq.getFilePointer() < arq.length()) {
            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            byte[] ba = new byte[tam];
            arq.readFully(ba);

            if (lapide == 0) {
                AnimalCampanha ac = new AnimalCampanha();
                ac.fromBytes(ba);

                if (ac.getIdAnimal() == idAnimal && ac.getIdCampanha() == idCampanha) {
                    arq.seek(pos);
                    arq.writeByte(1);
                    return true;
                }
            }
        }

        return false;
    }
}