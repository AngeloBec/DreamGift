/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.Clientes;
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
 * @author Alinson Lopez
 */
public class AccesoDatosClientes {

    public boolean GuardarCliente(Connection con, Clientes ClaseCliente) {
        String sql = "Insert into cliente (CLI_RUT, CLI_NOMBRE, CLI_APELLIDO, CLI_DIRECCION, CLI_TELEFONO, "
                + "CLI_CORREO, CLI_FECHANACIMIENTO, CAN_ID_CANAL, CLI_ESTADO)values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseCliente.getRutCliente());
            query.setString(2, ClaseCliente.getNombreCliente());
            query.setString(3, ClaseCliente.getApellidoCliente());
            query.setString(4, ClaseCliente.getDireccionCliente());
            query.setString(5, ClaseCliente.getTelefonoCliente());
            query.setString(6, ClaseCliente.getCorreoCliente());
            query.setString(7, ClaseCliente.getFechaNacimientoCliente());
            query.setInt(8, ClaseCliente.getCanalCliente());
            query.setInt(9, ClaseCliente.getEstadoCliente());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
            System.out.println("error"+e);
             JOptionPane.showMessageDialog(null, "error"+e);
        }
        return ingreso;
    } //fin de metodo GuardarCliente

    public void CargarCanalCliente(Connection con, JComboBox ComboBoxRedSocialCliente) {
        String sql = "select CAN_NOMBRE, CAN_ID_CANAL from canal where CAN_ESTADO = 1 order by CAN_NOMBRE";

        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                ComboBoxRedSocialCliente.addItem(resultado.getString("CAN_NOMBRE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    }//fin de metodo Cargar Canales     

    public boolean ModificarCliente(Connection con, Clientes ClaseCliente) {
        String sql = "UPDATE cliente SET CLI_RUT=?, CLI_NOMBRE=?, CLI_APELLIDO=?, CLI_DIRECCION=?, CLI_TELEFONO=?, CLI_CORREO=?, "
                + "CLI_FECHANACIMIENTO =?, CAN_ID_CANAL=?, CLI_ESTADO=? WHERE CLI_ID_CLIENTE=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, ClaseCliente.getRutCliente());
            query.setString(2, ClaseCliente.getNombreCliente());
            query.setString(3, ClaseCliente.getApellidoCliente());
            query.setString(4, ClaseCliente.getDireccionCliente());
            query.setString(5, ClaseCliente.getTelefonoCliente());
            query.setString(6, ClaseCliente.getCorreoCliente());
            query.setString(7, ClaseCliente.getFechaNacimientoCliente());
            query.setInt(8, ClaseCliente.getCanalCliente());
            query.setInt(9, ClaseCliente.getEstadoCliente());
            query.setInt(10, ClaseCliente.getIdentificadorCliente());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarBanco

    public boolean DesactivarCliente(Connection con, Clientes ClaseCliente) {
        String sql = "UPDATE cliente SET CLI_ESTADO=? WHERE CLI_ID_CLIENTE=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClaseCliente.getEstadoCliente());
            query.setInt(2, ClaseCliente.getIdentificadorCliente());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarBanco

    public boolean BuscarCliente(Connection con, String buscar) {
        String sql = "Select * from cliente where CLI_RUT='" + buscar + "'";
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

    public void ListarClientes(Connection con, JTable TablaClientes) {
        String sql = "select * from cliente order by CLI_ID_CLIENTE";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaClientes.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloclientes = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloclientes[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("CLI_ESTADO") == 1) {
                        arregloclientes[9] = "Activo";
                    } else {
                        arregloclientes[9] = "Inactivo";
                    }
                    arregloclientes[8] = BuscarNombreCanal(con, resultado.getInt("CAN_ID_CANAL"));
                }
                modelo.addRow(arregloclientes);
            }
            TablaClientes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarCliente Usado

    /*
     */
    public void FiltrarCliente(Connection con, JTable TablaCliente, String buscar) {
        String sql = "select * from cliente where CLI_RUT like '%" + buscar + "%' or CLI_ID_CLIENTE like'%" + buscar + "%' "
                + "   or CLI_NOMBRE like'%" + buscar + "%' or CLI_APELLIDO like '%" + buscar + "%' or CLI_CORREO like '%" + buscar + "%' "
                + "or CLI_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaCliente.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] arregloclientes = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    arregloclientes[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("CLI_ESTADO") == 1) {
                        arregloclientes[9] = "Activo";
                    } else {
                        arregloclientes[9] = "Inactivo";
                    }
                    arregloclientes[8] = BuscarNombreCanal(con, resultado.getInt("CAN_ID_CANAL"));
                }
                modelo.addRow(arregloclientes);
            }
            TablaCliente.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre Filtrar Canal    

    public int BuscarIdCanal(Connection con, String canal) {
        String sql = "SELECT CAN_ID_CANAL FROM canal WHERE CAN_NOMBRE = '" + canal + "'";
        int idCanal = 0;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                idCanal = resultado.getInt("CAN_ID_CANAL");
            }
        } catch (Exception e) {

        }
        return idCanal;
    } //fin de metodo BuscarIdProveedor   Usado

    public String BuscarNombreCanal(Connection con, int id) {
        String sql = "SELECT CAN_NOMBRE FROM canal INNER JOIN cliente ON canal.CAN_ID_CANAL=" + id;
        String canal = " ";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                canal = resultado.getString("CAN_NOMBRE");
            }
        } catch (Exception e) {

        }
        return canal;
    } //fin de metodo BuscarNombreCanal usado  

    public ArrayList BuscarClienteRut(Connection con, String rut) {
        String sql = "Select * from cliente where CLI_RUT='" + rut + "'";
//        boolean encontrado = false;
        ArrayList datosClientes = new ArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                datosClientes.add(resultado.getString("CLI_NOMBRE"));
                datosClientes.add(resultado.getString("CLI_TELEFONO"));
                datosClientes.add(resultado.getString("CLI_CORREO"));
                datosClientes.add(resultado.getString("CAN_ID_CANAL"));
            }
        } catch (Exception e) {

        }
        return datosClientes;
    } //fin de metodo BuscarClienteRut      

    public int ObtenerSecuenciaCliente(Connection con) {
        int id = 0;
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dreamgifts' AND TABLE_NAME = 'cliente'";
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                id = resultado.getInt("AUTO_INCREMENT");
            }
        } catch (Exception e) {

        }
        return id;
    } // Cierre ObtenerNumeroCliente

    public int ObtenerIdCliente(Connection con, String rut) {
        String sql = "Select CLI_ID_CLIENTE from cliente where CLI_RUT='" + rut + "'";
        int id = 0;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            if (resultado.next()) {
                id = resultado.getInt("CLI_ID_CLIENTE");
            }
        } catch (Exception e) {
        }
        return id;
    } //fin de metodo BuscarBanco

}
