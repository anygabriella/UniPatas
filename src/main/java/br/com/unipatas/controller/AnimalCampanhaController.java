package br.com.unipatas.controller;

import br.com.unipatas.dao.AnimalCampanhaDAO;
import java.util.List;

public class AnimalCampanhaController {
    
    private AnimalCampanhaDAO dao;

    public AnimalCampanhaController() throws Exception {
        // Inicializa o DAO otimizado com Árvore B+
        this.dao = new AnimalCampanhaDAO();
    }

    // Cria o vínculo entre o Animal e a Campanha
    public boolean vincular(int idAnimal, int idCampanha) throws Exception {
        return dao.create(idAnimal, idCampanha);
    }

    // Desfaz o vínculo
    public boolean desvincular(int idAnimal, int idCampanha) throws Exception {
        return dao.delete(idAnimal, idCampanha);
    }

    // Traz a lista de Campanhas de um Animal específico
    public List<Integer> buscarCampanhasDoAnimal(int idAnimal) throws Exception {
        return dao.readByAnimal(idAnimal);
    }

    // Traz a lista de Animais de uma Campanha específica
    public List<Integer> buscarAnimaisDaCampanha(int idCampanha) throws Exception {
        return dao.readByCampanha(idCampanha);
    }
}