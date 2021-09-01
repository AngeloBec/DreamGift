/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Bancos;
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
 * @author Alinson Lopez
 */
public class AccesoDatosBancos {
    public boolean GuardarBanco(Connection con, Bancos ClaseBanco) {
        String sql = "Insert into bancos (BAN_NOMBRE, BAN_ESTADO)values(?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseBanco.getNombreBanco());
            query.setInt(2, ClaseBanco.getEstadoBanco());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarBanco

    /*

     */
    public boolean ModificarBanco(Connection con, Bancos ClaseBanco) {
        String sql = "UPDATE bancos SET BAN_NOMBRE=?,BAN_ESTADO=? WHERE BAN_ID_BANCO=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseBanco.getNombreBanco());
            query.setInt(2, ClaseBanco.getEstadoBanco());
            query.setInt(3, ClaseBanco.getIdentificadorBanco());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarBanco

    public boolean DesactivarBanco(Connection con, Bancos ClaseBanco) {
        String sql = "UPDATE bancos SET BAN_ESTADO=? WHERE BAN_ID_BANCO=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseBanco.getEstadoBanco());
            query.setInt(2, ClaseBanco.getIdentificadorBanco());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarBanco

    public boolean BuscarBancoEstado(Connection con, Bancos ClaseBanco) {
        String sql = "Select * from bancos where BAN_ESTADO="+ClaseBanco.getEstadoBanco()+" and BAN_ID_BANCO="+ClaseBanco.getIdentificadorBanco()+"";
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
    
    
    public boolean BuscarBanco(Connection con, String buscar) {
        String sql = "Select * from bancos where BAN_NOMBRE='" + buscar + "'";
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

    public void ListarBancos(Connection con, JTable TablaBanco) {
        String sql = "select * from bancos order by BAN_ID_BANCO";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaBanco.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arreglobancos = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arreglobancos[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("BAN_ESTADO") == 1) {
                        arreglobancos[2] = "Activo";
                    } else {
                        arreglobancos[2] = "Inactivo";
                    }
                }
                modelo.addRow(arreglobancos);
            }
            TablaBanco.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarBanco

    /*
     */
    public void FiltrarBanco(Connection con, JTable TablaBanco, String buscar) {
        String sql = "select * from bancos where BAN_ID_BANCO like'%" + buscar + "%' "
                + "   or BAN_NOMBRE like'%" + buscar + "%' or BAN_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaBanco.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arreglobancos = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arreglobancos[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("BAN_ESTADO") == 1) {
                        arreglobancos[2] = "Activo";
                    } else {
                        arreglobancos[2] = "Inactivo";
                    }
                }
                modelo.addRow(arreglobancos);
            }
            TablaBanco.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre Filtrar Banco  
    
    public void CargarBanco(Connection con, JComboBox ComboBoxBancoConf) {
        int estado = 1;
        String sql = "select BAN_NOMBRE from bancos where BAN_ESTADO =" + estado + " order by BAN_NOMBRE";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            ComboBoxBancoConf.addItem("Seleccione Banco");
            while (resultado.next()) {
                ComboBoxBancoConf.addItem(resultado.getString("BAN_NOMBRE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo CargarCanal     

    public int ObtenerIdBanco(Connection con, String nombreBanco) {
        String sql = "Select BAN_ID_BANCO from bancos where BAN_NOMBRE='" + nombreBanco + "'";
        int id = 0;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                id = resultado.getInt("BAN_ID_BANCO");
            }
        } catch (Exception e) {
        }
        return id;
    } //fin de metodo ObtenerId    
}
