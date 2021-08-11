/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author William Ramos
 * Codigo para conectarnos a la BD dreamgifts
 * Importante saber que de acuerdo a lo leido ClassForName debe de llevar 
 * "com.mysql.cj.jdbc.Driver" porque de la otra forma esta descontinuado o 
 * no se usa
 * Este metodo toma la URL de nuestra BD y mediante las librerias 
 * import java.sql.Connection;
 * import java.sql.DriverManager;
 * Conecta hacia nuestra BD mediante codigo JAVA, Sino logra conectar manejamos
 * una excepción controlada y emitimos mensaje para alertar que la conexion
 * no fue exitosa
 */
public class Conexion 
{    
    private static Connection con = null;   
    public static Connection getConecction()
    {        
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dreamgifts","root","");
            /*if (con!=null) {
                  System.out.println("Conectado");  
                }    */                
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde"+e.getMessage());
            System.exit(0);
        }
        return con;
    }//fin metodo
    public void desconectar()
    {
        try {
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al desconectarse"+e.getMessage());
        }
    }//fin metodo
    public static void main(String[] args) {
        Conexion c = new Conexion();
        c.getConecction();
    }
}//fin de clase































/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;*/
/**
 *
 * @author Rafael
 */

    
        
  
    /*
    public static void main(String[] args){
        
        Conexion conectar = new Conexion();
        Connection con;
        DefaultTableModel modelo;
        
        List<Clientes> datos = new ArrayList<>();
        String sql="select * from `cliente`";
        
        try{
            con=conectar.ConexionBD();
            PreparedStatement pst = con.prepareStatement("select * from cliente");
            ResultSet rs = pst.executeQuery();
            
            
            while (rs.next()){
                Clientes cli = new Clientes();
                cli.setRut(rs.getInt(1));
                cli.setDv(rs.getString(2));
                cli.setNombre(rs.getString(3));
                cli.setCelular(rs.getInt(4));
                cli.setTelefono(rs.getInt(5));
                cli.setFecha_nac(rs.getString(6));
                cli.setEmail(rs.getString(7));
                cli.setRedsocial(rs.getString(8));
                datos.add(cli);
            }
        }catch(Exception e){
            
        }
        
        
           System.out.println("1.- "+datos);
       
        
    }
    
    
    
    
}*/
