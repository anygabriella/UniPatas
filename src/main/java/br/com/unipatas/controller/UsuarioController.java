package br.com.unipatas.controller;

import br.com.unipatas.dao.UsuarioDAO;
import br.com.unipatas.model.Usuario;

public class UsuarioController {
    
    private UsuarioDAO usuarioDAO;

    public UsuarioController() throws Exception {
        // Inicializa o acesso ao arquivo binário
        this.usuarioDAO = new UsuarioDAO();
    }

    // Recebe os dados da View e repassa para o Model
    public int salvarUsuario(String nome, String cpf, String email, String senha, String telefone, String cidade, String estado) throws Exception {
        Usuario novoUsuario = new Usuario(nome, cpf, email, senha, telefone, cidade, estado);
        
        // Retorna o ID gerado pelo seu arquivo RandomAccessFile
        return usuarioDAO.create(novoUsuario); 
    }

    public Usuario buscarUsuario(String nome) throws Exception {
        return usuarioDAO.read(nome);
    }


    public boolean atualizarUsuario(String nomeAntigo, int id, String nomeNovo, String cpf, String email, String senha, String telefone, String cidade, String estado) throws Exception {
        Usuario usuarioModificado = new Usuario(id, nomeNovo, cpf, email, senha, telefone, cidade, estado);
        return usuarioDAO.update(usuarioModificado, nomeAntigo);
    }

    public boolean deletarUsuario(String nome) throws Exception {
        return usuarioDAO.delete(nome);
    }
}