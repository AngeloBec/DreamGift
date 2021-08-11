package PruebasUnitarias;

import Entidades.Bancos;
import Entidades.Usuario;
import Negocio.NegocioBancos;
import Negocio.NegocioUsuario;

/**
 *
 * @author usuario
 */
public class PruebasUsuarios {
    NegocioUsuario UsuarioNeg = new NegocioUsuario();
    NegocioBancos BancoNeg = new NegocioBancos();
    Usuario User = new Usuario();
    Bancos Bank = new Bancos();
    boolean exito;
    public void insertar(){
        User.setNombre("Rafael");
        User.setApellido("Acuña");
        User.setUserName("raacuña");
        User.setClave("1234");
        User.setEstado(1);
        exito = UsuarioNeg.GuardarUsuario(User);
        if (exito){
           System.out.println("inserto correctamente"); 
        }
        else {
            System.out.println("Fallo la insercion"); 
        }
    }
    public void modificar(){
        User.setNombre("Williams");
        User.setApellido("Romero");
        User.setUserName("wiramoss");
        User.setClave("1238");
        User.setEstado(1);
        User.setIdentificador(1);
        exito = UsuarioNeg.ModificarUsuario(User);
        if (exito){
           System.out.println("Modifico correctamente"); 
        }
        else {
            System.out.println("Fallo"); 
        }
    }
    public void desactivar(){
        User.setEstado(0);
        User.setIdentificador(1);
        exito = UsuarioNeg.DesactivarUsuario(User);
        if (exito){
           System.out.println("Desactivo correctamente"); 
        }
        else {
            System.out.println("Fallo"); 
        }
    }
    public void buscar(){
        String busqueda = "raacuña";
        exito = UsuarioNeg.BuscarUsuario(busqueda);
        if (exito){
           System.out.println("Lo encontre"); 
        }
        else {
            System.out.println("No lo encontre"); 
        }        
    }
    
    public void buscarEstado(){
        Bank.setEstadoBanco(0);
        Bank.setIdentificadorBanco(2);
        exito = BancoNeg.BuscarBancoEstado(Bank);
        if (exito){
           System.out.println("Lo encontre"); 
        }
        else {
            System.out.println("No lo encontre"); 
        }        
    }
    public static void main(String[] args) {
        PruebasUsuarios Pruebas = new PruebasUsuarios();
        //Pruebas.insertar();    
        //Pruebas.modificar();
        //Pruebas.desactivar();
        Pruebas.buscarEstado();
    }
}
    

