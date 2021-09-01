/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosEstadoVenta;
import Entidades.EstadoVenta;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author usuario
 */
public class NegocioEstadoVenta {
    private boolean ejecucionVenta;
    private AccesoDatosEstadoVenta EstadoVentaNegocio = new AccesoDatosEstadoVenta();

    public boolean GuardarEstadoVenta(EstadoVenta ClaseVenta) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = EstadoVentaNegocio.GuardarEstadoVenta(con, ClaseVenta);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionVenta;
    } //fin de metodo GuardarEstadoVenta

    public boolean BuscarEstadoVentaEstado(EstadoVenta ClaseVenta) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = EstadoVentaNegocio.BuscarEstadoVentaEstado(con, ClaseVenta);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionVenta;
    } //fin de metodo BuscarEstadoVentaEstado

    public boolean ModificarEstadoVenta(EstadoVenta ClaseVenta) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = EstadoVentaNegocio.ModificarEstadoVenta(con, ClaseVenta);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionVenta;
    } //fin de metodo ModificarEstadoVenta

    public boolean DesactivarEstadoVenta(EstadoVenta ClaseVenta) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = EstadoVentaNegocio.DesactivarEstadoVenta(con, ClaseVenta);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionVenta;
    }//fin de metodo DesactivarEstadoVenta

    public boolean BuscarEstadoVenta(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = EstadoVentaNegocio.BuscarEstadoVenta(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionVenta;
    } //fin de metodo BuscarEstadoVenta   

    public void ListarEstadoVenta(JTable TablaEstadoVenta) {
        Connection con = Conexion.getConecction();
        EstadoVentaNegocio.ListarEstadoVenta(con, TablaEstadoVenta);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarEstadoVenta

    public void FiltrarEstadoVenta(JTable TablaEstadoVenta, String buscar) {
        Connection con = Conexion.getConecction();
        System.out.println(buscar);
        EstadoVentaNegocio.FiltrarEstadoVenta(con, TablaEstadoVenta, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }  //fin de metodo FiltrarEstadoVenta  

    public void CargarEstadoDespacho(JComboBox seleccion) {
        Connection con = Conexion.getConecction();
        EstadoVentaNegocio.CargarEstadoDespacho(con, seleccion);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo CargarEstadoDespacho

public int ObtenerIdEstadoVenta(String nombreEstadoVenta){
        Connection con = Conexion.getConecction();
        int id = EstadoVentaNegocio.ObtenerIdEstadoVenta(con, nombreEstadoVenta);
        try {
            con.close();
        } catch (Exception e) {
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return id;
    }  //fin de metodo ObtenerIdEstadoVenta     
    
}
