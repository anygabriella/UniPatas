package br.com.unipatas.controller;

import br.com.unipatas.dao.UsuarioDAO;
import br.com.unipatas.model.Usuario;

public class UsuarioController {

  private UsuarioDAO usuarioDAO;

  public UsuarioController() throws Exception {
    
    this.usuarioDAO = new UsuarioDAO();
  }

  
  public int salvarUsuario(String nome, String cpf, String email, String senha, String telefone, String cidade,
      String estado) throws Exception {
    Usuario novoUsuario = new Usuario(nome, cpf, email, senha, telefone, cidade, estado);

    
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
    Usuario usuarioModificado = new Usuario(id, nomeNovo, cpf, email, senha, telefone, cidade, estado);
    return usuarioDAO.updateById(usuarioModificado);
  }

  public boolean deletarUsuarioPorId(int id) throws Exception {
    return usuarioDAO.deleteById(id);
  }

  public boolean atualizarUsuario(String nomeAntigo, int id, String nomeNovo, String cpf, String email, String senha,
      String telefone, String cidade, String estado) throws Exception {
    Usuario usuarioModificado = new Usuario(id, nomeNovo, cpf, email, senha, telefone, cidade, estado);
    return usuarioDAO.update(usuarioModificado, nomeAntigo);
  }

  public boolean deletarUsuario(String nome) throws Exception {
    return usuarioDAO.delete(nome);
  }
}
