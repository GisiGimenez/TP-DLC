
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author julieta
 */
public class GestorBD2 {
 
//    private static final int batch=5000;
    private Acceso acceso;
    private Connection con;
    private Vocabulario voc;
    private ArrayList<String> documentos;
    private ArrayList<String> lista2;
    
    public GestorBD2()
    {
        acceso=new Acceso();
        voc=new Vocabulario();
        documentos= new ArrayList <String>();
        lista2= new ArrayList <String>();
    }
     public Vocabulario getVoc() {
        return voc;
    }
    public void materializar() {

        String sql = "", sql2 = "";
        Termino termino;
        String palabra;
        int frecuenciaMaxima;
        
        try {

            con=acceso.conectar();
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            Statement stm2 = con.createStatement();
            ResultSet rs, rs2;
            sql = "SELECT palabra,frecuenciaMaxima,n FROM Palabras";

            rs = stm.executeQuery(sql);

            while (rs.next()) {
                ArrayList<Documento> documentos = new ArrayList<>();
                palabra = rs.getString(1);
                frecuenciaMaxima = Integer.parseInt(rs.getString(2));
                sql2 = "SELECT documento,frecuencia FROM PalabrasXDocumentos WHERE palabra= '" + palabra + "' ORDER BY frecuencia DESC";
                rs2 = stm2.executeQuery(sql2);
                
                while (rs2.next()) {
                    if(!documentos.contains((String)rs2.getString("documento"))){
                        Documento doc= new Documento(rs2.getString(1),Integer.parseInt(rs2.getString(2)));
                        documentos.add(doc);
                        lista2.add(rs2.getString(1));
                    }   
                }
                termino = new Termino(palabra, frecuenciaMaxima, documentos,documentos.size());
                System.out.println(termino.toString());

            }
            con.commit();
            rs.close();
            stm.close();
            acceso.cerrar(con);
        } catch (SQLException ex) {
            Logger.getLogger(Vocabulario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void leerDoc(String nombre) {
       
        File f = new File(nombre);
        if (!f.getName().endsWith(".txt")) {
            return;
        }
        documentos.add(nombre);
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
//                        System.out.println("Entra aqui");
                        if (a != "") {
//                            System.out.println("guarda");
//                            System.out.println(a.toLowerCase());
//                            System.out.println(f.getName());
                            voc.agregarPalabra(a.toLowerCase(),f.getName());
//                            System.out.println(voc);
                        }
                        a = "";
                    }
                }
                if (a != "") {
                    voc.agregarPalabra(a.toLowerCase(), f.getName());
                }
                linea = in.readLine();
            } while (linea != null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void cargarDocumentos() throws SQLException 
    {
        int batch=500;
        String sql= "INSERT INTO Documentos (documento) VALUES (?)";
        con= acceso.conectar();
        PreparedStatement ps=con.prepareStatement(sql);
        int cont=0;
        
        for (int i = 0; i < documentos.size(); i++) 
        {
            if (!lista2.contains(documentos.get(i))) {
                ps.setString(1, documentos.get(i).toString());
                ps.addBatch();
                cont++;
            }
            
            if(cont==batch)
            {
                ps.executeBatch();
                cont=0;
            }
        }
        
        if(cont>0)
        {
            ps.executeBatch();
        }
        ps.close();
        acceso.cerrar(con);
        
        System.out.println("Carga Exitosa");
    }
    
    public void cargarPalabras() throws SQLException
    {
        String sql= "INSERT INTO Palabras (palabra,frecuenciaMaxima,n) VALUES (?,?,?)";
        String sql2="UPDATE Palabras SET frecuenciaMaxima=?, n=? WHERE palabra=?";
        String sql3= "INSERT INTO PalabrasXDocumentos (palabra,documento,frecuencia) VALUES (?,?,?)";
        con= acceso.conectar();
        PreparedStatement ps=con.prepareStatement(sql);
        PreparedStatement ps2=con.prepareStatement(sql2);
        PreparedStatement ps3=con.prepareStatement(sql3);
        
        int batch=200000;
        int cont=0;
        
        Collection<Termino>palabraColeccion=voc.getVocabulario().values();
        ArrayList <Termino> palabra= new ArrayList <Termino>(palabraColeccion);
      
        for (int i = 0; i < palabra.size(); i++) 
        {
            if(!palabra.get(i).getInsertada())
            {
                ps.setString(1, palabra.get(i).getTermino());
                ps.setString(2, String.valueOf(palabra.get(i).getTf()));
                ps.setString(3,String.valueOf(palabra.get(i).getN()));
                ps.addBatch();
                
                for (int j = 0; j < palabra.get(i).getPosteo().size(); j++) 
                {
                    ps3.setString(1, palabra.get(i).getTermino());
                    ps3.setString(2, palabra.get(i).getPosteo().get(j).getNombre());
                    ps3.setString(3, String.valueOf(palabra.get(i).getPosteo().get(j).getTf()));
                    ps3.addBatch();
                }
                palabra.get(i).setInsertada(true);
                
            }
            else
            {
                ps2.setString(1,String.valueOf(palabra.get(i).getTf()) );
                ps2.setString(2,String.valueOf(palabra.get(i).getN()) );
                ps2.setString(3, palabra.get(i).getTermino());
                ps2.addBatch();
            }
            cont++;
            
            if(cont==batch)
            {
               ps.executeBatch();
               ps2.executeBatch();
               ps3.executeBatch();
               cont=0;
               System.out.println("1");
            }
        }
        if(cont>0)
        {
            ps.executeBatch();
            ps2.executeBatch();
            ps3.executeBatch();
        }
        ps.close();
        ps2.close();
        ps3.close();
        acceso.cerrar(con);
        System.out.println("Cargadas bien");
        }
    
//    public void cargarPalabrasXDocumento () throws SQLException
//    {
//        int batch=5000;
//        String sql= "INSERT INTO PalabrasXDocumentos (palabra,documento,frecuencia) VALUES (?,?,?)";
//        con= acceso.conectar();
//        PreparedStatement ps=con.prepareStatement(sql);
//        int cont=0;
//        
//        Collection<Termino>palabraColeccion=voc.getVocabulario().values();
//        ArrayList <Termino> palabra= new ArrayList <Termino>(palabraColeccion);
//      
//        for (int i = 0; i < palabra.size(); i++) 
//        {
//            if(!palabra.get(i).getInsertada())
//            {
//                ArrayList <Documento> documento= palabra.get(i).getPosteo();
//                for (int j = 0; j < documento.size(); j++) 
//                {
//                    ps.setString(1, palabra.get(i).getTermino());
//                    ps.setString(2, String.valueOf(palabra.get(i).getTf()));
//                    ps.setString(3, String.valueOf(palabra.get(i).getN()));
//                    ps.addBatch();
//                    cont++;
//                    palabra.get(i).setInsertada(true);
//                }
//            }
//            if(cont==batch)
//            {
//                ps.executeBatch();
//                cont=0;
//            }
//        }
//        if(cont>0)
//        {
//            ps.executeBatch();
//        }
//        ps.close();
//        acceso.cerrar(con);
//        
//        System.out.println("Cargadas bien");
//    }
}
