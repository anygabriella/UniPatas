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

    public Usuario buscarUsuario(int id) throws Exception {
        return usuarioDAO.read(id);
    }


    public boolean atualizarUsuario(int id, String nome, String cpf, String email, String senha, String telefone, String cidade, String estado) throws Exception {
        // Cria um objeto com os novos dados, mas mantem o ID original
        Usuario usuarioModificado = new Usuario(id, nome, cpf, email, senha, telefone, cidade, estado);
        
        // Manda o DAO atualizar no arquivo binário
        return usuarioDAO.update(usuarioModificado);
    }

    public boolean deletarUsuario(int id) throws Exception {
        return usuarioDAO.delete(id);
    }
}