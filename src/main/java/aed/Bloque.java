package aed;

import java.util.ArrayList;

//PERDON POR LA CANTIDAD DE COMENTARIOS DESPROLIJOS, CORRIJAN LO QUE QUIERAN <3

public class Bloque {
    
    private int id; // nose si lo usamos, lo puse por las dudas
    private Transaccion[] arrayTransacciones;
    private MaxHeap<Transaccion> heapTransacciones;
    private int sumaMontos;
    private int cantidadTransacciones;

    // meti cambio aca
    private class HandleTransacciones {
        private Transaccion transaccionApuntada;
        private int referencia;

        public HandleTransacciones(Transaccion nuevaTransaccion){
            this.transaccionApuntada = nuevaTransaccion;
            this.referencia = nuevaTransaccion.id();
        }
    }


    public Bloque(Transaccion[] transacciones, int id){    // este contructor no se si esta bien A CHEQUEARRR
        this.id = id;

        this.arrayTransacciones = new Transaccion [transacciones.length]; // Pedir memoria O(n)
        for (int i = 0; i < transacciones.length; i++) { // O(n) por el for
            this.arrayTransacciones[i] = transacciones[i];
        }


        sumaMontos = 0;
        cantidadTransacciones = this.arrayTransacciones.length;
        
        if (this.arrayTransacciones.length > 0){
            Transaccion primera = this.arrayTransacciones[0];
            if (!primera.esCreacion()){
                sumaMontos+= primera.monto();
            } else {
                this.cantidadTransacciones -= 1;
            }
        }
        for (int i = 1; i < this.arrayTransacciones.length; i++){
            sumaMontos += transacciones[i].monto(); // Como ya esta especificado en la consigna, no hace falta verificar que no sean de creacion.
        }

        this.heapTransacciones = new MaxHeap<Transaccion>(transacciones); //O(n) por heapify

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
            if (this.arrayTransacciones[i] == null) {
                continue;
            }
            transacciones.add(this.arrayTransacciones[i]);
        }

        // Creo que queda todo O(n) porque se suma
        Transaccion[] toArray = new Transaccion[transacciones.size()];
        toArray = transacciones.toArray(toArray);
        return toArray;
    }

    public Transaccion obtenerMaximo(){
        return heapTransacciones.raiz();
    }

    // Aprovecho que esta ordenado el array y lo encuentro en log(n) en peor caso.
    public int encontrarTransaccion(Transaccion transaccionBuscada){
        int max = 0;
        int min = this.arrayTransacciones.length - 1;
        
        while (max <= min) {
            int medio = (max + min) / 2; // redondea para abajo
            Transaccion actual = this.arrayTransacciones[medio];
            if (actual.equals(transaccionBuscada)) {
                return medio; // Encontré la transacción
            } else if (actual.id() < transaccionBuscada.id()) {
                max = medio + 1; // Busco en la mitad derecha
            } else {
                min = medio - 1; // Busco en la mitad izquierda
            }

        }
        throw new RuntimeException("Transaccion no encontrada en el bloque!");
    }

    public Transaccion extraerMaximaTransaccion() {
        if (this.arrayTransacciones.length == 0) {
            throw new RuntimeException("No hay transacciones en el bloque!");
        }
        return this.heapTransacciones.extraerMax();
    }

    public void toto_caputo(int posicionEnArray){
        this.arrayTransacciones[posicionEnArray] = null;
    }

    //creo q nos falta un metodo publico para devolver la copia de las transacciones en el punto 4, no estoy segura


}
