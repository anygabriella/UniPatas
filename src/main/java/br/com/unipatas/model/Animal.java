package br.com.unipatas.model;

import java.io.*;

public class Animal {

    private int id;
    private String nome;
    private String raca;
    private String porte;
    private float peso;
    private String dataAdocao;
    private int idAbrigo;

    public Animal() {
        this.nome = "";
        this.raca = "";
        this.porte = "";
        this.dataAdocao = "";
    }

    public Animal(String nome, String raca, String porte, float peso, String dataAdocao, int idAbrigo) {
        this.nome = nome;
        this.raca = raca;
        this.porte = porte;
        this.peso = peso;
        this.dataAdocao = dataAdocao;
        this.idAbrigo = idAbrigo;
    }

    public Animal(int id, String nome, String raca, String porte, float peso, String dataAdocao, int idAbrigo) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.porte = porte;
        this.peso = peso;
        this.dataAdocao = dataAdocao;
        this.idAbrigo = idAbrigo;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getRaca() { return raca; }
    public String getPorte() { return porte; }
    public float getPeso() { return peso; }
    public String getDataAdocao() { return dataAdocao; }
    public int getIdAbrigo() { return idAbrigo; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setRaca(String raca) { this.raca = raca; }
    public void setPorte(String porte) { this.porte = porte; }
    public void setPeso(float peso) { this.peso = peso; }
    public void setDataAdocao(String dataAdocao) { this.dataAdocao = dataAdocao; }
    public void setIdAbrigo(int idAbrigo) { this.idAbrigo = idAbrigo; }

    public String mostrar() {
        return "ID: " + id +
               " | Nome: " + nome +
               " | Raça: " + raca +
               " | Porte: " + porte +
               " | Peso: " + peso +
               " | Adoção: " + dataAdocao +
               " | Abrigo: " + idAbrigo;
    }

    public byte[] toBytes() throws IOException {

        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(raca);
        dos.writeUTF(porte);
        dos.writeFloat(peso);
        dos.writeUTF(dataAdocao);
        dos.writeInt(idAbrigo);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        id = dis.readInt();
        nome = dis.readUTF();
        raca = dis.readUTF();
        porte = dis.readUTF();
        peso = dis.readFloat();
        dataAdocao = dis.readUTF();
        idAbrigo = dis.readInt();
    }
}