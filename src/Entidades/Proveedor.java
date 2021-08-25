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
public class Proveedor {

    private int IdentificadorProveedor;
    private String NombreProveedor;
    private String RazonSocialProveedor;
    private String DireccionProveedor;
    private String Rut;
    private String Dv;
    private String TelefonoProveedor;
    private String Correo;
    private int Estado;

    public Proveedor() {
    }

    public Proveedor(int IdentificadorProveedor, String NombreProveedor, String RazonSocialProveedor, String DireccionProveedor, String rut, String Dv, String TelefonoProveedor, String Correo, int Estado) {
        this.IdentificadorProveedor = IdentificadorProveedor;
        this.NombreProveedor = NombreProveedor;
        this.RazonSocialProveedor = RazonSocialProveedor;
        this.DireccionProveedor = DireccionProveedor;
        this.Rut = rut;
        this.Dv = Dv;
        this.TelefonoProveedor = TelefonoProveedor;
        this.Correo = Correo;
        this.Estado = Estado;
    }

    public int getIdentificadorProveedor() {
        return IdentificadorProveedor;
    }

    public void setIdentificadorProveedor(int IdentificadorProveedor) {
        this.IdentificadorProveedor = IdentificadorProveedor;
    }

    public String getNombreProveedor() {
        return NombreProveedor;
    }

    public void setNombreProveedor(String NombreProveedor) {
        this.NombreProveedor = NombreProveedor;
    }

    public String getRazonSocialProveedor() {
        return RazonSocialProveedor;
    }

    public void setRazonSocialProveedor(String RazonSocialProveedor) {
        this.RazonSocialProveedor = RazonSocialProveedor;
    }

    public String getDireccionProveedor() {
        return DireccionProveedor;
    }

    public void setDireccionProveedor(String DireccionProveedor) {
        this.DireccionProveedor = DireccionProveedor;
    }

    public String getRut() {
        return Rut;
    }

    public void setRut(String rut) {
        this.Rut = rut;
    }

    public String getDv() {
        return Dv;
    }

    public void setDv(String Dv) {
        this.Dv = Dv;
    }

    public String getTelefonoProveedor() {
        return TelefonoProveedor;
    }

    public void setTelefonoProveedor(String TelefonoProveedor) {
        this.TelefonoProveedor = TelefonoProveedor;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }

}
