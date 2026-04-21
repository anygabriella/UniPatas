package br.com.unipatas;

import java.util.*;

import br.com.unipatas.dao.UsuarioDAO;
import br.com.unipatas.model.Usuario;

public class MainConsole {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            int opcao;
            do {
                System.out.println("\n--- MENU CRUD USUÁRIO ---");
                System.out.println("1 - Criar");
                System.out.println("2 - Ler");
                System.out.println("3 - Alterar");
                System.out.println("4 - Remover");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                opcao = sc.nextInt();
                sc.nextLine();
                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Senha: ");
                        String senha = sc.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();
                        System.out.print("Cidade: ");
                        String cidade = sc.nextLine();
                        System.out.print("Estado: ");
                        String estado = sc.nextLine();

                        Usuario i = new Usuario(nome, cpf, email, senha, telefone, cidade, estado);
                        int novoUsuarioID = usuarioDAO.create(i);

                        System.out.println("---Usuário criado com sucesso---");
                        System.out.println("ID: " + novoUsuarioID);
                        System.out.println("Nome: " + nome);

                        break;
                    case 2:
                        System.out.print("Nome do usuário: ");
                        String nomeBusca = sc.nextLine();
                        Usuario busca = usuarioDAO.read(nomeBusca);
                        if (busca != null) {
                            System.out.println("ID: " + busca.getId());
                            System.out.println("Nome: " + busca.getNome());
                            System.out.println("CPF: " + busca.getCpf());
                            System.out.println("Email: " + busca.getEmail());
                            System.out.println("Telefone: " + busca.getTelefone());
                            System.out.println("Cidade: " + busca.getCidade());
                            System.out.println("Estado: " + busca.getEstado());
                        } else {
                            System.out.println("---Usuário não encontrado---");
                        }
                        break;
                    case 3:
                        System.out.print("Nome do usuário para alteração: ");
                        String nomeUpdate = sc.nextLine();
                        Usuario usuarioUpdate = usuarioDAO.read(nomeUpdate);

                        if (usuarioUpdate != null) {
                            System.out.println("---Novos dados---");
                            System.out.print("Nome: ");
                            nome = sc.nextLine();
                            System.out.print("CPF: ");
                            cpf = sc.nextLine();
                            System.out.print("Email: ");
                            email = sc.nextLine();
                            System.out.print("Senha: ");
                            senha = sc.nextLine();
                            System.out.print("Telefone: ");
                            telefone = sc.nextLine();
                            System.out.print("Cidade: ");
                            cidade = sc.nextLine();
                            System.out.print("Estado: ");
                            estado = sc.nextLine();
                            Usuario usuarioAtualizado = new Usuario(usuarioUpdate.getId(), nome, cpf, email, senha, telefone, cidade,
                                    estado);

                            boolean verificaUpdate = usuarioDAO.update(usuarioAtualizado, usuarioUpdate.getNome());
                            if (verificaUpdate) {
                                System.out.println("---Usuário atualizado com sucesso---");
                            } else {
                                System.out.println("---Erro na atualização---");
                            }
                        } else {
                            System.out.println("---Usuário não encontrado---");
                        }
                        break;
                    case 4:
                        System.out.print("Nome do usuário para remoção: ");
                        String nomeRemovido = sc.nextLine();
                        Usuario usuarioDelete = usuarioDAO.read(nomeRemovido);
                        if (usuarioDelete != null) {
                            System.out.println("Deseja realmente excluir " + usuarioDelete.getNome() + "? (S/N)");
                            char resp = sc.nextLine().toUpperCase().charAt(0);
                            if (resp == 'S') {
                                usuarioDAO.delete(nomeRemovido);
                                System.out.println("---Usuário Removido---");
                            } else if (resp == 'N') {
                                System.out.println("---Operação cancelada---");
                            } else {
                                System.out.println("---Comando Inválido---");
                            }

                        } else {
                            System.out.println("---Usuário não encontrado---");
                        }
                        break;
                    case 0:

                        break;
                    default:
                        System.out.println("---Comando Inválido---");
                        break;
                }

            } while (opcao != 0);

        } catch (Exception e) {
            System.err.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }

        sc.close();
    }
}