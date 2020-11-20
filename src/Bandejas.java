import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Bandejas {

    private ArrayBlockingQueue<Plato> platoslimpios = new ArrayBlockingQueue<>(10);
    private ArrayBlockingQueue<Plato> platossecos = new ArrayBlockingQueue<>(10);
    private ArrayBlockingQueue<Plato> platolisto = new ArrayBlockingQueue<>(10);

    Random random = new Random();

    DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");

    public synchronized void platoslimpios(Plato plato) throws InterruptedException {

        platoslimpios.add(plato);
        System.out.printf("%s => %s Se ha lavado el plato nº %d \n", LocalTime.now().format(hora), Thread.currentThread().getName(), plato.getN_serie());

    }

    public synchronized void platosseco() throws InterruptedException {


        try {
            platossecos.put(platoslimpios.peek());
            TimeUnit.SECONDS.sleep(random.nextInt(3) + 1);
            System.out.printf("%s => %s  Se ha secado el plato nº %d \n", LocalTime.now().format(hora), Thread.currentThread().getName(), platossecos.element().getN_serie());
            platoslimpios.take();
        } catch (NullPointerException e) {


        }

    }


    public synchronized void platolisto() throws InterruptedException {

        try {


            platolisto.put(platossecos.peek());
            TimeUnit.SECONDS.sleep(random.nextInt(2) + 1);
            System.out.printf("%s => %s Se ha añadido el plato nº %d a la alacena\n", LocalTime.now().format(hora), Thread.currentThread().getName(), platossecos.element().getN_serie());
            platossecos.take();
        } catch (NullPointerException e) {


        }


    }

}