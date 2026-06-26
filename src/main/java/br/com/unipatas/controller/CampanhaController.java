package br.com.unipatas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.unipatas.casamentopadrao.KMP;
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
    
    public List<Campanha> listarTodos() throws Exception {
        return dao.readAll();
    }

    public List<Campanha> buscarPorFiltro(String filtro) throws Exception {
        String termo = normalizar(filtro);
        List<Campanha> campanhas = listarTodos();

        if (termo.isEmpty()) {
            return campanhas;
        }

        List<Campanha> encontradas = new ArrayList<>();
        KMP kmp = new KMP(termo);

        for (Campanha campanha : campanhas) {
            boolean nomeEncontradoComKMP = kmp.buscar(normalizar(campanha.getNome()));

            if (contem(String.valueOf(campanha.getId()), termo)
                    || nomeEncontradoComKMP
                    || contem(campanha.getLocal(), termo)
                    || contem(campanha.getData(), termo)
                    || contem(String.valueOf(campanha.getCusto()), termo)) {
                encontradas.add(campanha);
            }
        }

        return encontradas;
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase(Locale.ROOT);
    }

    private boolean contem(String valor, String termo) {
        return valor != null && valor.toLowerCase(Locale.ROOT).contains(termo);
    }
}
