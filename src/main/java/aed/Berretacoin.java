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

    public void agregarBloque(Transaccion[] transacciones){
        this.listaDeBloques.agregar(new Bloque(transacciones, 1)); // O(n)
        for (int i = 0; i < transacciones.length; i++){
            Transaccion actual = transacciones[i];
            usuarios[actual.id_vendedor()-1].usuarioApuntado.saldo += actual.monto();
            if (actual.id_comprador() != 0){
                usuarios[actual.id_comprador()-1].usuarioApuntado.saldo -= actual.monto();
            }
            // no anda el sift downnnn1k2j3lkj
            // le paso un indice, debería poder pasarle la referencia, u obtener el indice de alguna manera?
        }
    }

    public Transaccion txMayorValorUltimoBloque(){
        return listaDeBloques.ultimo().obtenerMaximo();
    }

    public Transaccion[] txUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){
        return this.heapDeSaldos.raiz().id;
    }

    public int montoMedioUltimoBloque(){
        // return listaDeBloques.ultimo().montoPromedio();
        int cantidad = listaDeBloques.ultimo().cantidadTransacciones();
        int suma = listaDeBloques.ultimo().sumaMontos();
        if (cantidad == 0){
            return 0;
        }
        return suma / cantidad;
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
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
