package xadrez;

import tabuleiro.ExcecaoNoTabuleiro;

public class ExcecaoNoXadrez extends ExcecaoNoTabuleiro {
 
    private static final long serialVersionUID = 1L;

    public ExcecaoNoXadrez(String mensgem) {
        super(mensgem);
    }

}
