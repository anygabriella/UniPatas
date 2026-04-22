package br.com.unipatas.controller;

import br.com.unipatas.dao.CampanhaDAO;
import br.com.unipatas.model.Campanha;

public class CampanhaController {

    private CampanhaDAO dao;

    public CampanhaController() throws Exception {
        dao = new CampanhaDAO();
    }

    // CREATE (PADRÃO)
    public int cadastrar(String nome, String descricao, String dataInicio, String dataFim) throws Exception {
        Campanha c = new Campanha(nome, descricao, dataInicio, dataFim);
        return dao.create(c);
    }

    // READ por ID (PADRÃO)
    public Campanha buscar(int id) throws Exception {
        return dao.read(id);
    }

    // UPDATE (PADRÃO IGUAL USUARIO/ANIMAL)
    public boolean atualizar(int id, String nome, String descricao, String dataInicio, String dataFim) throws Exception {

        Campanha antiga = dao.read(id);
        if (antiga == null) return false;

        Campanha nova = new Campanha(id, nome, descricao, dataInicio, dataFim);

        return dao.update(nova);
    }

    // DELETE (PADRÃO)
    public boolean remover(int id) throws Exception {
        return dao.delete(id);
    }
}