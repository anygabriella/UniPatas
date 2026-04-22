package br.com.unipatas.model;

import java.io.*;

public class UsuarioIndicePK {

    private int id;
    private long posicao;

    public UsuarioIndicePK() {}

    public UsuarioIndicePK(int id, long posicao) {
        this.id = id;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public long getPosicao() {
        return posicao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosicao(long posicao) {
        this.posicao = posicao;
    }

    // transforma o objeto em bytes para ser gravado no arquivo de índice
    public byte[] toBytes() throws Exception {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);
        dos.writeLong(posicao);

        return ba.toByteArray();
    }

    // transforma os bytes lidos do arquivo de índice de volta para um objeto
    public void fromBytes(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        id = dis.readInt();
        posicao = dis.readLong();
    }
}