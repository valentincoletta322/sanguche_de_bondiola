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

    private MaxHeapActualizable<Usuario> heapDeSaldos;
    private ListaEnlazada<Bloque> listaDeBloques;
    
    private HandleUsuarios[] usuarios;


    
    /**
     * Constructor que inicializa el sistema con n_usuarios.
     * Complejidad: O(P), donde P es la cantidad de usuarios.
     *
     * @param n_usuarios Cantidad de usuarios en el sistema
     */
    public Berretacoin(int n_usuarios){
        this.usuarios = new HandleUsuarios[n_usuarios];
        Usuario[] usuariosToHeapify = new Usuario[n_usuarios];

        for (int i = 1; i <= n_usuarios; i++){
            Usuario nuevo = new Usuario(i, 0);
            usuariosToHeapify[i-1] = nuevo;
            this.usuarios[i-1] = new HandleUsuarios(nuevo);
        }
        
        this.heapDeSaldos = new MaxHeapActualizable<Usuario>(usuariosToHeapify); // O(P) por el algoritmo de heapify.
        
        this.listaDeBloques = new ListaEnlazada<Bloque>();
    }

    
    /**
     * Agrega un nuevo bloque de transacciones a la cadena de bloques.
     * Complejidad: O(n_b * log P), donde n_b es la cantidad de transacciones en el bloque.
     *
     * @param transacciones Arreglo de transacciones del nuevo bloque
     */
    public void agregarBloque(Transaccion[] transacciones){
        
        int idBloque = this.listaDeBloques.longitud(); // O(1)
        Bloque nuevBloque = new Bloque(transacciones, idBloque);
        this.listaDeBloques.agregar(nuevBloque); // Agregar el bloque es O(n) por crear el heap y el array.
        
        // Por cada transaccion (n_b) hacemos O(log P) (por ser un heap) para actualizar los saldos => O(n_b * log P)
        for (int i = 0; i < transacciones.length; i++){
            Transaccion actual = transacciones[i];
            actualizarSaldos(actual); // O(log P)
        }
    }

    /**
     * Devuelve la transacción de mayor valor del último bloque.
     * Complejidad: O(1)
     *
     * @return Transacción de mayor valor
     */
    public Transaccion txMayorValorUltimoBloque(){
        return listaDeBloques.ultimo().obtenerMaximo();
    }

    /**
     * Devuelve una copia de las transacciones del último bloque.
     * Complejidad: O(n_b), donde n_b es la cantidad de transacciones en el bloque.
     *
     * @return Arreglo de transacciones del último bloque
     */
    public Transaccion[] txUltimoBloque(){
        return listaDeBloques.ultimo().obtenerTransacciones();
    }

    /**
     * Devuelve el ID del usuario con el mayor saldo.
     * Complejidad: O(1)
     *
     * @return ID del usuario con mayor saldo
     */
    public int maximoTenedor(){
        return this.heapDeSaldos.raiz().getId();
    }

    /**
     * Devuelve el monto promedio de las transacciones no de creación del último bloque.
     * Complejidad: O(1)
     *
     * @return Monto promedio o 0 si no hay transacciones
     */
    public int montoMedioUltimoBloque(){
    
        int cantidad = listaDeBloques.ultimo().cantidadTransacciones();
        int suma = listaDeBloques.ultimo().sumaMontos();
        // Obtiene todo en O(1) y hace la operación.
        if (cantidad == 0){
            return 0;
        }
        return suma / cantidad;
    }

    /**
     * Hackea la transacción de mayor valor del último bloque, revirtiendo sus efectos.
     * Complejidad: O(log n_b + log P)
     */
    public void hackearTx(){
        Bloque ultimoBloque = this.listaDeBloques.ultimo();
        Transaccion maximaTransaccion = ultimoBloque.extraerMaximaTransaccion(); // O(log(n_b)), porque extraigo el max en O(1) y hago sift down O(log(n_b))
        this.actualizarUsuariosHackeados(maximaTransaccion); // O(log(P))
    }


    // Funcion auxiliar de complejidad O(log(P)) que actualiza los saldos de los usuarios involucrados en una transacción.
    public void actualizarSaldos(Transaccion transaccion){
        Usuario vendedorActual = usuarios[transaccion.id_vendedor()-1].usuarioApuntado;
        
        long calculoSaldoVendedor = vendedorActual.getSaldo() + transaccion.monto();
        vendedorActual.setSaldo(calculoSaldoVendedor);
        
        int referenciaVendedor = usuarios[transaccion.id_vendedor()-1].referencia;
        heapDeSaldos.sift_up(referenciaVendedor); // O(log(P)), sift up en el heap de saldos
        
        if (transaccion.id_comprador() != 0){
            Usuario compradorActual = usuarios[transaccion.id_comprador()-1].usuarioApuntado;
            
            long calculoSaldoComprador = compradorActual.getSaldo() - transaccion.monto();
            compradorActual.setSaldo(calculoSaldoComprador);
            
            int referenciaComprador = usuarios[transaccion.id_comprador()-1].referencia;
            heapDeSaldos.sift_down(referenciaComprador); // O(log(P)), sift down en el heap de saldos
        }

    }

    // Funcion auxiliar de complejidad O(log(P)), actualiza los saldos de los usuarios involucrados en una transacción hackeada.
    public void actualizarUsuariosHackeados(Transaccion transaccion){
        Usuario vendedorActual = usuarios[transaccion.id_vendedor()-1].usuarioApuntado;
        long calculoSaldoVendedor = vendedorActual.getSaldo() - transaccion.monto();
        vendedorActual.setSaldo(calculoSaldoVendedor);

        int referenciaVendedor = usuarios[transaccion.id_vendedor()-1].referencia;
        heapDeSaldos.sift_down(referenciaVendedor); // O(log(P)), sift down en el heap de saldos

        if (transaccion.id_comprador() != 0) {
            Usuario compradorActual = usuarios[transaccion.id_comprador()-1].usuarioApuntado;
            
            long calculoSaldoComprador = compradorActual.getSaldo() + transaccion.monto();
            compradorActual.setSaldo(calculoSaldoComprador);
            
            int referenciaComprador = usuarios[transaccion.id_comprador()-1].referencia;
            heapDeSaldos.sift_up(referenciaComprador); // O(log(P)), sift up en el heap de saldos
        }
    }

}
