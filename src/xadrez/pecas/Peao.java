package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca {

    private XadrezPartida xadrezPartida;

    public Peao(Tabuleiro tabuleiro, Cor cor, XadrezPartida xadrezPartida) {
        super(tabuleiro, cor);
        this.xadrezPartida = xadrezPartida;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao possiveisPosicoes = new Posicao(0, 0);
        if (getCor() == Cor.BRANCA) {
            possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes)) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            possiveisPosicoes.setPosicao(posicao.getLinha() - 2, posicao.getColuna());
            Posicao auxiliar = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes) && getTabuleiro().posicaoExiste(auxiliar) && !getTabuleiro().haUmaPeca(auxiliar) && getQuantidadeMovimento() == 0) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            // Jogada Especial (En Passant)
            if (posicao.getLinha() == 3) {
                Posicao aEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(aEsquerda) && existeUmaPecaAdversariaNaPosicao(aEsquerda) && getTabuleiro().peca(aEsquerda) == xadrezPartida.getEnPassant()) {
                    matriz[aEsquerda.getLinha() - 1][aEsquerda.getColuna()] = true;
                }
                Posicao aDireita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(aDireita) && existeUmaPecaAdversariaNaPosicao(aDireita) && getTabuleiro().peca(aDireita) == xadrezPartida.getEnPassant()) {
                    matriz[aDireita.getLinha() - 1][aDireita.getColuna()] = true;
                }
            }
        } else if (getCor() == Cor.PRETA) {
            possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes)) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            possiveisPosicoes.setPosicao(posicao.getLinha() + 2, posicao.getColuna());
            Posicao auxiliar = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes) && getTabuleiro().posicaoExiste(auxiliar) && !getTabuleiro().haUmaPeca(auxiliar) && getQuantidadeMovimento() == 0) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
                matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            }
            // Jogada Especial (En Passant)
            if (posicao.getLinha() == 4) {
                Posicao aEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(aEsquerda) && existeUmaPecaAdversariaNaPosicao(aEsquerda) && getTabuleiro().peca(aEsquerda) == xadrezPartida.getEnPassant()) {
                    matriz[aEsquerda.getLinha() + 1][aEsquerda.getColuna()] = true;
                }
                Posicao aDireita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(aDireita) && existeUmaPecaAdversariaNaPosicao(aDireita) && getTabuleiro().peca(aDireita) == xadrezPartida.getEnPassant()) {
                    matriz[aDireita.getLinha() + 1][aDireita.getColuna()] = true;
                }
            }
        }
        return matriz;
    }
    
}
