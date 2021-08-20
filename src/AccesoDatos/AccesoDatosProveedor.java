/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Angelo Becerra
 */
public class AccesoDatosProveedor {
    public boolean GuardarProveedor(Connection con, Proveedor proveedores  ) {
        String sql = "Insert into proveedor (PRO_NOMBRE_CONT, PRO_NOMBRE_CONT,PRO_RUT,PRO_TELEFONO,PRO_CORREO,PRO_DIRECCION,PRO_ESTADO)values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, proveedores.getNombreProveedor());
            query.setString(2, proveedores.getRazonSocialProveedor());
            query.setString(3, proveedores.getDireccionProveedor());
            query.setString(4, proveedores.getRut());
            query.setString(5, proveedores.getDv());
            query.setInt(6, proveedores.getTelefonoProveedor());
            query.setString(7, proveedores.getCorreo());
            query.setInt(8, proveedores.getEstado());
            
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    }
    
    public boolean ModificarProveedor(Connection con, Proveedor proveedores  ) {
        String sql = "UPDATE proveedor SET PRO_NOMBRE_CONT=?, PRO_NOMBRE_CONT=?,PRO_RUT=?,PRO_TELEFONO=?,PRO_CORREO=?,PRO_DIRECCION=?,PRO_ESTADO=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, proveedores.getNombreProveedor());
            query.setString(2, proveedores.getRazonSocialProveedor());
            query.setString(3, proveedores.getDireccionProveedor());
            query.setString(4, proveedores.getRut());
            query.setString(5, proveedores.getDv());
            query.setInt(6, proveedores.getTelefonoProveedor());
            query.setString(7, proveedores.getCorreo());
            query.setInt(8, proveedores.getEstado());
            
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    }

    public void CargarProveedor(Connection con, JComboBox ComboBoxProveedorArticulo) {
        int estado = 1;
        String sql = "select PRO_RAZONSOCIAL from proveedor where PRO_ESTADO ="+ estado +" order by PRO_RAZONSOCIAL";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            ComboBoxProveedorArticulo.addItem("Seleccione Proveedor");
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
