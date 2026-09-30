import java.io.PrintStream;

// Algoritmo (c): i llega hasta n/3 y j avanza de 4 en 4 hasta n.
public class AlgoritmoC {

    // Cuenta de operaciones (mismas reglas del ejercicio 1)
    public static long ops = 0;

    public static void function(int n, PrintStream out) {
        ops = 0;

        for (int i = 1; ; i++) {
            ops++;                      // comprobacion del ciclo i (n/3 + 1 veces)
            if (i > n / 3) break;
            for (int j = 1; ; j += 4) {
                ops++;                  // comprobacion del ciclo j (por cada i, iteraciones + 1)
                if (j > n) break;
                out.println("Sequence");
                ops++;                  // printf
            }
        }
    }

    // T(n) exacto: a = n/3 vueltas de i, b = ceil(n/4) vueltas de j por cada i
    // T = (a + 1) + a(b + 1) + a*b = 2ab + 2a + 1
    public static long formula(long n) {
        long a = n / 3;
        long b = (n + 3) / 4;
        return 2 * a * b + 2 * a + 1;
    }

    // T(n) del ejercicio 1 del companero: n^2/6 + 2n/3 + 1 (usa n/4 sin techo)
    public static double formulaCompanero(long n) {
        return n * (double) n / 6 + 2.0 * n / 3 + 1;
    }
}
