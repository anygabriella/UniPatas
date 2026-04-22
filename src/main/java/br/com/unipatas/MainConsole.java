package br.com.unipatas;

import java.util.*;

import br.com.unipatas.dao.*;
import br.com.unipatas.model.*;

public class MainConsole {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            AbrigoDAO abrigoDAO = new AbrigoDAO();
            AnimalDAO animalDAO = new AnimalDAO();

            int opcao;

            do {
                System.out.println("\n===== MENU =====");
                System.out.println("1 - CRUD Usuario");
                System.out.println("2 - Criar Abrigo");
                System.out.println("3 - Criar Animal");
                System.out.println("4 - Listar Animais por Abrigo");
                System.out.println("0 - Sair");

                System.out.print("Escolha: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {

                    case 1:
                        menuUsuario(sc, usuarioDAO);
                        break;

                    case 2:
                        System.out.print("Nome do abrigo: ");
                        String nomeAbrigo = sc.nextLine();

                        System.out.print("Cidade: ");
                        String cidade = sc.nextLine();

                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();

                        int idAbrigo = abrigoDAO.create(
                                new Abrigo(nomeAbrigo, cidade, telefone)
                        );

                        System.out.println("Abrigo criado com ID: " + idAbrigo);
                        break;

                    case 3:
                        System.out.print("Nome do animal: ");
                        String nome = sc.nextLine();

                        System.out.print("Idade: ");
                        int idade = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Espécie: ");
                        String especie = sc.nextLine();

                        System.out.print("Raça: ");
                        String raca = sc.nextLine();

                        System.out.print("ID do Abrigo: ");
                        int idAbrigoAnimal = sc.nextInt();
                        sc.nextLine();

                        int idAnimal = animalDAO.create(
                                new Animal(nome, idade, especie, raca, idAbrigoAnimal)
                        );

                        System.out.println("Animal criado com ID: " + idAnimal);
                        break;

                    case 4:
                        System.out.print("ID do abrigo: ");
                        int idBusca = sc.nextInt();
                        sc.nextLine();

                        List<Animal> lista = animalDAO.readByAbrigo(idBusca);

                        for (Animal a : lista) {
                            System.out.println(a.mostrar());
                        }

                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("Opção inválida");
                }

            } while (opcao != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }

    // reutiliza seu menu antigo
    public static void menuUsuario(Scanner sc, UsuarioDAO usuarioDAO) throws Exception {

        int opcao;

        do {
            System.out.println("\n--- MENU USUARIO ---");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("0 - Voltar");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    Usuario u = new Usuario(nome, "cpf", "email", "123", "tel", "cidade", "estado");

                    int id = usuarioDAO.create(u);

                    System.out.println("Criado com ID: " + id);
                    break;

                case 2:
                    System.out.print("Nome: ");
                    String busca = sc.nextLine();

                    Usuario user = usuarioDAO.read(busca);

                    if (user != null)
                        System.out.println(user.getNome());
                    else
                        System.out.println("Não encontrado");

                    break;
            }

        } while (opcao != 0);
    }
}