package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Cavalo extends XadrezPeca {

    public Cavalo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "C";
    }

    private boolean podeMover(Posicao posicao) {
        XadrezPeca peca = (XadrezPeca) getTabuleiro().peca(posicao);
        return peca == null || peca.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao possiveisPosicoes = new Posicao(0, 0);
        // L1
        possiveisPosicoes.setPosicao(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // L2
        possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // L3
        possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // L4
        possiveisPosicoes.setPosicao(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // L5
        possiveisPosicoes.setPosicao(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // L6
        possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // L7
        possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // L8
        possiveisPosicoes.setPosicao(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && podeMover(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }

        return matriz;
    }
    
}
