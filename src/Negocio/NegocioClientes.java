package Negocio;

import AccesoDatos.AccesoDatosClientes;
import Entidades.Clientes;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author Alinson Lopez
 */
public class NegocioClientes {
    private boolean ejecucionCliente;
    private AccesoDatosClientes ClienteNegocio = new AccesoDatosClientes();
    private int IdCanal = 0;
    
    public boolean GuardarCliente(Clientes ClaseCliente) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCliente = ClienteNegocio.GuardarCliente(con, ClaseCliente);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCliente;
    } //fin de metodo GuardarBanco

    public boolean ModificarCliente(Clientes ClaseCliente) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCliente = ClienteNegocio.ModificarCliente(con, ClaseCliente);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCliente;
    } //fin de metodo ModificarBanco

    public boolean DesactivarCliente(Clientes ClaseCliente) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCliente = ClienteNegocio.DesactivarCliente(con, ClaseCliente);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCliente;
    }//fin de metodo DesactivarBanco

    public boolean BuscarCliente(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionCliente = ClienteNegocio.BuscarCliente(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionCliente;
    } //fin de metodo BuscarCliente   

    public void ListarCliente(JTable TablaCliente) {
        Connection con = Conexion.getConecction();
        ClienteNegocio.ListarClientes(con, TablaCliente);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarCliente

    public void FiltrarCliente(JTable TablaCliente, String buscar) {
        Connection con = Conexion.getConecction();
        System.out.println(buscar);
        ClienteNegocio.FiltrarCliente(con, TablaCliente, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }  //fin de metodo FiltrarCliente
    
    public void CargarCanalCliente(JComboBox ComboBoxRedSocialCliente) {
        Connection con = Conexion.getConecction();
        ClienteNegocio.CargarCanalCliente(con, ComboBoxRedSocialCliente);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo Cargar Canal en Cliente
    
    public int BuscarIdCanal(String canal) {
        Connection con = Conexion.getConecction();
        
        try {
          IdCanal = ClienteNegocio.BuscarIdCanal(con, canal);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return IdCanal;
    }

}
