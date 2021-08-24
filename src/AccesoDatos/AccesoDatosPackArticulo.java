/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import Entidades.PackArticulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author usuario
 */
public class AccesoDatosPackArticulo {

    PackArticulo ClasePackArticulo = new PackArticulo();
    int idArticulo;
    int cantidad;
    boolean ingreso = false;

    public boolean GuardarPackArticulo(Connection con, JTable TablaPackConArticulos, int identificadorPack) {
        int registros = TablaPackConArticulos.getRowCount();
        String sql = "Insert pack_has_articulo (PCK_ID_PACK,ART_ID_ARTICULO,CANTIDAD)values(?, ?, ?)";
        PreparedStatement query = null;
        ClasePackArticulo.setIdentificadorPackArticulo(identificadorPack);
        for (int i = 0; i < registros; i++) {
            ClasePackArticulo.setIdentificadorArticulo(Integer.parseInt(TablaPackConArticulos.getValueAt(i, 0).toString()));
            ClasePackArticulo.setCantidadArticulo(Integer.parseInt(TablaPackConArticulos.getValueAt(i, 2).toString()));
            try {
                query = con.prepareStatement(sql);
                query.setInt(1, ClasePackArticulo.getIdentificadorPackArticulo());
                query.setInt(2, ClasePackArticulo.getIdentificadorArticulo());
                query.setInt(3, ClasePackArticulo.getCantidadArticulo());
                query.execute();
                query.close();
                ingreso = true;
            } catch (Exception e) {
            }
        }
        return ingreso;
    } //fin de metodo GuardarPackArticulo

    public boolean BuscarPackArticulo(Connection con, PackArticulo ClasePackArticulo) {
        String sql = "Select * from pack_has_articulo where PCK_ID_PACK=" + ClasePackArticulo.getIdentificadorPackArticulo() + " and ART_ID_ARTICULO=" + ClasePackArticulo.getIdentificadorArticulo() + "";
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

    /*

     */
    public boolean ModificarPackArticulo(Connection con, JTable TablaPackConArticulos, int identificadorPack) {
        int registros = TablaPackConArticulos.getRowCount();
        String sqlUpdate = "UPDATE pack_has_articulo SET CANTIDAD=? WHERE PCK_ID_PACK=? AND ART_ID_ARTICULO=?";
        String sqlInsert = "Insert pack_has_articulo (PCK_ID_PACK,ART_ID_ARTICULO,CANTIDAD)values(?, ?, ?)";
        PreparedStatement query = null;
        ClasePackArticulo.setIdentificadorPackArticulo(identificadorPack);
        boolean modificar = false;
        for (int i = 0; i < registros; i++) {
            ClasePackArticulo.setIdentificadorArticulo(Integer.parseInt(TablaPackConArticulos.getValueAt(i, 0).toString()));
            ClasePackArticulo.setCantidadArticulo(Integer.parseInt(TablaPackConArticulos.getValueAt(i, 2).toString()));
            if (BuscarPackArticulo(con, ClasePackArticulo)) {
                try {
                    query = con.prepareStatement(sqlUpdate);
                    query.setInt(1, ClasePackArticulo.getCantidadArticulo());
                    query.setInt(2, ClasePackArticulo.getIdentificadorPackArticulo());
                    query.setInt(3, ClasePackArticulo.getIdentificadorArticulo());
                    query.execute();
                    query.close();
                    modificar = true;
                } catch (Exception e) {
                }
            } else {
                try {
                    query = con.prepareStatement(sqlInsert);
                    query.setInt(1, ClasePackArticulo.getIdentificadorPackArticulo());
                    query.setInt(2, ClasePackArticulo.getIdentificadorArticulo());
                    query.setInt(3, ClasePackArticulo.getCantidadArticulo());
                    query.execute();
                    query.close();
                    modificar = true;
                } catch (Exception e) {
                }
            }

        }
        return modificar;
    } //fin de metodo ModificarPackArticulo

    public boolean EliminarPackArticulo(Connection con, PackArticulo ClasePackArticulo) {
        String sql = "DELETE FROM pack_has_articulo WHERE PCK_ID_PACK=? AND ART_ID_ARTICULO=?";
        PreparedStatement query = null;
        boolean modificar = false;
        try {
            query = con.prepareStatement(sql);
            query.setInt(1, ClasePackArticulo.getIdentificadorPackArticulo());
            query.setInt(2, ClasePackArticulo.getIdentificadorArticulo());
            //query.setInt(3, ClasePackArticulo.getCantidadArticulo());
            query.execute();
            query.close();
            modificar = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
        return modificar;
    } //fin de metodo EliminarPackArticulo
}
