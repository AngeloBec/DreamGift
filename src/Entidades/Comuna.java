/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Angelo Becerra
 */
public class Comuna {
    
    
    private int identificadorComuna;
    private String NombreComuna;
    private int EstadoComuna;

    public Comuna() {
    }

    public Comuna(int identificadorComuna, String NombreComuna, int EstadoComuna) {
        this.identificadorComuna = identificadorComuna;
        this.NombreComuna = NombreComuna;
        this.EstadoComuna = EstadoComuna;
    }

    public String getNombreComuna() {
        return NombreComuna;
    }

    public void setNombreComuna(String NombreComuna) {
        this.NombreComuna = NombreComuna;
    }

    public int getidentificadorComuna() {
        return identificadorComuna;
    }

    public void setidentificadorComuna(int identificadorComuna) {
        this.identificadorComuna = identificadorComuna;
    }

    public int getEstadoComuna() {
        return EstadoComuna;
    }

    public void setEstadoComuna(int EstadoComuna) {
        this.EstadoComuna = EstadoComuna;
    }
    
    
    
    
    
}