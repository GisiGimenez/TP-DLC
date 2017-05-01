
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author julieta
 */
public class Main {
    
    public static void main (String []args) throws SQLException
    {
        GestorBD2 gestor= new GestorBD2();
        
        String files;
        String path=".";
        
        File folder= new File(path);
        File[] listaLibros = folder.listFiles();
        
        for (int i = 0; i < listaLibros.length; i++) 
        {
            if(listaLibros[i].isFile())
            {
                files= listaLibros[i].getName();
                if(files.endsWith(".txt") || files.endsWith(".TXT"))
                {
                    gestor.leerDoc(files);
                }
            }            
        }
        gestor.cargarDocumentos();
        gestor.cargarPalabras();
    }
}
