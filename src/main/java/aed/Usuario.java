package aed;

public class Usuario implements Comparable<Usuario>{
    private int id;
    private long saldo;

   public Usuario(int id, long saldo){
       this.id = id;
       this.saldo = saldo;
   }

   public long getSaldo(){
         return this.saldo;
   }

   public int getId(){
         return this.id;
    }

   public void setSaldo(long nuevoSaldo){
         this.saldo = nuevoSaldo;
    }

    @Override
    public int compareTo(Usuario other) {
        if (this.saldo != other.saldo) {
            return Long.compare(this.saldo, other.saldo); // Mayor saldo primero
        }
        return Integer.compare(other.id, this.id); // Menor ID en empate
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
