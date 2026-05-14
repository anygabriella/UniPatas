package br.com.unipatas.index;

import br.com.unipatas.index.arvore.RegistroArvoreBMais;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

// index/ParStringInt.java
public class ParStringInt implements RegistroArvoreBMais<ParStringInt> {

    private String chave;  // raça, porte, nome — o que quiser indexar
    private int id;        // ID do Animal
    
    private final int TAMANHO_STRING = 30; // bytes fixos para a String
    private final short TAMANHO = (short)(TAMANHO_STRING + 4); // + int

    public ParStringInt() { this("", -1); }
    public ParStringInt(String c) { this(c, -1); }
    public ParStringInt(String c, int id) {
        // padroniza tamanho
        this.chave = String.format("%-" + TAMANHO_STRING + "s", c)
                           .substring(0, TAMANHO_STRING);
        this.id = id;
    }

    public int compareTo(ParStringInt outro) {
        int cmp = this.chave.trim().compareTo(outro.chave.trim());
        if (cmp != 0) return cmp;
        return this.id == -1 ? 0 : this.id - outro.id; // mesmo truque do -1
    }

    public ParStringInt clone() { return new ParStringInt(this.chave, this.id); }
    public short size() { return TAMANHO; }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        // String de tamanho fixo em bytes
        byte[] strBytes = new byte[TAMANHO_STRING];
        byte[] src = this.chave.getBytes("UTF-8");
        System.arraycopy(src, 0, strBytes, 0, Math.min(src.length, TAMANHO_STRING));
        dos.write(strBytes);
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] strBytes = new byte[TAMANHO_STRING];
        dis.read(strBytes);
        this.chave = new String(strBytes, "UTF-8");
        this.id = dis.readInt();
    }

    public String toString() {
        return chave.trim() + ";" + id;
    }

    public int getId() {
        return this.id;
    }

    public String getChave() {
        return this.chave.trim();
    }
}