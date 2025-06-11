package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ListaEnlazadaTests {

     @Test
    void nuevaListaEstaVacia() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        assertEquals(0, lista.longitud());
    }

     @Test
    void agregarUnElementoAtras() {
        ListaEnlazada<Boolean> lista = new ListaEnlazada<>();

        lista.agregar(true);

        assertEquals(1, lista.longitud());
        assertEquals(true, lista.obtener(0));
    }

    @Test
    void agregarVariosElementosAtras() {
        ListaEnlazada<Character> lista = new ListaEnlazada<>();

        lista.agregar('2');
        lista.agregar('3');
        lista.agregar('4');
        lista.agregar('5');

        assertEquals(4, lista.longitud());
        assertEquals('2', lista.obtener(0));
        assertEquals('3', lista.obtener(1));
        assertEquals('4', lista.obtener(2));
        assertEquals('5', lista.obtener(3));
    }

    //no me acuerdo como es la estructura de los bloques podriamos probar que funcione con esa estructura
    @Test
    void agregarVariosElementosdeOtroTipo() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(2);
        lista.agregar(3);
        lista.agregar(4);
        lista.agregar(5);

        assertEquals(4, lista.longitud());
        assertEquals(2, lista.obtener(0));
        assertEquals(3, lista.obtener(1));
        assertEquals(4, lista.obtener(2));
        assertEquals(5, lista.obtener(3));
    }


    @Test 
    void agregarMuchosElementos(){
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        int claves = 1000;
        for(Integer i=0; i<claves; i++){
            lista.agregar(i);
        }

        assertEquals(claves, lista.longitud());
        for (Integer i=0; i<claves; i++){
            assertEquals(i, lista.obtener(i));
        }
    }


    }



