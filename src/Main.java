import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Main {

    static final int[] TAMANOS = {1, 10, 100, 1000, 10000, 100000};

    public static void main(String[] args) {
        // La salida de los printf se descarta para que la consola no domine el tiempo
        PrintStream nulo = new PrintStream(new BufferedOutputStream(OutputStream.nullOutputStream()));

        System.out.println("Algoritmo B");
        System.out.println("n,tiempo_ms,ops,formula");
        for (int n : TAMANOS) {
            long inicio = System.nanoTime();
            AlgoritmoB.function(n, nulo);
            double ms = (System.nanoTime() - inicio) / 1e6;
            System.out.println(n + "," + ms + "," + AlgoritmoB.ops + "," + AlgoritmoB.formula(n));
        }

        // Sobre este tamano el tiempo real de (a) es demasiado largo: solo se calcula la formula
        final int LIMITE_A = 10000;

        System.out.println();
        System.out.println("Algoritmo A");
        System.out.println("n,tiempo_ms,ops,formula,formula_companero,medido");
        for (int n : TAMANOS) {
            if (n <= LIMITE_A) {
                long inicio = System.nanoTime();
                AlgoritmoA.function(n);
                double ms = (System.nanoTime() - inicio) / 1e6;
                System.out.println(n + "," + ms + "," + AlgoritmoA.ops + "," + AlgoritmoA.formula(n)
                        + "," + AlgoritmoA.formulaCompanero(n) + ",true");
            } else {
                System.out.println(n + ",," + AlgoritmoA.formula(n) + "," + AlgoritmoA.formula(n)
                        + "," + AlgoritmoA.formulaCompanero(n) + ",false");
            }
        }

        System.out.println();
        System.out.println("Algoritmo C");
        System.out.println("n,tiempo_ms,ops,formula,formula_companero");
        for (int n : TAMANOS) {
            long inicio = System.nanoTime();
            AlgoritmoC.function(n, nulo);
            double ms = (System.nanoTime() - inicio) / 1e6;
            System.out.println(n + "," + ms + "," + AlgoritmoC.ops + "," + AlgoritmoC.formula(n)
                    + "," + AlgoritmoC.formulaCompanero(n));
        }
    }
}
