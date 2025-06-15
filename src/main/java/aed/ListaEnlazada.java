package aed;

/**
 * Clase que implementa una lista enlazada simple para almacenar bloques.
 */
public class ListaEnlazada<T> {

    private Nodo primero;
    private Nodo ultimo;
    private int longitud;
    /**
     * Constructor de la lista enlazada.
     * Complejidad: O(1)
     */
    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
        this.longitud = 0;
    }

    /**
     * Agrega un elemento al final de la lista.
     * Complejidad: O(1) porque tenemos el último nodo almacenado.
     *
     * @param valor Elemento a agregar
     */

    public void agregar(T valor) {
        Nodo nuevoNodo = new Nodo(valor);

        if (this.primero == null) {
            primero = nuevoNodo;
        } else {
            ultimo.siguiente = nuevoNodo;
        }
        ultimo = nuevoNodo;
        this.longitud += 1;
    }

    /**
     * Devuelve el último elemento de la lista.
     * Complejidad: O(1)
     *
     * @return Último elemento
     * @throws RuntimeException si la lista está vacía
     */
    // Obtener el último bloque - O(1)
    public T ultimo() {
        if (ultimo == null) {
            throw new RuntimeException("La lista esta vacia!");
        }
        return ultimo.valor;
    }

    public int longitud() {
        return this.longitud;
    }

    /**
     * Devuelve el elemento en la posición i.
     * Complejidad: O(i)
     *
     * @param i Índice del elemento
     * @return Elemento en la posición i
     */
    public T obtener(int i) {
        if (i < 0 || i >= longitud) {
            throw new IndexOutOfBoundsException("Index out of range: " + i);
        }
        Nodo res = this.primero;
        for (int n = 0; n < i; n++) {
            res = res.siguiente;
        }
        return res.valor;
    }

    public void eliminar(int i) {
        if (i < 0 || i >= longitud) {
            throw new RuntimeException("Indice fuera de rango!");
        }
        else if (i == 0) {
            primero = primero.siguiente;
            if (longitud == 1) {
                ultimo = null;
            }
        }
        else {
            Nodo actual = primero;
            for (int j = 0; j < i-1; j++) {
                actual = actual.siguiente;
            }
            Nodo eliminado = actual.siguiente;
            actual.siguiente = eliminado.siguiente;
            if(eliminado == ultimo) {
                ultimo = actual;
            }
        }
        longitud--;
    }

    public void modificarPosicion(int i, T nuevoElem) {
        Nodo actual = primero;
        if (i < 0 || i >= longitud) {
            throw new RuntimeException("Indice fuera de rango!");
        }
        for (int j = 0; j < i; j++) {
            actual = actual.siguiente;
        }
        actual.valor = nuevoElem;
    }

    private class Nodo {
        private T valor;
        private Nodo siguiente;

        public Nodo(T nuevoValor) {
            this.valor = nuevoValor;
            this.siguiente = null;
        }
    }
}