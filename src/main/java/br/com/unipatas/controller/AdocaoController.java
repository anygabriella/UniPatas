package br.com.unipatas.controller;

import br.com.unipatas.dao.AdocaoDAO;
import br.com.unipatas.model.Adocao;

public class AdocaoController {

    private AdocaoDAO dao;

    public AdocaoController() throws Exception {
        dao = new AdocaoDAO();
    }

    // CREATE
    public int cadastrar(int idAnimal, int idUsuario, String data, String status) throws Exception {
        Adocao a = new Adocao(idAnimal, idUsuario, data, status);
        return dao.create(a);
    }

    // READ (PADRONIZADO)
    public Adocao buscar(int id) throws Exception {
        return dao.read(id);
    }

    // UPDATE
    public boolean atualizar(int id, int idAnimal, int idUsuario, String data, String status) throws Exception {

        Adocao existente = dao.read(id);
        if (existente == null) return false;

        Adocao novo = new Adocao(id, idAnimal, idUsuario, data, status);
        return dao.update(novo);
    }

    // DELETE (COM VALIDAÇÃO)
    public boolean deletar(int id) throws Exception {

        Adocao existente = dao.read(id);
        if (existente == null) return false;

        return dao.delete(id);
    }
}