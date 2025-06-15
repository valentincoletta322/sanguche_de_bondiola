package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class MaxHeapActualizableTests {

    @Test 
    void heapVacio() {
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(new Integer[] {}); 

        assertEquals(0, heap.cardinal());

    }

    @Test 
    void agregarUnElementoHeapify () {
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(new Integer[] {1}); 

        assertEquals(1, heap.cardinal());
        assertEquals(1, heap.raiz());

    }

    @Test 
    void agregarUnElementoaHeapVacio() {
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(new Integer[] {}); 
        heap.agregar(10);

        assertEquals(1, heap.cardinal());
        assertEquals(10, heap.raiz());         

    }



    @Test 
    void agregarUnElemento() {
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(new Integer[] {1}); 
        heap.agregar(10);

        assertEquals(2, heap.cardinal());
        assertEquals(10, heap.raiz());         

    }

    @Test 
    void agregarElementosCrecienteHeapify (){
        Integer[] elementos = {1,2,3,4,5};
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos); 

        assertEquals(5,heap.cardinal());
        assertEquals(5, heap.raiz());
        heap.extractMax();
        assertEquals(4,heap.cardinal());
        assertEquals(4, heap.raiz());
        heap.extractMax();
        assertEquals(3,heap.cardinal());
        assertEquals(3, heap.raiz());
        heap.extractMax();
        assertEquals(2,heap.cardinal());
        assertEquals(2, heap.raiz());
        heap.extractMax();
        assertEquals(1,heap.cardinal());
        assertEquals(1, heap.raiz());
        heap.extractMax();

    }


    @Test 
    void agregarVariosElementosCreciente(){
        Integer[] elementos = {1,2,3};
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos); 

        heap.agregar(4);
        heap.agregar(5);

        assertEquals(5,heap.cardinal());
        assertEquals(5, heap.raiz());
        heap.extractMax();
        assertEquals(4,heap.cardinal());
        assertEquals(4, heap.raiz());
        heap.extractMax();
        assertEquals(3,heap.cardinal());
        assertEquals(3, heap.raiz());
        heap.extractMax();
        assertEquals(2,heap.cardinal());
        assertEquals(2, heap.raiz());
        heap.extractMax();
        assertEquals(1,heap.cardinal());
        assertEquals(1, heap.raiz());
        heap.extractMax();

    }


    @Test 
    void agregarElementosDecrecienteHeapify(){
        Integer[] elementos = {5,4,3,2,1};
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos); 

        assertEquals(5,heap.cardinal());
        assertEquals(5, heap.raiz());
        heap.extractMax();
        assertEquals(4,heap.cardinal());
        assertEquals(4, heap.raiz());
        heap.extractMax();
        assertEquals(3,heap.cardinal());
        assertEquals(3, heap.raiz());
        heap.extractMax();
        assertEquals(2,heap.cardinal());
        assertEquals(2, heap.raiz());
        heap.extractMax();
        assertEquals(1,heap.cardinal());
        assertEquals(1, heap.raiz());
        heap.extractMax();
        
    }
    
    @Test 
    void agregarElementosDesordenadosHeapify(){
        Integer[] elementos = {15,23,2,8,100};
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos); 

        assertEquals(5,heap.cardinal());
        assertEquals(100, heap.raiz());
        heap.extractMax();
        assertEquals(4,heap.cardinal());
        assertEquals(23, heap.raiz());
        heap.extractMax();
        assertEquals(3,heap.cardinal());
        assertEquals(15, heap.raiz());
        heap.extractMax();
        assertEquals(2,heap.cardinal());
        assertEquals(8, heap.raiz());
        heap.extractMax();
        assertEquals(1,heap.cardinal());
        assertEquals(2, heap.raiz());
        heap.extractMax();

    }




    @Test
    void agregarElementosDesordenados(){
        Integer[] elementos = {15,23,2,8,100};
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos);

        assertEquals(5,heap.cardinal());
        assertEquals(100, heap.raiz());
        heap.extractMax();

        heap.agregar(102);
        heap.agregar(1);

        assertEquals(6,heap.cardinal());
        assertEquals(102, heap.raiz());
        heap.extractMax();

        assertEquals(5,heap.cardinal());
        assertEquals(23, heap.raiz());
        heap.extractMax();
        assertEquals(4,heap.cardinal());
        assertEquals(15, heap.raiz());
        heap.extractMax();
        assertEquals(3,heap.cardinal());
        assertEquals(8, heap.raiz());
        heap.extractMax();
        assertEquals(2,heap.cardinal());
        assertEquals(2, heap.raiz());
        heap.extractMax();
        assertEquals(1,heap.cardinal());
        assertEquals(1, heap.raiz());
        heap.extractMax();
        assertEquals(0,heap.cardinal());

        
    }


    @Test 
    void agregarElementosDuplicadosHeapify(){
        Integer[] elementos = {15,23,100,8,100};
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos); 

        assertEquals(5,heap.cardinal());
        assertEquals(100, heap.raiz());
        heap.extractMax();
        assertEquals(4,heap.cardinal());
        assertEquals(100, heap.raiz());
        heap.extractMax();
        assertEquals(3,heap.cardinal());
        assertEquals(23, heap.raiz());
        heap.extractMax();
        assertEquals(2,heap.cardinal());
        assertEquals(15, heap.raiz());
        heap.extractMax();
        assertEquals(1,heap.cardinal());
        assertEquals(8, heap.raiz());
        heap.extractMax();

    }


    Integer NCLAVES = 10; //poner numero par

    private Integer clave(Integer i) {
        return NCLAVES * ((i * i - 100 * i) % NCLAVES) + i;
    }
    

    @Test
    void agregarMuchosElementosHeapify() {
        Integer[] elementos = new Integer[NCLAVES];
        for(Integer i=0; i < NCLAVES; i++ ){
            Integer k = clave(i);
            elementos[i] = k;
        }

        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos);

        assertEquals(elementos.length, heap.cardinal());
        int raiz_ant = heap.raiz();
        heap.extractMax();

        for (Integer i= (elementos.length-1); i > 0; i= i-1 ){
            assertEquals(i,heap.cardinal());
            assertTrue(raiz_ant >= heap.raiz());
            raiz_ant = heap.raiz();
            heap.extractMax();
        }
    }



    @Test 
    void agregarMuchosElementos(){
        Integer[] elementos = new Integer[NCLAVES/2];
        for(Integer i=0; i < (NCLAVES/2); i++ ){
            Integer k = clave(i);
            elementos[i] = k;
        }

        //Creo con heapify agregando la mitad de los elementos
        MaxHeapActualizable<Integer> heap = new MaxHeapActualizable<>(elementos);

        assertEquals((elementos.length), heap.cardinal());
        int raiz_ant = heap.raiz();
        heap.extractMax();

        //Elimino la mitad de los elementos
        for(Integer i=(elementos.length/4)-1; i>0; i=i-1){
            assertEquals(i/4, heap.cardinal());
            assertTrue(raiz_ant >= heap.raiz());
            raiz_ant = heap.raiz();
            heap.extractMax();
        }   
        //Agrego mas elementos 
        for(int i = NCLAVES/2; i < NCLAVES; i++){
            Integer k = clave(i);
            heap.agregar(k);
        }

        //Elimino todos
        for (Integer i= heap.cardinal(); i > 0; i= i-1 ){
            assertEquals(i,heap.cardinal());
            assertTrue(raiz_ant >= heap.raiz());
            raiz_ant = heap.raiz();
            heap.extractMax();
        }

        }       

    }

