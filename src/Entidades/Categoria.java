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
public class Categoria {
    private int identificadorCategoria;
    private String nombreCategoria;
    private int estadoCategoria;
    private String literalCategoria;

    public Categoria() {
    }

    public Categoria(int identificadorCategoria, String nombreCategoria, int estadoCategoria, String literalCategoria) {
        this.identificadorCategoria = identificadorCategoria;
        this.nombreCategoria = nombreCategoria;
        this.estadoCategoria = estadoCategoria;
        this.literalCategoria = literalCategoria;
    }

    public int getIdentificadorCategoria() {
        return identificadorCategoria;
    }

    public void setIdentificadorCategoria(int identificadorCategoria) {
        this.identificadorCategoria = identificadorCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getEstadoCategoria() {
        return estadoCategoria;
    }

    public void setEstadoCategoria(int estadoCategoria) {
        this.estadoCategoria = estadoCategoria;
    }

    public String getLiteralCategoria() {
        return literalCategoria;
    }

    public void setLiteralCategoria(String literalCategoria) {
        this.literalCategoria = literalCategoria;
    }
    
}
