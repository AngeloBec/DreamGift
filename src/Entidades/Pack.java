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
public class Pack {
    
    private int identificadorPack;
    private String nombrePack;
    private int precioPack;
    private int stockPack;
    private int estadoPack;

    public Pack() {
    }

    public Pack(int identificadorPack, String nombrePack, int precioPack, int stockPack, int estadoPack) {
        this.identificadorPack = identificadorPack;
        this.nombrePack = nombrePack;
        this.precioPack = precioPack;
        this.stockPack = stockPack;
        this.estadoPack = estadoPack;
    }

    public int getIdentificadorPack() {
        return identificadorPack;
    }

    public void setIdentificadorPack(int identificadorPack) {
        this.identificadorPack = identificadorPack;
    }

    public String getNombrePack() {
        return nombrePack;
    }

    public void setNombrePack(String nombrePack) {
        this.nombrePack = nombrePack;
    }

    public int getPrecioPack() {
        return precioPack;
    }

    public void setPrecioPack(int precioPack) {
        this.precioPack = precioPack;
    }

    public int getStockPack() {
        return stockPack;
    }

    public void setStockPack(int stockPack) {
        this.stockPack = stockPack;
    }

    public int getEstadoPack() {
        return estadoPack;
    }

    public void setEstadoPack(int estadoPack) {
        this.estadoPack = estadoPack;
    }
    
    
    
}
