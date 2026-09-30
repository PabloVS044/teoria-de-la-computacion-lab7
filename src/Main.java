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
