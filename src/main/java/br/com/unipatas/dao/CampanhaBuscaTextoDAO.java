package br.com.unipatas.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import br.com.unipatas.model.Campanha;
import br.com.unipatas.casamentopadrao.KMP;

public class CampanhaBuscaTextoDAO {

    private CampanhaDAO campanhaDAO;

    public CampanhaBuscaTextoDAO(CampanhaDAO campanhaDAO) {
        this.campanhaDAO = campanhaDAO;
    }

    // Busca campanhas cujo nome contém o termo, usando KMP
    public List<Campanha> buscarPorNomeKMP(String termo) throws Exception {
        List<Campanha> resultado = new ArrayList<>();
        KMP kmp = new KMP(normalizar(termo));

        for (Campanha c : campanhaDAO.readAll()) {
            if (kmp.buscar(normalizar(c.getNome()))) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase(Locale.ROOT);
    }
}
