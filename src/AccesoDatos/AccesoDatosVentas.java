/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Venta;
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
public class AccesoDatosVentas {

    public boolean GuardarVenta(Connection con, Venta ClaseVenta) {
        String sql = "Insert into venta (CLI_ID_CLIENTE, PCK_ID_PACK, VTA_TOTAL, VTA_FECHA_VENTA, VTA_NOMBRE_DESTINATARIO, "
                + "VTA_APELLIDO_DESTINATARIO, VTA_DIRECCION_DESTINATARIO, COM_ID_COMUNA , VTA_TELEFONO_DESTINATARIO, "
                + "VTA_FECHA_ENTREGA, VTA_HORA_ENTREGA_INICIAL, VTA_HORA_ENTREGA_FINAL, VTA_SALUDO_TEXTO)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseVenta.getIdClienteVenta());
            query.setInt(2, ClaseVenta.getIdPackVenta());
            query.setDouble(3, ClaseVenta.getTotalVenta());
            query.setString(4, ClaseVenta.getFechaVenta());
            query.setString(5, ClaseVenta.getNombreDestinatario());
            query.setString(6, ClaseVenta.getApellidoDestinatario());
            query.setString(7, ClaseVenta.getDireccionDestinatario());
            query.setInt(8, ClaseVenta.getidComunaVenta());
            query.setString(9, ClaseVenta.getTelefonoDestinatario());
            query.setString(10, ClaseVenta.getFechaEntregaVenta());
            query.setString(11, ClaseVenta.getHoraEntregaInicio());
            query.setString(12, ClaseVenta.getHoraEntregaFinal());
            query.setString(13, ClaseVenta.getSaludo());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" + e);
        }
        return ingreso;
    } //fin de metodo GuardarVenta  

    public boolean ActualizarConfirmacionVenta(Connection con, Venta ClaseVenta) {
        String sql = "UPDATE venta SET BAN_ID_BANCO=?,VTA_FECHA_TRANSFERENCIA=?, "
                + "VTA_CODIGO_TRANSFERENCIA=? WHERE VTA_ID_VENTA=? ";
        PreparedStatement query = null;
        boolean actualizar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseVenta.getIdBancoVenta());
            query.setString(2, ClaseVenta.getFechaTransVenta());
            query.setString(3, ClaseVenta.getCodigoTranfeVenta());
            query.setInt(4, ClaseVenta.getIdVenta());
            query.execute();
            query.close();
            actualizar = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
        return actualizar;
    } //fin de metodo ActualizarConfirmacionVenta    

    public boolean ActualizarConfirmacionDespacho(Connection con, Venta ClaseVenta) {
        String sql = "UPDATE venta SET EST_ID_ESTADO=? WHERE VTA_ID_VENTA=?";
        PreparedStatement query = null;
        boolean actualizar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseVenta.getEstadoVenta());
            query.setInt(2, ClaseVenta.getIdVenta());
            query.execute();
            query.close();
            actualizar = true;

        } catch (Exception e) {
        }
        return actualizar;
    } //fin de metodo ActualizarConfirmacionDespacho      

    public int ObtenerNumeroPedido(Connection con) {
        int id = 0;
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dreamgifts' AND TABLE_NAME = 'venta'";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                id = resultado.getInt("AUTO_INCREMENT");
            }
        } catch (Exception e) {

        }
        return id;
    } // Cierre ObtenerNumeroPedido

    public void ListarVentasPorConfirmar(Connection con, JTable TablaConfirmacion) {
        String sql = "SELECT venta.VTA_ID_VENTA, venta.VTA_FECHA_VENTA, cliente.CLI_NOMBRE, "
                + "cliente.CLI_TELEFONO, venta.VTA_TOTAL,  pack.PCK_NOMBRE FROM cliente "
                + "INNER JOIN venta ON venta.CLI_ID_CLIENTE = cliente.CLI_ID_CLIENTE AND venta.VTA_CODIGO_TRANSFERENCIA IS NULL "
                + "INNER JOIN pack ON pack.PCK_ID_PACK = venta.PCK_ID_PACK ORDER BY venta.VTA_ID_VENTA";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaConfirmacion.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] ventas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    ventas[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(ventas);
            }
            TablaConfirmacion.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarVentasPorConfirmar  

    public void FiltrarVentaPedido(Connection con, JTable TablaConfirmacion, String buscar) {
        String sql = "SELECT venta.VTA_ID_VENTA, venta.VTA_FECHA_VENTA, cliente.CLI_NOMBRE, "
                + "cliente.CLI_TELEFONO, venta.VTA_TOTAL,  pack.PCK_NOMBRE FROM cliente "
                + "INNER JOIN venta ON venta.CLI_ID_CLIENTE = cliente.CLI_ID_CLIENTE AND venta.VTA_ID_VENTA = '" + buscar + "'"
                + "INNER JOIN pack ON pack.PCK_ID_PACK = venta.PCK_ID_PACK";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaConfirmacion.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] ventas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    ventas[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(ventas);
            }
            TablaConfirmacion.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre FiltrarVentaPedido    

    public void FiltrarVentaRut(Connection con, JTable TablaConfirmacion, String buscar) {
        String sql = "SELECT venta.VTA_ID_VENTA, venta.VTA_FECHA_VENTA, cliente.CLI_NOMBRE, "
                + "cliente.CLI_TELEFONO, venta.VTA_TOTAL,  pack.PCK_NOMBRE FROM cliente "
                + "INNER JOIN venta ON venta.CLI_ID_CLIENTE = cliente.CLI_ID_CLIENTE AND cliente.CLI_RUT LIKE '%" + buscar + "%'"
                + "INNER JOIN pack ON pack.PCK_ID_PACK = venta.PCK_ID_PACK";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaConfirmacion.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] ventas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    ventas[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(ventas);
            }
            TablaConfirmacion.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre FiltrarVentaRut 

    public void ListarDestinosDespachos(Connection con, JTable TablaListadoDestino) {
        String sql = "SELECT venta.VTA_CODIGO_TRANSFERENCIA, pack.PCK_NOMBRE, "
                + "venta.VTA_NOMBRE_DESTINATARIO, venta.VTA_FECHA_ENTREGA, comuna.COM_NOMBRE, "
                + "venta.VTA_DIRECCION_DESTINATARIO, venta.VTA_HORA_ENTREGA_INICIAL, "
                + "venta.VTA_HORA_ENTREGA_FINAL FROM venta "
                + "INNER JOIN comuna ON comuna.COM_ID = venta.COM_ID_COMUNA "
                + "AND venta.VTA_CODIGO_TRANSFERENCIA IS NOT NULL "
                + "INNER JOIN pack ON pack.PCK_ID_PACK = venta.PCK_ID_PACK";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaListadoDestino.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] ventas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    ventas[i] = resultado.getObject(i + 1);
                    ventas[6] = resultado.getString("VTA_HORA_ENTREGA_INICIAL") + "-" + resultado.getString("VTA_HORA_ENTREGA_FINAL");
                }
                modelo.addRow(ventas);
            }
            TablaListadoDestino.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarDestinosDespachos     

    public void FiltrarDestinosDespachosPorFecha(Connection con, JTable TablaListadoDestino, String Fecha) {
        String sql = "SELECT venta.VTA_CODIGO_TRANSFERENCIA, pack.PCK_NOMBRE, "
                + "venta.VTA_NOMBRE_DESTINATARIO, venta.VTA_FECHA_ENTREGA, comuna.COM_NOMBRE, "
                + "venta.VTA_DIRECCION_DESTINATARIO, venta.VTA_HORA_ENTREGA_INICIAL, "
                + "venta.VTA_HORA_ENTREGA_FINAL FROM venta "
                + "INNER JOIN comuna ON comuna.COM_ID = venta.COM_ID_COMUNA "
                + "AND venta.VTA_CODIGO_TRANSFERENCIA IS NOT NULL "
                + "AND venta.VTA_FECHA_ENTREGA = '" + Fecha + "'"
                + "INNER JOIN pack ON pack.PCK_ID_PACK = venta.PCK_ID_PACK";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaListadoDestino.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] ventas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    ventas[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(ventas);
            }
            TablaListadoDestino.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarDestinosDespachos     

    public void ListarEstadosDespachos(Connection con, JTable TablaListadoEstadosDespacho) {
        String sql = "SELECT venta.VTA_ID_VENTA, pack.PCK_NOMBRE, "
                + "venta.VTA_NOMBRE_DESTINATARIO, venta.VTA_FECHA_ENTREGA, comuna.COM_NOMBRE, "
                + "venta.VTA_HORA_ENTREGA_INICIAL, venta.VTA_HORA_ENTREGA_FINAL FROM venta "
                + "INNER JOIN comuna ON comuna.COM_ID = venta.COM_ID_COMUNA AND venta.VTA_CODIGO_TRANSFERENCIA IS NOT NULL "
                + "AND venta.EST_ID_ESTADO IS NULL "
                + "INNER JOIN pack ON pack.PCK_ID_PACK = venta.PCK_ID_PACK ";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaListadoEstadosDespacho.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount() - 1;
            Object[] ventas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    ventas[i] = resultado.getObject(i + 1);
                    ventas[5] = resultado.getString("VTA_HORA_ENTREGA_INICIAL") + "-" + resultado.getString("VTA_HORA_ENTREGA_FINAL");
                }
                modelo.addRow(ventas);
            }
            TablaListadoEstadosDespacho.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarDestinosDespachos     

    public void FiltrarEstadosDespachosPorFecha(Connection con, JTable TablaListadoEstadosDespacho, String fecha) {
        String sql = "SELECT venta.VTA_ID_VENTA, pack.PCK_NOMBRE, "
                + "venta.VTA_NOMBRE_DESTINATARIO, venta.VTA_FECHA_ENTREGA, comuna.COM_NOMBRE, "
                + "venta.VTA_HORA_ENTREGA_INICIAL, venta.VTA_HORA_ENTREGA_FINAL FROM venta "
                + "INNER JOIN comuna ON comuna.COM_ID = venta.COM_ID_COMUNA AND venta.VTA_CODIGO_TRANSFERENCIA IS NOT NULL "
                + "AND venta.VTA_FECHA_ENTREGA = '" + fecha + "'"
                + "INNER JOIN pack ON pack.PCK_ID_PACK = venta.PCK_ID_PACK ";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaListadoEstadosDespacho.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount() - 1;
            Object[] ventas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    ventas[i] = resultado.getObject(i + 1);
                    ventas[5] = resultado.getString("VTA_HORA_ENTREGA_INICIAL") + "-" + resultado.getString("VTA_HORA_ENTREGA_FINAL");
                }
                modelo.addRow(ventas);
            }
            TablaListadoEstadosDespacho.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarDestinosDespachos       
}
