package Negocio;

import AccesoDatos.AccesoDatosCanal;
import Entidades.Canal;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author Angelica Valdivia
 */
public class NegocioCanales {

    private boolean ejecucionCanal;
    private AccesoDatosCanal CanalNegocio = new AccesoDatosCanal();

    public boolean GuardarCanal(Canal medios) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCanal = CanalNegocio.GuardarCanal(con, medios);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCanal;
    } //fin de metodo GuardarCanal

    public boolean ModificarCanal(Canal medios) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCanal = CanalNegocio.ModificarCanal(con, medios);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCanal;
    } //fin de metodo ModificarCanal

    public boolean DesactivarCanal(Canal medios) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCanal = CanalNegocio.DesactivarCanal(con, medios);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCanal;
    }//fin de metodo DesactivarCanal

    public boolean BuscarCanalEstado(Canal medios) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCanal = CanalNegocio.BuscarCanalEstado(con, medios);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCanal;
    } //fin de metodo BuscarBancoEstado    

    public boolean BuscarCanal(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCanal = CanalNegocio.BuscarCanal(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCanal;
    } //fin de metodo BuscarCanal    

    public void ListarCanal(JTable TablaCanal) {
        Connection con = Conexion.getConecction();
        CanalNegocio.ListarCanal(con, TablaCanal);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarCanal

    public void FiltrarCanal(JTable TablaCanal, String buscar) {
        Connection con = Conexion.getConecction();
        CanalNegocio.FiltrarCanal(con, TablaCanal, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }
}
