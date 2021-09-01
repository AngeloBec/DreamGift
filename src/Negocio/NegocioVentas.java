/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosVentas;
import Entidades.Venta;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author usuario
 */
public class NegocioVentas {

    private boolean ejecucionVenta;
    private AccesoDatosVentas VentaNegocio = new AccesoDatosVentas();

    public boolean GuardarVenta(Venta ClaseVenta) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = VentaNegocio.GuardarVenta(con, ClaseVenta);
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
    } //fin de metodo GuardarCategoria  

    public boolean ActualizarConfirmacionVenta(Venta ClaseVenta) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = VentaNegocio.ActualizarConfirmacionVenta(con, ClaseVenta);
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
    } //fin de metodo ActualizarConfirmacionVenta     

    public boolean ActualizarConfirmacionDespacho(Venta ClaseVenta) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionVenta = VentaNegocio.ActualizarConfirmacionDespacho(con, ClaseVenta);
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
    } //fin de metodo ActualizarConfirmacionDespacho   

    public int ObtenerNumeroPedido() {
        Connection con = Conexion.getConecction();
        int id = VentaNegocio.ObtenerNumeroPedido(con);
        try {
            con.close();
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return id;
    }  //fin de metodo CantidadPack

    public void ListarVentasPorConfirmar(JTable TablaConfirmacion) {
        Connection con = Conexion.getConecction();
        VentaNegocio.ListarVentasPorConfirmar(con, TablaConfirmacion);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarVentasPorConfirmar

    public void FiltrarVentaPedido(JTable TablaConfirmacion, String buscar) {
        Connection con = Conexion.getConecction();
        VentaNegocio.FiltrarVentaPedido(con, TablaConfirmacion, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }

    public void FiltrarVentaRut(JTable TablaConfirmacion, String buscar) {
        Connection con = Conexion.getConecction();
        VentaNegocio.FiltrarVentaRut(con, TablaConfirmacion, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }

    public void ListarDestinosDespachos(JTable TablaListadoDestino) {
        Connection con = Conexion.getConecction();
        VentaNegocio.ListarDestinosDespachos(con, TablaListadoDestino);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarDestinosDespachos

    public void FiltrarDestinosDespachosPorFecha(JTable TablaListadoDestino, String Fecha) {
        Connection con = Conexion.getConecction();
        VentaNegocio.FiltrarDestinosDespachosPorFecha(con, TablaListadoDestino, Fecha);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo FiltrarDestinosDespachosPorFecha

    public void ListarEstadosDespachos(JTable TablaListadoEstadosDespacho) {
        Connection con = Conexion.getConecction();
        VentaNegocio.ListarEstadosDespachos(con, TablaListadoEstadosDespacho);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarDestinosDespachos  
    
    public void FiltrarEstadosDespachosPorFecha(JTable TablaListadoEstadosDespacho, String Fecha) {
        Connection con = Conexion.getConecction();
        VentaNegocio.FiltrarEstadosDespachosPorFecha(con, TablaListadoEstadosDespacho, Fecha);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo FiltrarDestinosDespachosPorFecha    
    
}
