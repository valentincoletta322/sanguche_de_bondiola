package aed;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que representa un bloque de transacciones en la cadena de bloques.
 */
public class Bloque {
    private final Transaccion[] arrayTransacciones;
    private final MaxHeap<Handle> heapTransacciones;
    private final boolean[] eliminadas;
    private int sumaMontos;
    private int cantidadTransacciones;

    /**
     * Constructor del bloque.
     * Complejidad: O(n_b), donde n_b es la cantidad de transacciones.
     *
     * @param transacciones Arreglo de transacciones del bloque
     */
    public Bloque(Transaccion[] transacciones) {
        this.arrayTransacciones = Arrays.copyOf(transacciones, transacciones.length);
        this.eliminadas = new boolean[transacciones.length];
        this.sumaMontos = 0;
        this.cantidadTransacciones = 0;
        Handle[] handles = new Handle[transacciones.length];
        for (int i = 0; i < transacciones.length; i++) {
            handles[i] = new Handle(i, transacciones[i]);
            if (!transacciones[i].esCreacion()) {
                sumaMontos += transacciones[i].monto();
                cantidadTransacciones++;
            }
        }
        this.heapTransacciones = new MaxHeap<>(handles); // O(n_b)
    }

    /**
     * Devuelve la transacción de mayor valor del bloque.
     * Complejidad: O(1)
     *
     * @return Transacción de mayor valor
     */
    public Transaccion obtenerMaximo() {
        if (heapTransacciones.cardinal() == 0) return null;
        return arrayTransacciones[heapTransacciones.raiz().referencia];
    }

    /**
     * Devuelve la suma de los montos de las transacciones no de creación.
     * Complejidad: O(1)
     *
     * @return Suma de montos
     */
    public int sumaMontos() {
        return this.sumaMontos;
    }

    /**
     * Devuelve la cantidad de transacciones no de creación.
     * Complejidad: O(1)
     *
     * @return Cantidad de transacciones
     */
    public int cantidadTransacciones() {
        return this.cantidadTransacciones;
    }

    /**
     * Devuelve una copia de las transacciones no eliminadas del bloque.
     * Complejidad: O(n_b)
     *
     * @return Arreglo de transacciones no eliminadas
     */
    public Transaccion[] getTransacciones() {
        ArrayList<Transaccion> lista = new ArrayList<>();
        for (int i = 0; i < arrayTransacciones.length; i++) {
            if (!eliminadas[i]) {
                lista.add(arrayTransacciones[i]);
            }
        }
        return lista.toArray(new Transaccion[0]);
    }

    /**
     * Hackea la transacción de mayor valor, eliminándola del bloque y actualizando los montos.
     * Complejidad: O(log n_b)
     *
     * @return Transacción hackeada
     */
    public Transaccion hackearTx() {
        if (heapTransacciones.cardinal() == 0) return null;
        Handle maxHandle = heapTransacciones.extractMax();
        int index = maxHandle.referencia;
        eliminadas[index] = true;
        Transaccion tx = arrayTransacciones[index];
        if (!tx.esCreacion()) {
            sumaMontos -= tx.monto();
            cantidadTransacciones--;
        }
        return tx;
    }

    private static class Handle implements Comparable<Handle> {
        private final int referencia;
        private final Transaccion tx;

        public Handle(int ref, Transaccion tx) {
            this.referencia = ref;
            this.tx = tx;
        }

        @Override
        public int compareTo(Handle otro) {
            return this.tx.compareTo(otro.tx);
        }
    }
}