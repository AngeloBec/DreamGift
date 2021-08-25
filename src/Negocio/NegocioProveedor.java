/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosProveedor;
import Entidades.Proveedor;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Angelo Becerra
 */
public class NegocioProveedor {
    private int IdProveedor=0;
    private boolean ejecucionProveedor;
    private AccesoDatosProveedor ProveedorNegocio = new AccesoDatosProveedor();

    public boolean GuardarProveedor(Proveedor proveedores) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionProveedor = ProveedorNegocio.GuardarProveedor(con, proveedores);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionProveedor;
    }

    public void CargarProveedor(JComboBox ComboBoxProveedorArticulo) {
        Connection con = Conexion.getConecction();
        ProveedorNegocio.CargarProveedor(con, ComboBoxProveedorArticulo);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo

    public void ListarProveedor(JTable TablaProveedor) {
        Connection con = Conexion.getConecction();
        ProveedorNegocio.ListarProveedor(con, TablaProveedor);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo 

    public boolean ModificarProveedor(Proveedor proveedores) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionProveedor = ProveedorNegocio.ModificarProveedor(con, proveedores);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
            
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                
            }
            return ejecucionProveedor;
        }

    }


public boolean DesactivarProveedor(Proveedor proveedores) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionProveedor = ProveedorNegocio.DesactivarProveedor(con, proveedores);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionProveedor;
    }//fin de metodo DesactivarUsuario
public boolean BuscarProveedor(String buscar) {
        Connection con = Conexion.getConecction();
        try {
            ejecucionProveedor = ProveedorNegocio.BuscarProveedor(con, buscar);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return ejecucionProveedor;
    } //fin de metodo BuscarUsuario   

public void FiltrarProveedor(JTable TablaProveedor, String buscar) {
        Connection con = Conexion.getConecction();
      ProveedorNegocio.FiltrarProveedor(con, TablaProveedor, buscar);
        try {
            con.close();
        } catch (Exception e) {
        }
    }

    public int BuscarIdProveedor(String proveedor) {
        Connection con = Conexion.getConecction();
        try {
          IdProveedor = ProveedorNegocio.BuscarIdProveedor(con, proveedor);
        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return IdProveedor;
    } //fin de BuscarIdProveedor   





}















