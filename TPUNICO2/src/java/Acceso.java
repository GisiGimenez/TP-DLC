
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author a5
 */
public class Acceso {

    private Connection con;
    public void conectar()
    {
        try
        {
            String controlador="org.sqlite.JDBC";
            String cadenaConexion="jdbc:sqlite:Vocabulario.sqlite";
            
            Class.forName(controlador);
            con=DriverManager.getConnection(cadenaConexion);
            System.out.println("Conexion realizada con exito");
        }catch(SQLException | ClassNotFoundException ex){}
    }
    
    public void cerrar()
    {
        try
        {
            con.close();
        }catch(SQLException ex){}
    }
    
    public void insertar() throws SQLException
    {
        String sql="INSERT INTO Palabras (palabra, frecuenciaMaxima, n) VALUES ('chau',3,1)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.close();
        cerrar();
        System.out.println("Inserto correctamente");
    }
    
//    public void insertar2() throws SQLException
//    {
//        String sql1="INSERT INTO Palabras (palabra, frecuenciaMaxima, n) VALUES ('chau', 5,2)";
//        PreparedStatement ps1 = con.prepareStatement(sql1);
//        ps1.close();
//        cerrar();
//        System.out.println("Inserto2 correctamente");
//    }
    
    
}

