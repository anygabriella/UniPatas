package br.com.unipatas.dao;

import java.io.*;
import java.util.*;
import br.com.unipatas.model.AnimalCampanha;
import br.com.unipatas.index.AnimalCampanhaIndex;
import br.com.unipatas.index.arvore.ArvoreBMais;

public class AnimalCampanhaDAO {

    private RandomAccessFile arq;
    
    // Declarando as duas árvores
    private ArvoreBMais<AnimalCampanhaIndex> arvorePorAnimal;
    private ArvoreBMais<AnimalCampanhaIndex> arvorePorCampanha;

    public AnimalCampanhaDAO() throws Exception {
        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdir();

        arq = new RandomAccessFile("data/AnimalCampanha.db", "rw");

        // Inicializando as árvores (usando ordem 5)
        arvorePorAnimal = new ArvoreBMais<>(AnimalCampanhaIndex.class.getConstructor(), 5, "data/arvore_animal_campanha.idx");
        arvorePorCampanha = new ArvoreBMais<>(AnimalCampanhaIndex.class.getConstructor(), 5, "data/arvore_campanha_animal.idx");
    }

    public boolean create(int idAnimal, int idCampanha) throws Exception {

        if (exists(idAnimal, idCampanha)) {
            return false;
        }

        AnimalCampanha ac = new AnimalCampanha(idAnimal, idCampanha);
        byte[] ba = ac.toBytes();

        long enderecoNoArquivo = arq.length(); 
        
        arq.seek(enderecoNoArquivo);
        arq.writeByte(0); // Lápide válida
        arq.writeShort(ba.length);
        arq.write(ba);

        // Salva nas duas árvores
        arvorePorAnimal.create(new AnimalCampanhaIndex(idAnimal, idCampanha, enderecoNoArquivo));
        arvorePorCampanha.create(new AnimalCampanhaIndex(idCampanha, idAnimal, enderecoNoArquivo));

        return true;
    }

    public boolean exists(int idAnimal, int idCampanha) throws Exception {
        ArrayList<AnimalCampanhaIndex> resultados = arvorePorAnimal.read(new AnimalCampanhaIndex(idAnimal, -1, -1));
        
        for (AnimalCampanhaIndex idx : resultados) {
            if (idx.getIdSecundario() == idCampanha) {
                return true;
            }
        }
        return false;
    }

    public List<Integer> readByAnimal(int idAnimal) throws Exception {
        List<Integer> campanhas = new ArrayList<>();
        
        // Pede a lista para a árvore
        ArrayList<AnimalCampanhaIndex> resultados = arvorePorAnimal.read(new AnimalCampanhaIndex(idAnimal, -1, -1));
        
        for (AnimalCampanhaIndex idx : resultados) {
            campanhas.add(idx.getIdSecundario());
        }
        return campanhas;
    }

    public List<Integer> readByCampanha(int idCampanha) throws Exception {
        List<Integer> animais = new ArrayList<>();
        
        // Pede a lista para a árvore invertida
        ArrayList<AnimalCampanhaIndex> resultados = arvorePorCampanha.read(new AnimalCampanhaIndex(idCampanha, -1, -1));
        
        for (AnimalCampanhaIndex idx : resultados) {
            animais.add(idx.getIdSecundario());
        }
        return animais;
    }

    public boolean delete(int idAnimal, int idCampanha) throws Exception {
        // Usa a árvore para achar exatamente em qual byte o arquivo esta
        ArrayList<AnimalCampanhaIndex> resultados = arvorePorAnimal.read(new AnimalCampanhaIndex(idAnimal, -1, -1));
        
        long enderecoParaExcluir = -1;
        for (AnimalCampanhaIndex idx : resultados) {
            if (idx.getIdSecundario() == idCampanha) {
                enderecoParaExcluir = idx.getEnderecoNoArquivo();
                break;
            }
        }

        if (enderecoParaExcluir != -1) {

            arq.seek(enderecoParaExcluir);
            arq.writeByte(1);

            arvorePorAnimal.delete(new AnimalCampanhaIndex(idAnimal, idCampanha, enderecoParaExcluir));
            arvorePorCampanha.delete(new AnimalCampanhaIndex(idCampanha, idAnimal, enderecoParaExcluir));

            return true;
        }

        return false;
    }
}