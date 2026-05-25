package br.com.unipatas.dao;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import br.com.unipatas.model.AnimalCampanha;

public class AnimalCampanhaDAO {

    private RandomAccessFile arq;

    public AnimalCampanhaDAO() throws Exception {
        File pasta = new File("data");

        if (!pasta.exists()) {
            pasta.mkdir();
        }

        arq = new RandomAccessFile("data/AnimalCampanha.db", "rw");
    }

    public boolean create(int idAnimal, int idCampanha) throws Exception {
        if (idAnimal <= 0 || idCampanha <= 0) {
            return false;
        }

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
            long posicao = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tamanho = arq.readShort();

            if (tamanho <= 0 || posicao + 3 + tamanho > arq.length()) {
                break;
            }

            byte[] ba = new byte[tamanho];
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
            long posicao = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tamanho = arq.readShort();

            if (tamanho <= 0 || posicao + 3 + tamanho > arq.length()) {
                break;
            }

            byte[] ba = new byte[tamanho];
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
            long posicao = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tamanho = arq.readShort();

            if (tamanho <= 0 || posicao + 3 + tamanho > arq.length()) {
                break;
            }

            byte[] ba = new byte[tamanho];
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
            long posicao = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tamanho = arq.readShort();

            if (tamanho <= 0 || posicao + 3 + tamanho > arq.length()) {
                break;
            }

            byte[] ba = new byte[tamanho];
            arq.readFully(ba);

            if (lapide == 0) {
                AnimalCampanha ac = new AnimalCampanha();
                ac.fromBytes(ba);

                if (ac.getIdAnimal() == idAnimal && ac.getIdCampanha() == idCampanha) {
                    arq.seek(posicao);
                    arq.writeByte(1);
                    return true;
                }
            }
        }

        return false;
    }
}