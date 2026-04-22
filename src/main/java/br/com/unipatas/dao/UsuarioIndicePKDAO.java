package br.com.unipatas.dao;

import br.com.unipatas.index.HashExtensivel;

public class UsuarioIndicePKDAO {

    private HashExtensivel hash;

    public UsuarioIndicePKDAO() throws Exception {
        hash = new HashExtensivel("UsuarioPK");
    }

    public void create(int id, long pos) throws Exception {
        hash.create(id, pos);
    }

    public long read(int id) throws Exception {
        return hash.read(id);
    }

    public void delete(int id) throws Exception {
        hash.delete(id);
    }
}