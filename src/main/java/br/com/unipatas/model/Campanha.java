package br.com.unipatas.model;

import java.io.*;

public class Campanha {

    private int id;
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFim;

    public Campanha() {
        this.nome = "";
        this.descricao = "";
        this.dataInicio = "";
        this.dataFim = "";
    }

    public Campanha(String nome, String descricao, String dataInicio, String dataFim) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Campanha(int id, String nome, String descricao, String dataInicio, String dataFim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String mostrar() {
        return "ID: " + id + " | Nome: " + nome +
               " | Início: " + dataInicio + " | Fim: " + dataFim +
               " | Descrição: " + descricao;
    }

    // SERIALIZAÇÃO
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(descricao);
        dos.writeUTF(dataInicio);
        dos.writeUTF(dataFim);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] by) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(by));

        id = dis.readInt();
        nome = dis.readUTF();
        descricao = dis.readUTF();
        dataInicio = dis.readUTF();
        dataFim = dis.readUTF();
    }
}