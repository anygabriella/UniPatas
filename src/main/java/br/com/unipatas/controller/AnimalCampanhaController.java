package br.com.unipatas.controller;

import java.util.List;
import br.com.unipatas.dao.AnimalCampanhaDAO;
import br.com.unipatas.dao.AnimalDAO;
import br.com.unipatas.dao.CampanhaDAO;

public class AnimalCampanhaController {

    private AnimalCampanhaDAO dao;
    private AnimalDAO animalDAO;
    private CampanhaDAO campanhaDAO;

    public AnimalCampanhaController() throws Exception {
        dao = new AnimalCampanhaDAO();
        animalDAO = new AnimalDAO();
        campanhaDAO = new CampanhaDAO();
    }

    public boolean vincular(int idAnimal, int idCampanha) throws Exception {

        if (animalDAO.read(idAnimal) == null) return false;
        if (campanhaDAO.read(idCampanha) == null) return false;

        return dao.create(idAnimal, idCampanha);
    }

    public List<Integer> listarCampanhasDoAnimal(int idAnimal) throws Exception {
        return dao.readByAnimal(idAnimal);
    }

    public List<Integer> listarAnimaisDaCampanha(int idCampanha) throws Exception {
        return dao.readByCampanha(idCampanha);
    }

    public boolean desvincular(int idAnimal, int idCampanha) throws Exception {
        return dao.delete(idAnimal, idCampanha);
    }
}