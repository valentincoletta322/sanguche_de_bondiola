package aed;

// Clase genérica para MaxHeap
public class MaxHeap<T extends Comparable<T>> {
    private T[] cola;
    private int cardinal;

    // Constructor que hace heapify
    // O(n)?
    public MaxHeap(T[] listaDeElementos){ 
        this.cola = listaDeElementos; // aca hay aliassing!!!
        this.cardinal = listaDeElementos.length;

        //cuenta medio falopa pero ignoras las hojas, no se si nos cambia la complejidad si no lo tenemos, (PREGUNTAR)
        for (int i = ((this.cardinal)-2)/2; i >= 0; i--){ // Hacer heapify es hacer sift down desde el final hasta la raiz
            this.sift_down(i);
        }
    }


    public T raiz(){
        if (cardinal > 0){
            return this.cola[0];
        }
        throw new RuntimeException("No hay elementos insertados en el heap!");
    }

    // O(log(n)) -> por que heapify es O(n)??
    private void sift_down(int indice){
        int hijoIzquierdo = 2*indice+1;
        int hijoDerecho = 2*indice+2;

        if (hijoIzquierdo >= cardinal){
            return;
        }
        
        int max = hijoIzquierdo;

        if (hijoDerecho < this.cardinal){ // Si hay derecho, hay izquierdo
            if (cola[hijoDerecho].compareTo(cola[hijoIzquierdo]) >= 0){
                max = hijoDerecho;
            }
        }

        if (cola[indice].compareTo(cola[max]) < 0){
            this.intercambiar(indice, max);
            this.sift_down(max);
        }
        return;
    }

    private void intercambiar(int index1, int index2){
        T aux = cola[index1];
        cola[index1] = cola[index2];
        cola[index2] = aux;
    }

}
