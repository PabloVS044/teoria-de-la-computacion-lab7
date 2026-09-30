import java.io.PrintStream;

// Algoritmo (b): un break en el ciclo interno hace que solo corra 1 vez por cada i.
public class AlgoritmoB {

    // Cuenta de operaciones (mismas reglas del ejercicio 1)
    public static long ops = 0;

    public static void function(int n, PrintStream out) {
        ops = 0;

        ops++;                          // if (n <= 1)
        if (n <= 1) return;

        for (int i = 1; ; i++) {
            ops++;                      // comprobacion del ciclo i (n + 1 veces)
            if (i > n) break;
            for (int j = 1; ; j++) {
                ops++;                  // comprobacion del ciclo j (n veces, el break lo corta)
                if (j > n) break;
                out.println("Sequence");
                ops++;                  // printf
                ops++;                  // break
                break;
            }
        }
    }

    // T(n) del ejercicio 1: 1 si n <= 1, 4n + 2 en otro caso
    public static long formula(long n) {
        return n <= 1 ? 1 : 4 * n + 2;
    }
}
