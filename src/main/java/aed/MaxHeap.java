package aed;

// Clase genérica para MaxHeap
public class MaxHeap<T extends Comparable<T>> {
    private Handle[] cola; // heapificado
    private Handle[] map; // para obtener la referencia de cada elemento en O(1)

    private int cardinal;

    private class Handle implements Comparable<Handle> {
        T valor;
        private int referencia;
        public Handle(T valor, int nuevaReferencia){
            this.valor = valor;
            this.referencia = nuevaReferencia;
        }
        public int referencia (){
            return this.referencia;
        }
        public T obtener (){
            return this.valor;
        }
        public int compareTo(Handle otro) {
            return this.valor.compareTo(otro.valor);
        }
    }

    private int obtenerReferencia(int indice) {
        if (indice < 0 || indice >= map.length) {
            throw new RuntimeException("Referencia fuera de rango!");
        }
        return map[indice].referencia;
    }
    
    // Constructor que hace heapify
    // O(n)?
    public MaxHeap(T[] listaDeElementos){ 
        this.cola = new MaxHeap.Handle[listaDeElementos.length];
        for (int i = 0; i < listaDeElementos.length; i++) {
            this.cola[i] = new Handle(listaDeElementos[i], i);
        }

        this.map = new MaxHeap.Handle[listaDeElementos.length];
        this.cardinal = listaDeElementos.length;

        for (int i = 0; i < listaDeElementos.length; i++) {
            this.map[i] = new Handle(listaDeElementos[i], i);
        }

        //cuenta medio falopa que ignoras las hojas, no se si nos cambia la complejidad si no lo tenemos (PREGUNTAR)
        for (int i = ((this.cardinal)-2)/2; i >= 0; i--){
            this.sift_down(i); // Hacer heapify es hacer sift down desde el final hasta la raiz
        }
    }


    public T raiz(){
        if (cardinal > 0){
            return this.cola[0].valor;
        }
        throw new RuntimeException("No hay elementos insertados en el heap!");
    }

    // O(log(n)) -> por que heapify es O(n)??
    public void sift_down(int indice){
        indice = obtenerReferencia(indice);
        
        while (true) {
            int hijoIzquierdo = 2*indice+1;
            int hijoDerecho = 2*indice+2;
            
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

    // cambio la recursion aca porque me estaba volviendo loco
    public void sift_up(int indice){
        indice = obtenerReferencia(indice);
        if (indice == 0){
            return;
        }
        while (indice > 0) {
            int padre = (indice-1)/2;
            if (cola[indice].compareTo(cola[padre]) <= 0) {
                break;
            }
            this.intercambiar(indice, padre);
            indice = padre;
        }
        return;
    }

    private void intercambiar(int index1, int index2){
        Handle aux = cola[index1];
        cola[index1] = cola[index2];
        cola[index2] = aux;
        
        map[cola[index1].referencia].referencia = index1;
        map[cola[index2].referencia].referencia = index2;

    }

    public T extraerMax() {
        if (cardinal == 0){
            throw new RuntimeException("Heap vacio");
        }
        T max = cola[0].valor;
        cardinal--;
        if (cardinal > 0) {
            cola[0] = cola[cardinal];
            map[cola[0].referencia].referencia = 0;
            sift_down(cola[0].referencia);
        }
        return max;
    }

}
