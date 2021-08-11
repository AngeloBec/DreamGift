package AccesoDatos;

import Entidades.Usuario;
import conexion.Conexion;
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
 * @author William Ramos
 */
public class AccesoDatosUsuarios {

    /*GuardarUsuario(): Metodo que se conecta con la BD de dreamgifts mediante
    la clase conexion, para luego ejecutar un sentencia SQL de INSERCIÓN para almacenar
    la informacion suministrada por el usuario mediante el formulario.
    Para la inserción de datos se asume que todo usuario nuevo se ingresa activo
    Maneja Caso de Excepción por si falla la conexio hacia la BD
     */
    public boolean GuardarUsuario(Connection con, Usuario users) {
        String sql = "Insert into usuarios(USU_NOMBRE, USU_APELLIDO,USU_USERNAME,USU_CLAVE,USU_ESTADO)values(?, ?, ?, ?, ?)";
        PreparedStatement query = null;
        boolean ingreso = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, users.getNombre());
            query.setString(2, users.getApellido());
            query.setString(3, users.getUserName());
            query.setString(4, users.getClave());
            query.setInt(5, users.getEstado());
            query.execute();
            query.close();
            ingreso = true;

        } catch (Exception e) {
        }
        return ingreso;
    } //fin de metodo GuardarUsuario

    /*ModificarUsuario(): Este metodo se conecta con la BD de dreamgifts mediante
    la clase conexion, para luego ejecutar un sentencia SQL de actualización de información
    existente en la BD mediante el identificador del usuario
    Para el caso del estado seleccionado mediante ComboBox:"ACTIVO" O "INACTIVO",
    se valida lo que obtenemos del formulario e insertamos en la Base de Datos 0 o 1
    Maneja Caso de Excepción por si falla la conexio hacia la BD
     */
    public boolean ModificarUsuario(Connection con, Usuario users) {
        String sql = "UPDATE usuarios SET USU_NOMBRE=?,USU_APELLIDO=?,USU_USERNAME=?,USU_CLAVE=?,USU_ESTADO=? WHERE USU_ID_USUARIO=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setString(1, users.getNombre());
            query.setString(2, users.getApellido());
            query.setString(3, users.getUserName());
            query.setString(4, users.getClave());
            query.setInt(5, users.getEstado());
            query.setInt(6, users.getIdentificador());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
        }
        return modificar;
    } //fin de metodo ModificarUsuario

    public boolean DesactivarUsuario(Connection con, Usuario users) {
        String sql = "UPDATE usuarios SET USU_ESTADO = ? WHERE USU_ID_USUARIO=?";
        PreparedStatement query = null;
        boolean desactivar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, users.getEstado());
            query.setInt(2, users.getIdentificador());
            query.execute();
            query.close();
            desactivar = true;
        } catch (Exception e) {

        }
        return desactivar;
    } //fin de metodo DesactivarUsuario

    public boolean BuscarUsuario(Connection con, String buscar) {
        String sql = "Select * from usuarios where USU_USERNAME='"+buscar+"'";
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
    } //fin de metodo BuscarUsuario

    public void ListarUsuario(Connection con, JTable TablaUsuario) {
        String sql = "select * from usuarios order by USU_ID_USUARIO";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaUsuario.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] usuarios = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    usuarios[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("USU_ESTADO") == 1) {
                        usuarios[5] = "Activo";
                    } else {
                        usuarios[5] = "Inactivo";
                    }
                }
                modelo.addRow(usuarios);
            }
            TablaUsuario.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } //fin de metodo ListarUsuario

    /*FiltrarUsuario(): Este metodo se conecta con la BD de dreamgifts mediante
    la clase conexion, para luego ejecutar un sentencia SQL de consulta de información
    existente en la BD mediante la sentencua like que permite traer datos coincidentes,
    segun un dato de busqueda ingresado
    Solo busca por Nombre, apellido, estado, usuario
    Maneja Caso de Excepción por si falla la conexio hacia la BD
     */
    public void FiltrarUsuario(Connection con, JTable TablaUsuario, String buscar) {
        String sql = "select * from usuarios where USU_ID_USUARIO like'%" + buscar + "%' "
                + "   or USU_NOMBRE like'%" + buscar + "%' or USU_APELLIDO like'%" + buscar + "%'"
                + "   or USU_USERNAME like'%" + buscar + "%' or USU_ESTADO like'%" + buscar + "%'";
        DefaultTableModel modelo;
        try {
            Statement st = con.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            modelo = (DefaultTableModel) TablaUsuario.getModel();
            ResultSetMetaData rsMd = resultado.getMetaData();
            int CantidadColumnas = rsMd.getColumnCount();
            Object[] usuarios = new Object[CantidadColumnas];
            while (resultado.next()) {
                for (int i = 0; i < CantidadColumnas; i++) {
                    usuarios[i] = resultado.getObject(i + 1);
                    if (resultado.getByte("USU_ESTADO") == 1) {
                        usuarios[5] = "Activo";
                    } else {
                        usuarios[5] = "Inactivo";
                    }
                }
                modelo.addRow(usuarios);
            }
            TablaUsuario.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
        }
    } // Cierre Filtrar usuario
}
