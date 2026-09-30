import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;

public class Main {

    static final int[] TAMANOS = {1, 10, 100, 1000, 10000, 100000, 1000000};

    // Sobre este tamano el tiempo real de (a) y (c) es de horas: solo se calcula la formula
    static final int LIMITE_A = 100000;
    static final int LIMITE_C = 100000;
    static final int LIMITE_B = 1000000;

    // La salida de los printf se descarta para que la consola no domine el tiempo
    static final PrintStream NULO = new PrintStream(new BufferedOutputStream(OutputStream.nullOutputStream()));

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of("resultados"));
        try (PrintWriter csv = new PrintWriter("resultados/resultados.csv")) {
            csv.println("algoritmo,n,tiempo_ms,ops,medido");

            probar("A", LIMITE_A, n -> AlgoritmoA.function(n), () -> AlgoritmoA.ops, AlgoritmoA::formula, csv);
            probar("B", LIMITE_B, n -> AlgoritmoB.function(n, NULO), () -> AlgoritmoB.ops, AlgoritmoB::formula, csv);
            probar("C", LIMITE_C, n -> AlgoritmoC.function(n, NULO), () -> AlgoritmoC.ops, AlgoritmoC::formula, csv);
        }
        System.out.println("Listo: resultados/resultados.csv");
    }

    static void probar(String nombre, int limite, IntConsumer algoritmo, LongSupplier ops,
                       LongUnaryOperator formula, PrintWriter csv) {
        // Calentamiento de la JVM
        for (int i = 0; i < 20; i++) algoritmo.accept(1000);

        for (int n : TAMANOS) {
            String tiempo = "";
            long conteo;
            boolean medido = n <= limite;

            if (medido) {
                // Mas repeticiones en tamanos pequenos, donde el tiempo es casi cero
                int reps = n <= 1000 ? 21 : (n <= 10000 ? 5 : 1);
                double[] tiempos = new double[reps];
                for (int r = 0; r < reps; r++) {
                    long inicio = System.nanoTime();
                    algoritmo.accept(n);
                    tiempos[r] = (System.nanoTime() - inicio) / 1e6;
                }
                Arrays.sort(tiempos);
                tiempo = String.valueOf(tiempos[reps / 2]);   // mediana
                conteo = ops.getAsLong();
                if (conteo != formula.applyAsLong(n)) {
                    System.out.println("AVISO " + nombre + " n=" + n + ": ops=" + conteo
                            + " pero formula=" + formula.applyAsLong(n));
                }
            } else {
                conteo = formula.applyAsLong(n);
            }

            csv.println(nombre + "," + n + "," + tiempo + "," + conteo + "," + medido);
            System.out.println(nombre + " n=" + n + " tiempo_ms=" + (medido ? tiempo : "no medido")
                    + " ops=" + conteo);
        }
    }
}
