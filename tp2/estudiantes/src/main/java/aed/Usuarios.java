package aed;

public class Usuarios {
    private int[] usuarios;
    
    public Usuarios(int cantidadUsuarios){
        this.usuarios = new int[cantidadUsuarios];
        for (int i = 1; i<=cantidadUsuarios; i++){
            this.usuarios[i] = i; // cambiar por handle
        }
    }

}
