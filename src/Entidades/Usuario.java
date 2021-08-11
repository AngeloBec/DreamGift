package Entidades;


/**
 *
 * @author William Ramos
 */
public class Usuario {
    private int Identificador;
    private String Nombre;
    private String Apellido;
    private String UserName;
    private String Clave;
    private int Estado;

    public Usuario() {
    }

    public Usuario(int Identificador, String Nombre, String Apellido, String UserName, String Clave, int Estado) {
        this.Identificador = Identificador;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.UserName = UserName;
        this.Clave = Clave;
        this.Estado = Estado;
    }

    public int getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(int Identificador) {
        this.Identificador = Identificador;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }
}
