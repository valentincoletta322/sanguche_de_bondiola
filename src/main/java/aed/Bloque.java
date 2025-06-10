package aed;

//PERDON POR LA CANTIDAD DE COMENTARIOS DESPROLIJOS, CORRIJAN LO QUE QUIERAN <3

public class Bloque {
    
    private int id; // nose si lo usamos, lo puse por las dudas
    private Transaccion[] arrayTransacciones;
    private MaxHeap<Handle> heapTransacciones;
    private int sumaMontos;
    private int cantidadTransacciones;

    // meti cambio aca
    private class Handle implements Comparable<Handle> {
        private int referencia;
        private int monto;
        public Handle(int nuevaReferencia, int nuevoMonto){
            this.referencia = nuevaReferencia;
            this.monto = nuevoMonto;
        }

        @Override
        public int compareTo(Handle otro){
            if (this.monto > otro.monto){
                return 1;
            }
            else if (this.monto < otro.monto){
                return -1;
            }
            return 0;
        }
    }

    public Bloque(Transaccion[] transacciones, int id){    // este contructor no se si esta bien A CHEQUEARRR
        this.id = id;
        this.arrayTransacciones = transacciones;     // O(n) acaa creo q hay aliasing (se soluciona con copy/clone, nose cual)
        // agrego aca:
        sumaMontos = 0;
        cantidadTransacciones = this.arrayTransacciones.length;
        Handle[] handles = new Handle[transacciones.length];
        if (this.arrayTransacciones.length > 0){
            Transaccion primera = this.arrayTransacciones[0];
            if (!primera.esCreacion()){
                sumaMontos+= primera.monto();
            } else {
                this.cantidadTransacciones -= 1;
            }
            handles[0] = new Handle(0, primera.monto());
        }
        for (int i = 1; i < this.arrayTransacciones.length; i++){
            sumaMontos += transacciones[i].monto();
            handles[i] = new Handle(i, this.arrayTransacciones[i].monto());
        }

        this.heapTransacciones = new MaxHeap<Handle>(handles);    //O(n) por heapify
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
    

    public Transaccion obtenerMaximo(){
        return this.arrayTransacciones[heapTransacciones.raiz().referencia];
    }

    //creo q nos falta un metodo publico para devolver la copia de las transacciones en el punto 4, no estoy segura

}
