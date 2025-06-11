package aed;

// Clase genérica para MaxHeap
public class MaxHeap<T extends Comparable<T>> {
    private T[] cola;
    private Handle[] handles;    
    private int cardinal;

    public class Handle {
        private int referencia;
        public Handle(int nuevaReferencia){
            this.referencia = nuevaReferencia;
        }
        public int referencia (){
            return this.referencia;
        }
        public T obtener (){
            if (referencia < cardinal && referencia >= 0) {
                return cola[referencia];
            }
            throw new RuntimeException("Referencia fuera de rango!");
        }
    }
    
    // Constructor que hace heapify
    // O(n)?
    public MaxHeap(T[] listaDeElementos){ 
        this.cola = listaDeElementos; // checkear esta cosa
        this.handles = new MaxHeap.Handle[listaDeElementos.length];
        this.cardinal = listaDeElementos.length;

        for (int i = 0; i < listaDeElementos.length; i++) {
            this.handles[i] = new Handle(i);
        }

        //cuenta medio falopa que ignoras las hojas, no se si nos cambia la complejidad si no lo tenemos (PREGUNTAR)
        for (int i = ((this.cardinal)-2)/2; i >= 0; i--){
            this.sift_down(i); // Hacer heapify es hacer sift down desde el final hasta la raiz
        }
    }


    public T raiz(){
        if (cardinal > 0){
            return this.cola[0];
        }
        throw new RuntimeException("No hay elementos insertados en el heap!");
    }

    public Handle getHandle(int indice){
        if (indice < 0 || indice >= cardinal) {
            throw new RuntimeException("Indice fuera de rango!");
        }
        return handles[indice];
    }

    // O(log(n)) -> por que heapify es O(n)??
    public void sift_down(int indice){
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
            this.sift_down(max); // tal vez es medio polémico
        }
        return;
    }

    public void sift_up(int indice){
        if (indice == 0){
            return;
        }
        int padre = (indice-1)/2;
        if (cola[indice] instanceof Usuario) {
            Usuario aux = (Usuario) cola[indice];
            System.out.println(aux.saldo);
            System.out.println("Con su id: " + aux.id);
            Usuario papa = (Usuario) cola[padre];
            System.out.println("Y su padre es: " + papa.saldo + " con id: " + papa.id);
        }
        if (cola[indice].compareTo(cola[padre]) > 0){
            this.intercambiar(indice, padre);
            this.sift_up(padre);
        }
        return;
    }

    private void intercambiar(int index1, int index2){
        T aux = cola[index1];
        cola[index1] = cola[index2];
        cola[index2] = aux;
        
        // casi me mato:
        Handle auxHandle = handles[index1];
        handles[index1] = handles[index2];
        handles[index2] = auxHandle;
        
        handles[index1].referencia = index1;
        handles[index2].referencia = index2;

    }

}
