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

/**
 *
 * @author Angelo Becerra
 */
public class NegocioProveedor {

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
    private int IdProveedor = 0;

    public void CargarProveedor(JComboBox ComboBoxProveedorArticulo) {
        Connection con = Conexion.getConecction();
        ProveedorNegocio.CargarProveedor(con, ComboBoxProveedorArticulo);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarCategoria

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
