package br.com.unipatas.controller;

import br.com.unipatas.dao.CampanhaDAO;
import br.com.unipatas.model.Campanha;

public class CampanhaController {

    private CampanhaDAO dao;

    public CampanhaController() throws Exception {
        dao = new CampanhaDAO();
    }

    
    public int cadastrar(String nome, String local, String data, double custo) throws Exception {
        Campanha c = new Campanha(nome, local, data, custo);
        return dao.create(c);
    }

    
    public Campanha buscar(int id) throws Exception {
        return dao.read(id);
    }

    
    public boolean atualizar(int id, String nome, String local, String data, double custo) throws Exception {

        Campanha antiga = dao.read(id);
        if (antiga == null) return false;

        Campanha nova = new Campanha(id, nome, local, data, custo);

        return dao.update(nova);
    }

    
    public boolean remover(int id) throws Exception {
        return dao.delete(id);
    }
}