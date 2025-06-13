package aed;

/**
 * Clase que representa una transacción en el sistema Berretacoin.
 * Cada transacción tiene un ID, un comprador, un vendedor y un monto.
 */
public class Transaccion implements Comparable<Transaccion> {
    private final int id;
    private final int id_comprador;
    private final int id_vendedor;
    private final int monto;

    /**
     * Constructor de la transacción.
     *
     * @param id           ID único de la transacción
     * @param id_comprador ID del comprador (0 si es una transacción de creación)
     * @param id_vendedor  ID del vendedor
     * @param monto        Monto de la transacción
     */
    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    /**
     * Devuelve el monto de la transacción.
     * Complejidad: O(1)
     *
     * @return Monto de la transacción
     */
    public int monto() {
        return monto;
    }

    /**
     * Devuelve el ID del comprador.
     * Complejidad: O(1)
     *
     * @return ID del comprador
     */
    public int id_comprador() {
        return id_comprador;
    }

    /**
     * Devuelve el ID del vendedor.
     * Complejidad: O(1)
     *
     * @return ID del vendedor
     */
    public int id_vendedor() {
        return id_vendedor;
    }

    /**
     * Devuelve el ID de la transacción.
     * Complejidad: O(1)
     *
     * @return ID de la transacción
     */
    public int id() {
        return id;
    }

    /**
     * Indica si la transacción es de creación (comprador es 0).
     * Complejidad: O(1)
     *
     * @return true si es una transacción de creación, false en caso contrario
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean esCreacion() {
        return this.id_comprador == 0;
    }

    /**
     * Compara esta transacción con otra para determinar su orden en el heap.
     * Prioriza el mayor monto y, en caso de empate, el mayor ID.
     * Complejidad: O(1)
     *
     * @param other Otra transacción a comparar
     * @return Un valor negativo si esta transacción es mayor, positivo si es menor, 0 si son iguales
     */
    @Override
    public int compareTo(Transaccion other) {
        if (other == null) throw new NullPointerException("Comparación con null");
        if (this.monto != other.monto) {
            return Integer.compare(this.monto, other.monto); // Mayor monto primero
        }
        return Integer.compare(this.id, other.id); // Mayor ID en empate
    }

    /**
     * Verifica si esta transacción es igual a otro objeto.
     * Complejidad: O(1)
     *
     * @param otro Objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (!(otro instanceof Transaccion)) return false;
        Transaccion t = (Transaccion) otro;
        return this.id == t.id() &&
                this.id_comprador == t.id_comprador() &&
                this.id_vendedor == t.id_vendedor() &&
                this.monto == t.monto;
    }
}