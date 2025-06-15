package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Random;


class MaxHeapTests {

    @Test
    void heapVacio() {
        MaxHeap<Integer> heap = new MaxHeap<>(new Integer[] {});

        assertEquals(0, heap.cardinal());

    }

    @Test
    void agregarUnElemento () {
        MaxHeap<Integer> heap = new MaxHeap<>(new Integer[] {1});

        assertEquals(1, heap.cardinal());
        assertEquals(1, heap.raiz());

    }

    @Test
    void agregarElementosCreciente (){
        Integer[] elementos = {1,2,3,4,5};
        MaxHeap<Integer> heap = new MaxHeap<>(elementos);

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
    void agregarElementosDecreciente (){
        Integer[] elementos = {5,4,3,2,1};
        MaxHeap<Integer> heap = new MaxHeap<>(elementos);

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
    void agregarElementosDesordenados (){
        Integer[] elementos = {15,23,2,8,100};
        MaxHeap<Integer> heap = new MaxHeap<>(elementos);

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

    //Agrega los dos duplicados, creo que esta bien pero por las dudas revisar
    @Test
    void agregarElementosDuplicados(){
        Integer[] elementos = {15,23,100,8,100};
        MaxHeap<Integer> heap = new MaxHeap<>(elementos);

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

    //Creo que clave devuelve numeros medio aletorios pero estaria bueno mirarlo un poco mas

    Integer NCLAVES = 10000;

    private Integer clave(Integer i) {
        return NCLAVES * ((i * i - 100 * i) % NCLAVES) + i;
    }


    @Test
    void agregarMuchosElementos() {
        Integer[] elementos = new Integer[NCLAVES];


        for(Integer i=0; i < NCLAVES; i++ ){
            Integer k = clave(i);
            elementos[i] = k;
        }

        MaxHeap<Integer> heap = new MaxHeap<>(elementos);

        assertEquals(elementos.length, heap.cardinal());
        int raiz_ant = heap.raiz();
        heap.extractMax();
        System.out.println(elementos.length);

        for (Integer i= (elementos.length-1); i > 0; i= i-1 ){
            System.out.println(i);
            assertEquals(i,heap.cardinal());
            assertTrue(raiz_ant >= heap.raiz());
            raiz_ant = heap.raiz();
            heap.extractMax();
        }
    }


}

