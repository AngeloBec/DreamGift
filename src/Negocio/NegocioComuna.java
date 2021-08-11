/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosComunas;
import Entidades.Comuna;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author Angelo Becerra
 */
public class NegocioComuna {

    private boolean ejecucionComuna;
    private AccesoDatosComunas ComunaNegocio = new AccesoDatosComunas();

    public boolean GuardarComuna(Comuna comunasClase) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionComuna = ComunaNegocio.GuardarComuna(con, comunasClase);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionComuna;
    } //fin de metodo GuardarComuna

    public boolean ModificarComuna(Comuna comunasClase) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionComuna = ComunaNegocio.ModificarComuna(con, comunasClase);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionComuna;
    } //fin de metodo ModificarComuna

    public boolean DesactivarComuna(Comuna comunasClase) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionComuna = ComunaNegocio.DesactivarComuna(con, comunasClase);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionComuna;
    }//fin de metodo DesactivarCanal

    public boolean BuscarComuna(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionComuna = ComunaNegocio.BuscarComuna(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionComuna;
    } //fin de metodo BuscarCanal    

    public boolean BuscarComunaEstado(Comuna comunaClase) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionComuna = ComunaNegocio.BuscarComunaEstado(con, comunaClase);
        } catch (Exception e) {
        } finally{
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }            
        }
        return ejecucionComuna;
    } //fin de metodo BuscarComunaEstado    
    public void ListarComuna(JTable TablaComuna) {
        Connection con = Conexion.getConecction();
        ComunaNegocio.ListarComunas(con, TablaComuna);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarCanal

    public void FiltrarComuna(JTable TablaComuna, String buscar) {
        Connection con = Conexion.getConecction();
        System.out.println(buscar);
        ComunaNegocio.FiltrarComuna(con, TablaComuna, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }
}
