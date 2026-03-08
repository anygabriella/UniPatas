package br.com.unipatas.model;

import java.io.*;

public class Usuario {
    private int id;

    // Identificação Pessoal
    private String nome;
    private String cpf;

    // Login
    private String email;
    private String senha;

    // Contato e Localização
    private String telefone;
    private String cidade;
    private String estado;

    public Usuario() {
        this.nome = "";
        this.cpf = "";
        this.email = "";
        this.senha = "";
        this.telefone = "";
        this.cidade = "";
        this.estado = "";
    }

    public Usuario(String nome, String cpf, String email, String senhaXOR, String telefone, String cidade,
            String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senhaXOR; // a senha já deve ser recebida criptografada
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Usuario(int id, String nome, String cpf, String email, String senhaXOR, String telefone, String cidade,
            String estado) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senhaXOR;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSenha(String senhaXOR) {
        this.senha = senhaXOR;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String mostrar() {
        return "ID: " + id + " | Nome: " + nome + " | CPF: " + cpf + " | Email: " + email + " | Telefone: " + telefone
                + " | Cidade: " + cidade + " | Estado: " + estado;
    }

    // tranformando os dados em bytes
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // cria um arquivo em memoria
        DataOutputStream dos = new DataOutputStream(baos); // permite escrever tipos primitivos

        dos.writeInt(id);
        dos.writeUTF(nome); // ele coloca o tamanho da string antes e depois a string
        dos.writeUTF(cpf);
        dos.writeUTF(email);
        dos.writeUTF(senha);
        dos.writeUTF(telefone);
        dos.writeUTF(cidade);
        dos.writeUTF(estado);

        return baos.toByteArray(); // retorna em bytes
    }

    // fazendo o processo de leitura
    public void fromBytes(byte[] by) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(by); // cria um fluxo de leitura no by
        DataInputStream dis = new DataInputStream(bais); // permite ler tipos primitivos

        id = dis.readInt();
        nome = dis.readUTF();
        cpf = dis.readUTF();
        email = dis.readUTF();
        senha = dis.readUTF();
        telefone = dis.readUTF();
        cidade = dis.readUTF();
        estado = dis.readUTF();
    }
}