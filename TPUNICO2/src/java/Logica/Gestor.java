package Logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

public class Gestor {

    private ArrayList<String> lista; //lista con palabras que voy a consultar
    private ArrayList<Termino> terminos;    //terminos que estan en el voc
    private Hashtable<String, DocumentoRan> documentos;    //documentos que tomo para calcular peso 
    private Vocabulario vocabulario;
    private int N = 3, R;
    private Object[] v, temp; //v vector con los documentos ordenados por peso

    public Gestor() {
        lista = new ArrayList<>();
        terminos = new ArrayList<>();
        documentos = new Hashtable<>();
        vocabulario = new Vocabulario();
        leer("Libros/gi.txt");
        leer("Libros/lalala.txt");
        leer("Libros/Shaky.txt");
        leer("Libros/elefante.txt");
        leer("Libros/elefante y vaca.txt");
        leer("Libros/hola soy anto.txt");

    }
    
    
    public void leer(String leer) {
        File f = new File(leer);
        if (!f.getName().endsWith(".txt")) {
            return;
        }

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "8859_1"));
            String linea = in.readLine();
            do {
                String a = "";
                char[] lineaVec = linea.toCharArray();
                for (int i = 0; i < lineaVec.length; i++) {
                    if (lineaVec[i] != ' ') {
                        if (Character.isAlphabetic(lineaVec[i])) {
                            a += lineaVec[i];
                        }
                    } else {
                        if (a != "") {
                            vocabulario.agregarPalabra(a.toLowerCase(), f.getName());
                        }
                        a = "";
                    }
                }
                if (a != "") {
                    vocabulario.agregarPalabra(a.toLowerCase(), f.getName());
                }
                linea = in.readLine();
            } while (linea != null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Object[] getDocumentos() {
        Collection<DocumentoRan> values = documentos.values();
        v = values.toArray();
        this.ordenar();
        return v;
    }

    private void ordenar() {
        int n = v.length;
        temp = new DocumentoRan[n];
        sort(0, n - 1);
    }

    private void sort(int izq, int der) {
        if (izq < der) {
            int centro = (izq + der) / 2;
            sort(izq, centro);
            sort(centro + 1, der);
            merge(izq, centro, der);
        }
    }

    private void merge(int izq, int centro, int der) {
        for (int i = izq; i <= der; i++) {
            temp[i] = v[i];
        }
        int i = izq, j = centro + 1, k = izq;
        while (i <= centro && j <= der) {
            if (((DocumentoRan) temp[i]).getPeso() >= ((DocumentoRan) temp[j]).getPeso()) {
                v[k] = temp[i];
                i++;
            } else {
                v[k] = temp[j];
                j++;
            }
            k++;
        }
        while (i <= centro) {
            v[k] = temp[i];
            k++;
            i++;
        }
    }

    private void listarPalabras(String consulta) {
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

    private void analizarLista() {
        String palabra;
        Termino term = null;

        for (int i = 0; i < lista.size(); i++) {
            palabra = lista.get(i);
            if (vocabulario.buscar(palabra)) {
                term = vocabulario.getVocabulario().get(Math.abs(palabra.toLowerCase().hashCode()));
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
        double tf = doc.getTf();
        double n = term.getN();
        double res = (double) (tf * Math.log10(N / n));
        return res;
    }

    private void buscarDocumentos() {
        ArrayList posteo;
        double peso = 0.0;
        String nombre;
        for (Termino termino : terminos) {
            posteo = termino.getPosteo();
            for (int j = 0; j < posteo.size() || j == R; j++) { //corto cuando termino el posteo o cuando llego a R docs
                peso = calcularPeso(termino, (Documento) posteo.get(j));
                nombre = ((Documento) posteo.get(j)).getNombre();
                DocumentoRan nuevo = new DocumentoRan(peso, nombre);
                if (documentos.contains(nombre)) {
                    documentos.get(nombre).actualizarPeso(documentos.get(nombre).getPeso() + peso);

                } else {
                    documentos.put(nombre, nuevo); //agrego el documento con el peso
                }
            }
        }
    }

    public void rankearDocumentos(String consulta) {
        this.listarPalabras(consulta);
        this.analizarLista();
        this.buscarDocumentos();


    }

}
