package aed;

public class ListaEnlazada<T> {

    private Nodo primero;
    private Nodo ultimo;
    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
    }

    // Agregar un bloque al final de la lista - O(1)
    public void agregar(T valor) {
        Nodo nuevoNodo = new Nodo(valor);
        if (this.primero == null) {
            primero = nuevoNodo;
        } else {
            ultimo.siguiente = nuevoNodo;
        }
        ultimo = nuevoNodo;
    }

    // Obtener el último bloque - O(1)
    public T ultimo() {
        if (ultimo == null) {
            throw new RuntimeException("La lista esta vacia!");
        }
        return ultimo.valor;
    }

    private class Nodo {
        private final T valor;
        private Nodo siguiente;

        public Nodo(T nuevoValor) {
            this.valor = nuevoValor;
            this.siguiente = null;
        }
    }
}