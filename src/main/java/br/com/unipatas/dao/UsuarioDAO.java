package br.com.unipatas.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.com.unipatas.model.Usuario;

public class UsuarioDAO {
  private RandomAccessFile arq;
  private UsuarioIndiceNomeDAO indiceNomeDAO;
  private UsuarioIndicePKDAO indicePKDAO;

  public UsuarioDAO() throws Exception {

    File pasta = new File("data");
    if (!pasta.exists()) {
      pasta.mkdir();
    }

    arq = new RandomAccessFile("data/Usuario.db", "rw");

    if (arq.length() == 0) {
      arq.writeInt(0);
    }

    indiceNomeDAO = new UsuarioIndiceNomeDAO();
    indicePKDAO = new UsuarioIndicePKDAO();
  }

  // CREATE
  public int create(Usuario i) throws Exception {
    arq.seek(0);
    int ultimoId = arq.readInt();
    ultimoId++;
    arq.seek(0);
    arq.writeInt(ultimoId);

    i.setId(ultimoId);

    arq.seek(arq.length());

    byte[] ba = i.toBytes();

    long pos = arq.getFilePointer();

    arq.writeByte(0);
    arq.writeShort(ba.length);
    arq.write(ba);

    indiceNomeDAO.create(i.getNome(), pos);
    indicePKDAO.create(i.getId(), pos);

    return ultimoId;
  }

  // READ por nome
  public Usuario read(String nome) throws Exception {
    long pos = indiceNomeDAO.read(nome);

    if (pos == -1)
      return null;

    arq.seek(pos);

    byte lapide = arq.readByte();
    short tam = arq.readShort();

    if (lapide == 1)
      return null;

    byte[] by = new byte[tam];
    arq.readFully(by);

    Usuario u = new Usuario();
    u.fromBytes(by);

    return u;
  }

  // READ por ID - PK
  public Usuario readById(int id) throws Exception {
    long pos = indicePKDAO.read(id);

    if (pos == -1)
      return null;

    arq.seek(pos);

    byte lapide = arq.readByte();
    short tam = arq.readShort();

    if (lapide == 1)
      return null;

    byte[] by = new byte[tam];
    arq.readFully(by);

    Usuario u = new Usuario();
    u.fromBytes(by);

    return u;
  }

  // UPDATE por nome 
  public boolean update(Usuario novo, String nomeAntigo) throws Exception {

    Usuario antigo = read(nomeAntigo);
    if (antigo == null) return false;

    long pos = indiceNomeDAO.read(nomeAntigo);
    if (pos == -1) return false;

    arq.seek(pos);
    byte lapide = arq.readByte();
    if (lapide == 1) return false;

    // garante que o ID não muda
    novo.setId(antigo.getId());

    arq.seek(pos);
    arq.writeByte(1);

    arq.seek(arq.length());
    byte[] novoBa = novo.toBytes();

    long novaPos = arq.getFilePointer();
    arq.writeByte(0);
    arq.writeShort(novoBa.length);
    arq.write(novoBa);

    indicePKDAO.delete(antigo.getId());
    indicePKDAO.create(novo.getId(), novaPos);

    indiceNomeDAO.delete(nomeAntigo);
    indiceNomeDAO.create(novo.getNome(), novaPos);

    return true;
  }

  // UPDATE por ID - PK
  public boolean updateById(Usuario novo) throws Exception {
    Usuario antigo = readById(novo.getId());
    if (antigo == null) return false;

    long pos = indicePKDAO.read(novo.getId());

    if (pos == -1)
      return false;

    arq.seek(pos);
    byte lapide = arq.readByte();

    if (lapide == 1)
      return false;

    arq.seek(pos);
    arq.writeByte(1);

    arq.seek(arq.length());
    byte[] novoBa = novo.toBytes();

    long novaPos = arq.getFilePointer();
    arq.writeByte(0);
    arq.writeShort(novoBa.length);
    arq.write(novoBa);


    indicePKDAO.delete(novo.getId());
    indicePKDAO.create(novo.getId(), novaPos);
    indiceNomeDAO.delete(antigo.getNome());
    indiceNomeDAO.create(novo.getNome(), novaPos);

    return true;
  }

  // DELETE por nome 
  public boolean delete(String nome) throws Exception {
    Usuario u = read(nome);
    if (u == null) return false;

    long pos = indiceNomeDAO.read(nome);

    if (pos == -1) return false;

    arq.seek(pos);
    byte lapide = arq.readByte();
    if (lapide == 1) return false;

    arq.seek(pos);
    arq.writeByte(1);

    indiceNomeDAO.delete(nome);
    indicePKDAO.delete(u.getId());

    return true;
  }

  // DELETE por ID — PK
  public boolean deleteById(int id) throws Exception {
    Usuario u = readById(id);
    if (u == null) return false;

    long pos = indicePKDAO.read(id);

    arq.seek(pos);
    byte lapide = arq.readByte();
    if (lapide == 1) return false;

    arq.seek(pos);
    arq.writeByte(1);

    indicePKDAO.delete(id);
    indiceNomeDAO.delete(u.getNome());

    return true;

  }

  public List<Usuario> readAll() throws Exception {
    List<Usuario> lista = new ArrayList<>();

    arq.seek(4);

    while (arq.getFilePointer() < arq.length()) {
        byte lapide = arq.readByte();
        short tam = arq.readShort();

        byte[] by = new byte[tam];
        arq.readFully(by);

        if (lapide == 0) {
            Usuario u = new Usuario();
            u.fromBytes(by);
            lista.add(u);
        }
    }

    return lista;
  }

