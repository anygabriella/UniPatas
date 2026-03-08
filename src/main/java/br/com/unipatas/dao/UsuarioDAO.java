package br.com.unipatas.dao;

import java.io.*;
import br.com.unipatas.model.Usuario;

public class UsuarioDAO {
    private RandomAccessFile arq;

    public UsuarioDAO() throws Exception {

        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        arq = new RandomAccessFile("data/Usuario.db", "rw");

        // Cabeçalho: último ID gerado
        if (arq.length() == 0) {
            arq.writeInt(0);
        }
    }

    // CREATE
    public int create(Usuario i) throws Exception {
        arq.seek(0); // cabeçalho
        int ultimoId = arq.readInt();
        ultimoId++; // le o ultimo id usado e atualiza para ser o novo
        arq.seek(0);
        arq.writeInt(ultimoId); // escreve o novo (e ultimo) id

        i.setId(ultimoId);

        arq.seek(arq.length()); // vai para a ultima posição

        byte[] ba = i.toBytes(); // transforma os dados em bytes

        arq.writeByte(0); // lapide 0 == ativa
        arq.writeShort(ba.length); // escreve o tamanho do registro
        arq.write(ba);

        return ultimoId;
    }

    // READ
    public Usuario read(int id) throws Exception {
        arq.seek(4); // pula cabeçalho

        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            Short tam = arq.readShort(); // tamanho dos dados

            if (lapide == 0) {
                int idLido = arq.readInt(); // le o id do registro

                if (idLido == id) { // se for o id procurado
                    arq.seek(arq.getFilePointer() - 4); // volta para o começo do registro
                    byte[] by = new byte[tam]; // cria um array de bytes daquele tamanho
                    arq.readFully(by); // preenche o array até o final ou lança erro

                    Usuario u = new Usuario();
                    u.fromBytes(by); // tranforma para obj

                    return u;

                } else
                    arq.skipBytes(tam - 4); // se não for o id, pula o resto do registro (já leu 4 bytes do id)
            } else
                arq.skipBytes(tam); // se lapide for 1, pula
        }

        return null;
    }

    // UPDATE
    public boolean update(Usuario i) throws Exception {
        arq.seek(4);

        while (arq.getFilePointer() < arq.length()) {
            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            Short tam = arq.readShort();

            if (lapide == 0) {
                byte[] by = new byte[tam];
                arq.readFully(by);

                Usuario u = new Usuario();
                u.fromBytes(by);

                if (u.getId() == i.getId()) {
                    arq.seek(pos);
                    arq.writeByte(1);

                    arq.seek(arq.length());
                    byte[] novo = i.toBytes();
                    arq.writeByte(0);
                    arq.writeShort(novo.length);
                    arq.write(novo);

                    return true;
                }
            } else
                arq.skipBytes(tam);
        }

        return false;
    }

    // DELETE
    public boolean delete(int id) throws Exception {
        arq.seek(4); // Pula o cabeçalho

        while (arq.getFilePointer() < arq.length()) {
            long pos = arq.getFilePointer(); // Guarda o início do registro (onde está a lápide)

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide == 0) {
                int idLido = arq.readInt(); // Lê apenas o ID (4 bytes)

                if (idLido == id) {
                    arq.seek(pos); // Volta para o início deste registro
                    arq.writeByte(1); // Marca a lápide como excluída
                    return true;
                } else {
                    // Se não for o ID, pula o resto (tamanho total menos os 4 bytes do ID já lidos)
                    arq.skipBytes(tam - 4);
                }
            } else {
                // Se o registro já estiver excluído (lápide 1), pula o tamanho total dele
                arq.skipBytes(tam);
            }
        }
        return false;
    }
}