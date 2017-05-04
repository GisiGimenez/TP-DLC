package Logica;


public class DocumentoRan implements Comparable {
    private double peso;
    private String nombre;

    public DocumentoRan() {
    }

    
    public DocumentoRan(double peso, String nombre) {
        this.peso = peso;
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    public void actualizarPeso(double peso){
        this.peso=peso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
     public int hashCode() {
        return Math.abs(nombre.toLowerCase().hashCode());
    }

    @Override
    public String toString() {
        return "DocumentoRan{" + "peso=" + peso + ", nombre=" + nombre + '}';
    }
    
    @Override
    public int compareTo(Object obj) { //lo usa contains
        DocumentoRan docComparo=(DocumentoRan) obj;
        if(nombre.compareToIgnoreCase(docComparo.getNombre())==0)
        {   
            return 0; // si son iguales devuelve 0
        
        }
        else
            return 1;  //si es mayor devuelve 1
        
    }

    
}
