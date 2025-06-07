package aed;

public class ListaEnlazada {
    private class Bloque {
        Transaccion[] bloque;
        Bloque siguiente;

        Bloque(Transaccion[] bloque) {
            this.bloque = bloque;
            this.siguiente = null;
        }
    }

    private Bloque primero;
    private Bloque ultimo;

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
    }

    // Agregar un bloque al final de la lista - O(1)
    public void agregarBloque(Transaccion[] bloque) {
        Bloque nuevo = new Bloque(bloque);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
    }

    // Obtener el último bloque - O(1)
    public Transaccion[] obtenerUltimoBloque() {
        if (ultimo == null) {
            return new Transaccion[0];
        }
        return ultimo.bloque;
    }
}