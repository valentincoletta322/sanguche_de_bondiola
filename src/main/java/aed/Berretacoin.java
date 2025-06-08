package aed;

public class Berretacoin {

    // private Usuarios usuarios; // haría falta esa clase?
    private MaxHeap<Integer> heapDeSaldos;
    // alternativa posible:
    private Integer[] usuarios;

    public Berretacoin(int n_usuarios){
        this.usuarios = new Integer[n_usuarios];
        for (int i = 0; i < n_usuarios; i++){
            this.usuarios[i] = i+1; //ids positivos
        }
        // esto no funcionaría sin darle una vuelta: this.heapDeSaldos = new MaxHeap<Integer>(this.usuarios);
        this.heapDeSaldos = new MaxHeap<Integer>(this.usuarios); // si uso el tipo primitivo no anda :(
    }

    public void agregarBloque(Transaccion[] transacciones){
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion txMayorValorUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion[] txUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){
        return this.heapDeSaldos.raiz();
    }

    public int montoMedioUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }
}
