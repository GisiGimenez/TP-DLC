
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Termino implements Comparable<Termino> {

    private String termino;
    private int tfMax; //frecuencia max en UN doc
    private ArrayList<Documento> posteo; //falta ordenar por tf
    private int n;
    private boolean insertada;

    public Termino(String p) {
         termino=p;   
    }

    public Termino(String p, String doc) {
        termino = p;
        posteo = new ArrayList<Documento>();
        posteo.add(new Documento(doc, 1));
        tfMax = posteo.get(0).getTf();
        n=posteo.size();
    }

    public Termino(String p, int f, ArrayList<Documento> lista, int n) //uso para materializar
    {
        termino = p;
        tfMax = f;
        posteo = lista;
        this.n=n;
        insertada = true;
    }

    public Termino(String p, Documento doc) {
        termino = p;
        tfMax = 1;
        posteo = new ArrayList<>();
        posteo.add(doc);
        insertada = false;

    }

    public boolean equals(Termino termino) {
        if (termino.getTermino().toLowerCase() == this.getTermino().toLowerCase()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Math.abs(termino.toLowerCase().hashCode());
    }

    public int getN() {
        return posteo.size();

    }

    public String getTermino() {
        return termino;
    }

    public int getTf() {
        return tfMax;
    }

    public void agregarDoc(String doc) {
        boolean ban = false;
        for (int i = 0; i < posteo.size(); i++) {
            if (posteo.get(i).getNombre() == doc) {

                ban = true;
                posteo.get(i).sumarUno();
                break;

            }
        }
        if (ban == false) {
            Documento docu = new Documento(doc, 1);
            posteo.add(docu);
        }
        Collections.sort(posteo, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Documento doc1 = (Documento) o1;
                Documento doc2 = (Documento) o2;
                return new Integer(doc2.getTf()).compareTo(new Integer(doc1.getTf()));
            }

        });
        tfMax = posteo.get(0).getTf();
        n = posteo.size();
    }

    public ArrayList<Documento> getPosteo() {
        return posteo;
    }

    public void setInsertada (boolean i)
    {
        this.insertada=i;
    }
    public void setTermino(String palabra) {
        this.termino = palabra;
    }

    public void setTf(int frecuencia) {
        this.tfMax = frecuencia;
    }

    public void setN()
    {
        n=posteo.size();
    }
    public void setPosteo(ArrayList<Documento> documentos) {
        this.posteo = documentos;
    }

    public boolean getInsertada()
    {
        return insertada;
    }
    @Override
    public String toString() {
        return "Palabra: " + termino + " - N: "+n+" - Frecuencia: " + this.getTf() + " - Documentos: " + posteo.toString() + "\n";
    }

    @Override
    public int compareTo(Termino o) {
        return termino.compareToIgnoreCase(o.termino);
    }

}
