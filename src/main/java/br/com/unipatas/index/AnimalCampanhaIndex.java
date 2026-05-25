package br.com.unipatas.index;

import br.com.unipatas.index.arvore.RegistroArvoreBMais;
import java.io.*;

public class AnimalCampanhaIndex implements RegistroArvoreBMais<AnimalCampanhaIndex> {

    private int idPrincipal;
    private int idSecundario;
    private long enderecoNoArquivo;

    public AnimalCampanhaIndex() {
    }

    public AnimalCampanhaIndex(int idPrincipal, int idSecundario, long enderecoNoArquivo) {
        this.idPrincipal = idPrincipal;
        this.idSecundario = idSecundario;
        this.enderecoNoArquivo = enderecoNoArquivo;
    }

    public int getIdPrincipal() {
        return idPrincipal;
    }

    public int getIdSecundario() {
        return idSecundario;
    }

    public long getEnderecoNoArquivo() {
        return enderecoNoArquivo;
    }

    @Override
    public short size() {
        return 16;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(idPrincipal);
        dos.writeInt(idSecundario);
        dos.writeLong(enderecoNoArquivo);

        return ba.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(ba));

        idPrincipal = dis.readInt();
        idSecundario = dis.readInt();
        enderecoNoArquivo = dis.readLong();
    }

    @Override
    public int compareTo(AnimalCampanhaIndex obj) {
        int comparacaoPrincipal = Integer.compare(this.idPrincipal, obj.idPrincipal);

        if (comparacaoPrincipal != 0) {
            return comparacaoPrincipal;
        }

        return Integer.compare(this.idSecundario, obj.idSecundario);
    }

    @Override
    public AnimalCampanhaIndex clone() {
        return new AnimalCampanhaIndex(
                this.idPrincipal,
                this.idSecundario,
                this.enderecoNoArquivo
        );
    }

    @Override
    public String toString() {
        return "AnimalCampanhaIndex{" +
                "idPrincipal=" + idPrincipal +
                ", idSecundario=" + idSecundario +
                ", enderecoNoArquivo=" + enderecoNoArquivo +
                '}';
    }
}