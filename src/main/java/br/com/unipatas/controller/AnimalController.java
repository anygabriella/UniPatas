package br.com.unipatas.controller;

import java.util.ArrayList;
import java.util.List;
import br.com.unipatas.dao.AnimalDAO;
import br.com.unipatas.model.Animal;

public class AnimalController {

    private AnimalDAO dao;

    public AnimalController() throws Exception {
        dao = new AnimalDAO();
    }

    public int salvar(String nome, String raca, String porte,
            float peso, String dataAdocao, int idAbrigo) throws Exception {

        Animal a = new Animal(nome, raca, porte, peso, dataAdocao, idAbrigo);
        return dao.create(a);
    }

    public Animal buscar(int id) throws Exception {
        return dao.read(id);
    }

    public List<Animal> listarPorAbrigo(int idAbrigo) throws Exception {
        return dao.readByAbrigo(idAbrigo);
    }

    public boolean atualizar(int id, String nome, String raca, String porte,
            float peso, String dataAdocao, int idAbrigo) throws Exception {

        Animal a = new Animal(id, nome, raca, porte, peso, dataAdocao, idAbrigo);
        return dao.update(a);
    }

    // DELETE
    public boolean deletar(int id) throws Exception {
        return dao.delete(id);
    }

    public List<Animal> listarTodos() throws Exception {
        return dao.readAll();
    }

    // Método que a tela vai chamar para pesquisar a Raça usando Boyer-Moore
    public List<Animal> buscarPorRacaBoyerMoore(String padraoDaBusca) throws Exception {

        List<Animal> todosOsAnimais = this.listarTodos();
        List<Animal> animaisEncontrados = new ArrayList<>();

        if (padraoDaBusca == null || padraoDaBusca.trim().isEmpty()) {
            return animaisEncontrados; 
        }

        br.com.unipatas.casamentopadrao.BoyerMoore bm = new br.com.unipatas.casamentopadrao.BoyerMoore(padraoDaBusca.toLowerCase());

        for (Animal animal : todosOsAnimais) {
            String racaDoAnimal = animal.getRaca();

            if (racaDoAnimal != null) {
                if (bm.buscar(racaDoAnimal.toLowerCase())) {
                    animaisEncontrados.add(animal); 
                }
            }
        }

        return animaisEncontrados;
    }
}