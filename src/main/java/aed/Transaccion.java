package aed;

public class Transaccion implements Comparable<Transaccion> {
    private final int id;
    private final int id_comprador;
    private final int id_vendedor;
    private final int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return id_comprador;
    }

    public int id_vendedor() {
        return id_vendedor;
    }

    public int id() {
        return id;
    }

    public boolean esCreacion(){
        if (this.id_comprador == 0){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Transaccion other) {
        if (this.monto != other.monto) {
            return Integer.compare(other.monto, this.monto); // Mayor monto primero
        }
        return Integer.compare(other.id, this.id); // Mayor ID en empate
        // Para transacciones tambien??
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro)
            return true;
        if (!(otro instanceof Transaccion))
            return false;
        Transaccion t = (Transaccion) otro;
        return this.id == t.id() &&
                this.id_comprador == t.id_comprador() &&
                this.id_vendedor == t.id_vendedor() &&
                this.monto == t.monto;
    }

}