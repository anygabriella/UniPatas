package br.com.unipatas.dao;

import java.io.*;
import java.util.*;
import br.com.unipatas.model.Animal;
import br.com.unipatas.index.ParStringInt;
import br.com.unipatas.index.arvore.ArvoreBMais;

public class AnimalIndiceDAO {

    private ArvoreBMais<ParStringInt> indiceRaca;
    private ArvoreBMais<ParStringInt> indicePorte;

    public AnimalIndiceDAO() throws Exception {
        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        indiceRaca  = new ArvoreBMais<>(
            ParStringInt.class.getConstructor(),
            5,
            "data/AnimalRaca.db"
        );
        indicePorte = new ArvoreBMais<>(
            ParStringInt.class.getConstructor(),
            5,
            "data/AnimalPorte.db"
        );
    }

    // CREATE — chama ao inserir um Animal
    public void create(Animal a) throws Exception {
        indiceRaca.create(new ParStringInt(a.getRaca(),  a.getId()));
        indicePorte.create(new ParStringInt(a.getPorte(), a.getId()));
    }

    // READ por raça — retorna lista de IDs
    public List<Integer> readByRaca(String raca) throws Exception {
        List<Integer> ids = new ArrayList<>();
        List<ParStringInt> resultado = indiceRaca.read(new ParStringInt(raca));
        for (ParStringInt p : resultado) ids.add(p.getId());
        return ids;
    }

    // READ por porte — retorna lista de IDs
    public List<Integer> readByPorte(String porte) throws Exception {
        List<Integer> ids = new ArrayList<>();
        List<ParStringInt> resultado = indicePorte.read(new ParStringInt(porte));
        for (ParStringInt p : resultado) ids.add(p.getId());
        return ids;
    }

    // DELETE — chama ao excluir um Animal
    public void delete(Animal a) throws Exception {
        indiceRaca.delete(new ParStringInt(a.getRaca(),  a.getId()));
        indicePorte.delete(new ParStringInt(a.getPorte(), a.getId()));
    }
}