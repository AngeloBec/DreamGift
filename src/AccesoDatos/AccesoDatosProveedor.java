/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Angelo Becerra
 */
public class AccesoDatosProveedor {

    public void CargarProveedor(Connection con, JComboBox ComboBoxProveedorArticulo) {
        String sql = "select PRO_RAZONSOCIAL from proveedor order by PRO_RAZONSOCIAL";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                ComboBoxProveedorArticulo.addItem(resultado.getString("PRO_RAZONSOCIAL"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo CargarCategorias  

    public int BuscarIdProveedor(Connection con, String proveedor) {
        String sql = "SELECT PRO_ID_PROVEEDOR FROM proveedor WHERE PRO_RAZONSOCIAL = '" + proveedor + "'";
        int idProveedor = 0;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                idProveedor = resultado.getInt("PRO_ID_PROVEEDOR");
            }
        } catch (Exception e) {

        }
        return idProveedor;
    } //fin de metodo BuscarIdProveedor       
    
}
