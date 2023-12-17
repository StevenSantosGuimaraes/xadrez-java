package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {

    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao) {
        XadrezPeca peca = (XadrezPeca) getTabuleiro().peca(posicao);
        return peca == null || peca.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao possiveisPosicoes = new Posicao(0, 0);
        // Acima
        possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Acima e Direita
        possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Direita
        possiveisPosicoes.setPosicao(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Abaixo e Direita
        possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Abaixo
        possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Abaixo e Esquerda
        possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Esquerda
        possiveisPosicoes.setPosicao(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Acima e Esquerda
        possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        return matriz;
    }
    
}
