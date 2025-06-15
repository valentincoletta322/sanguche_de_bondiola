package aed;

public class Berretacoin {
    // private Usuarios usuarios; // haría falta esa clase?

    private class HandleUsuarios {
        private Usuario usuarioApuntado;
        private int referencia;
        public HandleUsuarios(Usuario nuevoUsuario){
            this.usuarioApuntado = nuevoUsuario;
            this.referencia = nuevoUsuario.getId()-1;
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
        
        this.heapDeSaldos = new MaxHeap<Usuario>(usuariosToHeapify); // si uso el tipo primitivo no anda :(
        
        this.listaDeBloques = new ListaEnlazada<Bloque>();
    }

    public void agregarBloque(Transaccion[] transacciones){
        this.listaDeBloques.agregar(new Bloque(transacciones, 1)); // O(n)
        for (int i = 0; i < transacciones.length; i++){
            Transaccion actual = transacciones[i];
            actualizarSaldos(actual);
        }
    }

    public Transaccion txMayorValorUltimoBloque(){
        return listaDeBloques.ultimo().obtenerMaximo();
    }

    public Transaccion[] txUltimoBloque(){
        return listaDeBloques.ultimo().obtenerTransacciones();
    }

    public int maximoTenedor(){
        return this.heapDeSaldos.raiz().getId();
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
        Bloque ultimoBloque = this.listaDeBloques.ultimo();
        Transaccion maximaTransaccion = ultimoBloque.extraerMaximaTransaccion();

        // Me gustaria tener algo como actualizar saldos, pero esa recibe una transaccion
        // Entonces, o cambia actualizar saldos, o le hacemos una foo auxiliar, o queda aca y fue

        Usuario vendedorActual = usuarios[maximaTransaccion.id_vendedor()-1].usuarioApuntado;
        vendedorActual.setSaldo(vendedorActual.getSaldo() - maximaTransaccion.monto());
        heapDeSaldos.sift_down(usuarios[maximaTransaccion.id_vendedor()-1].referencia);
        if (maximaTransaccion.id_comprador() != 0) {
            Usuario compradorActual = usuarios[maximaTransaccion.id_comprador()-1].usuarioApuntado;
            compradorActual.setSaldo(compradorActual.getSaldo() + maximaTransaccion.monto());
            heapDeSaldos.sift_up(usuarios[maximaTransaccion.id_comprador()-1].referencia);
        }
    }

    public void actualizarSaldos(Transaccion transaccion){
        Usuario vendedorActual = usuarios[transaccion.id_vendedor()-1].usuarioApuntado;
        vendedorActual.setSaldo(vendedorActual.getSaldo() + transaccion.monto());
        
        heapDeSaldos.sift_up(usuarios[transaccion.id_vendedor()-1].referencia);
        if (transaccion.id_comprador() != 0){
            Usuario compradorActual = usuarios[transaccion.id_comprador()-1].usuarioApuntado;
            compradorActual.setSaldo(compradorActual.getSaldo() - transaccion.monto());
            heapDeSaldos.sift_down(usuarios[transaccion.id_comprador()-1].referencia);
        }

    }

    public static void main(String[] args){
        Berretacoin b = new Berretacoin(4);

        Transaccion[] transacciones3 = new Transaccion[] {
            new Transaccion(0, 0, 1, 1), // 1 -> $2, 2 -> $1
            new Transaccion(1, 1, 2, 2), // 2 -> $3
            new Transaccion(2, 2, 3, 3), // 3 -> $3
            new Transaccion(3, 3, 1, 2), // 1 -> $2, 3 -> $1
            new Transaccion(4, 1, 2, 1), // 1 -> $1, 2 -> $1, 3 -> $1
            new Transaccion(5, 2, 3, 1)  // 1 -> $1, 3 -> $2
        };


        b.agregarBloque(transacciones3);
        b.hackearTx();
        System.out.println(b.txUltimoBloque());
        b.hackearTx();
        System.out.println(b.txUltimoBloque());

    }
}
