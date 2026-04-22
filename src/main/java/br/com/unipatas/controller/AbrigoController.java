package br.com.unipatas.controller;

import br.com.unipatas.dao.AbrigoDAO;
import br.com.unipatas.model.Abrigo;

public class AbrigoController {

    private AbrigoDAO dao;

    public AbrigoController() throws Exception {
        dao = new AbrigoDAO();
    }

    // CREATE
    public int cadastrar(String nome, String cidade, String telefone) throws Exception {
        Abrigo a = new Abrigo(nome, cidade, telefone);
        return dao.create(a);
    }

    // READ
    public Abrigo buscar(int id) throws Exception {
        return dao.read(id);
    }

    public boolean atualizar(int id, String nome, String cidade, String telefone) throws Exception {

        Abrigo antigo = dao.read(id);
        if (antigo == null) return false;

        Abrigo novo = new Abrigo(id, nome, cidade, telefone);

        return dao.update(novo);
    }

    // DELETE
    public boolean remover(int id) throws Exception {
        return dao.delete(id);
    }
}