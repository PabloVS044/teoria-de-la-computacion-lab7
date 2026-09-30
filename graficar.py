import csv
from collections import defaultdict

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

NOMBRES = {
    "A": "Algoritmo (a)",
    "B": "Algoritmo (b)",
    "C": "Algoritmo (c)",
}

# Leer el CSV que genera Main.java
datos = defaultdict(list)
with open("resultados/resultados.csv") as f:
    for fila in csv.DictReader(f):
        tiempo = float(fila["tiempo_ms"]) if fila["tiempo_ms"] else None
        datos[fila["algoritmo"]].append((int(fila["n"]), tiempo, int(fila["ops"])))

# Una grafica por algoritmo: tiempo (eje izquierdo) y operaciones (eje derecho), ambos en log-log
for alg, filas in datos.items():
    ns = [n for n, _, _ in filas]
    ops = [o for _, _, o in filas]
    medidos = [(n, t) for n, t, _ in filas if t is not None]

    fig, ax1 = plt.subplots(figsize=(8, 5))
    ax1.plot([n for n, _ in medidos], [t for _, t in medidos], "o-", color="tab:blue", label="Tiempo real (ms)")
    ax1.set_xscale("log")
    ax1.set_yscale("log")
    ax1.set_xlabel("n")
    ax1.set_ylabel("Tiempo real (ms)", color="tab:blue")

    ax2 = ax1.twinx()
    ax2.plot(ns, ops, "s--", color="tab:red", label="Conteo de operaciones")
    ax2.set_yscale("log")
    ax2.set_ylabel("Operaciones", color="tab:red")

    lineas = ax1.get_lines() + ax2.get_lines()
    ax1.legend(lineas, [l.get_label() for l in lineas], loc="upper left")
    ax1.set_title(f"{NOMBRES[alg]}: tiempo real vs operaciones")
    ax1.grid(True, which="both", alpha=0.3)
    fig.tight_layout()
    fig.savefig(f"resultados/grafica_{alg}.png", dpi=150)
    plt.close(fig)

# Tabla en Markdown con tiempo y operaciones por tamano
with open("resultados/tabla.md", "w", encoding="utf-8") as out:
    for alg, filas in datos.items():
        out.write(f"### {NOMBRES[alg]}\n\n")
        out.write("| n | Tiempo real (ms) | Operaciones |\n")
        out.write("|---:|---:|---:|\n")
        for n, t, o in filas:
            tiempo = f"{t:.4f}" if t is not None else "no medido"
            out.write(f"| {n:,} | {tiempo} | {o:,} |\n")
        out.write("\n")

print("Listo: resultados/grafica_A.png, grafica_B.png, grafica_C.png y tabla.md")
