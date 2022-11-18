import java.security.MessageDigest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class WorkerThreadSenha implements Runnable{

    private String HASH;
    private String letra;
    private ExecutorService threadPool;
    int flag = 0;
    String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                    "u", "v", "w", "x", "y", "z"};

    public WorkerThreadSenha(String HASH, String letra, ExecutorService threadPool) {
        this.HASH = HASH;
        this.letra = letra;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {

        String nome = Thread.currentThread().getName();
        System.out.println("Thread: " + nome + " Letra: " + letra);

        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;


        while (flag == 0){
            try {
                Crack(i, j, k, l);
                l++;
                if (l == 26) {
                    l = 0;
                    k++;
                }
                if (k == 26) {
                    k = 0;
                    j++;
                }
                if (j == 26) {
                    j = 0;
                    i++;
                }
                if (i == 26) {
                    System.out.println("Hash não encontrado pela thread " + Thread.currentThread().getName());
                    Thread.currentThread().interrupt();
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void Crack(int i, int j, int k, int l) throws Exception {

        String palavra = letra + letras[i] + letras[j] + letras[k] + letras[l];

        CompletableFuture<String> completableFuture = new CompletableFuture();
        completableFuture.complete(GerarHash(palavra));
        String hash = completableFuture.get();

        if (HASH.equals(hash)) {
            flag = 1;
            System.out.println("A palavra é: " + palavra);
            threadPool.shutdownNow();
        }
    }

    private static String GerarHash(String senha) throws Exception {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte hash[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder texto = new StringBuilder();
        for (byte b : hash) {
            texto.append(String.format("%02X", 0xFF & b));
        }
        return texto.toString().toLowerCase();
    }

}
