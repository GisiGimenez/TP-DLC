
import Logica.DocumentoRan;
import Logica.Gestor;
import Logica.ListaDocumentos;
import Logica.Vocabulario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Main {

   private static Gestor gestor;
    public static void main(String[] args) {
        
//         String consulta;
//            consulta ="quiero dormir gise jajaja";
//            gestor = new Gestor();
//            gestor.rankearDocumentos(consulta);
//            for (int i = 0; i < ListaDocumentos.getInstance().getLista().size(); i++) {
//                System.out.print(ListaDocumentos.getInstance().getLista().get(i)); //etiqueta de link con la direccion del doc + atributo para descargar
//                
//            }
//            
//            System.out.println(ListaDocumentos.getInstance().getLista().toString());
    }
}
//        Vocabulario voc=new Vocabulario();
//        File f = new File(".\\gi.txt");
//        if (!f.getName().endsWith(".txt")) {
//            return;
//        }
//
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "8859_1"));
//            String linea = in.readLine();
//            do {
//                String a = "";
//                char[] lineaVec = linea.toCharArray();
//                for (int i = 0; i < lineaVec.length; i++) {
//                    if (lineaVec[i] != ' ') {
//                        if (Character.isAlphabetic(lineaVec[i])) {
//                            a += lineaVec[i];
//                        }
//                    } else {
//                        if (a != "") {
//                            voc.agregarPalabra(a.toLowerCase(), f.getName());
//                        }
//                        a = "";
//                    }
//                }
//                if (a != "") {
//                    voc.agregarPalabra(a.toLowerCase(), f.getName());
//                }
//                linea = in.readLine();
//            } while (linea != null);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        
//        System.out.println(voc);
//        

    
    
    

