package Entidades;

/**
 *
 * @author Angelica Valdivia
 */
public class Canal {
    
    private int identificadorCanal;
    private String nombreCanal;
    private int estadoCanal;
    
    //contructor de sin parametro

    public Canal() {
    }
    
    //contructor con parametros

    public Canal(int identificadorCanal, String nombreCanal, int estadoCanal) {
        this.identificadorCanal = identificadorCanal;
        this.nombreCanal = nombreCanal;
        this.estadoCanal = estadoCanal;
    }//fin de contructores

    //inicio de metodos get and set



    public int getIdentificadorCanal() {
        return identificadorCanal;
    }

    public void setIdentificadorCanal(int identificadorCanal) {
        this.identificadorCanal = identificadorCanal;
    }

    public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }

    public int getEstadoCanal() {
        return estadoCanal;
    }

    public void setEstadoCanal(int estadoCanal) {
        this.estadoCanal = estadoCanal;
    }
   
}