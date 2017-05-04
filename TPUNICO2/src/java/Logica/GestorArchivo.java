/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica;

import java.io.File;

/**
 *
 * @author a5
 */
public class GestorArchivo {
    
    public GestorArchivo() {
    }


    public void guardar(String ruta){
        System.out.println(ruta);
         File fUsuario = new File(ruta); //es del usuario
         String nombre=fUsuario.getName();
         File fGuardado= new File("D:\\gi\\Gise\\TPUNICO2\\src\\java\\Libros\\"+nombre);
         fUsuario.renameTo(fGuardado);
       
    }
}
