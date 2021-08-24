/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Articulos;
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
public class AccesoDatosArticulos {

    public boolean GuardarArticulo(Connection con, Articulos ClaseArticulo) {
        String sql = "Insert into articulo (ART_NOMBRE, ART_CODIGO_ARTICULO, ART_STOCK, ART_MARCA, ART_FECHA_VENCIMIENTO, ART_ESTADO, ART_ID_CATEGORIA, ART_ID_PROVEEDOR)values(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseArticulo.getNombreArticulo());
            query.setString(2, ClaseArticulo.getCodigoLetraArticulo());
            query.setInt(3, ClaseArticulo.getStockArticulo());
            query.setString(4, ClaseArticulo.getMarcaArticulo());
            query.setString(5, ClaseArticulo.getFechaVencimientoArticulo());
            query.setInt(6, ClaseArticulo.getEstadoArticulo());
            query.setInt(7, ClaseArticulo.getIdCategoriaArticulo());
            query.setInt(8, ClaseArticulo.getIdProveedorArticulo());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarArticulo

    /*

     */
    public boolean ModificarArticulo(Connection con, Articulos ClaseArticulo) {
        String sql = "UPDATE articulo SET "
                + "ART_NOMBRE=?, ART_CODIGO_ARTICULO=?, ART_STOCK=?, ART_MARCA=?, "
                + "ART_FECHA_VENCIMIENTO=?, ART_ESTADO=?, ART_ID_CATEGORIA=?, "
                + "ART_ID_PROVEEDOR=? WHERE ART_ID_ARTICULO=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseArticulo.getNombreArticulo());
            query.setString(2, ClaseArticulo.getCodigoLetraArticulo());
            query.setInt(3, ClaseArticulo.getStockArticulo());
            query.setString(4, ClaseArticulo.getMarcaArticulo());
            query.setString(5, ClaseArticulo.getFechaVencimientoArticulo());
            query.setInt(6, ClaseArticulo.getEstadoArticulo());
            query.setInt(7, ClaseArticulo.getIdCategoriaArticulo());
            query.setInt(8, ClaseArticulo.getIdProveedorArticulo());
            query.setInt(9, ClaseArticulo.getIdentificadorArticulo());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarArticulo

    public boolean DesactivarArticulo(Connection con, Articulos ClaseArticulo) {
        String sql = "UPDATE articulo SET ART_ESTADO=? WHERE ART_ID_ARTICULO=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseArticulo.getEstadoArticulo());
            query.setInt(2, ClaseArticulo.getIdentificadorArticulo());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarArticulo

    public boolean BuscarArticulo(Connection con, Articulos ClaseArticulo) {
        String sql = "Select * from articulo where ART_NOMBRE='" + ClaseArticulo.getNombreArticulo() + "' "
                + " and ART_ID_CATEGORIA= " + ClaseArticulo.getIdCategoriaArticulo() + ""
                + " and ART_ID_PROVEEDOR= " + ClaseArticulo.getIdProveedorArticulo() + "";
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

    public boolean BuscarArticuloEstado(Connection con, Articulos ClaseArticulo) {
        String sql = "Select * from articulo where ART_ESTADO=" + ClaseArticulo.getEstadoArticulo() + " and ART_ID_ARTICULO=" + ClaseArticulo.getIdentificadorArticulo() + "";
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
    } //fin de metodo BuscarArticuloEstado 

    public String BuscarNombreProveedor(Connection con, int id) {
        String sql = "SELECT PRO_RAZONSOCIAL FROM proveedor INNER JOIN articulo ON proveedor.PRO_ID_PROVEEDOR=" + id;
        String proveedor = " ";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                proveedor = resultado.getString("PRO_RAZONSOCIAL");
            }
        } catch (Exception e) {

        }
        return proveedor;
    } //fin de metodo BuscarNombreProveedor   

    public String BuscarNombreCategoria(Connection con, int id) {
        String sql = "SELECT CAT_NOMBRE_CATEG FROM categoria INNER JOIN articulo ON categoria.CAT_ID_CATEGORIA=" + id;
        String categoria = " ";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                categoria = resultado.getString("CAT_NOMBRE_CATEG");
            }
        } catch (Exception e) {

        }
        return categoria;
    } //fin de metodo BuscarNombreCategoria       

