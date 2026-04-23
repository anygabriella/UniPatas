package br.com.unipatas.model;

import java.io.*;

public class AnimalIndice {

    private int id;
    private long posicao;

    public AnimalIndice() {}

    public AnimalIndice(int id, long posicao) {
        this.id = id;
        this.posicao = posicao;
    }

    public int getId() { return id; }
    public long getPosicao() { return posicao; }

    public void setId(int id) { this.id = id; }
    public void setPosicao(long posicao) { this.posicao = posicao; }

    public byte[] toBytes() throws Exception {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeLong(posicao);

        return ba.toByteArray();
    }

    public void fromBytes(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        id = dis.readInt();
        posicao = dis.readLong();
    }
}