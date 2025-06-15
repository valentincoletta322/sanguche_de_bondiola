package aed;

public class MaxHeap<T extends Comparable<T>> {
    private T[] cola;
    private int cardinal;

    public MaxHeap(T[] listaDeElementos) { 
        this.cola = (T[]) new Comparable[listaDeElementos.length];
        System.arraycopy(listaDeElementos, 0, this.cola, 0, listaDeElementos.length);
        this.cardinal = listaDeElementos.length;

        // Heapify from bottom up - ignora las hojas
        for (int i = (cardinal - 2) / 2; i >= 0; i--) {
            this.sift_down(i);
        }
    }

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

    public T extraerMax() {
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

    private void redimensionar() {
        int nuevoTamaño = cola.length == 0 ? 1 : cola.length * 2;
        T[] nuevaCola = (T[]) new Comparable[nuevoTamaño];
        for (int i = 0; i < cardinal; i++) {
            nuevaCola[i] = cola[i];
        }
        cola = nuevaCola;
    }
}