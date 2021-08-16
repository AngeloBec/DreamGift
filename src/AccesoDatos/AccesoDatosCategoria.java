/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Categoria;
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
 * @author usuario
 */
public class AccesoDatosCategoria {

    public boolean GuardarCategoria(Connection con, Categoria ClaseCategoria) {
        String sql = "Insert into categoria(CAT_NOMBRE_CATEG, CAT_CODIGO_CATEG, CAT_ESTADO)values(?, ?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseCategoria.getNombreCategoria());
            query.setString(2, ClaseCategoria.getLiteralCategoria());
            query.setInt(3, ClaseCategoria.getEstadoCategoria());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarCategoria

    /*

     */
    public boolean ModificarCategoria(Connection con, Categoria ClaseCategoria) {
        String sql = "UPDATE categoria SET CAT_NOMBRE_CATEG=?, CAT_CODIGO_CATEG=?, CAT_ESTADO=? WHERE CAT_ID_CATEGORIA=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseCategoria.getNombreCategoria());
            query.setString(2, ClaseCategoria.getLiteralCategoria());
            query.setInt(3, ClaseCategoria.getEstadoCategoria());
            query.setInt(4, ClaseCategoria.getIdentificadorCategoria());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarCategoria

    public boolean DesactivarCategoria(Connection con, Categoria ClaseCategoria) {
        String sql = "UPDATE categoria SET CAT_ESTADO=? WHERE CAT_ID_CATEGORIA=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseCategoria.getEstadoCategoria());
            query.setInt(2, ClaseCategoria.getIdentificadorCategoria());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarCategoria

    public boolean BuscarCategoria(Connection con, String buscar) {
        String sql = "Select * from categoria where CAT_NOMBRE_CATEG='" + buscar + "'";
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
    } //fin de metodo BuscarCategoria

    public boolean BuscarCategoriaEstado(Connection con, Categoria ClaseCategoria) {
        String sql = "Select * from categoria where CAT_ESTADO=" + ClaseCategoria.getEstadoCategoria() + " and CAT_ID_CATEGORIA=" + ClaseCategoria.getIdentificadorCategoria() + "";
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
    } //fin de metodo BuscarCategoriaEstado   

    public void ListarCategoria(Connection con, JTable TablaCategoria) {
        String sql = "select * from categoria order by CAT_ID_CATEGORIA";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaCategoria.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloCategorias = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloCategorias[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("CAT_ESTADO") == 1) {
                        arregloCategorias[2] = "Activo";
                    } else {
                        arregloCategorias[2] = "Inactivo";
                    }
                }
                modelo.addRow(arregloCategorias);
            }
            TablaCategoria.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarCategorias

    public void CargarCategoria(Connection con, JComboBox ComboBoxCategoriaArticulo) {
        String sql = "select CAT_NOMBRE_CATEG from categoria order by CAT_NOMBRE_CATEG";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                ComboBoxCategoriaArticulo.addItem(resultado.getString("CAT_NOMBRE_CATEG"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo CargarCategorias    

    /*
     */
    public void FiltrarCategoria(Connection con, JTable TablaCanal, String buscar) {
        String sql = "select * from categoria where CAT_ID_CATEGORIA like'%" + buscar + "%' "
                + "   or CAT_NOMBRE_CATEG like'%" + buscar + "%' or CAT_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaCanal.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloCategorias = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloCategorias[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("CAT_ESTADO") == 1) {
                        arregloCategorias[2] = "Activo";
                    } else {
                        arregloCategorias[2] = "Inactivo";
                    }
                }
                modelo.addRow(arregloCategorias);
            }
            TablaCanal.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre Filtrar Categoria
}
