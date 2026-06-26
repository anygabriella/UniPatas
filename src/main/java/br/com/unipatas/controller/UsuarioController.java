package br.com.unipatas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import br.com.unipatas.dao.UsuarioDAO;
import br.com.unipatas.model.Usuario;
import br.com.unipatas.util.CriptografiaXOR;

public class UsuarioController {

  private UsuarioDAO usuarioDAO;

  public UsuarioController() throws Exception {
    this.usuarioDAO = new UsuarioDAO();
  }

  public int salvarUsuario(String nome, String cpf, String email, String senha, String telefone, String cidade,
      String estado) throws Exception {
    String senhaCriptografada = CriptografiaXOR.criptografar(senha);
    Usuario novoUsuario = new Usuario(nome, cpf, email, senhaCriptografada, telefone, cidade, estado);

    return usuarioDAO.create(novoUsuario);
  }

  public Usuario buscarUsuario(String nome) throws Exception {
    return usuarioDAO.read(nome);
  }

  public Usuario buscarUsuarioPorId(int id) throws Exception {
    return usuarioDAO.readById(id);
  }

  public boolean atualizarUsuarioPorId(int id, String nomeNovo, String cpf, String email, String senha,
      String telefone, String cidade, String estado) throws Exception {
    String senhaParaSalvar = resolverSenha(senha, usuarioDAO.readById(id));
    Usuario usuarioModificado = new Usuario(id, nomeNovo, cpf, email, senhaParaSalvar, telefone, cidade, estado);
    return usuarioDAO.updateById(usuarioModificado);
  }

  public boolean deletarUsuarioPorId(int id) throws Exception {
    return usuarioDAO.deleteById(id);
  }

  public boolean atualizarUsuario(String nomeAntigo, int id, String nomeNovo, String cpf, String email, String senha,
      String telefone, String cidade, String estado) throws Exception {
    String senhaParaSalvar = resolverSenha(senha, usuarioDAO.read(nomeAntigo));
    Usuario usuarioModificado = new Usuario(id, nomeNovo, cpf, email, senhaParaSalvar, telefone, cidade, estado);
    return usuarioDAO.update(usuarioModificado, nomeAntigo);
  }

  public boolean deletarUsuario(String nome) throws Exception {
    return usuarioDAO.delete(nome);
  }

  public List<Usuario> listarTodos() throws Exception {
    return usuarioDAO.readAll();
  }

  public List<Usuario> buscarPorFiltro(String filtro) throws Exception {
    String termo = normalizar(filtro);
    List<Usuario> usuarios = listarTodos();

    if (termo.isEmpty()) {
      return usuarios;
    }

    List<Usuario> encontrados = new ArrayList<>();
    for (Usuario usuario : usuarios) {
      if (contem(String.valueOf(usuario.getId()), termo)
          || contem(usuario.getNome(), termo)
          || contem(usuario.getCpf(), termo)
          || contem(usuario.getEmail(), termo)
          || contem(usuario.getTelefone(), termo)
          || contem(usuario.getCidade(), termo)
          || contem(usuario.getEstado(), termo)) {
        encontrados.add(usuario);
      }
    }
    return encontrados;
  }

  private String resolverSenha(String senhaDigitada, Usuario usuarioAtual) {
    if (senhaDigitada == null || senhaDigitada.isBlank()) {
      return usuarioAtual != null ? usuarioAtual.getSenha() : CriptografiaXOR.criptografar("");
    }
    return CriptografiaXOR.criptografar(senhaDigitada);
  }

  private String normalizar(String valor) {
    return valor == null ? "" : valor.trim().toLowerCase(Locale.ROOT);
  }

  private boolean contem(String valor, String termo) {
    return valor != null && valor.toLowerCase(Locale.ROOT).contains(termo);
  }
}
