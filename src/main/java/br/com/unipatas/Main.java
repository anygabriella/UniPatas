package br.com.unipatas;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            UsuarioDAO dao = new UsuarioDAO();

            System.out.println("--- 1. TESTE DE CRIAÇÃO (CREATE) ---");

            String senhaCripto1 = "senha123"; //futuramente, as senhas devem ser criptografadas antes de serem armazenadas
            String senhaCripto2 = "doguinho456";

            Usuario u1 = new Usuario("João Silva", "123.456.789-00", "joao@email.com", 
                                     senhaCripto1, "11988887777", "São Paulo", "SP");
            
            Usuario u2 = new Usuario("Maria ONG", "987.654.321-11", "contato@ong.org", 
                                     senhaCripto2, "21977776666", "Rio de Janeiro", "RJ");

            int id1 = dao.create(u1);
            int id2 = dao.create(u2);
            System.out.println("Usuário 1 criado com ID: " + id1);
            System.out.println("Usuário 2 criado com ID: " + id2);

            System.out.println("\n--- 2. TESTE DE LEITURA (READ) ---");
            Usuario busca = dao.read(id1);
            if (busca != null) {
                System.out.println("Encontrado: " + busca.mostrar());
            } else {
                System.out.println("Usuário não encontrado.");
            }

            System.out.println("\n--- 3. TESTE DE ATUALIZAÇÃO (UPDATE) ---");
            // Vamos mudar a cidade do João (ID 1)
            if (busca != null) {
                busca.setCidade("Campinas");
                boolean atualizou = dao.update(busca);
                System.out.println("Atualização de cidade realizada? " + atualizou);
                
                // Verificar se mudou mesmo
                Usuario buscaNovamente = dao.read(id1);
                System.out.println("Dados atualizados: " + buscaNovamente.mostrar());
            }

            System.out.println("\n--- 4. TESTE DE EXCLUSÃO (DELETE) ---");
            boolean deletou = dao.delete(id2);
            System.out.println("Usuário 2 deletado? " + deletou);

            // Tentar ler o deletado
            Usuario buscaDeletado = dao.read(id2);
            System.out.println("Busca por usuário deletado: " + (buscaDeletado == null ? "Nulo (Correto)" : "Erro"));

        } catch (Exception e) {
            System.err.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}