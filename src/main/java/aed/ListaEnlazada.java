package aed;

public class ListaEnlazada<T> {

    private class Nodo {
        private T valor;
        private Nodo siguiente;

        public Nodo(T nuevoValor){
            this.valor = nuevoValor;
            this.siguiente = null;
        }
    }

    private Nodo primero;
    private Nodo ultimo;
    private int longitud; 

    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
        this.longitud = 0;
    }

    // Agregar un bloque al final de la lista - O(1)
    public void agregar(T valor) {
        Nodo nuevoNodo = new Nodo(valor);

        if (this.longitud == 0) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.siguiente = nuevoNodo;
            ultimo = nuevoNodo;
        }
        this.longitud += 1; 
    }

    // Obtener el último bloque - O(1)
    public T ultimo() {
        if (ultimo == null) {
            throw new RuntimeException("La lista esta vacia!");
        }
        return ultimo.valor;
    }

    //agregue para poder testear

    public int longitud() {
        return this.longitud; 
    }

    public T obtener(int i) {
        Nodo res = this.primero;
        for (int n=0; n < i; n++){
            res = res.siguiente;
        }

        return res.valor; 
    }

}