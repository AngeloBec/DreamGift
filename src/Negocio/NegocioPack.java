/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosPack;
import Entidades.Pack;
import conexion.Conexion;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author usuario
 */
public class NegocioPack {
    private boolean ejecucionPack;
    ArrayList ejecucionPackLista = new ArrayList();
    private AccesoDatosPack PackNegocio = new AccesoDatosPack();

    public boolean GuardarPack(Pack ClasePack) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionPack = PackNegocio.GuardarPack(con, ClasePack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPack;
    } //fin de metodo GuardarPack

    public boolean ModificarPack(Pack ClasePack) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionPack= PackNegocio.ModificarPack(con, ClasePack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPack;
    } //fin de metodo ModificarPack

    public boolean DesactivarPack(Pack ClasePack) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionPack = PackNegocio.DesactivarPack(con, ClasePack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPack;
    }//fin de metodo DesactivarPack

    public boolean BuscarPackEstado(Pack ClasePack) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionPack = PackNegocio.BuscarPackEstado(con, ClasePack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPack;
    } //fin de metodo BuscarPackEstado    

    public boolean BuscarPack(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionPack = PackNegocio.BuscarPack(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPack;
    } //fin de metodo BuscarPack   

    public boolean BuscarPackEspecifico(Pack ClasePack) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionPack = PackNegocio.BuscarPackEspecifico(con, ClasePack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPack;
    } //fin de metodo BuscarPackRspecifico       
    
    public void ListarPack(JTable TablaPack) {
        Connection con = Conexion.getConecction();
        PackNegocio.ListarPack(con, TablaPack);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarPack

    public void FiltrarPack(JTable TablaPack, String buscar) {
        Connection con = Conexion.getConecction();
        PackNegocio.FiltrarPack(con, TablaPack, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }//fin de metodo FiltrarPack
    
    
    public int CantidadPack() {
        Connection con = Conexion.getConecction();
        int cantidadRegistros = PackNegocio.CantidadPack(con);
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
    }  //fin de metodo CantidadPack  

    public void CargarPack(JComboBox combobxPackDestinatario) {
        Connection con = Conexion.getConecction();
        PackNegocio.CargarPack(con, combobxPackDestinatario);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo CargarPack     

    public ArrayList ObtenerInformacionPack(String nombrePack) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionPackLista = PackNegocio.ObtenerInformacionPack(con, nombrePack);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionPackLista;
    } //fin de metodo BuscarClienteRut     
    
}
