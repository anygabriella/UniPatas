package br.com.unipatas.model;

import java.io.*;

public class UsuarioIndice {

    private String nome;
    private long posicao;

    public UsuarioIndice() {}

    public UsuarioIndice(String nome, long posicao) {
        this.nome = nome;
        this.posicao = posicao;
    }

    public String getNome() {
        return nome;
    }

    public long getPosicao() {
        return posicao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPosicao(long posicao) {
        this.posicao = posicao;
    }

    // transforma o objeto em bytes para ser gravado no arquivo de índice
    public byte[] toBytes() throws Exception {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeUTF(nome);
        dos.writeLong(posicao);

        return ba.toByteArray();
    }

    // transforma os bytes lidos do arquivo de índice de volta para um objeto
    public void fromBytes(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        nome = dis.readUTF();
        posicao = dis.readLong();
    }
}