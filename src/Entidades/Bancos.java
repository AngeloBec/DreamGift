package Entidades;

/**
 *
 * @author Alinson Lopez
 */
public class Bancos {
    int identificadorBanco;
    String NombreBanco;
    int estadoBanco;    

    public Bancos() {
    }

    public Bancos(int identificadorBanco, String NombreBanco, int estadoBanco) {
        this.identificadorBanco = identificadorBanco;
        this.NombreBanco = NombreBanco;
        this.estadoBanco = estadoBanco;
    }

    public int getIdentificadorBanco() {
        return identificadorBanco;
    }

    public void setIdentificadorBanco(int identificadorBanco) {
        this.identificadorBanco = identificadorBanco;
    }

    public String getNombreBanco() {
        return NombreBanco;
    }

    public void setNombreBanco(String NombreBanco) {
        this.NombreBanco = NombreBanco;
    }

    public int getEstadoBanco() {
        return estadoBanco;
    }

    public void setEstadoBanco(int estadoBanco) {
        this.estadoBanco = estadoBanco;
    }
    
}
