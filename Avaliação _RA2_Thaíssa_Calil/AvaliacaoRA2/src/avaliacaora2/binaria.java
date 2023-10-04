package avaliacaora2;

import java.util.Scanner;
import java.util.Random;

//Avaliacao RA2 - Arvore Binaria - Thaissa Vitoria Calil - Resolucao de Problemas Estruturados em Computacao

// definicao da classe node
class Node {
    int dado;
    Node esquerda;
    Node direita;

    public Node(int dado) {
        this.dado = dado;
        esquerda = direita = null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "dado=" + dado +
                '}';
    }
}

public class binaria {
    static int count = 0;  // contador 
    
    // insere um node na arvore
    public static Node inserir(Node p, int dado){
        if(p == null){
              return new Node(dado);
        } 
        
        if( dado < p.dado){
            p.esquerda = inserir(p.esquerda, dado);
        }
        else if( dado > p.dado){
            p.direita = inserir(p.direita, dado);        
        }
        
        return p;
    }

    // deleta um node da arvore
    public static Node deletar(Node p, int dado) {
        if (p == null) {
            return null;
        }

        if (dado < p.dado) {
            p.esquerda = deletar(p.esquerda, dado);
            return p;
        } else if (dado > p.dado) {
            p.direita = deletar(p.direita, dado);
            return p;
        }

        if (p.esquerda == null && p.direita == null) {
            return null;
        } else if (p.esquerda == null) {
            return p.direita;
        } else if (p.direita == null) {
            return p.esquerda;
        } else {
            int menorND = menorNE(p.direita);
            p.dado = menorND;
            p.direita = deletar(p.direita, menorND);
            return p;
        }
    }

    // calcula o menor numero da subarvore
    public static int menorNE(Node node) {
       if(node.esquerda != null) {
           return menorNE(node.esquerda);
       }
       return node.dado;
    }

    // busca um dado na arvore por posicao
    public static Node buscar(Node node, int posicao) {
        if(node == null) {
            return null;
        }

        Node esquerda = buscar(node.esquerda, posicao);

        if(esquerda != null) {
            return esquerda;
        }

        count++;
        if(count == posicao) {
            return node;
        }

        return buscar(node.direita, posicao);
    }
    
    // imprime a arvore em ordem crescente
    public static void imprimir(Node p){
         if(p != null){
             imprimir(p.esquerda);
             System.out.println(p.dado + " ");
             imprimir(p.direita);
         }
     }
    
    public static void main(String[] args){
        Node p = null;
        Random rand = new Random();
        
        for (int i = 0; i < 20000; i++) {
            int numero = rand.nextInt(1000000); 
            p = inserir(p, numero);
        }

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while(continuar) {
            System.out.println("--------------------------------");
            System.out.println("�rvore Bin�ria");
            System.out.println("Escolha uma op��o:");
            System.out.println("1. Inserir");
            System.out.println("2. Deletar");
            System.out.println("3. Buscar");
            System.out.println("4. Imprimir");
            System.out.println("5. Sair");
            System.out.println("--------------------------------");

            int opcao = scanner.nextInt();

            switch(opcao) {
                case 1:
                    long startTime1 = System.nanoTime();
                    System.out.println("Digite um n�mero para inserir na �rvore:");
                    int numeroInserir = scanner.nextInt();
                    p = inserir(p, numeroInserir);
                    long endTime1 = System.nanoTime();
                    long duration1 = (endTime1 - startTime1) / 1_000_000; 
                    System.out.println("Tempo de execu��o da inser��o: " + duration1 + " ms");
                    break;
                case 2:
                    long startTime2 = System.nanoTime();
                    System.out.println("Digite um n�mero para deletar da �rvore:");
                    int numeroDeletar = scanner.nextInt();
                    p = deletar(p, numeroDeletar);
                    long endTime2 = System.nanoTime();
                    long duration2 = (endTime2 - startTime2) / 1_000_000; 
                    System.out.println("Tempo de execu��o da exclus�o: " + duration2 + " ms");
                    break;
                case 3:
                    long startTime3 = System.nanoTime();
                    System.out.println("Digite uma posi��o para descobrir o seu dado:");
                    int posicaoBuscar = scanner.nextInt();
                    count = 0;  
                    Node resultadoBuscaPosicao = buscar(p, posicaoBuscar);
                    
                    if(resultadoBuscaPosicao != null){
                        System.out.println("Encontrado na posi��o " + posicaoBuscar + ": " + resultadoBuscaPosicao);
                    } else {
                        System.out.println("Nenhum n�mero encontrado na posi��o " + posicaoBuscar);
                    }
                    long endTime3 = System.nanoTime();
                    long duration3 = (endTime3 - startTime3) / 1_000_000; 
                    System.out.println("Tempo de execu��o da busca: " + duration3 + " ms");
                    break;
                case 4:
                    long startTime4 = System.nanoTime();
                    System.out.println("Imprimindo a �rvore bin�ria:");
                    imprimir(p);
                    long endTime4 = System.nanoTime();
                    long duration4 = (endTime4 - startTime4) / 1_000_000;  // Tempo em milissegundos
                    System.out.println("Tempo de execu��o da impress�o: " + duration4 + " ms");
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Erro, Comando n�o existe.");
            }
        }
    } 
}