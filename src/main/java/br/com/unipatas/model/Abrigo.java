package br.com.unipatas.model;

import java.io.*;

public class Abrigo {

    private int id;
    private String nome;
    private String cidade;
    private String telefone;

    public Abrigo() {
        this.nome = "";
        this.cidade = "";
        this.telefone = "";
    }

    public Abrigo(String nome, String cidade, String telefone) {
        this.nome = nome;
        this.cidade = cidade;
        this.telefone = telefone;
    }

    public Abrigo(int id, String nome, String cidade, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.telefone = telefone;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public String getTelefone() { return telefone; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String mostrar() {
        return "ID: " + id + " | Nome: " + nome +
               " | Cidade: " + cidade + " | Telefone: " + telefone;
    }

    // SERIALIZAÇÃO
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(cidade);
        dos.writeUTF(telefone);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        id = dis.readInt();
        nome = dis.readUTF();
        cidade = dis.readUTF();
        telefone = dis.readUTF();
    }
}