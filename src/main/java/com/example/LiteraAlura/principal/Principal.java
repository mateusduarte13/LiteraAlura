package com.example.LiteraAlura.principal;


import com.example.LiteraAlura.Api.AutorApi;
import com.example.LiteraAlura.Api.ConsumirApi;
import com.example.LiteraAlura.Api.ConverterDados;
import com.example.LiteraAlura.Api.LivroApi;
import com.example.LiteraAlura.Model.DadosLivro;
import com.example.LiteraAlura.Model.Gutendex;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class Principal {
    Scanner scanner = new Scanner(System.in);
    ConsumirApi consumoApi = new ConsumirApi();
    ConverterDados converterDados = new ConverterDados();


    @Autowired
    private LivroApi livroApi;
    @Autowired
    private AutorApi autorApi;
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public void exibirMenu() {

        int opcao = -1;
        while (opcao != 0) {
            String menu = """
                    ----------------------------
                    Escolha o número de sua opção:
                    1- buscar livro pelo título
                    2- listar livros registrados
                    3- listar autores registrados
                    4- listar autores vivos em um determinado ano
                    5- listar livros em um determinado idioma
                    6- buscar autor pelo nome
                    0- sair                                 
                    ----------------------------
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPeloTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEmUmDeterminadoAno();
                case 5 -> listarLivrosEmUmDeterminadoIdioma();
                case 6 -> buscarAutorPeloNome();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void buscarAutorPeloNome() {
        System.out.println("Digite o nome do autor que deseja buscar: ");
        String autor = scanner.nextLine();

        // buscar autor pelo nome no banco de dados
        String autorEncontrado = autorApi.buscarAutorPeloNome(autor);

        System.out.println(Objects.requireNonNullElse(autorEncontrado, "Autor não encontrado"));
    }

    private void listarLivrosEmUmDeterminadoIdioma() {

        System.out.println("Escolha o idioma que deseja buscar: ");
        System.out.println("""
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                de - alemão
                """);

        String idioma = scanner.nextLine();

        List<String> livrosPorIdioma = livroApi.listarLivrosPorIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado");
        } else {
            livrosPorIdioma.forEach(System.out::println);
        }

    }




    private void listarAutoresVivosEmUmDeterminadoAno() {
        System.out.println("Digite o ano que deseja buscar: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        List<String> autoresVivos = autorApi.listarAutoresVivosEmUmDeterminadoAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado");
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<String> autoresRegistrados = autorApi.listarAutores();

        if (autoresRegistrados.isEmpty()) {
            System.out.println("Nenhum autor cadastrado");
        } else {
            autoresRegistrados.forEach(System.out::println);
        }
    }

    private void listarLivrosRegistrados() {
        List<String> livrosRegistrados =  livroApi.listarLivros();

        if (livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro cadastrado");
        } else {
            livrosRegistrados.forEach(System.out::println);
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o livro que deseja buscar: ");
        String livro = scanner.nextLine();

        String dados = consumoApi.obterDados(ENDERECO + livro.replace(" ", "%20"));
        Gutendex.DadosGutendex dadosGutendex = converterDados.obterDados(dados, Gutendex.DadosGutendex.class);

        if (dadosGutendex.results() != null && !dadosGutendex.results().isEmpty()) {
            for (DadosLivro dadosLivro : dadosGutendex.results()) {
                System.out.println(dadosLivro);

                if (livroApi.buscarLivroPeloTitulo(dadosLivro.title()).isPresent()) {
                    System.out.println("Livro já cadastrado");
                } else {
                    livroApi.salvarLivro(dadosLivro);
                    System.out.println("Livro cadastrado com sucesso");
                }
            }
        } else {
            System.out.println("Livro não encontrado");
        }
    }
}