    public void ListarArticulos(Connection con, JTable TablaArticulos) {
//        String sql = "select * from articulo order by ART_ID_ARTICULO";
        String sql = "SELECT ART_ID_ARTICULO, ART_NOMBRE, ART_CODIGO_ARTICULO, ART_STOCK, ART_MARCA, ART_FECHA_VENCIMIENTO, ART_ESTADO, categoria.CAT_NOMBRE_CATEG, proveedor.PRO_RAZONSOCIAL FROM articulo \n"
                + "INNER JOIN categoria ON articulo.ART_ID_CATEGORIA = categoria.CAT_ID_CATEGORIA\n"
                + "INNER JOIN proveedor ON articulo.ART_ID_PROVEEDOR = proveedor.PRO_ID_PROVEEDOR";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaArticulos.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloArticulos = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloArticulos[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("ART_ESTADO") == 1) {
                        arregloArticulos[6] = "Activo";
                    } else {
                        arregloArticulos[6] = "Inactivo";
                    }
//                    arregloArticulos[7] = BuscarNombreCategoria(con, resultado.getInt("ART_ID_CATEGORIA"));
//                    arregloArticulos[8] = BuscarNombreProveedor(con, resultado.getInt("ART_ID_PROVEEDOR"));
                }
                modelo.addRow(arregloArticulos);
            }
            TablaArticulos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarArticulos

    /*
     */
    public void FiltrarArticulos(Connection con, JTable TablaArticulos, String buscar) {
        String sql = "select * from articulo where ART_ID_ARTICULO like'%" + buscar + "%' "
                + "   or ART_NOMBRE like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaArticulos.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloArticulos = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloArticulos[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("ART_ESTADO") == 1) {
                        arregloArticulos[6] = "Activo";
                    } else {
                        arregloArticulos[6] = "Inactivo";
                    }
                    arregloArticulos[7] = BuscarNombreCategoria(con, resultado.getInt("ART_ID_CATEGORIA"));
                    arregloArticulos[8] = BuscarNombreProveedor(con, resultado.getInt("ART_ID_PROVEEDOR"));
                }
                modelo.addRow(arregloArticulos);
            }
            TablaArticulos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre FiltrarArticulos

    /*
     */
    public int CantidadArticulos(Connection con) {
        String sql = "select * from articulo";
        DefaultTableModel modelo;
        int i = 0;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
        return i;
    } // Cierre CantidadArticulos

    public void ListarArticulosPack(Connection con, JTable TablaPackSeleccionArticulos) {
        int estado = 1;
        String sql = "select ART_ID_ARTICULO, ART_NOMBRE from articulo where ART_ESTADO ="+ estado +" order by ART_ID_ARTICULO";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaPackSeleccionArticulos.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloArticulos = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloArticulos[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(arregloArticulos);
            }
            TablaPackSeleccionArticulos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarArticulosPack

    public void ListarArticulosdelPack(Connection con, JTable TablaPackConArticulos, int identificadorPack) {
        String sql = "SELECT pack_has_articulo.ART_ID_ARTICULO, articulo.ART_NOMBRE, pack_has_articulo.CANTIDAD FROM pack_has_articulo INNER JOIN articulo ON pack_has_articulo.ART_ID_ARTICULO = articulo.ART_ID_ARTICULO AND pack_has_articulo.PCK_ID_PACK=" + identificadorPack;
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaPackConArticulos.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloArticulosPack = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloArticulosPack[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(arregloArticulosPack);
            }
            TablaPackConArticulos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarArticulosPack    
}
