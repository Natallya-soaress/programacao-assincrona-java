import java.util.concurrent.atomic.AtomicInteger;

public class WorkerThreadSomador implements Runnable{

    private AtomicInteger somador;

    public WorkerThreadSomador(AtomicInteger somador) {
        this.somador = somador;
    }

    @Override
    public void run() {

        String nome = Thread.currentThread().getName();
        System.out.println("Thread: " + nome);

        somador.decrementAndGet();

    }
}
