package aed;

import java.util.Arrays;

// Clase genérica para MaxHeap
public class MaxHeap<T extends Comparable<T>> {
    private final T[] cola;
    private int cardinal;

    public MaxHeap(T[] listaDeElementos) {
        this.cola = Arrays.copyOf(listaDeElementos, listaDeElementos.length);
        this.cardinal = listaDeElementos.length;

        for (int i = (cardinal - 1) / 2; i >= 0; i--) { // Corregido el índice inicial
            siftDown(i); // Hacer heapify es hacer sift down desde el final hasta la raiz
        }

        for (int i = 0; i < cardinal; i++) {
            updateIndex(i);
        }
    }

    // Devuelve el máximo - O(1)
    public T raiz() {
        if (cardinal == 0)
            throw new RuntimeException("No hay elementos insertados en el heap!");
        return cola[0];
    }

    // O(log(n)) -> por que heapify es O(n)??
    // Ojo, no actualiza los índices si se usan handles
    private void sift_down(int indice) {
        int hijoIzquierdo = 2 * indice + 1;
        int hijoDerecho = 2 * indice + 2;

        if (hijoIzquierdo >= cardinal) {
            return;
        }

        int max = hijoIzquierdo;

        if (hijoDerecho < this.cardinal) { // Si hay derecho, hay izquierdo
            if (cola[hijoDerecho].compareTo(cola[hijoIzquierdo]) >= 0) {
                max = hijoDerecho;
            }
        }

        if (cola[indice].compareTo(cola[max]) < 0) {
            this.intercambiar(indice, max);
            this.sift_down(max); // tal vez es medio polémico
        }
    }

    private void intercambiar(int index1, int index2) {
        T aux = cola[index1];
        cola[index1] = cola[index2];
        cola[index2] = aux;
    }

    //hice el heap de 0 por las dudas --

    // Extrae el máximo - O(log n)
    public T extractMax() {
        if (cardinal == 0) throw new RuntimeException("Heap vacío");
        T max = cola[0];
        cardinal--;
        if (cardinal > 0) {
            cola[0] = cola[cardinal];
            updateIndex(0);
            siftDown(0);
        }
        return max;
    }

    // Actualiza la posición de un elemento - O(log n)
    public void update(int index) {
        if (index < 0 || index >= cardinal) return;
        int parent = (index - 1) / 2;
        if (index > 0 && cola[index].compareTo(cola[parent]) > 0) {
            // VERIFICAR LO DE ABAJOOOOO
            siftUp(index); // La prioridad aumentó (saldo mayor)
        } else {
            siftDown(index); // La prioridad disminuyó o no cambió
        }
    }

    // Reordena hacia abajo - O(log n)
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

    // Intercambia elementos y actualiza índices
    private void swap(int i, int j) {
        T temp = cola[i];
        cola[i] = cola[j];
        cola[j] = temp;
        updateIndex(i);
        updateIndex(j);
    }

    // Actualiza el índice en el objeto si es un Usuario
    private void updateIndex(int index) {
        if (index < cardinal && cola[index] instanceof Usuario) {
            ((Usuario) cola[index]).heapIndex = index;
        }
    }

    public int size() {
        return cardinal;
    }
}


