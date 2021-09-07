/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class AccesoDatosInformes {

    public void InformeVencimientos(Connection con, JTable TablaVencimientos, String rangofechamin, String rangofechamax, boolean ReqCategoria, String categoria) {
        String sql = "SELECT T0.ART_CODIGO_ARTICULO, T0.ART_NOMBRE, T0.ART_STOCK, T0.ART_FECHA_VENCIMIENTO, T1.CAT_NOMBRE_CATEG"
                + " FROM ARTICULO T0 INNER JOIN CATEGORIA T1 ON T0.ART_ID_CATEGORIA = T1.CAT_ID_CATEGORIA INNER JOIN PROVEEDOR T2 ON T0.ART_ID_PROVEEDOR = T2.PRO_ID_PROVEEDOR"
                + " WHERE T0.ART_ESTADO = 1 AND (T0.ART_FECHA_VENCIMIENTO BETWEEN '" + rangofechamin + "' AND '" + rangofechamax + "')"
                + " ORDER BY T0.ART_FECHA_VENCIMIENTO ASC";
        DefaultTableModel modelo;
        try {
            String casilla;
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaVencimientos.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arreglovencimientos = new Object[CantidadColumnas];

            while (resultado.next()) {
                if (ReqCategoria == true) {
                    if (resultado.getString("T1.CAT_NOMBRE_CATEG").equals(categoria)) {
                        for (int i = 0; i < CantidadColumnas; i++) {
                            arreglovencimientos[i] = resultado.getObject(i + 1);
                            
                        }
                        modelo.addRow(arreglovencimientos);
                        TablaVencimientos.setModel(modelo);
                    }

                } else {
                    if (ReqCategoria == false) {
                        for (int i = 0; i < CantidadColumnas; i++) {
                            arreglovencimientos[i] = resultado.getObject(i + 1);
                        }
                        modelo.addRow(arreglovencimientos);
                        TablaVencimientos.setModel(modelo);
                    }
                }
            }
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Ningun resultado encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de conexion, Intente mas tarde");
        }
    }

    public void InformeVentaPack(Connection con, JTable TablaVentaPack, String rangofechamin, String rangofechamax) {
        String sql = "SELECT T1.PCK_NOMBRE, COUNT(T0.PCK_ID_PACK), T1.PCK_PRECIO "
                + "FROM VENTA T0 INNER JOIN PACK T1 ON T0.PCK_ID_PACK = T1.PCK_ID_PACK, "
                + "(SELECT DISTINCT T0.PCK_ID_PACK As PACK FROM VENTA T0 INNER JOIN PACK T1 ON T0.PCK_ID_PACK = T1.PCK_ID_PACK) A "
                + "WHERE T0.PCK_ID_PACK = A.Pack AND (T0.VTA_FECHA_VENTA BETWEEN '" + rangofechamin + "' AND '" + rangofechamax + "') "
                + "GROUP BY A.Pack, T1.PCK_NOMBRE ORDER BY COUNT(T0.PCK_ID_PACK) DESC";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaVentaPack.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloventapack = new Object[CantidadColumnas];

            while (resultado.next()) {

                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloventapack[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(arregloventapack);

            }
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Ningun resultado encontrado");
            }
            TablaVentaPack.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de conexion, Intente mas tarde");
        }

    }

    public void InformeComunas(Connection con, JTable TablaComunas, String rangofechamin, String rangofechamax, String Comuna) {
        String sql = "SELECT T2.COM_NOMBRE, T1.PCK_NOMBRE, COUNT(T0.PCK_ID_PACK), T1.PCK_PRECIO "
                + "FROM VENTA T0 INNER JOIN PACK T1 ON T0.PCK_ID_PACK = T1.PCK_ID_PACK INNER JOIN COMUNA T2 ON T0.COM_ID_COMUNA = T2.COM_ID , "
                + " (SELECT DISTINCT T0.PCK_ID_PACK As PACK FROM VENTA T0 INNER JOIN PACK T1 ON T0.PCK_ID_PACK = T1.PCK_ID_PACK) A "
                + "WHERE T0.PCK_ID_PACK = A.Pack AND (T0.VTA_FECHA_VENTA BETWEEN '" + rangofechamin + "' AND '" + rangofechamax + "') "
                + "GROUP BY A.Pack, T1.PCK_NOMBRE "
                + "ORDER BY T2.COM_NOMBRE ASC, COUNT(T0.PCK_ID_PACK) DESC";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaComunas.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arreglocomunas = new Object[CantidadColumnas];

            while (resultado.next()) {
                if (resultado.getString("T2.COM_NOMBRE").equals(Comuna)) {
                    for (int i = 0; i < CantidadColumnas; i++) {
                        arreglocomunas[i] = resultado.getObject(i + 1);
                    }
                    modelo.addRow(arreglocomunas);
                    TablaComunas.setModel(modelo);
                }
            }
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Ningun resultado encontrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de conexion, Intente mas tarde");
        }
    }

    public void InformesInventario(Connection con, JTable TablaInventario, int stock, String Categoria) {
        String sql = "SELECT T0.ART_CODIGO_ARTICULO, T0.ART_NOMBRE, T0.ART_STOCK, T1.CAT_NOMBRE_CATEG FROM "
                + "ARTICULO T0 INNER JOIN CATEGORIA T1 ON T0.ART_ID_CATEGORIA = T1.CAT_ID_CATEGORIA WHERE T0.ART_STOCK <= "
                + stock + " AND T1.CAT_NOMBRE_CATEG = '" + Categoria + "' ORDER BY T0.ART_STOCK ASC";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaInventario.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloinventario = new Object[CantidadColumnas];

            while (resultado.next()) {

                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloinventario[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(arregloinventario);
            }
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Ningun resultado encontrado");
            }
            TablaInventario.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de conexion, Intente mas tarde");
        }

    }

    public void InformeVentasCliente(Connection con, JTable TablaVentaCliente, String Fmin, String Fmax, String Cliente) {
        String sql = "SELECT T0.VTA_ID_VENTA, CONCAT(T1.CLI_NOMBRE, ' ' ,T1.CLI_APELLIDO), T2.PCK_NOMBRE, T0.VTA_FECHA_VENTA, T0.VTA_FECHA_ENTREGA, T0.VTA_TOTAL "
                + "FROM VENTA T0 INNER JOIN CLIENTE T1 ON T0.CLI_ID_CLIENTE = T1.CLI_ID_CLIENTE INNER JOIN PACK T2 ON T0.PCK_ID_PACK = T2.PCK_ID_PACK "
                + "WHERE (T0.VTA_FECHA_VENTA BETWEEN '" + Fmin + "' AND '" + Fmax + "') AND (T1.CLI_NOMBRE LIKE '%" + Cliente + "%'OR T1.CLI_APELLIDO LIKE '%" + Cliente + "%') "
                + "ORDER BY CONCAT(T1.CLI_NOMBRE, ' ' ,T1.CLI_APELLIDO) DESC";

        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaVentaCliente.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloventacliente = new Object[CantidadColumnas];

            while (resultado.next()) {

                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloventacliente[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(arregloventacliente);
            }
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Ningun resultado encontrado");
            }
            TablaVentaCliente.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de conexion, Intente mas tarde");
        }

    }

}
