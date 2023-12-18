package xadrez;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class XadrezPartida {

    private int turno;
    private Tabuleiro tabuleiro;
    private Cor corVez;
    private boolean check;
    private boolean checkMate;
    private XadrezPeca enPassant;
    private XadrezPeca promocao;

    private List<Peca> listaPecasNoTabuleiro = new ArrayList<>();
    private List<Peca> listaPecasForaDoTabuleiro = new ArrayList<>();

    public XadrezPartida() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        corVez = Cor.BRANCA;
        check = false;
        checkMate = false;
        iniciarPecasNoTabuleiro();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getCorVez() {
        return corVez;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public XadrezPeca getEnPassant() {
        return enPassant;
    }

    public XadrezPeca getPromocao() {
        return promocao;
    }

    public XadrezPeca[][] getPecas() {
        XadrezPeca[][] matriz = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int linha = 0; linha < tabuleiro.getLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getColunas(); coluna++) {
                matriz[linha][coluna] = (XadrezPeca) tabuleiro.peca(linha, coluna);
            }
        }
        return matriz;
    }

    public XadrezPeca realizarMovimento(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino) {
        Posicao origem = posicaoOrigem.paraPosicao();
        Posicao destino = posicaoDestino.paraPosicao();
        validarUmaPecaNaPosicaoDeOrigem(origem);
        validarUmaPecaNaPosicaoDeDestino(origem, destino);
        Peca pecaCapturada = movimentarPeca(origem, destino);
        if (testeCheck(corVez)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new ExcecaoNoXadrez("Você não pode se expor ao Check para o oponente.");
        }
        XadrezPeca pecaMovida = (XadrezPeca) tabuleiro.peca(destino);
        // Joga Especial (Promoção)
        promocao = null;
        if (pecaMovida instanceof Peao) {
            if ((pecaMovida.getCor() == Cor.BRANCA && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETA && destino.getLinha() == 7)) {
                promocao = (XadrezPeca) tabuleiro.peca(destino);
                promocao = substituirPecaPromovida("D");
            }
        }
        if (testeCheck(oponente(corVez))) {
            check = true;
        } else {
            check = false;
        }
        if (testeCheckMate(oponente(corVez))) {
            checkMate = true;
        } else {
            proximoTurno();
        }
        // Jogada Especial (En Passant)
        if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
            enPassant = pecaMovida;
        } else {
            enPassant = null;
        }
        return (XadrezPeca) pecaCapturada;
    }

    public XadrezPeca substituirPecaPromovida(String tipo) {
        if (promocao == null) {
            throw new IllegalStateException("Não há peça a ser promovida.");
        }
        if (!tipo.equals("T") && !tipo.equals("C") && !tipo.equals("B") && !tipo.equals("D")) {
            return promocao;
        }
        Posicao posicaoPecaPromovida = promocao.getXadrezPosicao().paraPosicao();
        Peca peca = tabuleiro.removerPeca(posicaoPecaPromovida);
        listaPecasNoTabuleiro.remove(peca);
        XadrezPeca novaPeca = novaPecaPromocao(tipo, promocao.getCor());
        tabuleiro.colocarPeca(novaPeca, posicaoPecaPromovida);
        listaPecasNoTabuleiro.add(novaPeca);
        return novaPeca;
    }

    private XadrezPeca novaPecaPromocao(String tipo, Cor cor) {
        if (tipo.equals("T")) {
            return new Torre(tabuleiro, cor);
        }
        if (tipo.equals("C")) {
            return new Cavalo(tabuleiro, cor);
        }
        if (tipo.equals("B")) {
            return new Bispo(tabuleiro, cor);
        }
        return new Rainha(tabuleiro, cor);
    }

    private Peca movimentarPeca(Posicao origem, Posicao destino) {
        XadrezPeca peca = (XadrezPeca) tabuleiro.removerPeca(origem);
        peca.incrementarQuantidadeMovimento();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(peca, destino);
        if (pecaCapturada != null) {
            listaPecasNoTabuleiro.remove(pecaCapturada);
            listaPecasForaDoTabuleiro.add(pecaCapturada);
        }
        // Jogada Especial (Roque: pequeno)
        if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao posicaoTorreOrigem = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao posicaoTorreDestino = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            XadrezPeca torreDireita = (XadrezPeca) tabuleiro.removerPeca(posicaoTorreOrigem);
            tabuleiro.colocarPeca(torreDireita, posicaoTorreDestino);
            torreDireita.incrementarQuantidadeMovimento();
        }
        // Jogada Especial (Roque: grande)
        if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao posicaoTorreOrigem = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao posicaoTorreDestino = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            XadrezPeca torreEsquerda = (XadrezPeca) tabuleiro.removerPeca(posicaoTorreOrigem);
            tabuleiro.colocarPeca(torreEsquerda, posicaoTorreDestino);
            torreEsquerda.incrementarQuantidadeMovimento();
        }
        // Jogada Especial (En Passant)
        if (peca instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
                Posicao posicaoPeao = null;
                if (peca.getCor() == Cor.BRANCA) {
                    posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                } else if (peca.getCor() == Cor.PRETA) {
                    posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
                listaPecasForaDoTabuleiro.add(pecaCapturada);
                listaPecasNoTabuleiro.remove(pecaCapturada);
            }
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        XadrezPeca peca = (XadrezPeca) tabuleiro.removerPeca(destino);
        peca.decrementarQuantidadeMovimento();
        tabuleiro.colocarPeca(peca, origem);
        if (pecaCapturada != null) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            listaPecasForaDoTabuleiro.remove(pecaCapturada);
            listaPecasNoTabuleiro.add(pecaCapturada);
        }
        // Jogada Especial (Roque: pequeno)
        if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao posicaoTorreOrigem = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao posicaoTorreDestino = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            XadrezPeca torreDireita = (XadrezPeca) tabuleiro.removerPeca(posicaoTorreDestino);
            tabuleiro.colocarPeca(torreDireita, posicaoTorreOrigem);
            torreDireita.decrementarQuantidadeMovimento();
        }
        // Jogada Especial (Roque: grande)
        if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao posicaoTorreOrigem = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao posicaoTorreDestino = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            XadrezPeca torreEsquerda = (XadrezPeca) tabuleiro.removerPeca(posicaoTorreDestino);
            tabuleiro.colocarPeca(torreEsquerda, posicaoTorreOrigem);
            torreEsquerda.decrementarQuantidadeMovimento();
        }
        // Jogada Especial (En Passant)
        if (peca instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassant) {
                XadrezPeca peao = (XadrezPeca) tabuleiro.removerPeca(destino);
                Posicao posicaoPeao = null;
                if (peca.getCor() == Cor.BRANCA) {
                    posicaoPeao = new Posicao(3, destino.getColuna());
                } else if (peca.getCor() == Cor.PRETA) {
                    posicaoPeao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.colocarPeca(peao, posicaoPeao);
            }
        }
    }

    private void validarUmaPecaNaPosicaoDeOrigem(Posicao posicao) {
        if (!tabuleiro.posicaoExiste(posicao)) {
            throw new ExcecaoNoXadrez("Não há uma peça na posição de origem.");
        }
        if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
            throw new ExcecaoNoXadrez("Não há movimentos possíveis para a peça.");
        }
        if (corVez != ((XadrezPeca) tabuleiro.peca(posicao)).getCor()) {
            throw new ExcecaoNoXadrez("A peça selecionada não pertence ao jogador da vez.");
        }
    }

    private void validarUmaPecaNaPosicaoDeDestino(Posicao origem, Posicao destino) {
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
            throw new ExcecaoNoXadrez("A peça escolhida não pode ser movida ao local de destino.");
        }
    }

    public boolean[][] possivelMovimentos(XadrezPosicao posicaoOrigem) {
        Posicao posicao = posicaoOrigem.paraPosicao();
        validarUmaPecaNaPosicaoDeOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    private void colocarNovaPeca(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).paraPosicao());
        listaPecasNoTabuleiro.add(peca);
    }

    private void proximoTurno() {
        if (corVez == Cor.BRANCA) {
            corVez = Cor.PRETA;
        } else if (corVez == Cor.PRETA) {
            corVez = Cor.BRANCA;
        }
        turno++;
    }

    private Cor oponente(Cor cor) {
        if (cor == Cor.BRANCA) {
            return Cor.PRETA;
        } else if (cor == Cor.PRETA) {
            return Cor.BRANCA;
        }
        return null;
    }

    private XadrezPeca encontrarReiDaCor(Cor cor) {
        List<Peca> lista = listaPecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : lista) {
            if (p instanceof Rei) {
                return (XadrezPeca) p;
            }
        }
        throw new IllegalStateException("Não há uma peça REI para na cor " + cor + ", na partida.");
    }

    private boolean testeCheck(Cor cor) {
        Posicao posicaoRei = encontrarReiDaCor(cor).getXadrezPosicao().paraPosicao();
        List<Peca> pecasDoOponente = listaPecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == oponente(cor)).collect(Collectors.toList());
        for (Peca p : pecasDoOponente) {
            boolean[][] matriz = p.movimentosPossiveis();
            if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testeCheckMate(Cor cor) {
        if (!testeCheck(cor)) {
            return false;
        }
        List<Peca> lista = listaPecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : lista) {
            boolean[][] matriz = p.movimentosPossiveis();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (matriz[i][j]) {
                        Posicao origem = ((XadrezPeca) p).getXadrezPosicao().paraPosicao();
                        Posicao tentativa = new Posicao(i, j);
                        Peca pecaCapturada = movimentarPeca(origem, tentativa);
                        boolean testeAindaEmCheck = testeCheck(cor);
                        desfazerMovimento(origem, tentativa, pecaCapturada);
                        if (!testeAindaEmCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void iniciarPecasNoTabuleiro() {

        colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));

        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA, this));

        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));

        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA, this));

    }
    
}
