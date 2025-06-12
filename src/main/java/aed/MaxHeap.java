package aed;

import java.util.Arrays;

/**
 * Clase genérica para un Max-Heap que mantiene el elemento máximo en la raíz.
 * Soporta operaciones de agregar, extraer máximo y actualizar elementos.
 *
 * @param <T> Tipo de elementos en el heap, debe implementar Comparable<T>
 */
public class MaxHeap<T extends Comparable<T>> {
    private final T[] cola; // Arreglo que almacena los elementos del heap
    private int cardinal; // Número actual de elementos en el heap

    /**
     * Constructor que crea un heap a partir de un arreglo de elementos.
     * Complejidad: O(n), donde n es la cantidad de elementos en listaDeElementos.
     *
     * @param listaDeElementos Arreglo de elementos a incluir en el heap
     */
    public MaxHeap(T[] listaDeElementos) {
        this.cola = Arrays.copyOf(listaDeElementos, listaDeElementos.length);
        this.cardinal = listaDeElementos.length;
        // Construir el heap usando heapify (siftDown en cada nodo no hoja)
        for (int i = (cardinal - 1) / 2; i >= 0; i--) {
            siftDown(i);
        }
        // Actualizar índices para los usuarios si es necesario
        for (int i = 0; i < cardinal; i++) {
            updateIndex(i);
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
        if (cardinal == 0) {
            throw new RuntimeException("No hay elementos insertados en el heap!");
        }
        return cola[0];
    }

    /**
     * Extrae y devuelve el elemento máximo del heap.
     * Complejidad: O(log n), donde n es la cantidad de elementos en el heap.
     *
     * @return El elemento máximo extraído
     * @throws RuntimeException si el heap está vacío
     */
    public T extractMax() {
        if (cardinal == 0) {
            throw new RuntimeException("Heap vacío");
        }
        T max = cola[0];
        cardinal--;
        if (cardinal > 0) {
            cola[0] = cola[cardinal];
            updateIndex(0);
            siftDown(0);
        }
        return max;
    }

    /**
     * Actualiza la posición de un elemento en el heap después de un cambio en su valor.
     * Complejidad: O(log n), donde n es la cantidad de elementos en el heap.
     *
     * @param index Índice del elemento a actualizar
     */
    public void update(int index) {
        if (index < 0 || index >= cardinal) return;
        siftUp(index);  // Subir si el valor aumentó
        siftDown(index); // Bajar si el valor disminuyó
    }

    /**
     * Reordena el heap hacia abajo desde el índice dado.
     * Complejidad: O(log n)
     *
     * @param index Índice desde donde empezar a reordenar
     */
    private void siftDown(int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        if (left < cardinal && cola[left].compareTo(cola[largest]) > 0) {
            largest = left;
        }
        if (right < cardinal && cola[right].compareTo(cola[largest]) > 0) {
            largest = right;
        }
        if (largest != index) {
            swap(index, largest);
            siftDown(largest);
        }
    }

    /**
     * Reordena el heap hacia arriba desde el índice dado.
     * Complejidad: O(log n)
     *
     * @param index Índice desde donde empezar a reordenar
     */
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (cola[index].compareTo(cola[parent]) > 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    /**
     * Intercambia dos elementos en el heap y actualiza sus índices si son Usuarios.
     * Complejidad: O(1)
     *
     * @param i Índice del primer elemento
     * @param j Índice del segundo elemento
     */
    private void swap(int i, int j) {
        T temp = cola[i];
        cola[i] = cola[j];
        cola[j] = temp;
        updateIndex(i);
        updateIndex(j);
    }

    /**
     * Actualiza el índice en el objeto Usuario si corresponde.
     * Complejidad: O(1)
     *
     * @param index Índice en el heap
     */
    private void updateIndex(int index) {
        if (index < cardinal && cola[index] instanceof Usuario) {
            ((Usuario) cola[index]).setHeapIndex(index);
        }
    }

    /**
     * Devuelve el número de elementos en el heap.
     * Complejidad: O(1)
     *
     * @return Cantidad de elementos
     */
    public int size() {
        return cardinal;
    }
}