  // ── ORDENAÇÃO ─────────────────────────────────────

  public void ordenarPorNome() throws Exception {
    Path pastaTemp = Files.createTempDirectory("OrdenacaoTemp");
    Path[] caminhos = {
        pastaTemp.resolve("arq1.db"), pastaTemp.resolve("arq2.db"),
        pastaTemp.resolve("arq3.db"), pastaTemp.resolve("arq4.db")
    };

    arq.seek(0);
    int ultimoId = arq.readInt();
    arq.seek(4);
    int totalRegistros = 0;

    try (RandomAccessFile out1 = new RandomAccessFile(caminhos[0].toFile(), "rw");
        RandomAccessFile out2 = new RandomAccessFile(caminhos[1].toFile(), "rw")) {

      int destino = 1;

      while (arq.getFilePointer() < arq.length()) {
        Usuario[] array = new Usuario[4];
        int preenchidos = 0;

        while (preenchidos < 4 && arq.getFilePointer() < arq.length()) {
          byte lapide = arq.readByte();
          short tam = arq.readShort();

          if (lapide == 0) {
            byte[] by = new byte[tam];
            arq.readFully(by);
            Usuario u = new Usuario();
            u.fromBytes(by);
            array[preenchidos++] = u;
            totalRegistros++;
          } else {
            arq.skipBytes(tam);
          }
        }

        if (preenchidos > 0) {
          Arrays.sort(array, 0, preenchidos,
              (u1, u2) -> u1.getNome().compareTo(u2.getNome()));

          RandomAccessFile out = (destino == 1) ? out1 : out2;
          for (int i = 0; i < preenchidos; i++) {
            escreverUsuario(out, array[i]);
          }
          destino = (destino == 1) ? 2 : 1;
        }
      }
    }

    int tamBloco = 4;
    boolean nosAuxiliares = true;

    while (tamBloco < totalRegistros) {
      if (nosAuxiliares) {
        mesclarArquivos(caminhos[0], caminhos[1], caminhos[2], caminhos[3], tamBloco);
      } else {
        mesclarArquivos(caminhos[2], caminhos[3], caminhos[0], caminhos[1], tamBloco);
      }
      tamBloco *= 2;
      nosAuxiliares = !nosAuxiliares;
    }

    Path resultadoFinal = nosAuxiliares ? caminhos[0] : caminhos[2];
    copiarParaArquivoOrdenado(resultadoFinal, ultimoId);
  }

  public void mesclarArquivos(Path arqA, Path arqB, Path arqC, Path arqD, int tamBloco) throws Exception {
    try (RandomAccessFile a = new RandomAccessFile(arqA.toFile(), "r");
        RandomAccessFile b = new RandomAccessFile(arqB.toFile(), "r");
        RandomAccessFile c = new RandomAccessFile(arqC.toFile(), "rw");
        RandomAccessFile d = new RandomAccessFile(arqD.toFile(), "rw")) {

      c.setLength(0);
      d.setLength(0);

      boolean destinoC = true;

      while (a.getFilePointer() < a.length() || b.getFilePointer() < b.length()) {
        RandomAccessFile out = destinoC ? c : d;
        int lidosA = 0, lidosB = 0;

        Usuario uA = lerUsuario(a);
        Usuario uB = lerUsuario(b);

        while (lidosA < tamBloco || lidosB < tamBloco) {
          if ((uA != null && lidosA < tamBloco) &&
              (uB == null || lidosB >= tamBloco ||
                  uA.getNome().compareTo(uB.getNome()) <= 0)) {

            escreverUsuario(out, uA);
            lidosA++;
            uA = (lidosA < tamBloco) ? lerUsuario(a) : null;

          } else if (uB != null && lidosB < tamBloco) {

            escreverUsuario(out, uB);
            lidosB++;
            uB = (lidosB < tamBloco) ? lerUsuario(b) : null;

          } else {
            break;
          }
        }
        destinoC = !destinoC;
      }
    }
  }

  public Usuario lerUsuario(RandomAccessFile arq) throws Exception {
    while (arq.getFilePointer() < arq.length()) {
      byte lapide = arq.readByte();
      short tam = arq.readShort();

      if (lapide == 1) {
        arq.skipBytes(tam);
        continue;
      }

      byte[] by = new byte[tam];
      arq.readFully(by);

      Usuario u = new Usuario();
      u.fromBytes(by);
      return u;
    }
    return null;
  }

  public void escreverUsuario(RandomAccessFile arq, Usuario u) throws Exception {
    byte[] by = u.toBytes();
    arq.writeByte(0);
    arq.writeShort(by.length);
    arq.write(by);
  }

  public void copiarParaArquivoOrdenado(Path origem, int ultimoId) throws Exception {
    Path destino = Paths.get("UsuarioOrdenado.db");

    if (Files.exists(destino))
      Files.delete(destino);

    try (RandomAccessFile in = new RandomAccessFile(origem.toFile(), "r");
        RandomAccessFile out = new RandomAccessFile(destino.toFile(), "rw")) {

      out.setLength(0);
      out.seek(0);
      out.writeInt(ultimoId);

      while (in.getFilePointer() < in.length()) {
        Usuario u = lerUsuario(in);
        if (u != null)
          escreverUsuario(out, u);
      }
    }
  }
}
