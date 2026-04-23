package br.com.unipatas.model;

import java.io.*;

public class Campanha {

    private int id;
    private String nome;
    private String local;
    private String data;
    private double custo;

    public Campanha() {
        this.nome = "";
        this.local = "";
        this.data = "";
    }

    public Campanha(String nome, String local, String data, double custo) {
        this.nome = nome;
        this.local = local;
        this.data = data;
        this.custo = custo;
    }

    public Campanha(int id, String nome, String local, String data, double custo) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.data = data;
        this.custo = custo;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public String getLocal() { return local; }
    public String getData() { return data; }
    public double getCusto() { return custo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setLocal(String local) { this.local = local; }
    public void setData(String data) { this.data = data; }
    public void setCusto(double custo) { this.custo = custo; }

    public String mostrar() {
        return "ID: " + id + 
               " | Nome: " + nome +
               " | Local: " + local +
               " | Data: " + data +
               " | Custo: " + custo;
    }

    // SERIALIZAÇÃO
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(local);
        dos.writeUTF(data);
        dos.writeDouble(custo);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        id = dis.readInt();
        nome = dis.readUTF();
        local = dis.readUTF();
        data = dis.readUTF();
        custo = dis.readDouble();
    }
}