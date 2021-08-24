/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosPackArticulo;
import Entidades.PackArticulo;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author usuario
 */
public class NegocioPackArticulo {
    
    private boolean ejecucionPackArticulo;
    private AccesoDatosPackArticulo PackArticuloNegocio = new AccesoDatosPackArticulo();
    public boolean GuardarPackArticulo(JTable TablaPackConArticulos, int identificadorPack){
        Connection con = Conexion.getConecction();
        try {
            ejecucionPackArticulo = PackArticuloNegocio.GuardarPackArticulo(con, TablaPackConArticulos, identificadorPack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPackArticulo;
    } //fin de metodo GuardarPackArticulo
            
    public boolean ModificarPackArticulo(JTable TablaPackConArticulos, int identificadorPack){
        Connection con = Conexion.getConecction();
        try {
            ejecucionPackArticulo = PackArticuloNegocio.ModificarPackArticulo(con, TablaPackConArticulos, identificadorPack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPackArticulo;
    } //fin de metodo ModificarPackArticulo
   
    public boolean EliminarPackArticulo(PackArticulo ClasePackArticulo){
                Connection con = Conexion.getConecction();
        try {
            ejecucionPackArticulo = PackArticuloNegocio.EliminarPackArticulo(con, ClasePackArticulo);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPackArticulo;
    } //fin de metodo ModificarPackArticulo
}
