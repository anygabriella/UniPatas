package br.com.unipatas.model;

import java.io.*;

public class Adocao {

    private int id;
    private int idAnimal;
    private int idUsuario;
    private String data;
    private String status;

    public Adocao() {}

    public Adocao(int idAnimal, int idUsuario, String data, String status) {
        this.idAnimal = idAnimal;
        this.idUsuario = idUsuario;
        this.data = data;
        this.status = status;
    }

    public Adocao(int id, int idAnimal, int idUsuario, String data, String status) {
        this.id = id;
        this.idAnimal = idAnimal;
        this.idUsuario = idUsuario;
        this.data = data;
        this.status = status;
    }

    // GETTERS/SETTERS
    public int getId() { return id; }
    public int getIdAnimal() { return idAnimal; }
    public int getIdUsuario() { return idUsuario; }
    public String getData() { return data; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }

    // SERIALIZAÇÃO
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeInt(idAnimal);
        dos.writeInt(idUsuario);
        dos.writeUTF(data);
        dos.writeUTF(status);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        id = dis.readInt();
        idAnimal = dis.readInt();
        idUsuario = dis.readInt();
        data = dis.readUTF();
        status = dis.readUTF();
    }
}