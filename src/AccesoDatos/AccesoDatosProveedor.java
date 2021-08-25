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
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Angelo Becerra
 */
public class AccesoDatosProveedor {

    public boolean GuardarProveedor(Connection con, Proveedor proveedores) {
        String sql = "Insert into proveedor(PRO_RAZONSOCIAL,PRO_NOMBRE_CONT,PRO_RUT,PRO_DIRECCION,PRO_TELEFONO,PRO_CORREO,PRO_ESTADO)values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, proveedores.getRazonSocialProveedor());
            query.setString(2, proveedores.getNombreProveedor());
            query.setString(3, proveedores.getRut());
            query.setString(4, proveedores.getDireccionProveedor());
            query.setString(5, proveedores.getTelefonoProveedor());
            query.setString(6, proveedores.getCorreo());
            query.setInt(7, proveedores.getEstado());

            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    }

    public boolean ModificarProveedor(Connection con, Proveedor proveedores) {
        String sql = "UPDATE proveedor SET PRO_RAZONSOCIAL=?,PRO_NOMBRE_CONT=?,PRO_RUT=?,PRO_DIRECCION=?,PRO_TELEFONO=?,PRO_CORREO=?,PRO_ESTADO=? WHERE PRO_ID_PROVEEDOR=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, proveedores.getRazonSocialProveedor());
            query.setString(2, proveedores.getNombreProveedor());
            query.setString(3, proveedores.getRut());
            query.setString(4, proveedores.getDireccionProveedor());
            query.setString(5, proveedores.getTelefonoProveedor());
            query.setString(6, proveedores.getCorreo());
            query.setInt(7, proveedores.getEstado());
            query.setInt(8, proveedores.getIdentificadorProveedor());

            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
        return modificar;
    }

    public void CargarProveedor(Connection con, JComboBox ComboBoxProveedorArticulo) {
        int estado = 1;
        String sql = "select PRO_RAZONSOCIAL from proveedor where PRO_ESTADO="+ estado +" order by PRO_RAZONSOCIAL";
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

    public void ListarProveedor(Connection con, JTable TablaProveedor) {
        String sql = "select * from proveedor order by PRO_ID_PROVEEDOR";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaProveedor.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] proveedor = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    proveedor[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("PRO_ESTADO") == 1) {
                        proveedor[7] = "Activo";
                    } else {
                        proveedor[7] = "Inactivo";
                    }
                }
                modelo.addRow(proveedor);
            }
            TablaProveedor.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarUsuario

    public boolean DesactivarProveedor(Connection con, Proveedor proveedores) {
        String sql = "UPDATE proveedor SET PRO_ESTADO =? WHERE PRO_ID_PROVEEDOR=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, proveedores.getEstado());
            query.setInt(2, proveedores.getIdentificadorProveedor());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    }

    public boolean BuscarProveedor(Connection con, String buscar) {
        String sql = "Select * from proveedor where PRO_NOMBRE_CONT='" + buscar + "'";
        boolean encontrado = false;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                encontrado = true;
            }
        } catch (Exception e) {

        }
        return encontrado;
    } //fin de metodoo

    public void FiltrarProveedor(Connection con, JTable TablaProveedor, String buscar) {
        String sql = "select * from proveedor where PRO_ID_PROVEEDOR like'%" + buscar + "%' "
                + "   or PRO_RAZONSOCIAL like'%" + buscar + "%' or PRO_NOMBRE_CONT like'%" + buscar + "%'"
                + "   or PRO_RUT like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaProveedor.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] Proveedor = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    Proveedor[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("PRO_ESTADO") == 1) {
                        Proveedor[7] = "Activo";
                    } else {
                        Proveedor[7] = "Inactivo";
                    }
                }
                modelo.addRow(Proveedor);
            }
            TablaProveedor.setModel(modelo);
        } catch (Exception e) {

        }
    } // Cierre Filtrar usuario

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
