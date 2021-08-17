/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Entidades.Usuario;
import AccesoDatos.AccesoDatosUsuarios;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author William Ramos
 */
public class NegocioUsuario {

    private boolean ejecucion;
    private AccesoDatosUsuarios UsuarioNegocio = new AccesoDatosUsuarios();

    public boolean GuardarUsuario(Usuario users) {
        Connection con = Conexion.getConecction();
        try {
            ejecucion = UsuarioNegocio.GuardarUsuario(con, users);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucion;
    } //fin de metodo GuardarUsuario

    public boolean ModificarUsuario(Usuario users) {
        Connection con = Conexion.getConecction();
        try {
            ejecucion = UsuarioNegocio.ModificarUsuario(con, users);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucion;
    } //fin de metodo ModificarUsuario

    public boolean DesactivarUsuario(Usuario users) {
        Connection con = Conexion.getConecction();
        try {
            ejecucion = UsuarioNegocio.DesactivarUsuario(con, users);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucion;
    }//fin de metodo DesactivarUsuario

    public boolean BuscarUsuario(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucion = UsuarioNegocio.BuscarUsuario(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucion;
    } //fin de metodo BuscarUsuario    

    public void ListarUsuario(JTable TablaUsuario) {
        Connection con = Conexion.getConecction();
        UsuarioNegocio.ListarUsuario(con, TablaUsuario);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarUsuario

    public void FiltrarUsuario(JTable TablaUsuario, String buscar) {
        Connection con = Conexion.getConecction();
        UsuarioNegocio.FiltrarUsuario(con, TablaUsuario, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }
}
