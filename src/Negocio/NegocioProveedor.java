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

    public void CargarProveedor(JComboBox ComboBoxProveedorArticulo) {
        Connection con = Conexion.getConecction();
        ProveedorNegocio.CargarProveedor(con, ComboBoxProveedorArticulo);
        try {
            con.close();
        } catch (Exception e) {
        }
    } //fin de metodo ListarCategoria

}
