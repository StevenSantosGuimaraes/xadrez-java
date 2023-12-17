package application;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void imprimirTabuleiro(XadrezPeca[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void imprimirTabuleiro(XadrezPeca[][] pecas, boolean[][] movimentosPossiveis) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void imprimirPeca(XadrezPeca peca, boolean destacar) {
        if (destacar) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (peca == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (peca.getCor() == Cor.BRANCA) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    public static XadrezPosicao lerPosicaoXadrez(Scanner sc) {
        try {
            String posicao = sc.nextLine();
            char coluna = posicao.charAt(0);
            int linha = Integer.parseInt(posicao.substring(1));
            return new XadrezPosicao(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler possição no xadrez, use valores entre a1 e h8.");
        }
    }

    public static void imprimirPartida(XadrezPartida xadrezPartida, List<XadrezPeca> listaCapituradas) {
        imprimirTabuleiro(xadrezPartida.getPecas());
        imprimirPecasCapturadas(listaCapituradas);
        if (!xadrezPartida.getCheckMate()) {
            System.out.println("\nTurno (" + xadrezPartida.getTurno() + "), Peças (" + xadrezPartida.getCorVez() + "):");
            if (xadrezPartida.getCheck()) {
                System.out.println("Jogador em CHECK!");
            }
        } else {
            System.out.println("CHECK-MATE!");
            System.out.println("Vitória das peças: " + xadrezPartida.getCorVez() + ".");
        }
        
    }

    public static void imprimirPecasCapturadas(List<XadrezPeca> listaPecasCapturadas) {
        List<XadrezPeca> listaPecasBranca = listaPecasCapturadas.stream().filter(x -> x.getCor() == Cor.BRANCA).collect(Collectors.toList());
        List<XadrezPeca> listaPecasPreta = listaPecasCapturadas.stream().filter(x -> x.getCor() == Cor.PRETA).collect(Collectors.toList());
        System.out.println("\nPeças capturadas:");
        System.out.println("> Brancas: " + ANSI_WHITE + Arrays.toString(listaPecasBranca.toArray()) + ANSI_RESET);
        System.out.println("> Pretas: " + ANSI_YELLOW + Arrays.toString(listaPecasPreta.toArray()) + ANSI_RESET);
    }

    public static void limparTerminal() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                System.out.println("Não foi possível determinar o sistema operacional para limpar o terminal.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
