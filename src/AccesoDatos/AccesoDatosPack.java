/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Pack;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public class AccesoDatosPack {
    public boolean GuardarPack(Connection con, Pack ClasePack) {
        String sql = "Insert pack (PCK_NOMBRE, PCK_PRECIO, PCK_STOCK, PCK_ESTADO)values(?, ?, ?, ?)";       
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClasePack.getNombrePack());
            query.setInt(2, ClasePack.getPrecioPack());
            query.setInt(3, ClasePack.getStockPack());
            query.setInt(4, ClasePack.getEstadoPack());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarPack

    /*

     */
    public boolean ModificarPack(Connection con, Pack ClasePack) {
        String sql = "UPDATE pack SET PCK_NOMBRE=?, PCK_PRECIO=?, PCK_STOCK=?, PCK_ESTADO=? WHERE PCK_ID_PACK=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClasePack.getNombrePack());
            query.setInt(2, ClasePack.getPrecioPack());
            query.setInt(3, ClasePack.getStockPack());
            query.setInt(4, ClasePack.getEstadoPack());
            query.setInt(5, ClasePack.getIdentificadorPack());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarPack

    public boolean DesactivarPack(Connection con, Pack ClasePack) {
        String sql = "UPDATE pack SET PCK_ESTADO=? WHERE PCK_ID_PACK=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClasePack.getEstadoPack());
            query.setInt(2, ClasePack.getIdentificadorPack());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarPack

    public boolean BuscarPack(Connection con, String buscar) {
        String sql = "Select * from pack where PCK_NOMBRE='" + buscar + "'";
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
    } //fin de metodo BuscarPack

    public boolean BuscarPackEstado(Connection con, Pack ClasePack) {
        String sql = "Select * from pack where PCK_ESTADO="+ClasePack.getEstadoPack()+" and PCK_ID_PACK="+ClasePack.getIdentificadorPack()+"";
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
    } //fin de metodo BuscarPackEstado   
    
    public boolean BuscarPackEspecifico(Connection con, Pack ClasePack) {
        String sql = "Select * from pack where PCK_ESTADO="+ClasePack.getEstadoPack()+" and PCK_ID_PACK="+ClasePack.getIdentificadorPack()+" and PCK_PRECIO="+ClasePack.getPrecioPack()+" and PCK_STOCK="+ClasePack.getStockPack();
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
    } //fin de metodo BuscarPackEstado 
    
    public void ListarPack(Connection con, JTable TablaPack) {
        String sql = "select * from pack order by PCK_ID_PACK";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaPack.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloPack = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloPack [i] = resultado.getObject(i + 1);
                    if (resultado.getByte("PCK_ESTADO") == 1) {
                        arregloPack [4] = "Activo";
                    } else {
                        arregloPack [4] = "Inactivo";
                    }
                }
                modelo.addRow(arregloPack);
            }
            TablaPack.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarPack

    /*
     */
    public void FiltrarPack(Connection con, JTable TablaPack, String buscar) {
        String sql = "select * from pack where PCK_ID_PACK like'%" + buscar + "%' "
                + "   or PCK_NOMBRE like'%" + buscar + "%' or PCK_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaPack.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloPack = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloPack[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("PCK_ESTADO") == 1) {
                        arregloPack[4] = "Activo";
                    } else {
                        arregloPack[4] = "Inactivo";
                    }
                }
                modelo.addRow(arregloPack);
            }
            TablaPack.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre FiltrarPack
    
    public int CantidadPack(Connection con) {
        String sql = "select * from pack";
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
    } // Cierre CantidadPack

    public void CargarPack(Connection con, JComboBox combobxPackDestinatario) {
        int estado = 1;
        String sql = "select PCK_NOMBRE from pack where PCK_ESTADO ="+ estado +" order by PCK_NOMBRE";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            combobxPackDestinatario.addItem("Seleccione Pack");
            while (resultado.next()) {
                combobxPackDestinatario.addItem(resultado.getString("PCK_NOMBRE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo CargarPack  

    public ArrayList ObtenerInformacionPack(Connection con, String nombrePack) {
        String sql = "Select PCK_ID_PACK, PCK_PRECIO from pack where PCK_NOMBRE='" + nombrePack + "'";
        ArrayList datosPack = new ArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                datosPack.add(resultado.getString("PCK_ID_PACK"));
                datosPack.add(resultado.getString("PCK_PRECIO"));
            }
        } catch (Exception e) {
        }
        return datosPack;
    } //fin de metodo BuscarBanco    
}
