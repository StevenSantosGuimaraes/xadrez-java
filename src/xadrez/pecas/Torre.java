package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Torre extends XadrezPeca {

    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "T";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao possiveisPosicoes = new Posicao(0, 0);
        // Acima
        possiveisPosicoes.setPosicao(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            possiveisPosicoes.setLinha(possiveisPosicoes.getLinha() - 1);
        }
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Direita
        possiveisPosicoes.setPosicao(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            possiveisPosicoes.setColuna(possiveisPosicoes.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Abaixo
        possiveisPosicoes.setPosicao(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            possiveisPosicoes.setLinha(possiveisPosicoes.getLinha() + 1);
        }
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        // Esquerda
        possiveisPosicoes.setPosicao(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(possiveisPosicoes) && !getTabuleiro().haUmaPeca(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
            possiveisPosicoes.setColuna(possiveisPosicoes.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(possiveisPosicoes) && existeUmaPecaAdversariaNaPosicao(possiveisPosicoes)) {
            matriz[possiveisPosicoes.getLinha()][possiveisPosicoes.getColuna()] = true;
        }
        return matriz;
    }
    
}
