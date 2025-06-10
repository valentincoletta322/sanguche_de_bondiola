package aed;

import java.util.ArrayList;
import java.util.Arrays;

//PERDON POR LA CANTIDAD DE COMENTARIOS DESPROLIJOS, CORRIJAN LO QUE QUIERAN <3
// quién hubiera pensado que esta clase sería la peor de todas??
public class Bloque {

    private Transaccion[] arrayTransacciones;
    private MaxHeap<Handle> heapTransacciones;
    private boolean[] eliminadas; // era esto o marcar las Tx eliminadas, nadie quería esa opción :(
    private int sumaMontos;
    private int cantidadTransacciones;

    // meti cambio aca
    private class Handle implements Comparable<Handle> {
        private int referencia;
        private Transaccion tx;

        public Handle(int ref, Transaccion tx) {
            this.referencia = ref;
            this.tx = tx;
        }

        @Override
        public int compareTo(Handle otro) {
            return this.tx.compareTo(otro.tx); // Usar compareTo de Transaccion
        }
    }

    // Constructor de Bloque - O(n_b)
    public Bloque(Transaccion[] transacciones) {    // este contructor no se si esta bien A CHEQUEARRR
        this.arrayTransacciones = Arrays.copyOf(transacciones, transacciones.length);        // agrego aca:
        this.eliminadas = new boolean[transacciones.length];
        sumaMontos = 0;
        cantidadTransacciones = 0;
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

    // creo con estos 3 metodos publicos garantizamos O(1) en el punto 3 y 5 (CREO)
    // creo lo mismo ~O
    // Máxima transacción - O(1)
    public Transaccion obtenerMaximo() {
        if (heapTransacciones.size() == 0) return null;
        return arrayTransacciones[heapTransacciones.raiz().referencia];
    }

    public int sumaMontos() {
        return this.sumaMontos;
    }

    public int cantidadTransacciones() {
        return this.cantidadTransacciones;
    }
          
    // lo anoto como alternativa y le preguntamos a juli si no:
    // podríamos directamente hacerlo aca sin exponer las cosas (no cambia nada)
    
    /* public float montoPromedio(){
        return this.sumaMontos/this.cantidadTransacciones; // O(1) polémico para que ande con hackearTx
    } */

    
    // Si lo hacemos con el for se puede comentar, si lo hacemos con los metodos se puede dejar asi:

    private int sumaTransacciones(Transaccion[] transacciones, int id) {
        int suma = 0;
        if (transacciones.length == 0) {
            return 0;
        }
        else if (id < 3000) {    //hay q ver bien como vamos a asignar el id al bloque
            for (int i = 1; i < transacciones.length; i++) {
                suma = suma + transacciones[i].monto();
            }
        }
        else {
            for (int i =0; i < transacciones.length; i++) {
                suma = suma + transacciones[i].monto();
            }
        }
        return suma;
    }

    // Transacciones no eliminadas - O(n_b)
    public Transaccion[] getTransacciones() {
        ArrayList<Transaccion> lista = new ArrayList<>();
        for (int i = 0; i < arrayTransacciones.length; i++) {
            if (!eliminadas[i]) {
                lista.add(arrayTransacciones[i]);
            }
        }
        return lista.toArray(new Transaccion[0]);
    }

    // Extrae la máxima transacción - O(log n_b)
    public Transaccion hackearTx() {
        if (heapTransacciones.size() == 0) return null;
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
}
