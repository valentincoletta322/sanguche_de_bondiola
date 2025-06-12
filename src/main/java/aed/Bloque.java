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
        this.arrayTransacciones = transacciones;     // O(n) acaa creo q hay aliasing (se soluciona con copy/clone, nose cual)
        // agrego aca:
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
            sumaMontos += transacciones[i].monto();
        }

        this.heapTransacciones = new MaxHeap<Transaccion>(transacciones);    //O(n) por heapify
        // Esta bien todo esto, pero lo comento asi lo hacemos de una en el primer for (REVISAR)
        //this.sumaMontos = sumaTransacciones(transacciones, id);    //O(n)????? le pido q me pase el id pq si es menor a 3000, no hay q contar la de creacion
        //this.cantidadTransacciones = cantTransacciones(transacciones, id);    //O(1) aca lo mismo q arriba con el id

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
    
    private int cantTransacciones(Transaccion[] transacciones, int id) {
        if (transacciones.length == 0) {
            return 0;
        }
        else if (id < 3000) {    //aca tmb hay q chequear como asignamos el id al bloque
            return transacciones.length - 1;
        }
        else {
            return transacciones.length;
        }

    }
    
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

    //creo q nos falta un metodo publico para devolver la copia de las transacciones en el punto 4, no estoy segura


}
