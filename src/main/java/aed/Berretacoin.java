package aed;

/**
 * Clase principal que gestiona la cadena de bloques y los usuarios en el sistema Berretacoin.
 */
public class Berretacoin {
    private final MaxHeap<Usuario> heapDeSaldos; // Heap para mantener el usuario con mayor saldo
    private final ListaEnlazada<Bloque> listaDeBloques; // Lista enlazada de bloques
    private final Usuario[] usuarios; // Arreglo de usuarios

    /**
     * Constructor que inicializa el sistema con n_usuarios.
     * Complejidad: O(P), donde P es la cantidad de usuarios.
     *
     * @param n_usuarios Cantidad de usuarios en el sistema
     */
    public Berretacoin(int n_usuarios) {
        this.usuarios = new Usuario[n_usuarios];
        for (int i = 1; i <= n_usuarios; i++) {
            Usuario nuevo = new Usuario(i, 0);
            this.usuarios[i - 1] = nuevo;
        }
        this.heapDeSaldos = new MaxHeap<>(this.usuarios);
        this.listaDeBloques = new ListaEnlazada<>();
    }

    /**
     * Agrega un nuevo bloque de transacciones a la cadena de bloques.
     * Complejidad: O(n_b * log P), donde n_b es la cantidad de transacciones en el bloque.
     *
     * @param transacciones Arreglo de transacciones del nuevo bloque
     */
    public void agregarBloque(Transaccion[] transacciones) {
        listaDeBloques.agregar(new Bloque(transacciones)); // O(n_b)
        for (Transaccion tx : transacciones) {
            Usuario vendedor = usuarios[tx.id_vendedor() - 1];
            vendedor.setSaldo(vendedor.getSaldo() + tx.monto());
            heapDeSaldos.update(vendedor.getHeapIndex()); // O(log P)
            if (tx.id_comprador() != 0) {
                Usuario comprador = usuarios[tx.id_comprador() - 1];
                comprador.setSaldo(comprador.getSaldo() - tx.monto());
                heapDeSaldos.update(comprador.getHeapIndex()); // O(log P)
            }
        }
    }

    /**
     * Devuelve la transacción de mayor valor del último bloque.
     * Complejidad: O(1)
     *
     * @return Transacción de mayor valor
     */
    public Transaccion txMayorValorUltimoBloque() {
        return listaDeBloques.ultimo().obtenerMaximo();
    }

    /**
     * Devuelve una copia de las transacciones del último bloque.
     * Complejidad: O(n_b), donde n_b es la cantidad de transacciones en el bloque.
     *
     * @return Arreglo de transacciones del último bloque
     */
    public Transaccion[] txUltimoBloque() {
        return listaDeBloques.ultimo().getTransacciones();
    }

    /**
     * Devuelve el ID del usuario con el mayor saldo.
     * Complejidad: O(1)
     *
     * @return ID del usuario con mayor saldo
     */
    public int maximoTenedor() {
        return heapDeSaldos.raiz().getId();
    }

    /**
     * Devuelve el monto promedio de las transacciones no de creación del último bloque.
     * Complejidad: O(1)
     *
     * @return Monto promedio o 0 si no hay transacciones
     */
    public int montoMedioUltimoBloque() {
        Bloque ultimo = listaDeBloques.ultimo();
        int cantidad = ultimo.cantidadTransacciones();
        return cantidad == 0 ? 0 : ultimo.sumaMontos() / cantidad;
    }

    /**
     * Hackea la transacción de mayor valor del último bloque, revirtiendo sus efectos.
     * Complejidad: O(log n_b + log P)
     */
    public void hackearTx() {
        Bloque ultimo = listaDeBloques.ultimo();
        Transaccion tx = ultimo.hackearTx(); // O(log n_b)
        if (tx != null) {
            Usuario vendedor = usuarios[tx.id_vendedor() - 1];
            vendedor.setSaldo(vendedor.getSaldo() - tx.monto());
            heapDeSaldos.update(vendedor.getHeapIndex()); // O(log P)
            if (tx.id_comprador() != 0) {
                Usuario comprador = usuarios[tx.id_comprador() - 1];
                comprador.setSaldo(comprador.getSaldo() + tx.monto());
                heapDeSaldos.update(comprador.getHeapIndex()); // O(log P)
            }
        }
    }
}
