package aed;

//PERDON POR LA CANTIDAD DE COMENTARIOS DESPROLIJOS, CORRIJAN LO QUE QUIERAN <3

public class Bloque {
    
    private int id; // nose si lo usamos, lo puse por las dudas
    private Transaccion[] arrayTransacciones;
    private MaxHeap<Transaccion> heapTransacciones;
    private int sumaMontos;
    private int cantidadTransacciones;

    public Bloque(Transaccion[] transacciones, int id) {    // este contructor no se si esta bien A CHEQUEARRR
        this.id = id;
        this.arrayTransacciones = transacciones;    // O(n) acaa creo q hay aliasing (se soluciona con copy/clone, nose cual)
        this.heapTransacciones = new MaxHeap<>(transacciones);    //O(n) por heapify
        this.sumaMontos = sumaTransacciones(transacciones, id);    //O(n)????? le pido q me pase el id pq si es menor a 3000, no hay q contar la de creacion
        this.cantidadTransacciones = cantTransacciones(transacciones, id);    //O(1) aca lo mismo q arriba con el id
    }
    
    // creo con estos 3 metodos publicos garantizamos O(1) en el punto 3 y 5 (CREO)
    public Transaccion transaccionMayorMonto() {
        return heapTransacciones.raiz();
    }
    
    public int sumaMontos() {
        return this.sumaMontos;
    }

    public int cantidadTransacciones() {
        return this.cantidadTransacciones;
    }

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
    //creo q nos falta un metodo publico para devolver la copia de las transacciones en el punto 4, no estoy segura
}
