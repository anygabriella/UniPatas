package br.com.unipatas.index;

import java.io.*;
import java.util.*;

public class HashExtensivel {

    private RandomAccessFile dir;
    private RandomAccessFile bucket;

    private int profundidadeGlobal;
    private final int bucketSize = 4;

    private String basePath;

    public HashExtensivel(String nome) throws Exception {

        basePath = System.getProperty("user.dir");

        File pasta = new File(basePath + "/data");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        System.out.println("HASH salvando em: " + pasta.getAbsolutePath());

        dir = new RandomAccessFile(basePath + "/data/" + nome + ".dir", "rw");
        bucket = new RandomAccessFile(basePath + "/data/" + nome + ".bkt", "rw");

        if (dir.length() == 0) {
            profundidadeGlobal = 1;
            dir.writeInt(profundidadeGlobal);

            for (int i = 0; i < 2; i++) {
                long pos = createBucket(1);
                dir.writeLong(pos);
            }
        } else {
            dir.seek(0);
            profundidadeGlobal = dir.readInt();
        }
    }

    private long createBucket(int pl) throws Exception {

        long pos = bucket.length();
        bucket.seek(pos);

        bucket.writeInt(pl); 
        bucket.writeInt(0);  

        for (int i = 0; i < bucketSize; i++) {
            bucket.writeInt(-1);
            bucket.writeLong(-1);
        }

        return pos;
    }

    private int hash(int chave) {
        return chave & ((1 << profundidadeGlobal) - 1);
    }

    public void create(int chave, long endereco) throws Exception {

        int h = hash(chave);

        dir.seek(4 + h * 8);
        long posBucket = dir.readLong();

        bucket.seek(posBucket);
        int pl = bucket.readInt();
        int qtd = bucket.readInt();

        if (qtd < bucketSize) {

            bucket.seek(posBucket + 8 + qtd * 12);
            bucket.writeInt(chave);
            bucket.writeLong(endereco);

            bucket.seek(posBucket + 4);
            bucket.writeInt(qtd + 1);

        } else {
            split(h);
            create(chave, endereco);
        }
    }

    private void split(int h) throws Exception {

        dir.seek(4 + h * 8);
        long posBucket = dir.readLong();

        bucket.seek(posBucket);
        int pl = bucket.readInt();

        if (pl == profundidadeGlobal) {
            duplicarDiretorio();
        }

        long novoBucket = createBucket(pl + 1);

        bucket.seek(posBucket);
        bucket.writeInt(pl + 1);

        redistribuir(posBucket, novoBucket);
    }

    private void duplicarDiretorio() throws Exception {

        int tamanho = 1 << profundidadeGlobal;

        for (int i = 0; i < tamanho; i++) {
            dir.seek(4 + i * 8);
            long val = dir.readLong();

            dir.seek(4 + (i + tamanho) * 8);
            dir.writeLong(val);
        }

        profundidadeGlobal++;
        dir.seek(0);
        dir.writeInt(profundidadeGlobal);
    }

    private void redistribuir(long b1, long b2) throws Exception {

    List<long[]> dados = new ArrayList<>();

    bucket.seek(b1);
    int pl = bucket.readInt();
    int qtd = bucket.readInt();

    for (int i = 0; i < qtd; i++) {
        int c = bucket.readInt();
        long e = bucket.readLong();
        dados.add(new long[]{c, e});
    }

    // limpa bucket antigo
    bucket.seek(b1);
    bucket.writeInt(pl);
    bucket.writeInt(0);

    // limpa novo bucket
    bucket.seek(b2);
    bucket.writeInt(pl);
    bucket.writeInt(0);

    // reinsere corretamente
    for (long[] d : dados) {
        create((int)d[0], d[1]);
    }
}

    public long read(int chave) throws Exception {

    int h = hash(chave);

    dir.seek(4 + h * 8);
    long posBucket = dir.readLong();

    bucket.seek(posBucket);
    bucket.readInt(); // pl
    int qtd = bucket.readInt();

    long endereco = -1;

    for (int i = 0; i < qtd; i++) {
        int c = bucket.readInt();
        long e = bucket.readLong();

        if (c == chave)
            endereco = e; 
    }

    return endereco;
}

public void delete(int chave) throws Exception {

    int h = hash(chave);

    dir.seek(4 + h * 8);
    long posBucket = dir.readLong();

    bucket.seek(posBucket);
    int pl = bucket.readInt();
    int qtd = bucket.readInt();

    List<long[]> dados = new ArrayList<>();

    // lê todos os dados MENOS o que será removido
    for (int i = 0; i < qtd; i++) {
        int c = bucket.readInt();
        long e = bucket.readLong();

        if (c != chave) {
            dados.add(new long[]{c, e});
        }
    }

    // limpa bucket
    bucket.seek(posBucket);
    bucket.writeInt(pl);
    bucket.writeInt(0);

    // reinsere sem o elemento removido
    for (long[] d : dados) {
        create((int)d[0], d[1]);
    }
}

}