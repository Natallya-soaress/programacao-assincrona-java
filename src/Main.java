import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int NUMERO_DE_THREAD = 10;
    private static int resultado[][] = new int[100][100];
    private static int a[][] = new int[100][100];
    private static int b[][] = new int[100][100];
    private static int checagem[][] = new int[100][100];


    public static void main(String[] args) throws InterruptedException {
        a = GeraMatriz(100, 100);
        b = GeraMatriz(100, 100);

        ExecutorService threadPool = Executors.newFixedThreadPool(NUMERO_DE_THREAD);

        for (int linha = 0; linha < a.length; linha++) {
            for (int coluna = 0; coluna < b[0].length; coluna++) {
                threadPool.execute(new WorkerThread(linha, coluna, a, b, resultado, checagem));
            }
        }

        threadPool.shutdown();
        while(!threadPool.isTerminated()){
        }
        ChecaMatriz(checagem);
    }

    private static int[][] GeraMatriz(int linha, int coluna){
        int[][] m = new int [linha][coluna];
        Random generator = new Random();

        for(int i = 0; i < linha ; i++){
            for(int k = 0; k < coluna ; k++){
                m[i][k] = generator.nextInt( 10 );
            }

        }
        return m;
    }

    private static void PrintaMatriz(int[][] matriz){
        for (int linha = 0; linha < matriz[0].length; linha++) {
            for (int coluna = 0; coluna < matriz[0].length; coluna++) {
                System.out.print(" " + matriz[linha][coluna] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void ChecaMatriz(int[][] matriz){

        for (int linha = 0; linha < matriz[0].length; linha++) {
            for (int coluna = 0; coluna < matriz[0].length; coluna++) {
                if(matriz[linha][coluna] == 0){
                    System.out.println("Ops, a multiplicação deu errado!!");
                    break;
                }
            }
        }
        System.out.println("Sucesso, multiplicação finalizada!!");
    }

}