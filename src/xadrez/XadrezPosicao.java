package xadrez;

import tabuleiro.Posicao;

public class XadrezPosicao {
    
    private int linha;
    private char coluna;

    public XadrezPosicao(char coluna, int linha) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
            throw new ExcecaoNoXadrez("Erro ao instanciar a Posicao do Xadrez, use valores entre a1 e h8.");
        }
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public char getColuna() {
        return coluna;
    }
    
    protected Posicao paraPosicao() {
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected static XadrezPosicao daPosicao(Posicao posicao) {
        return new XadrezPosicao((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }

}
