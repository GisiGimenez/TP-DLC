package Logica;


import java.util.Hashtable;


public class Vocabulario {

    private Hashtable<Integer, Termino> vocabulario;

    public Vocabulario() {
        vocabulario = new Hashtable<>();
    }

    public Vocabulario(Termino term) {
        vocabulario = new Hashtable<>();
        vocabulario.put(term.hashCode(), term);
    }

    public Hashtable<Integer, Termino> getVocabulario() {
        return vocabulario;
    }
    public void agregarPalabra(String termino, String documento){
        if(vocabulario.containsKey(Math.abs(termino.toLowerCase().hashCode())))
                {
                     vocabulario.get(Math.abs(termino.toLowerCase().hashCode())).agregarDoc(documento);
                }
        else{  
            Termino term= new Termino(termino,documento);
            vocabulario.put(term.hashCode(), term);
        }
    }
    public boolean buscar(String termino){
       return vocabulario.containsKey(Math.abs(termino.toLowerCase().hashCode()));
    }
    @Override
    public String toString() {
        return vocabulario.toString();
    }

}
