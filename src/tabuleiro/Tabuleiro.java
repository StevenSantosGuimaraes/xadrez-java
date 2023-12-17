package tabuleiro;

public class Tabuleiro {
    
    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new ExcecaoNoTabuleiro("Erro ao criar um tabuleiro, é necessário um tamanho no mínimo de 1x1.");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {
        if (!posicaoExiste(linha, coluna)) {
            throw new ExcecaoNoTabuleiro("Posição informada (" + linha + "," + coluna + ") não consta no tabuleiro.");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new ExcecaoNoTabuleiro("Posição informada (" + posicao + ") não consta no tabuleiro.");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void colocarPeca(Peca peca, Posicao posicao) {
        if (haUmaPeca(posicao)) {
            throw new ExcecaoNoTabuleiro("A posição informada: (" + posicao + "), já contem uma peça.");
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public boolean posicaoExiste(Posicao posicao) {
        return posicaoExiste(posicao.getLinha(), posicao.getColuna());
    }

    private boolean posicaoExiste(int linha, int coluna) {
        return linha >= 0 && coluna >= 0 && linha < linhas && coluna < colunas;
    }

    public boolean haUmaPeca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new ExcecaoNoTabuleiro("Posição informada (" + posicao + ") não consta no tabuleiro.");
        }
        return peca(posicao) != null;
    }

    public Peca removerPeca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new ExcecaoNoTabuleiro("Posição informada (" + posicao + ") não consta no tabuleiro.");
        }
        if (peca(posicao) == null) {
            return null;
        }
        Peca auxiliar = peca(posicao);
        auxiliar.posicao = null;
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return auxiliar;
    }

}
