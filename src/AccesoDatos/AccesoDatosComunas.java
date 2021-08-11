/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Comuna;
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
 * @author Angelo Bcerra
 */
public class AccesoDatosComunas {
    
        public boolean GuardarComuna(Connection con, Comuna comunas) {
        String sql = "Insert into comuna(COM_NOMBRE, COM_ESTADO)values(?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, comunas.getNombreComuna());
            query.setInt(2, comunas.getEstadoComuna());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarComuna

    /*

     */
    public boolean ModificarComuna(Connection con, Comuna comunas) {
        String sql = "UPDATE comuna SET COM_NOMBRE=?,COM_ESTADO=? WHERE COM_ID=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, comunas.getNombreComuna());
            query.setInt(2, comunas.getEstadoComuna());
            query.setInt(3, comunas.getidentificadorComuna());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarComuna

    public boolean DesactivarComuna(Connection con, Comuna comunas) {
        String sql = "UPDATE comuna SET COM_ESTADO=? WHERE COM_ID=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, comunas.getEstadoComuna());
            query.setInt(2, comunas.getidentificadorComuna());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarComuna

    public boolean BuscarComuna(Connection con, String buscar) {
        String sql = "Select * from comuna where COM_NOMBRE='"+buscar+"'";
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
    } //fin de metodo BuscarComuna

    
    public boolean BuscarComunaEstado(Connection con, Comuna comunas) {
        String sql = "Select * from comuna where COM_ESTADO="+comunas.getEstadoComuna()+" and COM_ID="+comunas.getidentificadorComuna()+"";
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
    } //fin de metodo BuscarComunaEstado    
    
    public void ListarComunas(Connection con, JTable TablaComuna) {
        String sql = "select * from comuna order by COM_ID";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaComuna.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] comunas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    comunas[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("COM_ESTADO") == 1) {
                        comunas[2] = "Activo";
                    } else {
                        comunas[2] = "Inactivo";
                    }
                }
                modelo.addRow(comunas);
            }
            TablaComuna.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarComunas

    /*
     */
    public void FiltrarComuna(Connection con, JTable TablaComuna, String buscar) {
        String sql = "select * from comuna where COM_ID like'%" + buscar + "%' "
                + "   or COM_NOMBRE like'%" + buscar + "%' or COM_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        System.out.println(buscar);
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaComuna.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] comunas = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    comunas[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("COM_ESTADO") == 1) {
                        comunas[2] = "Activo";
                    } else {
                        comunas[2] = "Inactivo";
                    }
                }
                modelo.addRow(comunas);
            }
            TablaComuna.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre Filtrar usuario
    
}
