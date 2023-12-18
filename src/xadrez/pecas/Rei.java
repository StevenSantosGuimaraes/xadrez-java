package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {

    private XadrezPartida xadrezPartida;

    public Rei(Tabuleiro tabuleiro, Cor cor, XadrezPartida xadrezPartida) {
        super(tabuleiro, cor);
        this.xadrezPartida = xadrezPartida;
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean testeTorreAptaParaRoque(Posicao posicao) {
        XadrezPeca peca = (XadrezPeca) getTabuleiro().peca(posicao);
        return peca != null && peca instanceof Torre && peca.getCor() == getCor() && peca.getQuantidadeMovimento() == 0;
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
        // Jogada Especial (Roque)
        if (getQuantidadeMovimento() == 0 && !xadrezPartida.getCheck()) {
            // Roque Pequeno
            Posicao posicaoTorreLadoRei = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testeTorreAptaParaRoque(posicaoTorreLadoRei)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                    matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
            // Roque Grande
            Posicao posicaoTorreLadoRainha = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testeTorreAptaParaRoque(posicaoTorreLadoRainha)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
                    matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }
        return matriz;
    }
    
}
