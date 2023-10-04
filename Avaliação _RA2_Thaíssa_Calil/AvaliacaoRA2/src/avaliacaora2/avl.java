package avaliacaora2;

import java.util.Random;
import java.util.Scanner;

//Avaliacao RA2 - Arvore AVL - Thaissa Vitoria Calil - Resolucao de Problemas Estruturados em Computacao

public class avl {
    class Node {
        int dado;
        Node esquerda; 
        Node direita; 
        int altura; 
    }

    private Node raiz;

    // calcula a altura
    private int altura(Node N) {
        if (N == null) {
            return 0; 
        }
        return N.altura; 
    }

    // calcula o maior numero entre dois nos(a e b)
    private int maiorValor(int a, int b) {
        return (a > b) ? a : b; 
    }

    // faz a rotação para direita em um node
    private Node rotacaoD(Node y) {
        Node x = y.esquerda;
        Node T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = maiorValor(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = maiorValor(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    // faz a rotação para esquerda em um node
    private Node rotacaoE(Node x) {
        Node y = x.direita;
        Node T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = maiorValor(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = maiorValor(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    // calcula o equilibrio de um node 
    private int equilibrio(Node N) {
        if (N == null) {
            return 0; 
        }
        return altura(N.esquerda) - altura(N.direita);
    }

    // metodo de inserção de maneira recursiva
    private Node inserir(Node node, int dado) {
        if (node == null) {
          
            node = new Node();
            node.dado = dado;
            node.esquerda = null;
            node.direita = null;
            node.altura = 1;
            return node;
        }

        if (dado < node.dado) {
            node.esquerda = inserir(node.esquerda, dado);
        } else if (dado > node.dado) {
            node.direita = inserir(node.direita, dado);
        } else {
            return node; 
        }

        node.altura = 1 + maiorValor(altura(node.esquerda), altura(node.direita));

        int balanceamento = equilibrio(node);

        if (balanceamento > 1 && dado < node.esquerda.dado) {
            return rotacaoD(node);
        }

        if (balanceamento < -1 && dado > node.direita.dado) {
            return rotacaoE(node);
        }

        if (balanceamento > 1 && dado > node.esquerda.dado) {
            node.esquerda = rotacaoE(node.esquerda);
            return rotacaoD(node);
        }

        if (balanceamento < -1 && dado < node.direita.dado) {
            node.direita = rotacaoD(node.direita);
            return rotacaoE(node);
        }

        return node;
    }
    
    // metodo que deleta um node de maneira recursiva
    private Node deletarNode(Node raiz, int dado) {
        if (raiz == null) {
            return raiz;
        }

        if (dado < raiz.dado) {
            raiz.esquerda = deletarNode(raiz.esquerda, dado);
        } else if (dado > raiz.dado) {
            raiz.direita = deletarNode(raiz.direita, dado);
        } else {
            if ((raiz.esquerda == null) || (raiz.direita == null)) {
                Node temp = null;
                if (temp == raiz.esquerda) {
                    temp = raiz.direita;
                } else {
                    temp = raiz.esquerda;
                }

                if (temp == null) {
                    temp = raiz;
                    raiz = null;
                } else {
                    raiz = temp; 
                }
            } else {             
                Node temp = valorMinimo(raiz.direita);

                raiz.dado = temp.dado;

                raiz.direita = deletarNode(raiz.direita, temp.dado);
            }
        }

        if (raiz == null) {
            return raiz;
        }

        raiz.altura = maiorValor(altura(raiz.esquerda), altura(raiz.direita)) + 1;

        int balanceamento = equilibrio(raiz);

        if (balanceamento > 1 && equilibrio(raiz.esquerda) >= 0) {
            return rotacaoD(raiz);
        }

        if (balanceamento > 1 && equilibrio(raiz.esquerda) < 0) {
            raiz.esquerda = rotacaoE(raiz.esquerda);
            return rotacaoD(raiz);
        }

        if (balanceamento < -1 && equilibrio(raiz.direita) <= 0) {
            return rotacaoE(raiz);
        }

        if (balanceamento < -1 && equilibrio(raiz.direita) > 0) {
            raiz.direita = rotacaoD(raiz.direita);
            return rotacaoE(raiz);
        }

        return raiz;
    }

    // metodo para buscar node na arvore de modo recursivo
    private boolean buscarNaArvore(Node node, int dado) {
        if (node == null) {
            System.out.println("Número não encontrado"); 
            return false;
        }
        if (dado == node.dado) {
            System.out.println("Número " + dado + " encontrado no node com altura " + node.altura); 
            return true;
        }
        if (dado < node.dado) {
            return buscarNaArvore(node.esquerda, dado); 
        }

        return buscarNaArvore(node.direita, dado); 
    }
    
    // mode pre-ordem da arvore
    void ordem(Node node) {
        if (node != null) {
            System.out.print(node.dado + " \n"); 
            ordem(node.esquerda); 
            ordem(node.direita); 
        }
    }

    // acha o menor numero da subarvore
    private Node valorMinimo(Node node) {
        Node atual = node;
        
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    // insere um dado da arvore
    public void inserir(int dado) {
        this.raiz = inserir(this.raiz, dado);
    }

    // exclui um no com um dado especifico
    public void deletar(int dado) {
        this.raiz = deletarNode(this.raiz, dado);
    }

    // busca um dado 
    public boolean buscar(int dado) {
        return buscarNaArvore(this.raiz, dado);
    }

    // imprime a árvore no mode pre-ordem
    public void imprimir() {
        ordem(this.raiz);
    }

    public static void main(String[] args) {
        avl arvore = new avl(); 
        Random gerador = new Random();
        
        for (int i = 0; i < 20000; i++) {
            arvore.inserir(gerador.nextInt(1000000));
        }

        Scanner scanner = new Scanner(System.in);
        boolean sair = false;
        
        while (!sair) {
            System.out.println("--------------------------------");
            System.out.println("Árvore AVL");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Inserir");
            System.out.println("2. Deletar");
            System.out.println("3. Buscar");
            System.out.println("4. Imprimir");
            System.out.println("5. Sair");
            System.out.println("--------------------------------");
            int opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1:
                    long startTime1 = System.nanoTime();
                    System.out.println("Digite um número para inserir na árvore:");
                    int nInserir = scanner.nextInt();
                    arvore.inserir(nInserir);
                    long endTime1 = System.nanoTime();
                    long duration1 = (endTime1 - startTime1) / 1_000_000; 
                    System.out.println("Tempo de execução da inserção: " + duration1 + " ms");
                    break;
                case 2:
                    long startTime2 = System.nanoTime();
                    System.out.println("Digite um número para deletar da árvore:");
                    int nDeletar = scanner.nextInt();
                    arvore.deletar(nDeletar);
                    long endTime2 = System.nanoTime();
                    long duration2 = (endTime2 - startTime2) / 1_000_000; 
                    System.out.println("Tempo de execução da exclusão: " + duration2 + " ms");
                    break;
                case 3:
                    long startTime3 = System.nanoTime();
                    System.out.println("Digite uma posição para descobrir o seu dado:");
                    int nBuscar = scanner.nextInt();
                    arvore.buscar(nBuscar);
                    long endTime3 = System.nanoTime();
                    long duration3 = (endTime3 - startTime3) / 1_000_000; 
                    System.out.println("Tempo de execução da busca: " + duration3 + " ms");
                    break;
                case 4:
                    long startTime4 = System.nanoTime();
                    System.out.println("Imprimindo a árvore AVL:");
                    arvore.imprimir();
                    long endTime4 = System.nanoTime();
                    long duration4 = (endTime4 - startTime4) / 1_000_000; 
                    System.out.println("Tempo de execução da impressão: " + duration4 + " ms");
                    break;
                case 5:
                    sair = true;
                    break;
                default:
                    System.out.println("Erro, Comando não existe.");
            }
        }
        scanner.close();
    }
}