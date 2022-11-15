import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws Exception {
        //MultiplicacaoMatriz();
        //Somador();
        QuebraSenha();
    }

    private static void Somador() {

        AtomicInteger somador = new AtomicInteger();

        ExecutorService threadPool = Executors.newFixedThreadPool(8);

        for(int i = 0; i < 20000; i++){
            somador.addAndGet(1);
        }

        for (int i = 0; i < 20000; i++) {
            threadPool.execute(new WorkerThreadSomador(somador));
        }

        threadPool.shutdown();
        while(!threadPool.isTerminated()){
        }
        System.out.println("Valor final: " + somador);

    }

    private static void QuebraSenha() throws Exception {

        int i =0, j = 0;
        final int NUMERO_DE_THREAD = 8;
        final String HASH = "682ba2316eec5c1568f077b4065e818fd3f693cb340657e8c70d19501fac5c49";
        String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"};

        ExecutorService threadPool = Executors.newFixedThreadPool(NUMERO_DE_THREAD);

        while(i < letras.length){
            threadPool.execute(new WorkerThreadSenha(HASH, letras[i]));
            i++;
            j= ++j % NUMERO_DE_THREAD;
        }


    }

    private static void MultiplicacaoMatriz() throws InterruptedException {

        final int NUMERO_DE_THREAD = 10;
        int resultado[][] = new int[100][100];
        int a[][] = new int[100][100];
        int b[][] = new int[100][100];
        int checagem[][] = new int[100][100];

        a = GeraMatriz(100, 100);
        b = GeraMatriz(100, 100);

        ExecutorService threadPool = Executors.newFixedThreadPool(NUMERO_DE_THREAD);

        for (int linha = 0; linha < a.length; linha++) {
            for (int coluna = 0; coluna < b[0].length; coluna++) {
                threadPool.execute(new WorkerThreadMatriz(linha, coluna, a, b, resultado, checagem));
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
