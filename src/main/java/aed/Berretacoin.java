package aed;

public class Berretacoin {
    // private Usuarios usuarios; // haría falta esa clase?

    private class HandleUsuarios {
        private Usuario usuarioApuntado;
        public HandleUsuarios(Usuario nuevUsuario){
            this.usuarioApuntado = nuevUsuario;
        }
    }

    private MaxHeap<Usuario> heapDeSaldos;
    private ListaEnlazada<Bloque> listaDeBloques;
    // alternativa posible:
    private HandleUsuarios[] usuarios;

    // Constructor - O(P)
    public Berretacoin(int n_usuarios){
        this.usuarios = new HandleUsuarios[n_usuarios];
        Usuario[] usuariosToHeapify = new Usuario[n_usuarios];
        for (int i = 1; i <= n_usuarios; i++){
            Usuario nuevo = new Usuario(i, 0);
            usuariosToHeapify[i-1] = nuevo;
            this.usuarios[i-1] = new HandleUsuarios(nuevo);
        }
        // esto no funcionaría sin darle una vuelta: this.heapDeSaldos = new MaxHeap<Integer>(this.usuarios);
        this.heapDeSaldos = new MaxHeap<Usuario>(usuariosToHeapify); // si uso el tipo primitivo no anda :(
        this.listaDeBloques = new ListaEnlazada<Bloque>();
    }

// Agrega bloque - O(n_b * log P)
public void agregarBloque(Transaccion[] transacciones) {
    listaDeBloques.agregar(new Bloque(transacciones)); // O(n_b)
    for (Transaccion tx : transacciones) {
        Usuario vendedor = usuarios[tx.id_vendedor() - 1].usuarioApuntado;
        vendedor.saldo += tx.monto();
        heapDeSaldos.update(vendedor.heapIndex); // O(log P)
        if (tx.id_comprador() != 0) {
            Usuario comprador = usuarios[tx.id_comprador() - 1].usuarioApuntado;
            comprador.saldo -= tx.monto();
            heapDeSaldos.update(comprador.heapIndex); // O(log P)
        }
    }
}
    // Máxima transacción del último bloque - O(1)
    public Transaccion txMayorValorUltimoBloque(){
        return listaDeBloques.ultimo().obtenerMaximo();
    }

    // Transacciones del último bloque - O(n_b)
    public Transaccion[] txUltimoBloque() {
        return listaDeBloques.ultimo().getTransacciones();
    }

    // Usuario con mayor saldo - O(1)
    public int maximoTenedor() {
        return heapDeSaldos.raiz().id;
    }

    // Promedio del último bloque - O(1)
    public int montoMedioUltimoBloque() {
        Bloque ultimo = listaDeBloques.ultimo();
        int cantidad = ultimo.cantidadTransacciones();
        return cantidad == 0 ? 0 : ultimo.sumaMontos() / cantidad;
    }

    // Hackea transacción - O(log n_b + log P)
    public void hackearTx() {
        Bloque ultimo = listaDeBloques.ultimo();
        Transaccion tx = ultimo.hackearTx(); // O(log n_b)
        if (tx != null) {
            Usuario vendedor = usuarios[tx.id_vendedor() - 1].usuarioApuntado;
            vendedor.saldo -= tx.monto();
            heapDeSaldos.update(vendedor.heapIndex); // O(log P)
            if (tx.id_comprador() != 0) {
                Usuario comprador = usuarios[tx.id_comprador() - 1].usuarioApuntado;
                comprador.saldo += tx.monto();
                heapDeSaldos.update(comprador.heapIndex); // O(log P)
            }
        }
    }


    public static void main(String[] args){
        Berretacoin b = new Berretacoin(5);
        Transaccion tx1 = new Transaccion(1, 0, 2, 50);
        Transaccion tx2 = new Transaccion(2, 1, 2, 100);
        Transaccion tx3 = new Transaccion(3, 1, 2, 100);
        Transaccion tx4 = new Transaccion(4, 1, 2, 100);
        Transaccion tx5 = new Transaccion(5, 1, 2, 100);
        Transaccion tx6 = new Transaccion(6, 1, 2, 100);
        Transaccion[] txs = {tx1, tx2, tx3, tx4, tx5, tx6};
        b.agregarBloque(txs);
        System.out.println(b.montoMedioUltimoBloque());
    }
}
