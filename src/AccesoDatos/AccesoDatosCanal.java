/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Canal;
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
 * @author Angelica Valdivia
 */
public class AccesoDatosCanal {

    public boolean GuardarCanal(Connection con, Canal medios) {
        String sql = "Insert into canal(CAN_NOMBRE, CAN_ESTADO)values(?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, medios.getNombreCanal());
            query.setInt(2, medios.getEstadoCanal());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarCanal

    /*

     */
    public boolean ModificarCanal(Connection con, Canal medios) {
        String sql = "UPDATE canal SET CAN_NOMBRE=?,CAN_ESTADO=? WHERE CAN_ID_CANAL=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, medios.getNombreCanal());
            query.setInt(2, medios.getEstadoCanal());
            query.setInt(3, medios.getIdentificadorCanal());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarCanal

    public boolean DesactivarCanal(Connection con, Canal medios) {
        String sql = "UPDATE canal SET CAN_ESTADO=? WHERE CAN_ID_CANAL=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, medios.getEstadoCanal());
            query.setInt(2, medios.getIdentificadorCanal());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarCanal

    public boolean BuscarCanal(Connection con, String buscar) {
        String sql = "Select * from canal where CAN_NOMBRE='" + buscar + "'";
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
    } //fin de metodo BuscarCanal

    public boolean BuscarCanalEstado(Connection con, Canal medios) {
        String sql = "Select * from canal where CAN_ESTADO=" + medios.getEstadoCanal() + " and CAN_ID_CANAL=" + medios.getIdentificadorCanal() + "";
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
    } //fin de metodo BuscarCanalEstado   

    public void ListarCanal(Connection con, JTable TablaCanal) {
        String sql = "select * from canal order by CAN_ID_CANAL";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaCanal.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] canales = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    canales[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("CAN_ESTADO") == 1) {
                        canales[2] = "Activo";
                    } else {
                        canales[2] = "Inactivo";
                    }
                }
                modelo.addRow(canales);
            }
            TablaCanal.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarCanales

    /*
     */
    public void FiltrarCanal(Connection con, JTable TablaCanal, String buscar) {
        String sql = "select * from canal where CAN_ID_CANAL like'%" + buscar + "%' "
                + "   or CAN_NOMBRE like'%" + buscar + "%' or CAN_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaCanal.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] canales = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    canales[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("CAN_ESTADO") == 1) {
                        canales[2] = "Activo";
                    } else {
                        canales[2] = "Inactivo";
                    }
                }
                modelo.addRow(canales);
            }
            TablaCanal.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre Filtrar Canal

    public void CargarCanal(Connection con, JComboBox ComboBoxCanalVentas) {
        int estado = 1;
        String sql = "select CAN_NOMBRE from canal where CAN_ESTADO =" + estado + " order by CAN_NOMBRE";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            ComboBoxCanalVentas.addItem("Seleccione Canal");
            while (resultado.next()) {
                ComboBoxCanalVentas.addItem(resultado.getString("CAN_NOMBRE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo CargarCanal 

    public String ObtenerNombreCanal(Connection con, int idCanal) {
        String sql = "Select CAN_NOMBRE from canal where CAN_ID_CANAL=" + idCanal + "";
        String nombre = "";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                nombre = resultado.getString("CAN_NOMBRE");
            }
        } catch (Exception e) {
        }
        return nombre;
    } //fin de metodo ObtenerIdCanal

    public int ObtenerIdCanal(Connection con, String nombreCanal) {
        String sql = "Select CAN_ID_CANAL from canal where CAN_NOMBRE='" + nombreCanal + "'";
        int id = 0;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                id = resultado.getInt("CAN_ID_CANAL");
            }
        } catch (Exception e) {
        }
        return id;
    } //fin de metodo ObtenerIdCanal    
}
