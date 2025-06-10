package aed;

public class Usuario implements Comparable<Usuario>{
    public int id;
    public long saldo;
    public int heapIndex; // índice en el heap

   public Usuario(int id, long saldo){
       this.id = id;
       this.saldo = saldo;
       this.heapIndex = -1; // inicialmente el usuario no está en el heap
   }

    @Override
    public int compareTo(Usuario other) {
        if (this.saldo != other.saldo) {
            return Long.compare(other.saldo, this.saldo); // Mayor saldo primero
        }
        return Integer.compare(this.id, other.id); // Menor ID en empate
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro)
            return true;
        if (!(otro instanceof Usuario))
            return false;
        Usuario other = (Usuario) otro;
        return this.id == other.id && this.saldo == other.saldo;
    }
}
