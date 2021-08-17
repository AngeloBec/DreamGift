/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosCategoria;
import Entidades.Categoria;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author Wwilliam Ramos
 */
public class NegocioCategoria {

    private boolean ejecucionCategoria;
    private int IdCategoria = 0;
    private String CodigoCategoria = "";
    private AccesoDatosCategoria CategoriaNegocio = new AccesoDatosCategoria();

    public boolean GuardarCategoria(Categoria ClaseCategoria) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCategoria = CategoriaNegocio.GuardarCategoria(con, ClaseCategoria);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCategoria;
    } //fin de metodo GuardarCategoria

    public boolean BuscarCategoriaEstado(Categoria ClaseCategoria) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCategoria = CategoriaNegocio.BuscarCategoriaEstado(con, ClaseCategoria);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCategoria;
    } //fin de metodo BuscarCategoriaEstado

    public boolean ModificarCategoria(Categoria ClaseCategoria) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCategoria = CategoriaNegocio.ModificarCategoria(con, ClaseCategoria);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCategoria;
    } //fin de metodo ModificarCategoria

    public boolean DesactivarCategoria(Categoria ClaseCategoria) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCategoria = CategoriaNegocio.DesactivarCategoria(con, ClaseCategoria);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCategoria;
    }//fin de metodo DesactivarCategoria

    public boolean BuscarCategoria(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCategoria = CategoriaNegocio.BuscarCategoria(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCategoria;
    } //fin de metodo BuscarCategoria   

    public void ListarCategoria(JTable TablaCategoria) {
        Connection con = Conexion.getConecction();
        CategoriaNegocio.ListarCategoria(con, TablaCategoria);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarCategoria
    
    public void CargarCategoria(JComboBox ComboBoxCategoriaArticulo) {
        Connection con = Conexion.getConecction();
        CategoriaNegocio.CargarCategoria(con, ComboBoxCategoriaArticulo);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarCategoria
    
    public void FiltrarCategoria(JTable TablaCategoria, String buscar) {
        Connection con = Conexion.getConecction();
        CategoriaNegocio.FiltrarCategoria(con, TablaCategoria, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }  //fin de metodo FiltrarCategoria  
    
    public int BuscarIdCategoria(String categoria) {
        Connection con = Conexion.getConecction();
        try {
          IdCategoria = CategoriaNegocio.BuscarIdCategoria(con, categoria);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return IdCategoria;
    } //fin de metodo BuscarIdCategoria
    
    public String BuscarCodigoCategoria(int categoria) {
        Connection con = Conexion.getConecction();
        try {
          CodigoCategoria = CategoriaNegocio.BuscarCodigoCategoria(con, categoria);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return CodigoCategoria;
    } //fin de metodo BuscarCodigoCategoria   
}
