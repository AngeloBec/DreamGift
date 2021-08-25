package Entidades;

/**
 *
 * @author Alinson Lopez
 */
public class Clientes {
    int identificadorCliente;
    String RutCliente;
    String NombreCliente;
    String ApellidoCliente;
    String DireccionCliente;
    String telefonoCliente;
    String CorreoCliente;
    String FechaNacimientoCliente;
    int CanalCliente;
    int estadoCliente;    

 public Clientes() {    }    
 
    public Clientes(int identificadorCliente, String RutCliente, String NombreCliente, String ApellidoCliente, String DireccionCliente, String telefonoCliente, String CorreoCliente, String FechaNacimientoCliente, int CanalCliente, int estadoCliente) {
        this.identificadorCliente = identificadorCliente;
        this.RutCliente = RutCliente;
        this.NombreCliente = NombreCliente;
        this.ApellidoCliente = ApellidoCliente;
        this.DireccionCliente = DireccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.CorreoCliente = CorreoCliente;
        this.FechaNacimientoCliente = FechaNacimientoCliente;
        this.CanalCliente = CanalCliente;
        this.estadoCliente = estadoCliente;
    }
    

    public int getIdentificadorCliente() {
        return identificadorCliente;
    }

    public void setIdentificadorCliente(int identificadorCliente) {
        this.identificadorCliente = identificadorCliente;
    }

    public String getRutCliente() {
        return RutCliente;
    }

    public void setRutCliente(String RutCliente) {
        this.RutCliente = RutCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getApellidoCliente() {
        return ApellidoCliente;
    }

    public void setApellidoCliente(String ApellidoCliente) {
        this.ApellidoCliente = ApellidoCliente;
    }

    public String getDireccionCliente() {
        return DireccionCliente;
    }

    public void setDireccionCliente(String DireccionCliente) {
        this.DireccionCliente = DireccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoCliente() {
        return CorreoCliente;
    }

    public void setCorreoCliente(String CorreoCliente) {
        this.CorreoCliente = CorreoCliente;
    }

    public String getFechaNacimientoCliente() {
        return FechaNacimientoCliente;
    }

    public void setFechaNacimientoCliente(String FechaNacimientoCliente) {
        this.FechaNacimientoCliente = FechaNacimientoCliente;
    }

    public int getCanalCliente() {
        return CanalCliente;
    }

    public void setCanalCliente(int CanalCliente) {
        this.CanalCliente = CanalCliente;
    }

    public int getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(int estadoCliente) {
        this.estadoCliente = estadoCliente;
    }
}
