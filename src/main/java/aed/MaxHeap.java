package aed;

public class MaxHeap<T extends Comparable<T>> {
    private T[] cola;
    private int cardinal;

    /**
     * Constructor que crea un heap a partir de un arreglo de elementos.
     * Complejidad: O(n), donde n es la cantidad de elementos en listaDeElementos.
     *
     * @param listaDeElementos Arreglo de elementos a incluir en el heap
     */
    @SuppressWarnings("unchecked")
    public MaxHeap(T[] listaDeElementos) {
        this.cola = (T[]) new Comparable[listaDeElementos.length];
        System.arraycopy(listaDeElementos, 0, this.cola, 0, listaDeElementos.length);
        this.cardinal = listaDeElementos.length;

        // Heapify from bottom up - ignora las hojas
        for (int i = (cardinal - 2) / 2; i >= 0; i--) {
            this.sift_down(i);
        }
    }

    /**
     * Devuelve el elemento máximo (raíz del heap).
     * Complejidad: O(1)
     *
     * @return El elemento máximo del heap
     * @throws RuntimeException si el heap está vacío
     */
    public T raiz() {
        if (cardinal > 0) {
            return this.cola[0];
        }
        throw new RuntimeException("No hay elementos insertados en el heap!");
    }

    public void sift_down(int indice) {
        while (true) {
            int hijoIzquierdo = 2 * indice + 1;
            int hijoDerecho = 2 * indice + 2;

            // si no tenemos hijo izquierdo, llegué al final
            if (hijoIzquierdo >= cardinal) {
                break;
            }

            int max = hijoIzquierdo;
            if (hijoDerecho < cardinal && cola[hijoDerecho].compareTo(cola[hijoIzquierdo]) >= 0) {
                max = hijoDerecho;
            }

            if (cola[indice].compareTo(cola[max]) >= 0) {
                break;
            }

            this.intercambiar(indice, max);
            indice = max;
        }
    }

    public int cardinal() {
        return cardinal;
    }


    public void sift_up(int indice) {
        while (indice > 0) {
            int padre = (indice - 1) / 2;
            if (cola[indice].compareTo(cola[padre]) <= 0) {
                break;
            }
            this.intercambiar(indice, padre);
            indice = padre;
        }
    }

    private void intercambiar(int index1, int index2) {
        T aux = cola[index1];
        cola[index1] = cola[index2];
        cola[index2] = aux;
    }

    public T extractMax() {
        if (cardinal == 0) {
            throw new RuntimeException("Heap vacio");
        }
        T max = cola[0];
        cardinal--;
        if (cardinal > 0) {
            cola[0] = cola[cardinal];
            sift_down(0);
        }
        return max;
    }

    public void agregar(T elemento) {
        if (cardinal >= cola.length) {
            redimensionar();
        }

        cola[cardinal] = elemento;

        sift_up(cardinal);

        cardinal++;
    }

    @SuppressWarnings("unchecked")
    private void redimensionar() {
        int nuevoTamanio = cola.length == 0 ? 1 : cola.length * 2;
        T[] nuevaCola = (T[]) new Comparable[nuevoTamanio];
        if (cardinal >= 0) System.arraycopy(cola, 0, nuevaCola, 0, cardinal);
        cola = nuevaCola;
    }
}