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
        this.listaDeBloques = new ListaEnlazada<Bloque>();
        Usuario[] usuariosToHeapify = new Usuario[n_usuarios];
        for (int i = 1; i <= n_usuarios; i++){
            Usuario nuevo = new Usuario(i, 0);
            usuariosToHeapify[i-1] = nuevo;
            this.usuarios[i-1] = new HandleUsuarios(nuevo);
        }
        // esto no funcionaría sin darle una vuelta: this.heapDeSaldos = new MaxHeap<Integer>(this.usuarios);
        this.heapDeSaldos = new MaxHeap<Usuario>(usuariosToHeapify); // si uso el tipo primitivo no anda :(
    }

    public void agregarBloque(Transaccion[] transacciones){
        this.listaDeBloques.agregar(new Bloque(transacciones)); // O(n)
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
        throw new UnsupportedOperationException("Implementar!");
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public static void main(String[] args){
        Berretacoin b = new Berretacoin(10);
        Transaccion nueva = new Transaccion(1, 0, 1, 100);
        Transaccion otra = new Transaccion(2, 1, 3, 50);
        Transaccion otra2 = new Transaccion(3, 1, 3, 100000);
        Transaccion otra3 = new Transaccion(4, 1, 3, 2000);
        Transaccion otra4 = new Transaccion(5, 1, 3, 150);

        Transaccion[] ts = new Transaccion[]{nueva, otra, otra2, otra3, otra4};
        System.out.println(ts);
        b.agregarBloque(ts);
        b.txMayorValorUltimoBloque();
        System.out.println(b.txMayorValorUltimoBloque().monto());
    }
}
