package br.com.unipatas.model;

import java.io.*;

public class AnimalCampanha {

    private int idAnimal;
    private int idCampanha;

    public AnimalCampanha() {}

    public AnimalCampanha(int idAnimal, int idCampanha) {
        this.idAnimal = idAnimal;
        this.idCampanha = idCampanha;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public int getIdCampanha() {
        return idCampanha;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public void setIdCampanha(int idCampanha) {
        this.idCampanha = idCampanha;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(idAnimal);
        dos.writeInt(idCampanha);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        idAnimal = dis.readInt();
        idCampanha = dis.readInt();
    }
}