package Logica;

import java.util.ArrayList;

public class ListaDocumentos {

    static ArrayList<String> lista;

    public ListaDocumentos() {
        lista = new ArrayList<>();
    }

    public static ArrayList<String> getLista() {
        return lista;
    }

    public void agregarDocumento(String doc) {
        lista.add(doc);
    }

    public void clear() {
        lista.clear();
    }
}
