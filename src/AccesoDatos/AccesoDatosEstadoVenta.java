/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.EstadoVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public class AccesoDatosEstadoVenta {
        public boolean GuardarEstadoVenta(Connection con, EstadoVenta ClaseVenta) {
        String sql = "Insert into estado_venta (EST_NOMBRE, EST_ESTADO)values(?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseVenta.getNombreEstadoVenta());
            query.setInt(2, ClaseVenta.getEstado());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarEstadoVenta

    /*

     */
    public boolean ModificarEstadoVenta(Connection con, EstadoVenta ClaseVenta) {
        String sql = "UPDATE estado_venta SET EST_NOMBRE=?, EST_ESTADO=? WHERE EST_ID_ESTADO=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseVenta.getNombreEstadoVenta());
            query.setInt(2, ClaseVenta.getEstado());
            query.setInt(3, ClaseVenta.getIdentificadorEstadoVenta());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarEstadoVenta

    public boolean DesactivarEstadoVenta(Connection con, EstadoVenta ClaseVenta) {
        String sql = "UPDATE estado_venta SET EST_ESTADO=? WHERE EST_ID_ESTADO=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseVenta.getEstado());
            query.setInt(2, ClaseVenta.getIdentificadorEstadoVenta());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarEstadoVenta

    public boolean BuscarEstadoVentaEstado(Connection con, EstadoVenta ClaseVenta) {
        String sql = "Select * from estado_venta where EST_ESTADO="+ClaseVenta.getEstado()+" and EST_ID_ESTADO="+ClaseVenta.getIdentificadorEstadoVenta()+"";
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
    } //fin de metodo BuscarEstadoVentaEstado
    
    
    public boolean BuscarEstadoVenta(Connection con, String buscar) {
        String sql = "Select * from estado_venta where EST_NOMBRE='" + buscar + "'";
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
    } //fin de metodo BuscarBanco

    public void ListarEstadoVenta(Connection con, JTable TablaEstadoVenta) {
        String sql = "select * from estado_venta order by EST_ID_ESTADO";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaEstadoVenta.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloEstadoVenta = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloEstadoVenta[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("EST_ESTADO") == 1) {
                        arregloEstadoVenta[2] = "Activo";
                    } else {
                        arregloEstadoVenta[2] = "Inactivo";
                    }
                }
                modelo.addRow(arregloEstadoVenta);
            }
            TablaEstadoVenta.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarEstadoVenta

    /*
     */
    public void FiltrarEstadoVenta(Connection con, JTable TablaEstadoVenta, String buscar) {
        String sql = "select * from estado_venta where EST_ID_ESTADO like'%" + buscar + "%' "
                + "   or EST_NOMBRE like'%" + buscar + "%' or EST_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaEstadoVenta.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloEstadoVenta = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloEstadoVenta[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("EST_ESTADO") == 1) {
                        arregloEstadoVenta[2] = "Activo";
                    } else {
                        arregloEstadoVenta[2] = "Inactivo";
                    }
                }
                modelo.addRow(arregloEstadoVenta);
            }
            TablaEstadoVenta.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre Filtrar Canal   
    
  
    
}
