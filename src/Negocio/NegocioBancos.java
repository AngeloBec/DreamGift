package Negocio;

import AccesoDatos.AccesoDatosBancos;
import Entidades.Bancos;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author Alinson Lopez
 */
public class NegocioBancos {
    private boolean ejecucionBanco;
    private AccesoDatosBancos BancoNegocio = new AccesoDatosBancos();
    public boolean GuardarBanco(Bancos ClaseBanco) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionBanco = BancoNegocio.GuardarBanco(con, ClaseBanco);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionBanco;
    } //fin de metodo GuardarBanco

    public boolean BuscarBancoEstado(Bancos ClaseBanco) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionBanco = BancoNegocio.BuscarBancoEstado(con, ClaseBanco);
        } catch (Exception e) {
        } finally{
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }            
        }
        return ejecucionBanco;
    } //fin de metodo BuscarBancoEstado
    
    public boolean ModificarBanco(Bancos ClaseBanco) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionBanco = BancoNegocio.ModificarBanco(con, ClaseBanco);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionBanco;
    } //fin de metodo ModificarBanco

    public boolean DesactivarBanco(Bancos ClaseBanco) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionBanco = BancoNegocio.DesactivarBanco(con, ClaseBanco);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionBanco;
    }//fin de metodo DesactivarBanco

    public boolean BuscarBanco(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionBanco = BancoNegocio.BuscarBanco(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionBanco;
    } //fin de metodo BuscarBanco   

    public void ListarBanco(JTable TablaBanco) {
        Connection con = Conexion.getConecction();
        BancoNegocio.ListarBancos(con, TablaBanco);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarBanco

    public void FiltrarBanco(JTable TablaBanco, String buscar) {
        Connection con = Conexion.getConecction();
        System.out.println(buscar);
        BancoNegocio.FiltrarBanco(con, TablaBanco, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }  //fin de metodo FiltrarBanco  
}
