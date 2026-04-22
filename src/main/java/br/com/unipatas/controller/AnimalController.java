package br.com.unipatas.controller;

import java.util.List;
import br.com.unipatas.dao.AnimalDAO;
import br.com.unipatas.model.Animal;

public class AnimalController {

    private AnimalDAO dao;

    public AnimalController() throws Exception {
        dao = new AnimalDAO();
    }

    // CREATE
    public int salvarAnimal(String nome, int idade, String especie, String raca, int idAbrigo) throws Exception {
        Animal a = new Animal(nome, idade, especie, raca, idAbrigo);
        return dao.create(a);
    }

    // READ por ID
    public Animal buscarAnimal(int id) throws Exception {
        return dao.read(id);
    }

    // READ por abrigo (1:N)
    public List<Animal> listarAnimaisPorAbrigo(int idAbrigo) throws Exception {
        return dao.readByAbrigo(idAbrigo);
    }

    // UPDATE 🔥 (ESSENCIAL)
    public boolean atualizarAnimal(int id, String nome, int idade, String especie, String raca, int idAbrigo) throws Exception {
        Animal a = new Animal(id, nome, idade, especie, raca, idAbrigo);
        return dao.update(a);
    }

    // DELETE
    public boolean deletarAnimal(int id) throws Exception {
        return dao.delete(id);
    }
}