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
    void obtenerElementoOutOfIndex() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.agregar(3);
        lista.agregar(1);

        try {
            lista.obtener(31);
            fail("Should have thrown IndexOutOfBoundsException for index 31");
        } catch (IndexOutOfBoundsException e) {
            // Optionally verify message
            assertEquals("Index out of range: 31", e.getMessage());
        }

        try {
            lista.obtener(-1);
            fail("Should have thrown IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of range: -1", e.getMessage());
        }

        try {
            new ListaEnlazada<Integer>().obtener(0);
            fail("Should have thrown IndexOutOfBoundsException for empty list");
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of range: 0", e.getMessage());
        }
    }

    @Test
    void obtenerElementoValidIndex() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.agregar(3);
        lista.agregar(1);
        assertEquals(3, lista.obtener(0));
        assertEquals(1, lista.obtener(1));
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

    @Test
    void eliminarElementosDelMedio() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(22);
        lista.agregar(23);
        lista.agregar(24);
        lista.agregar(25);
        lista.agregar(26);

        assertEquals(5, lista.longitud());

        lista.eliminar(2);

        assertEquals(4, lista.longitud());
        assertEquals(22, lista.obtener(0));
        assertEquals(23, lista.obtener(1));
        assertEquals(25, lista.obtener(2));
        assertEquals(26, lista.obtener(3));

        lista.eliminar(1);

        assertEquals(3, lista.longitud());
        assertEquals(22, lista.obtener(0));
        assertEquals(25, lista.obtener(1));
        assertEquals(26, lista.obtener(2));

        lista.eliminar(1);

        assertEquals(2, lista.longitud());
        assertEquals(22, lista.obtener(0));
        assertEquals(26, lista.obtener(1));
    }

    @Test
    void eliminarExtremos(){
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        lista.agregar(5);
        lista.agregar(6);
        lista.agregar(7);
        lista.agregar(8);
        lista.agregar(9);
        
        assertEquals(5, lista.longitud());

        lista.eliminar(0);  //eliminamos el primero

        assertEquals(4, lista.longitud());
        assertEquals(6, lista.obtener(0));
        assertEquals(7, lista.obtener(1));
        assertEquals(8, lista.obtener(2));
        assertEquals(9, lista.obtener(3));

        lista.eliminar(3);  //eliminamos el ultimo

        assertEquals(3, lista.longitud());
        assertEquals(8, lista.ultimo());

    }

    @Test
    void modificarTodasLasPosiciones() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(5);
        lista.agregar(45);
        lista.agregar(96);
        lista.agregar(112);

        lista.modificarPosicion(0, 15);

        assertEquals(4, lista.longitud());
        assertEquals(15, lista.obtener(0));
        assertEquals(45, lista.obtener(1));
        assertEquals(96, lista.obtener(2));
        assertEquals(112, lista.obtener(3));

        lista.modificarPosicion(1,16);

        assertEquals(4, lista.longitud());
        assertEquals(15, lista.obtener(0));
        assertEquals(16, lista.obtener(1));
        assertEquals(96, lista.obtener(2));
        assertEquals(112, lista.obtener(3));

        lista.modificarPosicion(2,17);

        assertEquals(4, lista.longitud());
        assertEquals(15, lista.obtener(0));
        assertEquals(16, lista.obtener(1));
        assertEquals(17, lista.obtener(2));
        assertEquals(112, lista.obtener(3));

        lista.modificarPosicion(3,18);

        assertEquals(4, lista.longitud());
        assertEquals(15, lista.obtener(0));
        assertEquals(16, lista.obtener(1));
        assertEquals(17, lista.obtener(2));
        assertEquals(18, lista.obtener(3));
        assertEquals(18, lista.ultimo());
    }

    @Test
    void testConMuchosElementos() {
    ListaEnlazada<Integer> lista = new ListaEnlazada<>();
    int n = 10_000;

    for (int i = 0; i < n; i++) {
        lista.agregar(i);
    }

    assertEquals(n, lista.longitud());
    assertEquals(n - 1, lista.ultimo());
    assertEquals(0, lista.obtener(0));
    assertEquals(n /4 , lista.obtener(n / 4));

    lista.eliminar(n-1);    // elimino el ultimo

    assertEquals(n-1, lista.longitud());
    assertEquals(n-2, lista.ultimo());  

    lista.eliminar(0);

    assertEquals(n-2, lista.longitud());
    assertEquals(1, lista.obtener(0));  

    lista.modificarPosicion(n/2, 999);  //modificamos un elem cualquiera del medio

    assertEquals(n-2, lista.longitud());  
    assertEquals(1, lista.obtener(0));  
    assertEquals(n-2, lista.ultimo());
    assertEquals(999, lista.obtener(n/2));
    }

}


