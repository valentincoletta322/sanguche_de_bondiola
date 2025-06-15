package aed;

// Clase genérica para un Max Heap actualizable

/*
    Decidimos no heredar esta clase de un MaxHeap genérico porque no podemos simplemente 
agregar un atributo más y que el heap tenga sentido. Si el heap fuera completamente genérico, su atributo principal
seria "T[] cola", y para hacerlo actualizable estamos utilizando una cola de Handles. 
Como no podemos en una herencia redefinir un atributo, tendríamos que optar por ignorar el atributo "cola" del MaxHeap genérico, o
agregar una lista mas que nos permita guardar las referencias hacia el heap:
    private T[] cola;
    private int [] referenciaEnHeap[];  // Esta en correspondencia con un array de afuera
    private Referencias[] indicesDeHeap; // Me permite encontrar el indice de un objeto en el heap, o volver al mapa
    
    private class Referencias {
        int indiceEnHeap;
        int indiceEnMapa;
    }
    
    Además habría que redefinir todos los metodos, la lógica es completamente distinta, entonces preferimos llamarlo MaxHeapActualizable y
hacer tests con esa clase. Con esta clase se pueden guardar cualquier tipo de elemento y no afecta a la lógica de ordenamiento.
 */

public class MaxHeapActualizable<T extends Comparable<T>> {
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
    
    /**
     * Constructor que crea un heap a partir de un arreglo de elementos.
     * Complejidad: O(n), donde n es la cantidad de elementos en listaDeElementos.
     *
     * @param listaDeElementos Arreglo de elementos a incluir en el heap
     */
    // Complejidad O(n) ya que recorremos todos los elementos del array y los insertamos en el heap
    public MaxHeapActualizable(T[] listaDeElementos){ 
        this.cola = new MaxHeapActualizable.Handle[listaDeElementos.length];
        for (int i = 0; i < listaDeElementos.length; i++) {
            this.cola[i] = new Handle(listaDeElementos[i], i);
        }

        this.map = new MaxHeapActualizable.Handle[listaDeElementos.length];
        this.cardinal = listaDeElementos.length;

        for (int i = 0; i < listaDeElementos.length; i++) {
            this.map[i] = new Handle(listaDeElementos[i], i);
        }

        // El algoritmo de heapify ignora las hojas y arranca desde abajo hacia arriba
        // Entonces, la cuenta es ((this.cardinal)-2)/2 (DIVISION ENTERA), que es el padre del último elemento
        for (int i = ((this.cardinal)-2)/2; i >= 0; i--){
            this.sift_down(i); // Hacer heapify es hacer sift down desde el final hasta la raiz
        }
    }


    /**
     * Devuelve el elemento máximo (raíz del heap).
     * Complejidad: O(1)
     *
     * @return El elemento máximo del heap
     * @throws RuntimeException si el heap está vacío
     */
    public T raiz(){
        if (cardinal > 0){
            return this.cola[0].valor;
        }
        throw new RuntimeException("No hay elementos insertados en el heap!");
    }


    /**
     * Reordena el heap hacia abajo desde el índice dado.
     * Complejidad: O(log n)
     *
     * @param index Índice desde donde empezar a reordenar
    */
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

    /**
     * Reordena el heap hacia arriba desde el índice dado.
     * Complejidad: O(log n)
     *
     * @param index Índice desde donde empezar a reordenar
     */
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

    /**
     * Intercambia dos elementos en el heap y actualiza sus índices en el mapa.
     * Complejidad: O(1)
     *
     * @param index1 Índice del primer elemento
     * @param index2 Índice del segundo elemento
     */
    private void intercambiar(int index1, int index2){
        Handle aux = cola[index1];
        cola[index1] = cola[index2];
        cola[index2] = aux;
        
        map[cola[index1].referencia].referencia = index1;
        map[cola[index2].referencia].referencia = index2;

    }


    /**
     * Extrae y devuelve el elemento máximo del heap.
     * Complejidad: O(log n), donde n es la cantidad de elementos en el heap.
     *
     * @return El elemento máximo extraído
     * @throws RuntimeException si el heap está vacío
     */
    public T extractMax() {
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

    public int cardinal() {
        return cardinal;
    }

    public void redimensionar() {
        int length = cola.length;
        if (length == 0) {
            length = 1; // Si esta vacio le doy 2 de espacio.
        }
        Handle[] nuevaCola = new MaxHeapActualizable.Handle[length * 2];
        Handle[] nuevoMap = new MaxHeapActualizable.Handle[length * 2];
        
        for (int i = 0; i < cola.length; i++) {
            nuevaCola[i] = cola[i];
            nuevoMap[i] = map[i];
        }
        
        cola = nuevaCola;
        map = nuevoMap;
    }

    public void agregar(T valor) {
        if (cardinal >= cola.length) {
            redimensionar();
        }
        
        cola[cardinal] = new Handle(valor, cardinal);
        map[cardinal] = new Handle(valor, cardinal);
        
        sift_up(cardinal);
        
        cardinal++;
    }
    
}
