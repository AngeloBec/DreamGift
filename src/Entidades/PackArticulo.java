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
public class PackArticulo {
    
    private int identificadorPackArticulo;
    private int identificadorArticulo;
    private int cantidadArticulo;

    public PackArticulo() {
    }

    public PackArticulo(int identificadorPackArticulo, int identificadorArticulo, int cantidadArticulo) {
        this.identificadorPackArticulo = identificadorPackArticulo;
        this.identificadorArticulo = identificadorArticulo;
        this.cantidadArticulo = cantidadArticulo;
    }

    public int getIdentificadorPackArticulo() {
        return identificadorPackArticulo;
    }

    public void setIdentificadorPackArticulo(int identificadorPackArticulo) {
        this.identificadorPackArticulo = identificadorPackArticulo;
    }

    public int getIdentificadorArticulo() {
        return identificadorArticulo;
    }

    public void setIdentificadorArticulo(int identificadorArticulo) {
        this.identificadorArticulo = identificadorArticulo;
    }

    public int getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(int cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    
}
