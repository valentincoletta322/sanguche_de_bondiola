package aed;

public class Bloque {
    private Transaccion[] transOrderedById;
    private int montoTotal;
    private int cardinal;
    private MaxHeap<Handle> transaccionesHeap;

    private class Handle implements Comparable<Handle> {
        private int referencia;
        private int monto;
        public Handle(int nuevaReferencia, int nuevoMonto){
            this.referencia = nuevaReferencia;
            this.monto = nuevoMonto;
        }

        @Override
        public int compareTo(Handle otro){
            if (this.monto > otro.monto){
                return 1;
            }
            else if (this.monto < otro.monto){
                return -1;
            }
            return 0;
        }


    }

    public Bloque(Transaccion[] transacciones){
        montoTotal = 0;
        this.transOrderedById = transacciones; // aliassing
        // this.transaccionesHeap = new MaxHeap<>(transacciones);

        cardinal = this.transOrderedById.length;
        Handle[] handles = new Handle[transacciones.length];

        if (this.transOrderedById.length > 0){
            Transaccion primera = this.transOrderedById[0];
            if (!primera.esCreacion()){
                montoTotal+= primera.monto();
            } else {
                this.cardinal -= 1;
            }
            handles[0] = new Handle(0, primera.monto());
        }
        
        for (int i = 1; i < this.transOrderedById.length; i++){
            montoTotal += this.transOrderedById[i].monto();
            handles[i] = new Handle(i, this.transOrderedById[i].monto());
        }

        this.transaccionesHeap = new MaxHeap<Handle>(handles); // En teoria esto es O(n)?

    }

    public Transaccion obtenerMaximo(){
        return this.transOrderedById[transaccionesHeap.raiz().referencia];
    }

    public float montoPromedio(){
        return this.montoTotal/this.cardinal; // O(1) polémico para que ande con hackearTx
    }

}
