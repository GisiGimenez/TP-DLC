

public class Documento {
    private String nombre;
    private int tf;

    public Documento(String nombre, int tf) {
        this.nombre = nombre.toLowerCase();
        this.tf = tf;
    }
    public void sumarUno()
    {
        tf++;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    @Override
    public String toString() {
        return  "Documento: "+nombre+ "-tf: "+tf ;
    }
    
    
    
}
