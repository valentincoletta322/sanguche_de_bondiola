package aed;

import java.util.ArrayList;

//PERDON POR LA CANTIDAD DE COMENTARIOS DESPROLIJOS, CORRIJAN LO QUE QUIERAN <3

public class Bloque {
    
    private int id; // nose si lo usamos, lo puse por las dudas
    private HandleTransacciones[] arrayTransacciones;
    private MaxHeap<HandleTransacciones> heapTransacciones;
    private int sumaMontos;
    private int cantidadTransacciones;

    // meti cambio aca
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


    public Bloque(Transaccion[] transacciones, int id){    // este contructor no se si esta bien A CHEQUEARRR
        this.id = id;

        this.arrayTransacciones = new HandleTransacciones[transacciones.length]; // Pedir memoria O(n)
        
        sumaMontos = 0;
        cantidadTransacciones = this.arrayTransacciones.length;

        for (int i = 0; i < transacciones.length; i++) { // O(n) por el for
            HandleTransacciones nuevo = new HandleTransacciones(transacciones[i]);
            this.arrayTransacciones[i] = nuevo;
        }
        
        if (this.arrayTransacciones.length > 0){
            Transaccion primera = this.arrayTransacciones[0].transaccionApuntada;
            if (!primera.esCreacion()){
                sumaMontos+= primera.monto();
            } else {
                this.cantidadTransacciones -= 1;
            }
        }
        for (int i = 1; i < this.arrayTransacciones.length; i++){
            sumaMontos += transacciones[i].monto(); // Como ya esta especificado en la consigna, no hace falta verificar que no sean de creacion.
        }

        this.heapTransacciones = new MaxHeap<HandleTransacciones>(this.arrayTransacciones);

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
    
    public Transaccion[] obtenerTransacciones(){
        ArrayList<Transaccion> transacciones = new ArrayList<>(this.cantidadTransacciones);
        // Con esto me aseguro de tener el espacio necesario y no tener que volver a pedir memoria

        for (int i = 0; i < this.arrayTransacciones.length; i++) {
            if (this.arrayTransacciones[i].enLista) {
                transacciones.add(this.arrayTransacciones[i].transaccionApuntada);
            }
        }

        // Creo que queda todo O(n) porque se suma
        Transaccion[] toArray = new Transaccion[transacciones.size()];
        toArray = transacciones.toArray(toArray);
        return toArray;
    }

    public Transaccion obtenerMaximo(){
        return heapTransacciones.raiz().transaccionApuntada;
    }

    public Transaccion extraerMaximaTransaccion() {
        if (this.arrayTransacciones.length == 0) {
            throw new RuntimeException("No hay transacciones en el bloque!");
        }
        HandleTransacciones maxima = this.heapTransacciones.extraerMax();
        maxima.enLista = false;
        if (maxima.transaccionApuntada.id_comprador() != 0){
            this.sumaMontos -= maxima.transaccionApuntada.monto();
            this.cantidadTransacciones--;
        }
        return maxima.transaccionApuntada;
    }

    //creo q nos falta un metodo publico para devolver la copia de las transacciones en el punto 4, no estoy segura


}
