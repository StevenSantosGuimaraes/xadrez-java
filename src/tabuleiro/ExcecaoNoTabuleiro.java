package tabuleiro;

public class ExcecaoNoTabuleiro extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcecaoNoTabuleiro(String mensagem) {
        super(mensagem);
    }
    
}
