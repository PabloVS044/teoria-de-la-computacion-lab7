// Algoritmo (a): tres ciclos anidados; el de k se duplica, asi que da log2(n) vueltas.
public class AlgoritmoA {

    // Cuenta de operaciones (mismas reglas del ejercicio 1)
    public static long ops = 0;

    // Devuelve counter para que el resultado se use y la JIT no elimine los ciclos
    public static long function(int n) {
        ops = 0;
        long counter = 0;
        ops++;                              // counter = 0

        for (int i = n / 2; ; i++) {
            ops++;                          // comprobacion del ciclo i
            if (i > n) break;
            for (int j = 1; ; j++) {
                ops++;                      // comprobacion del ciclo j
                if (j + n / 2 > n) break;
                for (int k = 1; ; k = k * 2) {
                    ops++;                  // comprobacion del ciclo k
                    if (k > n) break;
                    counter++;
                    ops++;                  // counter++
                }
            }
        }
        return counter;
    }

    // T(n) exacto con division entera, sin ejecutar los ciclos:
    //   A = vueltas de i, m = vueltas de j por cada i, L = vueltas de k por cada (i, j)
    //   T = 1 + (A+1) + A(m+1) + A*m(L+1) + A*m*L
    public static long formula(long n) {
        long A = n - n / 2 + 1;
        long m = n - n / 2;
        long L = 63 - Long.numberOfLeadingZeros(n) + 1;     // floor(log2 n) + 1
        return 1 + (A + 1) + A * (m + 1) + A * m * (L + 1) + A * m * L;
    }

    // T(n) del ejercicio 1 del companero:
    //   1 + (n/2+2) + (n^2/4+n+1) + (n^2/4+n/2)(log2 n+2) + (n^2/4+n/2)(log2 n+1)
    public static double formulaCompanero(long n) {
        double lg = Math.log(n) / Math.log(2);
        double c = n * (double) n / 4 + n / 2.0;
        return 1 + (n / 2.0 + 2) + (n * (double) n / 4 + n + 1) + c * (lg + 2) + c * (lg + 1);
    }
}
