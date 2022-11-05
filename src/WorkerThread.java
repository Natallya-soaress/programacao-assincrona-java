public class WorkerThread implements Runnable{

    private int linha;
    private int coluna;
    private int a[][];
    private int b[][];
    private int resultado[][];
    private int checagem[][];

    public WorkerThread(int linha, int coluna, int[][] a, int[][] b, int[][] resultado, int[][] checagem) {
        this.linha = linha;
        this.coluna = coluna;
        this.a = a;
        this.b = b;
        this.resultado = resultado;
        this.checagem = checagem;
    }

    @Override
    public void run() {
        String nome = Thread.currentThread().getName();
        System.out.println("Thread: " + nome);
        for (int auxiliar = 0; auxiliar < a[0].length; auxiliar++) {
            resultado[linha][coluna] += a[linha][auxiliar] * b[auxiliar][coluna];
        }
        checagem[linha][coluna] = 1;
    }
}
