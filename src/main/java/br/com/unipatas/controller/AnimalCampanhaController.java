package br.com.unipatas.controller;

import br.com.unipatas.dao.AnimalCampanhaDAO;
import br.com.unipatas.model.Animal;
import br.com.unipatas.model.Campanha;

import java.util.List;

public class AnimalCampanhaController {

    private AnimalCampanhaDAO dao;
    private AnimalController animalController;
    private CampanhaController campanhaController;

    public AnimalCampanhaController() throws Exception {
        this.dao = new AnimalCampanhaDAO();
        this.animalController = new AnimalController();
        this.campanhaController = new CampanhaController();
    }

    public boolean vincular(int idAnimal, int idCampanha) throws Exception {
        validarIds(idAnimal, idCampanha);
        return dao.create(idAnimal, idCampanha);
    }

    public boolean desvincular(int idAnimal, int idCampanha) throws Exception {
        validarIds(idAnimal, idCampanha);
        return dao.delete(idAnimal, idCampanha);
    }

    public List<Integer> buscarCampanhasDoAnimal(int idAnimal) throws Exception {
        if (idAnimal <= 0) {
            throw new IllegalArgumentException("Selecione um animal válido.");
        }

        Animal animal = animalController.buscar(idAnimal);

        if (animal == null) {
            throw new IllegalArgumentException("Animal não encontrado.");
        }

        return dao.readByAnimal(idAnimal);
    }

    public List<Integer> buscarAnimaisDaCampanha(int idCampanha) throws Exception {
        if (idCampanha <= 0) {
            throw new IllegalArgumentException("Selecione uma campanha válida.");
        }

        Campanha campanha = campanhaController.buscar(idCampanha);

        if (campanha == null) {
            throw new IllegalArgumentException("Campanha não encontrada.");
        }

        return dao.readByCampanha(idCampanha);
    }

    private void validarIds(int idAnimal, int idCampanha) throws Exception {
        if (idAnimal <= 0) {
            throw new IllegalArgumentException("Selecione um animal válido.");
        }

        if (idCampanha <= 0) {
            throw new IllegalArgumentException("Selecione uma campanha válida.");
        }

        Animal animal = animalController.buscar(idAnimal);

        if (animal == null) {
            throw new IllegalArgumentException("Animal não encontrado.");
        }

        Campanha campanha = campanhaController.buscar(idCampanha);

        if (campanha == null) {
            throw new IllegalArgumentException("Campanha não encontrada.");
        }
    }
}