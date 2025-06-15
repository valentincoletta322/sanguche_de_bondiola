package aed;

/**
 * Clase que representa a un usuario en el sistema Berretacoin.
 * Cada usuario tiene un ID, un saldo y un índice en el heap.
 */
public class Usuario implements Comparable<Usuario> {
    public static final int notInHeap = -1;
    private final int id;
    private long saldo;
    private int heapIndex;

    /**
     * Constructor del usuario.
     *
     * @param id    ID único del usuario
     * @param saldo Saldo inicial del usuario
     */
    public Usuario(int id, long saldo) {
        this.id = id;
        this.saldo = saldo;
        this.heapIndex = notInHeap;
    }

    /**
     * Devuelve el ID del usuario.
     * Complejidad: O(1)
     *
     * @return ID del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el saldo del usuario.
     * Complejidad: O(1)
     *
     * @return Saldo del usuario
     */
    public long getSaldo() {
        return saldo;
    }

    /**
     * Establece el saldo del usuario.
     * Complejidad: O(1)
     *
     * @param nuevoSaldo Nuevo saldo del usuario
     */
    void setSaldo(long nuevoSaldo) {
        this.saldo = nuevoSaldo;
    }

    /**
     * Devuelve el índice del usuario en el heap.
     * Complejidad: O(1)
     *
     * @return Índice en el heap
     */
    public int getHeapIndex() {
        return heapIndex;
    }

    /**
     * Establece el índice del usuario en el heap.
     * Complejidad: O(1)
     *
     * @param index Nuevo índice en el heap
     */
    void setHeapIndex(int index) {
        this.heapIndex = index;
    }

    /**
     * Compara este usuario con otro para determinar su orden en el heap.
     * Prioriza el mayor saldo y, en caso de empate, el menor ID.
     * Complejidad: O(1)
     *
     * @param other Otro usuario a comparar
     * @return Un valor negativo si este usuario es mayor, positivo si es menor, 0 si son iguales
     */
    @Override
    public int compareTo(Usuario other) {
        if (this.saldo != other.saldo) {
            return Long.compare(this.saldo, other.saldo); // Mayor saldo primero
        }
        return Integer.compare(other.id, this.id); // Menor ID en empate
    }

    /**
     * Verifica si este usuario es igual a otro objeto. Solo comparo IDs.
     * Complejidad: O(1)
     *
     * @param otro Objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (!(otro instanceof Usuario)) return false;
        Usuario other = (Usuario) otro;
        return this.id == other.id;
    }
}