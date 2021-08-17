/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.AccesoDatosProveedor;
import conexion.Conexion;
import java.sql.Connection;
import javax.swing.JComboBox;

/**
 *
 * @author Angelo Becerra
 */
public class NegocioProveedor {
    
    private AccesoDatosProveedor ProveedorNegocio = new AccesoDatosProveedor();
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
    } //fin de metodo GuardarComuna    
    
}
