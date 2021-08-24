/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosArticulos;
import Entidades.Articulos;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author usuario
 */
public class NegocioArticulo {
    private boolean ejecucionArticulo;
    private AccesoDatosArticulos ArticulosNegocio = new AccesoDatosArticulos();

    public boolean GuardarArticulo(Articulos ClaseArticulos) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionArticulo = ArticulosNegocio.GuardarArticulo(con, ClaseArticulos);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionArticulo;
    } //fin de metodo GuardarCategoria

    public boolean BuscarArticuloEstado(Articulos ClaseArticulo) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionArticulo = ArticulosNegocio.BuscarArticuloEstado(con, ClaseArticulo);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionArticulo;
    } //fin de metodo BuscarArticuloaEstado

    public boolean ModificarArticulo(Articulos ClaseArticulo) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionArticulo = ArticulosNegocio.ModificarArticulo(con, ClaseArticulo);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionArticulo;
    } //fin de metodo ModificarArticulo

    public boolean DesactivarArticulo(Articulos ClaseArticulo) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionArticulo = ArticulosNegocio.DesactivarArticulo(con, ClaseArticulo);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionArticulo;
    }//fin de metodo DesactivarArticulo

    public boolean BuscarArticulo(Articulos ClaseArticulo) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionArticulo = ArticulosNegocio.BuscarArticulo(con, ClaseArticulo);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionArticulo;
    } //fin de metodo BuscarArticulo   

    public void ListarArticulos(JTable TablaArticulos) {
        Connection con = Conexion.getConecction();
        ArticulosNegocio.ListarArticulos(con, TablaArticulos);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarArticulos
    
 
    public void FiltrarArticulos(JTable TablaArticulos, String buscar) {
        Connection con = Conexion.getConecction();
        ArticulosNegocio.FiltrarArticulos(con, TablaArticulos, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }  //fin de metodo FiltrarArticulos  
    
    public int CantidadArticulos() {
        Connection con = Conexion.getConecction();
        int cantidadRegistros = ArticulosNegocio.CantidadArticulos(con);
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
        return cantidadRegistros;
    }  //fin de metodo CantidadArticulo    
    
    public void ListarArticulosPack(JTable TablaPackSeleccionArticulos) {
        Connection con = Conexion.getConecction();
        ArticulosNegocio.ListarArticulosPack(con, TablaPackSeleccionArticulos);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarArticulosPack
    
    public void ListarArticulosdelPack(JTable TablaPackConArticulos, int idPack) {
        Connection con = Conexion.getConecction();
        ArticulosNegocio.ListarArticulosdelPack(con, TablaPackConArticulos, idPack);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarArticulosdelPack
}
