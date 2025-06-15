package aed;

import java.util.ArrayList;

public class Bloque {
    
    private int id;
    private HandleTransacciones[] arrayTransacciones;
    private MaxHeapActualizable<HandleTransacciones> heapTransacciones;
    private int sumaMontos;
    private int cantidadTransacciones;

    // Clase para poder manejar las transacciones eliminadas por hackearTx()
    private class HandleTransacciones implements Comparable<HandleTransacciones> {
        private Transaccion transaccionApuntada;
        private boolean enLista;

        public HandleTransacciones(Transaccion nuevaTransaccion){
            this.transaccionApuntada = nuevaTransaccion;
            this.enLista = true; 
        }
        @Override
        public int compareTo(HandleTransacciones otro){
            return this.transaccionApuntada.compareTo(otro.transaccionApuntada);
        }
    }


    public Bloque(Transaccion[] transacciones, int id){
        this.id = id;
        this.arrayTransacciones = new HandleTransacciones[transacciones.length]; // Pedir memoria O(n)
        
        sumaMontos = 0;
        cantidadTransacciones = this.arrayTransacciones.length;

        // O(n): Recorremos todas las transacciones y creamos un HandleTransacciones para cada una.
        for (int i = 0; i < transacciones.length; i++) { 
            HandleTransacciones nuevo = new HandleTransacciones(transacciones[i]);
            this.arrayTransacciones[i] = nuevo;
            // Como queremos obtener el medio en O(1), aprovechamos el recorrido para sumar los montos
            sumaMontos += transacciones[i].monto();
        }
        // Restamos en caso de que exista una de creacion
        if (this.arrayTransacciones.length > 0){
            Transaccion primera = this.arrayTransacciones[0].transaccionApuntada;
            if (primera.esCreacion()){
                sumaMontos-= primera.monto();
                this.cantidadTransacciones -= 1;
            }
        }
        this.heapTransacciones = new MaxHeapActualizable<HandleTransacciones>(this.arrayTransacciones); // O(n)
    }
    
    //O(1)
    public int sumaMontos() {
        return this.sumaMontos;
    }
    //O(1)
    public int cantidadTransacciones() {
        return this.cantidadTransacciones;
    }
    
    // O(n): Recorre el array de transacciones y devuelve las que estan en la lista
    public Transaccion[] obtenerTransacciones(){
        ArrayList<Transaccion> transacciones = new ArrayList<>(this.cantidadTransacciones);
        // Con esto me aseguro de tener el espacio necesario y no tener que volver a pedir memoria


        // O(n)
        for (int i = 0; i < this.arrayTransacciones.length; i++) {
            if (this.arrayTransacciones[i].enLista) {
                transacciones.add(this.arrayTransacciones[i].transaccionApuntada);
            }
        }

        // Sumando las complejidades:  O(n) + O(n) = O(2n) => O(n)
        Transaccion[] toArray = new Transaccion[transacciones.size()];
        toArray = transacciones.toArray(toArray);
        return toArray;
    }

    public Transaccion obtenerMaximo(){
        return heapTransacciones.raiz().transaccionApuntada;
    }


    // O(log(n)): Hace extraerMax que llama a un sift_down O(log(n)), el resto es O(1)
    public Transaccion extraerMaximaTransaccion() {
        if (this.arrayTransacciones.length == 0) {
            throw new RuntimeException("No hay transacciones en el bloque!");
        }
        HandleTransacciones maxima = this.heapTransacciones.extractMax(); // O(log(n))
        maxima.enLista = false;
        if (maxima.transaccionApuntada.id_comprador() != 0){
            this.sumaMontos -= maxima.transaccionApuntada.monto();
            this.cantidadTransacciones--;
        }
        return maxima.transaccionApuntada;
    }

}
