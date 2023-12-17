package application;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import xadrez.ExcecaoNoXadrez;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<XadrezPeca> listaCapturadas = new ArrayList<>();
        XadrezPartida xadrezPartida = new XadrezPartida();
        while (!xadrezPartida.getCheckMate()) {
            try {
                UI.limparTerminal();
                UI.imprimirPartida(xadrezPartida, listaCapturadas);
                System.out.print("Origem -> ");
                XadrezPosicao posicaoOrigem = UI.lerPosicaoXadrez(sc);
                boolean[][] movimentosPossiveis = xadrezPartida.possivelMovimentos(posicaoOrigem);
                UI.limparTerminal();
                UI.imprimirTabuleiro(xadrezPartida.getPecas(), movimentosPossiveis);
                System.out.print("\nDestino -> ");
                XadrezPosicao posicaoDestino = UI.lerPosicaoXadrez(sc);
                XadrezPeca pecaCapturada = xadrezPartida.realizarMovimento(posicaoOrigem, posicaoDestino);
                if (pecaCapturada != null) {
                    listaCapturadas.add(pecaCapturada);
                }
            } catch (ExcecaoNoXadrez e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limparTerminal();
        UI.imprimirPartida(xadrezPartida, listaCapturadas);
    }
}