package com.example;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import java.io.IOException;
import java.nio.channels.Pipe.SourceChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String projectId = "seuprojetoid"; // sua id
        String location = "us-central1";
        String modelName = "gemini-1.5-flash-001";

        String palavra = simpleQuestion(projectId, location, modelName,
                "retorne apenas uma palavra sem acento para ser usado jogo da  forca")
                .toLowerCase().trim();
        // String output = simpleQuestion(projectId, location, modelName,
        // "retorne apenas o desenho de um cachorro usando asc ");
        System.out.println(palavra);

        Scanner teclado = new Scanner(System.in);

        int vidasRestantes = 6;
        String letrasCertas = "";
        String letrasTentadas = "";
        char letraChutada;

        // String palavra = "treme";

        // contagem de vidas
        do {
            System.out.println("\n\nVidas Restantes: " + vidasRestantes);
            System.out.println("Letras já Tentadas: " + letrasTentadas + "\n");

            exibeForca(vidasRestantes);
            exibePalavraTela(palavra, letrasCertas);

            System.out.println("\n\n");
            System.out.print("\nDigite uma letra: ");
            // TODO 02: caso o usuario digite uma letra ja tentada, peça uma nova letra
            // ate que seja informado uma letra que ainda nao foi tentada

            letraChutada = teclado.nextLine().toLowerCase().charAt(0);

            if (!letrasTentadas.contains(String.valueOf(letraChutada))) {

                // atualiza a lista de letras tentadas
                letrasTentadas += letraChutada;

                // atualiza a lista de letras certas
                if (acertouLetra(palavra, letraChutada))
                    letrasCertas += letraChutada;
                else
                    // tira uma vida
                    vidasRestantes--;

                String resultado = exibePalavraTela(palavra, letrasCertas);

                if (resultado.length() == palavra.length()) {
                    vidasRestantes = 0;
                    String output = simpleQuestion(projectId, location, modelName,
                            " Faça uma arte em ASCII que represente uma imagem da palavra :" + palavra
                                    + "  e retorne exatamente uma imagem em ASCII");
                    System.out.println("");
                    System.out.println(output);
                }

                // TODO 03: implemente um bloco de código que verifique
                // se o usuario já descobriu todas as letras da palavra.
                // Uma possibilidade seria aproveitar o exibePalavraTela
                // para descobrir isto. Provavelmente tenha que mudar de void
                // para um metodo que retorna o valor

                System.out.println();
            } else {
                System.out.printf("Esta letra já foi digitada %s . Tente novamente", letraChutada);
            }
        } while (vidasRestantes > 0);

        // TODO 04: implemente aqui neste ponto, um bloco de comandos
        // que informe uma mensagem dizendo se o jogador ganhou ou perdeu
        // se ele perdeu além da mensagem deve ser exibido a forca completa

        teclado.close();

    }

    public static String simpleQuestion(String projectId, String location, String modelName, String msg)
            throws IOException {

        // Initialize client that will be used to send requests. This client only needs
        // to be created once, and can be reused for multiple requests.
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {

            String output;
            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            // Send the question to the model for processing.
            GenerateContentResponse response = model.generateContent(msg);
            // Extract the generated text from the model's response.
            output = ResponseHandler.getText(response);
            return output;

        }

    }

    static boolean acertouLetra(String palavra, char letraChutada) {
        return palavra.contains(Character.toString(letraChutada));
    }

    static String exibePalavraTela(String palavraSecreta, String letrasCertas) {
        char letra;
        String palavraResolvida = "";
        System.out.println("\n\n");

        for (int posicaoLetra = 0; posicaoLetra < palavraSecreta.length(); posicaoLetra++) {
            letra = palavraSecreta.charAt(posicaoLetra);

            if (letrasCertas.contains(Character.toString(letra))) {
                palavraResolvida = palavraResolvida + letra;
                System.out.print("  _" + letra + "_  ");
            } else {
                System.out.print("  ____  ");
            }

        }
        return palavraResolvida;
    }

    static void exibeForca(int contagemErro) {
        switch (contagemErro) {
            case 6:
                System.out.println("""
                            +---+
                            |   |
                                |
                                |
                                |
                                |
                        ========= """);
                break;
            case 5:
                System.out.println("""
                            +---+
                            |   |
                            O   |
                                |
                                |
                                |
                        ========= """);
                break;
            case 4:
                System.out.println("""
                            +---+
                            |   |
                            O   |
                            |   |
                                |
                                |
                        ========= """);
                break;
            case 3:
                System.out.println("""
                            +---+
                            |   |
                            O   |
                           /|   |
                                |
                                |
                        ========= """);
                break;
            case 2:
                System.out.println("""
                            +---+
                            |   |
                            O   |
                           /|\\ |
                                |
                                |
                        ========= """);
                break;
            case 1:
                System.out.println("""
                            +---+
                            |   |
                            O   |
                           /|\\ |
                           /    |
                                |
                        ========= """);
                break;
            case 0:
                System.out.println("""
                            +---+
                            |   |
                            O   |
                           /|\\ |
                           / \\ |
                                |
                        ========= """);
                break;

            // TODO 01: implemente aqui o desenho da forca quando a
            // qtde de vidas restantes for 5, 4, 3, 2, 1
            default:
                break;
        }
    }

    // +---+
    // | |
    // |
    // |
    // |
    // |
    // =========

    // +---+
    // | |
    // O |
    // |
    // |
    // |
    // =========

    // +---+
    // | |
    // O |
    // | |
    // |
    // |
    // =========

    // +---+
    // | |
    // O |
    // /| |
    // |
    // |
    // =========

    // +---+
    // | |
    // O |
    // /|\\ |
    // |
    // |
    // =========

    // +---+
    // | |
    // O |
    // /|\\ |
    // / |
    // |
    // =========

    // +---+
    // | |
    // O |
    // /|\\ |
    // / \\ |
    // |
    // =========

}