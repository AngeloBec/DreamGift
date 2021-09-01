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
public class Venta {

    private int idVenta;
    private int idClienteVenta;
    private int idPackVenta;
    private int idBancoVenta;
    private int idComunaVenta;
    private double TotalVenta;
    private String FechaVenta;
    private String FechaTransVenta;
    private String CodigoTranfeVenta;
    private String NombreDestinatario;
    private String ApellidoDestinatario;
    private String DireccionDestinatario;
    private String TelefonoDestinatario;
    private String FechaEntregaVenta;
    private String HoraEntregaInicio;
    private String HoraEntregaFinal;
    private String Saludo;
    private int EstadoVenta;

    public Venta() {
    }

    public Venta(int idVenta, int idClienteVenta, int idPackVenta, int idBancoVenta, double TotalVenta, String FechaVenta, String FechaTransVenta, String CodigoTranfeVenta, String NombreDestinatario, String ApellidoDestinatario, String DireccionDestinatario, int idComunaVenta, String TelefonoDestinatario, String FechaEntregaVenta, String HoraEntregaInicio, String HoraEntregaFinal, String Saludo, int EstadoVenta) {
        this.idVenta = idVenta;
        this.idClienteVenta = idClienteVenta;
        this.idPackVenta = idPackVenta;
        this.idBancoVenta = idBancoVenta;
        this.TotalVenta = TotalVenta;
        this.FechaVenta = FechaVenta;
        this.FechaTransVenta = FechaTransVenta;
        this.CodigoTranfeVenta = CodigoTranfeVenta;
        this.NombreDestinatario = NombreDestinatario;
        this.ApellidoDestinatario = ApellidoDestinatario;
        this.DireccionDestinatario = DireccionDestinatario;
        this.idComunaVenta = idComunaVenta;
        this.TelefonoDestinatario = TelefonoDestinatario;
        this.FechaEntregaVenta = FechaEntregaVenta;
        this.HoraEntregaInicio = HoraEntregaInicio;
        this.HoraEntregaFinal = HoraEntregaFinal;
        this.Saludo = Saludo;
        this.EstadoVenta = EstadoVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdClienteVenta() {
        return idClienteVenta;
    }

    public void setIdClienteVenta(int idClienteVenta) {
        this.idClienteVenta = idClienteVenta;
    }

    public int getIdPackVenta() {
        return idPackVenta;
    }

    public void setIdPackVenta(int idPackVenta) {
        this.idPackVenta = idPackVenta;
    }

    public int getIdBancoVenta() {
        return idBancoVenta;
    }

    public void setIdBancoVenta(int idBancoVenta) {
        this.idBancoVenta = idBancoVenta;
    }

    public double getTotalVenta() {
        return TotalVenta;
    }

    public void setTotalVenta(double TotalVenta) {
        this.TotalVenta = TotalVenta;
    }

    public String getFechaVenta() {
        return FechaVenta;
    }

    public void setFechaVenta(String FechaVenta) {
        this.FechaVenta = FechaVenta;
    }

    public String getFechaTransVenta() {
        return FechaTransVenta;
    }

    public void setFechaTransVenta(String FechaTransVenta) {
        this.FechaTransVenta = FechaTransVenta;
    }

    public String getCodigoTranfeVenta() {
        return CodigoTranfeVenta;
    }

    public void setCodigoTranfeVenta(String CodigoTranfeVenta) {
        this.CodigoTranfeVenta = CodigoTranfeVenta;
    }

    public String getNombreDestinatario() {
        return NombreDestinatario;
    }

    public void setNombreDestinatario(String NombreDestinatario) {
        this.NombreDestinatario = NombreDestinatario;
    }

    public String getApellidoDestinatario() {
        return ApellidoDestinatario;
    }

    public void setApellidoDestinatario(String ApellidoDestinatario) {
        this.ApellidoDestinatario = ApellidoDestinatario;
    }

    public String getDireccionDestinatario() {
        return DireccionDestinatario;
    }

    public void setDireccionDestinatario(String DireccionDestinatario) {
        this.DireccionDestinatario = DireccionDestinatario;
    }

    public int getidComunaVenta() {
        return idComunaVenta;
    }

    public void setidComunaVenta(int idComunaVenta) {
        this.idComunaVenta = idComunaVenta;
    }

    public String getTelefonoDestinatario() {
        return TelefonoDestinatario;
    }

    public void setTelefonoDestinatario(String TelefonoDestinatario) {
        this.TelefonoDestinatario = TelefonoDestinatario;
    }

    public String getFechaEntregaVenta() {
        return FechaEntregaVenta;
    }

    public void setFechaEntregaVenta(String FechaEntregaVenta) {
        this.FechaEntregaVenta = FechaEntregaVenta;
    }

    public String getHoraEntregaInicio() {
        return HoraEntregaInicio;
    }

    public void setHoraEntregaInicio(String HoraEntregaInicio) {
        this.HoraEntregaInicio = HoraEntregaInicio;
    }

    public String getHoraEntregaFinal() {
        return HoraEntregaFinal;
    }

    public void setHoraEntregaFinal(String HoraEntregaFinal) {
        this.HoraEntregaFinal = HoraEntregaFinal;
    }

    public String getSaludo() {
        return Saludo;
    }

    public void setSaludo(String Saludo) {
        this.Saludo = Saludo;
    }

    public int getEstadoVenta() {
        return EstadoVenta;
    }

    public void setEstadoVenta(int EstadoVenta) {
        this.EstadoVenta = EstadoVenta;
    }

}
