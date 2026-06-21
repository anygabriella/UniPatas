package br.com.unipatas.controller;

import java.util.List;
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
    // Campo sensível: a senha nunca é gravada em texto puro.
    // Ela é criptografada (XOR + Base64) antes de ser persistida.
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

  public boolean atualizarUsuarioPorId(int id, String nomeNovo, String cpf, String email, String senha, String telefone,
      String cidade, String estado) throws Exception {
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

  /**
   * Decide qual senha (já criptografada) deve ser persistida em uma
   * atualização: se o usuário digitou uma senha nova, ela é criptografada;
   * caso contrário, mantém-se a senha (já criptografada) que já estava
   * salva, evitando perder a senha original quando o campo é deixado em branco.
   */
  private String resolverSenha(String senhaDigitada, Usuario usuarioAtual) {
    if (senhaDigitada == null || senhaDigitada.isBlank()) {
      return usuarioAtual != null ? usuarioAtual.getSenha() : CriptografiaXOR.criptografar("");
    }
    return CriptografiaXOR.criptografar(senhaDigitada);
  }
}
