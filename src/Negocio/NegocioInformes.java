/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosInformes;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Usuario
 */
public class NegocioInformes {
    private AccesoDatos.AccesoDatosInformes InformesNegocio = new AccesoDatos.AccesoDatosInformes();
  
    
    public void InformeVencimiento(JTable TablaVencimiento, String Fmin, String Fmax, boolean reqcategoria, String categoria) {
        Connection con = Conexion.getConecction();
        InformesNegocio.InformeVencimientos(con, TablaVencimiento, Fmin, Fmax, reqcategoria, categoria);
        try {
            con.close();
        } catch (Exception e) {
        }
    } 
    
    public void InformeVentaPack(JTable TablaVentaPack, String Fmin, String Fmax) {
        Connection con = Conexion.getConecction();
        InformesNegocio.InformeVentaPack(con, TablaVentaPack, Fmin, Fmax);
        try {
            con.close();
        } catch (Exception e) {
        }
    } 
    
    public void InformeComunas (JTable TablaComunas, String Fmin, String Fmax, String Comuna){
    Connection con = Conexion.getConecction();
        InformesNegocio.InformeComunas(con, TablaComunas, Fmin, Fmax, Comuna);
        try {
            con.close();
        } catch (Exception e) {
        }
    }
    
     public void InformesInventario (JTable TablaInventario, int stock, String Categoria){
    Connection con = Conexion.getConecction();
        InformesNegocio.InformesInventario(con, TablaInventario, stock, Categoria);
        try {
            con.close();
        } catch (Exception e) {
        }
    }
     
     public void InformeVentasCliente (JTable TablaVentaCliente, String Fmin, String Fmax, String Cliente)
     {
     Connection con = Conexion.getConecction();
        InformesNegocio.InformeVentasCliente(con, TablaVentaCliente, Fmin, Fmax, Cliente);
        try {
            con.close();
        } catch (Exception e) {
        }
     }
}
