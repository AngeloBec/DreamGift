/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author usuario
 */
public class EstadoVenta {
    private int identificadorEstadoVenta;
    private String nombreEstadoVenta;
    private int estado;

    public EstadoVenta() {
    }

    public EstadoVenta(int identificadorEstadoVenta, String nombreEstadoVenta, int estado) {
        this.identificadorEstadoVenta = identificadorEstadoVenta;
        this.nombreEstadoVenta = nombreEstadoVenta;
        this.estado = estado;
    }

    public int getIdentificadorEstadoVenta() {
        return identificadorEstadoVenta;
    }

    public void setIdentificadorEstadoVenta(int identificadorEstadoVenta) {
        this.identificadorEstadoVenta = identificadorEstadoVenta;
    }

    public String getNombreEstadoVenta() {
        return nombreEstadoVenta;
    }

    public void setNombreEstadoVenta(String nombreEstadoVenta) {
        this.nombreEstadoVenta = nombreEstadoVenta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
}
