package br.com.unipatas.model;

import java.io.*;

public class Animal {

    private int id;
    private String nome;
    private int idade;
    private String especie;
    private String raca;
    private int idAbrigo;

    public Animal() {
        this.nome = "";
        this.especie = "";
        this.raca = "";
    }

    public Animal(String nome, int idade, String especie, String raca, int idAbrigo) {
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.raca = raca;
        this.idAbrigo = idAbrigo;
    }

    public Animal(int id, String nome, int idade, String especie, String raca, int idAbrigo) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.raca = raca;
        this.idAbrigo = idAbrigo;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public int getIdAbrigo() { return idAbrigo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setRaca(String raca) { this.raca = raca; }
    public void setIdAbrigo(int idAbrigo) { this.idAbrigo = idAbrigo; }

    public String mostrar() {
        return "ID: " + id + " | Nome: " + nome + " | Espécie: " + especie +
               " | Raça: " + raca + " | Idade: " + idade + " | Abrigo: " + idAbrigo;
    }

    // SERIALIZAÇÃO
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeInt(idade);
        dos.writeUTF(especie);
        dos.writeUTF(raca);
        dos.writeInt(idAbrigo);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        id = dis.readInt();
        nome = dis.readUTF();
        idade = dis.readInt();
        especie = dis.readUTF();
        raca = dis.readUTF();
        idAbrigo = dis.readInt();
    }
}