
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

public class Gestor {

    private ArrayList<String> lista; //lista con palabras que voy a consultar
    private ArrayList<Termino> terminos;    //terminos que estan en el voc
    private Hashtable<String, Double> documentos;    //documentos que tomo para calcular peso
    private Vocabulario vocabulario;
    private ArrayList<String> ranking; //ranking de docs a mostrar
    private int N, R;

    public Gestor() {
        lista = new ArrayList<>();
        terminos = new ArrayList<>();
        documentos = new Hashtable<>();
        vocabulario = new Vocabulario();
    }

    public void listarPalabras(String consulta) {
        String palabra = "";
        char[] lineaVec = consulta.toCharArray();
        for (int i = 0; i < lineaVec.length; i++) {
            if (lineaVec[i] != ' ') {
                if (Character.isAlphabetic(lineaVec[i])) {
                    palabra += lineaVec[i];
                }
            } else {
                if (palabra != "") {
                    lista.add(palabra.toLowerCase());
                }
                palabra = "";
            }
        }
        if (palabra != "") {
            lista.add(palabra.toLowerCase());
        }
    }

    /**
     * Se comienza con el término de la consulta que tenga el mayor idf (o sea,
     * la lista de posteo más corta. En nuestro caso, comenzamos por el término
     * con menor nr) y traemos de su lista de posteo los R primeros documentos.
     * Si no se llega a reunir R documentos, se continúa con el segundo término
     * de mayor idf, y así sucesivamente.
     */
    public void analizarLista() {
        String palabra;
        double peso;
        Termino term = null;

        for (int i = 0; i < lista.size(); i++) {
            palabra = lista.get(i);
            if (vocabulario.buscar(palabra)) {
                term = vocabulario.getVocabulario().get(palabra.hashCode());
                terminos.add(term);
            }
        }
        Collections.sort(terminos, new Comparator() { //ordena la lista de menor a mayor segun n
            @Override
            public int compare(Object o1, Object o2) {
                Termino term1 = (Termino) o1;
                Termino term2 = (Termino) o2;
                return new Integer(term1.getN()).compareTo(new Integer(term2.getN()));
            }

        });
    }

    private double calcularPeso(Termino term, Documento doc) { //peso de una palabra en un documento
        int tf = doc.getTf();
        int n = term.getN();

        return tf * Math.log10(N / n);
    }

    public void buscarDocumentos() {
        ArrayList posteo;
        double peso;
        String nombre;
        for (Termino termino : terminos) {
            posteo = termino.getPosteo();
            for (int j = 0; j < posteo.size() || j == R; j++) { //corto cuando termino el posteo o cuando llego a R docs
                peso = calcularPeso(termino, (Documento) posteo.get(j));
                nombre = ((Documento) posteo.get(j)).getNombre();
                if (documentos.containsKey(nombre)) {
                    documentos.replace(nombre, documentos.get(nombre) + peso);//acumulo el peso si ese documento ya existe
                } else {
                    documentos.put(nombre, peso); //agrego el documento con el peso
                }
            }
        }
    }

    public void rankearDocumentos() {
      // ordenar documentos segun peso mayor a menor, ver si clase ranking o coleccion
    }

}
