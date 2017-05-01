
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


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

    public Connection conectar()
    {
        Connection con = null;
        try
        {
            String controlador="org.sqlite.JDBC";
            String cadenaConexion="jdbc:sqlite:Vocabulario.sqlite";
            
            Class.forName(controlador);
            con=DriverManager.getConnection(cadenaConexion);
            System.out.println("Conexion realizada con exito");
        }catch(SQLException | ClassNotFoundException ex){}
        
        return con;
    }
    
    public void cerrar(Connection con)
    {
        try
        {
            con.close();
        }catch(SQLException ex){}
    }   
 }

    
    


