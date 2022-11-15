import java.security.MessageDigest;
import java.util.concurrent.CompletableFuture;

public class WorkerThreadSenha implements Runnable{

    private String HASH;
    private String letra;

String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                    "u", "v", "w", "x", "y", "z"};

    public WorkerThreadSenha(String HASH, String letra) {
        this.HASH = HASH;
        this.letra = letra;
    }

    @Override
    public void run() {

        String palavra = new String();
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;

        try {
            Crack(i, j, k, l, palavra);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static String GerarHash(String senha) throws Exception {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte hash[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder texto = new StringBuilder();
        for (byte b : hash) {
            texto.append(String.format("%02X", 0xFF & b));
        }
        return texto.toString();
    }

    private void Crack(int i, int j, int k, int l, String palavra) throws Exception {

        palavra = letra + letras[i] + letras[j] + letras[k] + letras[l];

        String finalPalavra = palavra;

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> finalPalavra).thenApply(pal -> {
            try {
                return GerarHash(pal);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        String hash = completableFuture.get();

        if (HASH.equals(hash)) {
            System.out.println("A palavra é: " + palavra);
        } else {
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
                return;
            }
            Crack(i, j, k, l, palavra);
        }
    }

}
