package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca {
    
    private Cor cor;
    private int quantidadeMovimento;

    public XadrezPeca(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getQuantidadeMovimento() {
        return quantidadeMovimento;
    }

    protected boolean existeUmaPecaAdversariaNaPosicao(Posicao posicao) {
        XadrezPeca peca = (XadrezPeca) getTabuleiro().peca(posicao);
        return peca != null && peca.getCor() != cor;
    }

    public XadrezPosicao getXadrezPosicao() {
        return XadrezPosicao.daPosicao(posicao);
    }

    public void incrementarQuantidadeMovimento() {
        quantidadeMovimento++;
    }

    public void decrementarQuantidadeMovimento() {
        quantidadeMovimento--;
    }

}
