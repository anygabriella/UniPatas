package br.com.unipatas.model;

import java.io.*;

public class Abrigo {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private double custoMensal;

    public Abrigo() {
        this.nome = "";
        this.endereco = "";
        this.telefone = "";
        this.custoMensal = 0.0;
    }

    public Abrigo(String nome, String endereco, String telefone, double custoMensal) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.custoMensal = custoMensal;
    }

    public Abrigo(int id, String nome, String endereco, String telefone, double custoMensal) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.custoMensal = custoMensal;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getCustoMensal() { return custoMensal; }
    public void setCustoMensal(double custoMensal) { this.custoMensal = custoMensal; }
    
    public String getNome() { return nome; }
    public String getendereco() { return endereco; }
    public String getTelefone() { return telefone; }

    public void setNome(String nome) { this.nome = nome; }
    public void setendereco(String endereco) { this.endereco = endereco; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String mostrar() {
        return "ID: " + id + " | Nome: " + nome +
               " | endereco: " + endereco + " | Telefone: " + telefone + " | Custo Mensal: R$ " + custoMensal;
    }

    // SERIALIZAÇÃO
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(endereco);
        dos.writeUTF(telefone);
        dos.writeDouble(custoMensal);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        id = dis.readInt();
        nome = dis.readUTF();
        endereco = dis.readUTF();
        telefone = dis.readUTF();
        custoMensal = dis.readDouble();
    }
}