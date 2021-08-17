/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author William Ramos
 */
public class Articulos {
    private int identificadorArticulo;
    private String nombreArticulo;
    private String codigoLetraArticulo;
    private int stockArticulo;
    private String marcaArticulo;
    private String fechaVencimientoArticulo;
    private int estadoArticulo;
    private int idCategoriaArticulo;
    private int idProveedorArticulo;

    public Articulos() {
    }

    public Articulos(int identificadorArticulo, String nombreArticulo, String codigoLetraArticulo, int stockArticulo, String marcaArticulo, String fechaVencimientoArticulo, int estadoArticulo, int idCategoriaArticulo, int idProveedorArticulo) {
        this.identificadorArticulo = identificadorArticulo;
        this.nombreArticulo = nombreArticulo;
        this.codigoLetraArticulo = codigoLetraArticulo;
        this.stockArticulo = stockArticulo;
        this.marcaArticulo = marcaArticulo;
        this.fechaVencimientoArticulo = fechaVencimientoArticulo;
        this.estadoArticulo = estadoArticulo;
        this.idCategoriaArticulo = idCategoriaArticulo;
        this.idProveedorArticulo = idProveedorArticulo;
    }

    public int getIdentificadorArticulo() {
        return identificadorArticulo;
    }

    public void setIdentificadorArticulo(int identificadorArticulo) {
        this.identificadorArticulo = identificadorArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getCodigoLetraArticulo() {
        return codigoLetraArticulo;
    }

    public void setCodigoLetraArticulo(String codigoLetraArticulo) {
        this.codigoLetraArticulo = codigoLetraArticulo;
    }

    public int getStockArticulo() {
        return stockArticulo;
    }

    public void setStockArticulo(int stockArticulo) {
        this.stockArticulo = stockArticulo;
    }

    public String getMarcaArticulo() {
        return marcaArticulo;
    }

    public void setMarcaArticulo(String marcaArticulo) {
        this.marcaArticulo = marcaArticulo;
    }

    public String getFechaVencimientoArticulo() {
        return fechaVencimientoArticulo;
    }

    public void setFechaVencimientoArticulo(String fechaVencimientoArticulo) {
        this.fechaVencimientoArticulo = fechaVencimientoArticulo;
    }

    public int getEstadoArticulo() {
        return estadoArticulo;
    }

    public void setEstadoArticulo(int estadoArticulo) {
        this.estadoArticulo = estadoArticulo;
    }

    public int getIdCategoriaArticulo() {
        return idCategoriaArticulo;
    }

    public void setIdCategoriaArticulo(int idCategoriaArticulo) {
        this.idCategoriaArticulo = idCategoriaArticulo;
    }

    public int getIdProveedorArticulo() {
        return idProveedorArticulo;
    }

    public void setIdProveedorArticulo(int idProveedorArticulo) {
        this.idProveedorArticulo = idProveedorArticulo;
    }
    
    
}
