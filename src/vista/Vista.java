package vista;

import Entidades.Articulos;
import Entidades.Bancos;
import Entidades.Canal;
import Entidades.Categoria;
import Entidades.Comuna;
import Entidades.Usuario;
import Negocio.NegocioArticulo;
import Negocio.NegocioBancos;
import Negocio.NegocioCanales;
import Negocio.NegocioCategoria;
import Negocio.NegocioComuna;
import Negocio.NegocioProveedor;
import Negocio.NegocioUsuario;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class Vista extends javax.swing.JFrame {
    private NegocioUsuario UserNegocio = new NegocioUsuario();
    private NegocioCanales MediosNegocio = new NegocioCanales();
    private NegocioComuna ComunasNegocio = new NegocioComuna();
    private NegocioBancos BancoNegocio = new NegocioBancos();
    private NegocioCategoria CategoriaNegocio = new NegocioCategoria();
    private NegocioProveedor ProveedorNegocio = new NegocioProveedor();
    private NegocioArticulo ArticulosNegocio = new NegocioArticulo();
   
    public Vista() {
        initComponents();
        this.setLocationRelativeTo(null); 
        ListarUsuariosVista();
        ListarCanalesVista();
        ListarComunasVista();
        ListarBancoVista();
        ListarCategoriaVista();
        ListarArticulosVista();
    }
    public void ListarUsuariosVista(){
        UserNegocio.ListarUsuario(TablaUsuarios);
    }
    public void ListarCanalesVista(){
        MediosNegocio.ListarCanal(TablaCanal);
    } 
    public void ListarComunasVista(){
        ComunasNegocio.ListarComuna(TablaComuna);
    }  

    public void ListarBancoVista(){
        BancoNegocio.ListarBanco(TablaBanco);
    } 
    
    public void ListarCategoriaVista(){
        CategoriaNegocio.ListarCategoria(TablaCategoria);
        CategoriaNegocio.CargarCategoria(ComboBoxCategoriaArticulo);
        ProveedorNegocio.CargarProveedor(ComboBoxProveedorArticulo);
        
    }
    
    public void ListarArticulosVista(){
        ArticulosNegocio.ListarArticulos(TablaArticulos);
    } 
    /*LimpiarFormularioUsuario(): Este metodo limpia o deja en en blanco
    todos los campos que contiene el formulario de Usuarios
    */     
    public void LimpiarFormularioUsuario(){
        txtIdUsuario.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCuentaUsuario.setText("");
        PasswordUsuario.setText("");
        ComboBoxEstado.setSelectedItem("Activo");
        ComboBoxEstado.setEnabled(false);
        btnGuardarUsuario.setEnabled(true);
        btnEditarUsuario.setEnabled(false);
        btnDesactivarUsuario.setEnabled(false);
    }
    public void LimpiarFormularioCanal(){
        txtCodigoCanal.setText("");
        txtNombreCanal.setText("");
        ComboBoxEstadoCanal.setSelectedItem("Activo");
        ComboBoxEstadoCanal.setEnabled(false);
        btnGuardarCanal.setEnabled(true);
        btnEditarCanal.setEnabled(false);
        btnDesactivarCanal.setEnabled(false);
    }
    public void LimpiarFormularioComuna(){
        txtCodigoComuna.setText("");
        txtNombreComuna.setText("");
        ComboBoxEstadoComuna.setSelectedItem("Activo");
        ComboBoxEstadoComuna.setEnabled(false);
        btnGuardarComuna.setEnabled(true);
        btnEditarComuna.setEnabled(false);
        btnDesactivarComuna.setEnabled(false);
    }

    public void LimpiarFormularioBanco(){
        txtCodigoBanco.setText("");
        txtNombreBanco.setText("");
        ComboBoxEstadoBanco.setSelectedItem("Activo");
        ComboBoxEstadoBanco.setEnabled(false);
        btnGuardarBanco.setEnabled(true);
        btnEditarBanco.setEnabled(false);
        btnDesactivarBanco.setEnabled(false);
    }    
    
    public void LimpiarFormularioCategoria(){
        txtCodigoCategoria.setText("");
        txtNombreCategoria.setText("");
        ComboBoxEstadoCategoria.setSelectedItem("Activo");
        ComboBoxEstadoCategoria.setEnabled(false);
        btnGuardarCategoria.setEnabled(true);
        btnEditarCategoria.setEnabled(false);
        btnDesactivarCategoria.setEnabled(false);
    }  
    
    public void LimpiarFormularioArticulos(){
        txtNombreArticulo.setText("");
        txtUnidadArticulo.setText("");
        txtMarcaArticulo.setText("");
        txtCodigoArticulo.setText("");
        ComboBoxCategoriaArticulo.setSelectedItem("Seleccione Categoria");
        DateFVArticulo.setDateFormatString("");
        ComboBoxProveedorArticulo.setSelectedItem("Seleccione Proveedor");
        ComboBoxEstadoArticulo.setSelectedItem("Activo");
        ComboBoxEstadoArticulo.setEnabled(false);
        btnGuardarArticulos.setEnabled(true);
        btnEditarArticulos.setEnabled(false);
        btnDesactivarArticulos.setEnabled(false);
    }     
    /*VerificarCuentaUsuario: Esta Función se conecta con la BD de dreamgifts mediante
    la clase conexion, para luego ejecutar un sentencia SQL de consulta para validar
    que la cuenta de usuario generada si existe o no en la BD, retornando verdadero
    o falso segun sea el caso
    Maneja Caso de Excepción por si falla la conexio hacia la BD
    */     
    public boolean VerificarCuentaUsuario(String Buscar)
    {   boolean busqueda = false;
        if(UserNegocio.BuscarUsuario(Buscar)){
            busqueda = true;
            return busqueda;
        }
        else 
            return busqueda;
            
    }
    /*FormarCuentaUsuaria(): Este funcion genera la cuenta usuario de cada persona,
      con la union de las dos primeras letras del nombre y su apellido.
      En conjunto con otra funcion permiten generar una cuenta de usuario unica
    */ 

    
    
    public String FormarCodigoArticulo(int idCategoria, String NombreCategoria)
    {
        String codigoCategoria = CategoriaNegocio.BuscarCodigoCategoria(idCategoria);
        int idArticulo = ArticulosNegocio.CantidadArticulos() + 1;
        String codigoArticulo = codigoCategoria + idArticulo;
        return codigoArticulo;
    }
    
    public String FormarCuentaUsuaria(String Nombre, String Apellido)
    {
        int i = 0;
        String ParteUno = Nombre.substring(0,2);
        String ParteDos = Apellido;
        txtCuentaUsuario.setText(ParteUno.toLowerCase() + ParteDos.toLowerCase());
        String Buscar = txtCuentaUsuario.getText();
        //System.out.println("Nombre de Usuario"+ VerificarCuentaUsuario(Buscar));
        while (VerificarCuentaUsuario(Buscar))
            {
               i++;
               Buscar = txtCuentaUsuario.getText() + i;
            }
        return Buscar;
    }
    /*LimpiarTablaUsuario(): Este metodo elimina la informacion que se encuentra
    en la tabla de la pantalla de usuarios
    */     
    public void LimpiarTablaUsuario() {
        DefaultTableModel model1 = (DefaultTableModel) TablaUsuarios.getModel();
        while (TablaUsuarios.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }

    public void LimpiarTablaCanal() {
        DefaultTableModel model1 = (DefaultTableModel) TablaCanal.getModel();
        while (TablaCanal.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }   
    
    public void LimpiarTablaComuna() {
        DefaultTableModel model1 = (DefaultTableModel) TablaComuna.getModel();
        while (TablaComuna.getRowCount() > 0) {
            model1.removeRow(0);
        }
    } 

    public void LimpiarTablaBanco() {
        DefaultTableModel model1 = (DefaultTableModel) TablaBanco.getModel();
        while (TablaBanco.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }    
    
    public void LimpiarTablaCategoria() {
        DefaultTableModel model1 = (DefaultTableModel) TablaCategoria.getModel();
        while (TablaCategoria.getRowCount() > 0) {
            model1.removeRow(0);
        }
    } 
    
    public void LimpiarTablaArticulos() {
        DefaultTableModel model1 = (DefaultTableModel) TablaArticulos.getModel();
        while (TablaArticulos.getRowCount() > 0) {
            model1.removeRow(0);
        }
    } 
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jFrame1 = new javax.swing.JFrame();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu13 = new javax.swing.JMenu();
        jMenu14 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu15 = new javax.swing.JMenu();
        jMenu16 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jFrame4 = new javax.swing.JFrame();
        jPopupMenu4 = new javax.swing.JPopupMenu();
        jMenuBar5 = new javax.swing.JMenuBar();
        jMenu17 = new javax.swing.JMenu();
        jMenu18 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPopupMenu5 = new javax.swing.JPopupMenu();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jPopupMenu6 = new javax.swing.JPopupMenu();
        jPopupMenu7 = new javax.swing.JPopupMenu();
        jPopupMenu8 = new javax.swing.JPopupMenu();
        jFrame5 = new javax.swing.JFrame();
        jFrame6 = new javax.swing.JFrame();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Titulo_Prov = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Provedores = new javax.swing.JTable();
        jTextField11 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        dreamGifts = new javax.swing.JLabel();
        buscar = new javax.swing.JButton();
        notificaciones = new javax.swing.JButton();
        configuracion = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        ventas = new javax.swing.JTabbedPane();
        jPanel20 = new javax.swing.JPanel();
        Titulo_Prov10 = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        jLabel50 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextField50 = new javax.swing.JTextField();
        jButton58 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        buscar1 = new javax.swing.JButton();
        faq = new javax.swing.JButton();
        Titulo_Prov11 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jTextField53 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jButton66 = new javax.swing.JButton();
        jButton67 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel63 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jTextField58 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        compras = new javax.swing.JTabbedPane();
        informes = new javax.swing.JTabbedPane();
        maestros = new javax.swing.JTabbedPane();
        clientes = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTelefonoCliente = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtEmailCliente = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtRutCliente = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btnCancelarCliente = new javax.swing.JButton();
        btnGuardarCliente = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        ComboBoxRedSocialCliente = new javax.swing.JComboBox<>();
        txtCelularCliente = new javax.swing.JTextField();
        txtDV = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaClientes = new javax.swing.JTable();
        txtCliente = new javax.swing.JTextField();
        btnEditarCliente = new javax.swing.JButton();
        btnVentaCliente = new javax.swing.JButton();
        btnDesactivarCliente = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        txtRutProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        txtRazonSocialProveedor = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        txtEmailProveedor = new javax.swing.JTextField();
        btnCancelarProveedor = new javax.swing.JButton();
        btnGuardarProveedor = new javax.swing.JButton();
        jTextField12 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        btnComprarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnDesactivarProveedor = new javax.swing.JButton();
        btnBuscarProveedor = new javax.swing.JButton();
        txtBuscarProveedor = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtNombreArticulo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtUnidadArticulo = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtMarcaArticulo = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btnCancelarArticulos = new javax.swing.JButton();
        btnGuardarArticulos = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txtCodigoArticulo = new javax.swing.JTextField();
        ComboBoxCategoriaArticulo = new javax.swing.JComboBox<>();
        ComboBoxProveedorArticulo = new javax.swing.JComboBox<>();
        DateFVArticulo = new com.toedter.calendar.JDateChooser();
        jLabel80 = new javax.swing.JLabel();
        ComboBoxEstadoArticulo = new javax.swing.JComboBox<>();
        jLabel81 = new javax.swing.JLabel();
        txtIdentificadorArticulo = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaArticulos = new javax.swing.JTable();
        btnDesactivarArticulos = new javax.swing.JButton();
        btnEditarArticulos = new javax.swing.JButton();
        txtBuscarArticulo = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtNombrePack = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        txtPrecioPack = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaPack = new javax.swing.JTable();
        txtBuscarPack = new javax.swing.JTextField();
        btnEditarPack = new javax.swing.JButton();
        btnDesactivarPack = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        txtBuscarCanal = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        TablaCanal = new javax.swing.JTable();
        btnEditarCanal = new javax.swing.JButton();
        btnDesactivarCanal = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCodigoCanal = new javax.swing.JTextField();
        txtNombreCanal = new javax.swing.JTextField();
        btnCancelarCanal = new javax.swing.JButton();
        btnGuardarCanal = new javax.swing.JButton();
        jLabel77 = new javax.swing.JLabel();
        ComboBoxEstadoCanal = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        txtCodigoCategoria = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNombreCategoria = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        ComboBoxEstadoCategoria = new javax.swing.JComboBox<>();
        btnGuardarCategoria = new javax.swing.JButton();
        btnCancelarCategoria = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        txtBuscarCategoria = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        TablaCategoria = new javax.swing.JTable();
        btnEditarCategoria = new javax.swing.JButton();
        btnDesactivarCategoria = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtNombreComuna = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtCodigoComuna = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        ComboBoxEstadoComuna = new javax.swing.JComboBox<>();
        btnCancelarComuna = new javax.swing.JButton();
        btnGuardarComuna = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        txtBuscarComuna = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        TablaComuna = new javax.swing.JTable();
        btnEditarComuna = new javax.swing.JButton();
        btnDesactivarComuna = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtCodigoBanco = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtNombreBanco = new javax.swing.JTextField();
        Titulo_Prov7 = new javax.swing.JLabel();
        ComboBoxEstadoBanco = new javax.swing.JComboBox<>();
        btnCancelarBanco = new javax.swing.JButton();
        btnGuardarBanco = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TablaBanco = new javax.swing.JTable();
        txtBuscarBanco = new javax.swing.JTextField();
        btnEditarBanco = new javax.swing.JButton();
        btnDesactivarBanco = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        txtEstadoPagoVenta = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        btnCancelarEstadoVenta = new javax.swing.JButton();
        btnGuardarEstadoVenta = new javax.swing.JButton();
        txtCodigoEstadoVenta = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jPanel36 = new javax.swing.JPanel();
        txtBuscarEstadoVenta = new javax.swing.JTextField();
        btnDesactivarEstadoVenta = new javax.swing.JButton();
        btnEditarEstadoVenta = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        TablaEstadoVenta = new javax.swing.JTable();
        jLabel70 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        txtIdUsuario = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        txtCuentaUsuario = new javax.swing.JTextField();
        PasswordUsuario = new javax.swing.JPasswordField();
        jLabel76 = new javax.swing.JLabel();
        btnCancelarUsuario = new javax.swing.JButton();
        btnGuardarUsuario = new javax.swing.JButton();
        ComboBoxEstado = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        txtbuscarUsuario = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        TablaUsuarios = new javax.swing.JTable();
        btnEditarUsuario = new javax.swing.JButton();
        btnDesactivarUsuario = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        faq1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenu3.setText("jMenu3");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu4.setText("jMenu4");

        jMenu5.setText("jMenu5");

        jMenu6.setText("jMenu6");

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu9.setText("File");
        jMenuBar2.add(jMenu9);

        jMenu10.setText("Edit");
        jMenuBar2.add(jMenu10);

        jMenuItem1.setText("jMenuItem1");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jCheckBox1.setText("jCheckBox1");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jMenu13.setText("File");
        jMenuBar3.add(jMenu13);

        jMenu14.setText("Edit");
        jMenuBar3.add(jMenu14);

        jMenu15.setText("File");
        jMenuBar4.add(jMenu15);

        jMenu16.setText("Edit");
        jMenuBar4.add(jMenu16);

        jMenuItem2.setText("jMenuItem2");

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu17.setText("File");
        jMenuBar5.add(jMenu17);

        jMenu18.setText("Edit");
        jMenuBar5.add(jMenu18);

        jMenuItem3.setText("jMenuItem3");

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        javax.swing.GroupLayout jFrame5Layout = new javax.swing.GroupLayout(jFrame5.getContentPane());
        jFrame5.getContentPane().setLayout(jFrame5Layout);
        jFrame5Layout.setHorizontalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame5Layout.setVerticalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame6Layout = new javax.swing.GroupLayout(jFrame6.getContentPane());
        jFrame6.getContentPane().setLayout(jFrame6Layout);
        jFrame6Layout.setHorizontalGroup(
            jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame6Layout.setVerticalGroup(
            jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenuItem4.setText("jMenuItem4");

        jMenu11.setText("jMenu11");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        Titulo_Prov.setText("Proveedores");

        jLabel5.setText("Rut Proveedor");

        jTextField4.setText("70.123.345-6");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre Contacto");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel7.setText("Direccion");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel8.setText("Razón Social");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel9.setText("Teléfono");

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jLabel10.setText("E-Mail");

        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jButton6.setText("Cancelar");

        jButton7.setText("Guardar");

        jLabel11.setText("PROVEEDORES");

        Tabla_Provedores.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Tabla_Provedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Razón Social", "Nombre Contacto", "Teléfono", "E-Mail", "Acción"
            }
        ));
        Tabla_Provedores.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(Tabla_Provedores);
        Tabla_Provedores.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        jButton8.setText("Buscar");

        jButton12.setText("Comprar");

        jButton13.setText("Editar");

        jButton14.setText("Desactivar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator3)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Titulo_Prov)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField10)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton6)
                                                .addGap(26, 26, 26)
                                                .addComponent(jButton7)
                                                .addGap(0, 105, Short.MAX_VALUE)))))))
                        .addContainerGap(22, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(jLabel11)
                        .addGap(129, 129, 129)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addGap(18, 18, 18)
                .addComponent(jButton13)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addGap(54, 54, 54))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titulo_Prov)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(460, Short.MAX_VALUE)))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable3);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jScrollPane8.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dreamGifts.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        dreamGifts.setText("Dream Gifts");
        getContentPane().add(dreamGifts, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 11, -1, -1));

        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.PNG"))); // NOI18N
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        getContentPane().add(buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 20, 20));

        notificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/campana.PNG"))); // NOI18N
        getContentPane().add(notificaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 20, 20));

        configuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/signo de pregunta.PNG"))); // NOI18N
        getContentPane().add(configuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 20, 20));

        Titulo_Prov10.setText("Datos Cliente Solicitante ");

        jLabel50.setText("Número Pedido");

        jTextField45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField45ActionPerformed(evt);
            }
        });

        jLabel51.setText("Nombre Cliente");

        jTextField46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField46ActionPerformed(evt);
            }
        });

        jLabel52.setText("E-Mail");

        jTextField47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField47ActionPerformed(evt);
            }
        });

        jLabel54.setText("Rut");

        jTextField49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField49ActionPerformed(evt);
            }
        });

        jLabel55.setText("Teléfono");

        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });

        jButton58.setText("Cancelar");

        jButton61.setText("Guardar");

        buscar1.setBackground(new java.awt.Color(153, 153, 0));
        buscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.PNG"))); // NOI18N

        faq.setBackground(new java.awt.Color(204, 204, 204));
        faq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/signo de pregunta.PNG"))); // NOI18N

        Titulo_Prov11.setText("Datos Destinatario ");

        jLabel58.setText("Nombre Destinatario");

        jLabel59.setText("Fecha de Entrega");

        jLabel60.setText("Dirección");

        jTextField53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField53ActionPerformed(evt);
            }
        });

        jTextField55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField55ActionPerformed(evt);
            }
        });

        jLabel61.setText("Pack");

        jLabel62.setText("Hora Inicio Entrega");

        jButton66.setText("Cancelar");

        jButton67.setText("Guardar");

        jLabel63.setText("Comuna");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pack-01 - Cumpleaños", "Pack-02 - Dia del Niño", "Pack-03 - Dia del Padre" }));

        jLabel64.setText("Saludo");

        jTextField58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField58ActionPerformed(evt);
            }
        });

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Las Condes", "Santiago", "San Ramón", "Maipú" }));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "08:00", "09:00", "10:00", "11:00" }));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "08:00", "09:00", "10:00", "11:00" }));

        jLabel65.setText("Hora Fin Entrega");

        jLabel1.setText("Sub - Total");

        jLabel2.setText("Envios");

        jLabel3.setText("TOTAL");

        jTextField1.setText("54.990");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setText("gratis");

        jTextField3.setText("54.990");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)))
                                .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(113, 113, 113)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(jButton66)
                                .addGap(18, 18, 18)
                                .addComponent(jButton67))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox4, 0, 165, Short.MAX_VALUE)
                                    .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(46, 46, 46)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(47, 47, 47)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(26, 26, 26)
                                        .addComponent(jTextField1))
                                    .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131)))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(jButton58)
                                .addGap(26, 26, 26)
                                .addComponent(jButton61))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField49))
                                    .addGroup(jPanel20Layout.createSequentialGroup()
                                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(faq, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addComponent(Titulo_Prov11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator1))
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addComponent(Titulo_Prov10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Titulo_Prov10)
                    .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel50)
                                .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel54)
                                .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55)
                            .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton58)
                            .addComponent(jButton61)))
                    .addComponent(faq, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Titulo_Prov11)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jLabel62)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton66)
                            .addComponent(jButton67))
                        .addContainerGap())
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        ventas.addTab("Ventas", jPanel20);

        jTabbedPane4.addTab("Ventas", ventas);
        jTabbedPane4.addTab("Compras", compras);
        jTabbedPane4.addTab("Informes", informes);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

        jLabel13.setText("Nombre Cliente");

        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });

        jLabel14.setText("Teléfono");

        txtTelefonoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoClienteActionPerformed(evt);
            }
        });

        jLabel21.setText("E- Mail");

        txtEmailCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailClienteActionPerformed(evt);
            }
        });

        jLabel22.setText("Rut");

        txtRutCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutClienteActionPerformed(evt);
            }
        });

        jLabel23.setText("F. Nacimiento");

        jLabel24.setText("Celular");

        btnCancelarCliente.setText("Cancelar");
        btnCancelarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarClienteActionPerformed(evt);
            }
        });

        btnGuardarCliente.setText("Guardar");
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        jLabel26.setText("-");

        jLabel27.setText("Red Social");

        ComboBoxRedSocialCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Canal" }));
        ComboBoxRedSocialCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxRedSocialClienteActionPerformed(evt);
            }
        });

        txtCelularCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularClienteActionPerformed(evt);
            }
        });

        txtDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(ComboBoxRedSocialCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnCancelarCliente)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGuardarCliente)
                                .addGap(44, 44, 44))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel22)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCelularCliente)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtRutCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtDV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(7, 7, 7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addComponent(txtRutCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(txtCelularCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(ComboBoxRedSocialCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarCliente)
                    .addComponent(btnGuardarCliente))
                .addGap(13, 13, 13))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder("TablaClientes"));

        TablaClientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Cliente", "Celular", "Teléfono", "E-Mail", "Red Social", "Estado"
            }
        ));
        TablaClientes.setColumnSelectionAllowed(true);
        TablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaClientesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaClientes);
        TablaClientes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setText("Editar");
        btnEditarCliente.setEnabled(false);
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnVentaCliente.setText("Venta");
        btnVentaCliente.setEnabled(false);

        btnDesactivarCliente.setText("Desactivar");
        btnDesactivarCliente.setEnabled(false);

        jLabel73.setText("Buscar:");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(btnVentaCliente)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDesactivarCliente)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarCliente)
                    .addComponent(btnVentaCliente)
                    .addComponent(btnDesactivarCliente))
                .addContainerGap())
        );

        javax.swing.GroupLayout clientesLayout = new javax.swing.GroupLayout(clientes);
        clientes.setLayout(clientesLayout);
        clientesLayout.setHorizontalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        clientesLayout.setVerticalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientesLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );

        maestros.addTab("Clientes", clientes);

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores"));

        jLabel56.setText("Rut Proveedor");

        jLabel57.setText("Nombre Contacto");

        jLabel66.setText("Direccion");

        txtRutProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutProveedorActionPerformed(evt);
            }
        });

        txtNombreProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProveedorActionPerformed(evt);
            }
        });

        txtDireccionProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionProveedorActionPerformed(evt);
            }
        });

        jLabel67.setText("Razón Social");

        txtRazonSocialProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRazonSocialProveedorActionPerformed(evt);
            }
        });

        jLabel68.setText("Teléfono");

        txtTelefonoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoProveedorActionPerformed(evt);
            }
        });

        jLabel69.setText("E-Mail");

        txtEmailProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailProveedorActionPerformed(evt);
            }
        });

        btnCancelarProveedor.setText("Cancelar");
        btnCancelarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProveedorActionPerformed(evt);
            }
        });

        btnGuardarProveedor.setText("Guardar");
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        jLabel25.setText("-");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel32Layout.createSequentialGroup()
                                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32))
                                    .addGroup(jPanel32Layout.createSequentialGroup()
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                                        .addComponent(txtRutProveedor)
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefonoProveedor)
                            .addComponent(txtRazonSocialProveedor)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(txtEmailProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarProveedor)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarProveedor)
                        .addGap(51, 51, 51))))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(txtRutProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel67)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtRazonSocialProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel57)
                        .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel68)))
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69)
                    .addComponent(txtEmailProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarProveedor)
                    .addComponent(btnGuardarProveedor))
                .addGap(14, 14, 14))
        );

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores Registrados"));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Razón Social", "Nombre De Contacto", "Dirección", "Teléfono", "Email", "Estado"
            }
        ));
        jScrollPane7.setViewportView(jTable4);

        btnComprarProveedor.setText("Comprar");
        btnComprarProveedor.setEnabled(false);

        btnEditarProveedor.setText("Editar");
        btnEditarProveedor.setEnabled(false);

        btnDesactivarProveedor.setText("Desactivar");
        btnDesactivarProveedor.setEnabled(false);

        btnBuscarProveedor.setText("Buscar");

        jLabel72.setText("Buscar:");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane7)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarProveedor)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addComponent(btnComprarProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDesactivarProveedor)
                        .addContainerGap())))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarProveedor)
                    .addComponent(txtBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesactivarProveedor)
                    .addComponent(btnEditarProveedor)
                    .addComponent(btnComprarProveedor))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 44, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        maestros.addTab("Proveedores", jPanel21);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Artículos"));

        jLabel15.setText("Nombre Artículo:");

        txtNombreArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreArticuloActionPerformed(evt);
            }
        });

        jLabel16.setText("Unidades:");

        txtUnidadArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadArticuloActionPerformed(evt);
            }
        });

        jLabel28.setText("Marca:");

        txtMarcaArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaArticuloActionPerformed(evt);
            }
        });

        jLabel29.setText("Categoria Artículo:");

        jLabel30.setText("F. Vencimiento:");

        jLabel31.setText("Proveedor:");

        btnCancelarArticulos.setText("Cancelar");
        btnCancelarArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarArticulosActionPerformed(evt);
            }
        });

        btnGuardarArticulos.setText("Guardar");
        btnGuardarArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarArticulosActionPerformed(evt);
            }
        });

        jLabel34.setText("Codigo Artículo:");

        txtCodigoArticulo.setEnabled(false);
        txtCodigoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoArticuloActionPerformed(evt);
            }
        });

        ComboBoxCategoriaArticulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Categoria" }));
        ComboBoxCategoriaArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboBoxCategoriaArticuloMouseClicked(evt);
            }
        });

        ComboBoxProveedorArticulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Proveedor" }));
        ComboBoxProveedorArticulo.setToolTipText("");

        jLabel80.setText("Estado:");

        ComboBoxEstadoArticulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoArticulo.setEnabled(false);

        jLabel81.setText("Id Artículo:");

        txtIdentificadorArticulo.setEnabled(false);
        txtIdentificadorArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdentificadorArticuloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUnidadArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMarcaArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(txtCodigoArticulo)
                            .addComponent(txtIdentificadorArticulo))))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarArticulos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarArticulos))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel80))
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGap(63, 63, 63)
                                        .addComponent(ComboBoxEstadoArticulo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(ComboBoxProveedorArticulo, 0, 134, Short.MAX_VALUE))))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ComboBoxCategoriaArticulo, 0, 134, Short.MAX_VALUE)
                                    .addComponent(DateFVArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(24, 24, 24))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtNombreArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(ComboBoxCategoriaArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txtUnidadArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30))
                    .addComponent(DateFVArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(txtMarcaArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ComboBoxProveedorArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel31))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80)
                    .addComponent(ComboBoxEstadoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarArticulos)
                    .addComponent(btnCancelarArticulos)
                    .addComponent(jLabel81)
                    .addComponent(txtIdentificadorArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Artículos Registrados"));

        TablaArticulos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Artículo", "Artículo", "Código", "Unidades", "Marca", "F. Vencimiento", "Estado", "Categoria", "Proveedor"
            }
        ));
        TablaArticulos.setColumnSelectionAllowed(true);
        jScrollPane4.setViewportView(TablaArticulos);
        TablaArticulos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (TablaArticulos.getColumnModel().getColumnCount() > 0) {
            TablaArticulos.getColumnModel().getColumn(0).setMinWidth(60);
            TablaArticulos.getColumnModel().getColumn(0).setMaxWidth(60);
            TablaArticulos.getColumnModel().getColumn(1).setMinWidth(100);
            TablaArticulos.getColumnModel().getColumn(1).setMaxWidth(100);
            TablaArticulos.getColumnModel().getColumn(2).setMinWidth(60);
            TablaArticulos.getColumnModel().getColumn(2).setMaxWidth(60);
            TablaArticulos.getColumnModel().getColumn(3).setMinWidth(60);
            TablaArticulos.getColumnModel().getColumn(3).setMaxWidth(60);
            TablaArticulos.getColumnModel().getColumn(5).setMinWidth(100);
            TablaArticulos.getColumnModel().getColumn(5).setMaxWidth(100);
            TablaArticulos.getColumnModel().getColumn(6).setMinWidth(60);
            TablaArticulos.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        btnDesactivarArticulos.setText("Desactivar");
        btnDesactivarArticulos.setEnabled(false);

        btnEditarArticulos.setText("Editar");
        btnEditarArticulos.setEnabled(false);

        txtBuscarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarArticuloActionPerformed(evt);
            }
        });

        jLabel44.setText("Buscar:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addComponent(btnEditarArticulos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDesactivarArticulos)
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesactivarArticulos)
                    .addComponent(btnEditarArticulos))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        maestros.addTab("Artículos", jPanel4);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Packs"));

        jLabel17.setText("Nombre Pack:");

        txtNombrePack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrePackActionPerformed(evt);
            }
        });

        jLabel35.setText("Precio Pack:");

        jButton24.setText("Cancelar");

        jButton27.setText("Crear Pack");

        txtPrecioPack.setEditable(false);
        txtPrecioPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioPackActionPerformed(evt);
            }
        });

        jLabel4.setText("Unidades");

        jTextField7.setText("2");

        jButton9.setText("-->");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("<---");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Articulo", "cantidad"
            }
        ));
        jScrollPane15.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Articulo", "cantidad"
            }
        ));
        jScrollPane16.setViewportView(jTable2);

        jLabel38.setText("Estado:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo", " ", " " }));
        jComboBox1.setEnabled(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(527, 527, 527)
                .addComponent(jButton24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton27)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombrePack, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioPack, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrecioPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombrePack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(jButton10)))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton27)
                    .addComponent(jButton24))
                .addGap(18, 18, 18))
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de Packs"));

        TablaPack.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo De Pack", "Nombre De Pack", "Unidades De Bodega", "Estado"
            }
        ));
        jScrollPane5.setViewportView(TablaPack);

        btnEditarPack.setText("Editar");
        btnEditarPack.setEnabled(false);

        btnDesactivarPack.setText("Desactivar");
        btnDesactivarPack.setEnabled(false);

        jLabel71.setText("Buscar:");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarPack, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEditarPack, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnDesactivarPack)))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarPack)
                    .addComponent(btnDesactivarPack))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 121, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        maestros.addTab("Packs", jPanel5);

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder("Canales Registrados"));

        txtBuscarCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCanalActionPerformed(evt);
            }
        });
        txtBuscarCanal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCanalKeyReleased(evt);
            }
        });

        TablaCanal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaCanal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Canal", "Nombre de Canal", "Estado"
            }
        ));
        TablaCanal.setColumnSelectionAllowed(true);
        TablaCanal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCanalMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TablaCanal);
        TablaCanal.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarCanal.setText("Editar");
        btnEditarCanal.setEnabled(false);
        btnEditarCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCanalActionPerformed(evt);
            }
        });

        btnDesactivarCanal.setText("Desactivar");
        btnDesactivarCanal.setEnabled(false);
        btnDesactivarCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarCanalActionPerformed(evt);
            }
        });

        jLabel42.setText("Buscar:");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEditarCanal)
                .addGap(18, 18, 18)
                .addComponent(btnDesactivarCanal)
                .addGap(15, 15, 15))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditarCanal)
                    .addComponent(btnDesactivarCanal)))
        );

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Canal"));

        jLabel36.setText("Código Canal: ");

        jLabel18.setText("Nombre de Canal:");

        txtCodigoCanal.setEditable(false);
        txtCodigoCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoCanalActionPerformed(evt);
            }
        });

        txtNombreCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCanalActionPerformed(evt);
            }
        });
        txtNombreCanal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCanalKeyTyped(evt);
            }
        });

        btnCancelarCanal.setText("Cancelar");
        btnCancelarCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCanalActionPerformed(evt);
            }
        });

        btnGuardarCanal.setText("Guardar");
        btnGuardarCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCanalActionPerformed(evt);
            }
        });

        jLabel77.setText("Estado:");

        ComboBoxEstadoCanal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoCanal.setEnabled(false);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarCanal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardarCanal)
                .addContainerGap())
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel77)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigoCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxEstadoCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtCodigoCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombreCanal)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(ComboBoxEstadoCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarCanal)
                    .addComponent(btnGuardarCanal)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        maestros.addTab("Canal", jPanel6);

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Categoría de artículos"));

        txtCodigoCategoria.setEditable(false);
        txtCodigoCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoCategoriaActionPerformed(evt);
            }
        });

        jLabel37.setText("Código de  Categoría: ");

        jLabel19.setText("Categoría Artículo:");

        txtNombreCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCategoriaActionPerformed(evt);
            }
        });

        jLabel79.setText("Estado:");

        ComboBoxEstadoCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoCategoria.setEnabled(false);

        btnGuardarCategoria.setText("Guardar");
        btnGuardarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCategoriaActionPerformed(evt);
            }
        });

        btnCancelarCategoria.setText("Cancelar");
        btnCancelarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxEstadoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(170, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarCategoria)
                .addGap(8, 8, 8)
                .addComponent(btnGuardarCategoria)
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addComponent(txtCodigoCategoria, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxEstadoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarCategoria)
                    .addComponent(btnGuardarCategoria)))
        );

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder("Categorías Registradas"));

        txtBuscarCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarCategoriaMouseClicked(evt);
            }
        });
        txtBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCategoriaActionPerformed(evt);
            }
        });
        txtBuscarCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCategoriaKeyReleased(evt);
            }
        });

        TablaCategoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Estado"
            }
        ));
        TablaCategoria.setColumnSelectionAllowed(true);
        TablaCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCategoriaMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(TablaCategoria);
        TablaCategoria.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarCategoria.setText("Editar");
        btnEditarCategoria.setEnabled(false);
        btnEditarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCategoriaActionPerformed(evt);
            }
        });

        btnDesactivarCategoria.setText("Desactivar");
        btnDesactivarCategoria.setEnabled(false);
        btnDesactivarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarCategoriaActionPerformed(evt);
            }
        });

        jLabel40.setText("Buscar:");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEditarCategoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDesactivarCategoria)
                .addGap(13, 13, 13))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarCategoria)
                    .addComponent(btnDesactivarCategoria))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 864, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 133, Short.MAX_VALUE)))
        );

        maestros.addTab("Categorías Artículos", jPanel7);

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Comunas"));

        jLabel20.setText("Nombre Comuna");

        txtNombreComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreComunaActionPerformed(evt);
            }
        });
        txtNombreComuna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreComunaKeyTyped(evt);
            }
        });

        jLabel41.setText("Código Comuna ");

        txtCodigoComuna.setEditable(false);
        txtCodigoComuna.setEnabled(false);
        txtCodigoComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoComunaActionPerformed(evt);
            }
        });

        jLabel78.setText("Estado");

        ComboBoxEstadoComuna.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoComuna.setEnabled(false);

        btnCancelarComuna.setText("Cancelar");
        btnCancelarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarComunaActionPerformed(evt);
            }
        });

        btnGuardarComuna.setText("Guardar");
        btnGuardarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarComunaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel78)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(ComboBoxEstadoComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarComuna)
                        .addGap(8, 8, 8)
                        .addComponent(btnGuardarComuna))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoComuna, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreComuna, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 356, Short.MAX_VALUE))))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(txtCodigoComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboBoxEstadoComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel78))))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarComuna)
                    .addComponent(btnGuardarComuna))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(304, 304, 304))
        );

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("Comunas Registradas"));

        txtBuscarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarComunaActionPerformed(evt);
            }
        });
        txtBuscarComuna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarComunaKeyReleased(evt);
            }
        });

        TablaComuna.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaComuna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Comuna", "Nombre de la Comuna", "Estado"
            }
        ));
        TablaComuna.setColumnSelectionAllowed(true);
        TablaComuna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaComunaMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(TablaComuna);
        TablaComuna.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarComuna.setText("Editar");
        btnEditarComuna.setEnabled(false);
        btnEditarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarComunaActionPerformed(evt);
            }
        });

        btnDesactivarComuna.setText("Desactivar");
        btnDesactivarComuna.setEnabled(false);
        btnDesactivarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarComunaActionPerformed(evt);
            }
        });

        jLabel39.setText("Buscar:");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(0, 569, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarComuna, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(btnEditarComuna)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDesactivarComuna)
                        .addGap(11, 11, 11))))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarComuna)
                    .addComponent(btnDesactivarComuna))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                    .addContainerGap(36, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(49, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(220, Short.MAX_VALUE)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 470, Short.MAX_VALUE)))
        );

        maestros.addTab("Comunas", jPanel8);

        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Banco"));

        jLabel43.setText("Código Banco:");

        txtCodigoBanco.setEditable(false);
        txtCodigoBanco.setEnabled(false);
        txtCodigoBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoBancoActionPerformed(evt);
            }
        });

        jLabel33.setText("Nombre del  Banco:");

        txtNombreBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreBancoActionPerformed(evt);
            }
        });
        txtNombreBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreBancoKeyTyped(evt);
            }
        });

        Titulo_Prov7.setText("Estado:");

        ComboBoxEstadoBanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoBanco.setEnabled(false);
        ComboBoxEstadoBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxEstadoBancoActionPerformed(evt);
            }
        });

        btnCancelarBanco.setText("Cancelar");
        btnCancelarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBancoActionPerformed(evt);
            }
        });

        btnGuardarBanco.setText("Guardar");
        btnGuardarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarBancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Titulo_Prov7)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxEstadoBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarBanco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarBanco)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtCodigoBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtNombreBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Titulo_Prov7)
                    .addComponent(ComboBoxEstadoBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarBanco)
                    .addComponent(btnGuardarBanco)))
        );

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder("Bancos Registrados"));

        TablaBanco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaBanco.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del Banco", "Nombre del Banco", "Estado"
            }
        ));
        TablaBanco.setColumnSelectionAllowed(true);
        TablaBanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaBancoMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(TablaBanco);
        TablaBanco.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        txtBuscarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarBancoActionPerformed(evt);
            }
        });
        txtBuscarBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarBancoKeyReleased(evt);
            }
        });

        btnEditarBanco.setText("Editar");
        btnEditarBanco.setEnabled(false);
        btnEditarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarBancoActionPerformed(evt);
            }
        });

        btnDesactivarBanco.setText("Desactivar");
        btnDesactivarBanco.setEnabled(false);
        btnDesactivarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarBancoActionPerformed(evt);
            }
        });

        jLabel32.setText("Buscar:");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                                .addComponent(btnEditarBanco)
                                .addGap(18, 18, 18)
                                .addComponent(btnDesactivarBanco))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditarBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDesactivarBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 866, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addContainerGap(28, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(94, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 104, Short.MAX_VALUE)))
        );

        maestros.addTab("Bancos", jPanel9);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Estados De Venta"));

        jLabel45.setText("Estado Pago De Venta:");

        txtEstadoPagoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoPagoVentaActionPerformed(evt);
            }
        });

        jLabel46.setText("Código Estado De Ventas:");

        btnCancelarEstadoVenta.setText("Cancelar");

        btnGuardarEstadoVenta.setText("Guardar");

        txtCodigoEstadoVenta.setEditable(false);
        txtCodigoEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoEstadoVentaActionPerformed(evt);
            }
        });

        jLabel47.setText("Estado:");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        jComboBox8.setEnabled(false);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarEstadoVenta)
                .addGap(18, 18, 18)
                .addComponent(btnGuardarEstadoVenta)
                .addGap(84, 84, 84))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel47)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstadoPagoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txtEstadoPagoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtCodigoEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarEstadoVenta)
                    .addComponent(btnGuardarEstadoVenta))
                .addContainerGap())
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla Estado De Ventas"));

        txtBuscarEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarEstadoVentaActionPerformed(evt);
            }
        });

        btnDesactivarEstadoVenta.setText("Desactivar");
        btnDesactivarEstadoVenta.setEnabled(false);

        btnEditarEstadoVenta.setText("Editar");
        btnEditarEstadoVenta.setEnabled(false);

        TablaEstadoVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código De Venta", "Estado De Venta", "Estado"
            }
        ));
        jScrollPane12.setViewportView(TablaEstadoVenta);

        jLabel70.setText("Buscar:");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(0, 467, Short.MAX_VALUE)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addComponent(btnEditarEstadoVenta)
                                .addGap(18, 18, 18)
                                .addComponent(btnDesactivarEstadoVenta)
                                .addGap(56, 56, 56))
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscarEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarEstadoVenta)
                    .addComponent(btnDesactivarEstadoVenta))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 141, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );

        maestros.addTab("Estado Ventas", jPanel10);

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Usuarios"));

        txtIdUsuario.setEditable(false);

        jLabel49.setText("Identificador");

        jLabel12.setText("Nombre:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        jLabel74.setText("Apellido:");

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        jLabel48.setText("Ingrese Clave:");

        jLabel75.setText("Cuenta de Usuario:");

        txtCuentaUsuario.setEditable(false);
        txtCuentaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCuentaUsuarioActionPerformed(evt);
            }
        });

        PasswordUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PasswordUsuarioKeyTyped(evt);
            }
        });

        jLabel76.setText("Estado:");

        btnCancelarUsuario.setText("Cancelar");
        btnCancelarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarUsuarioActionPerformed(evt);
            }
        });

        btnGuardarUsuario.setText("Guardar");
        btnGuardarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuarioActionPerformed(evt);
            }
        });

        ComboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstado.setEnabled(false);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel74))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(110, 110, 110)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel75)
                            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel76)
                                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCuentaUsuario)
                            .addComponent(PasswordUsuario)
                            .addComponent(ComboBoxEstado, 0, 116, Short.MAX_VALUE)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(btnCancelarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarUsuario)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel75)
                    .addComponent(txtCuentaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel48)
                    .addComponent(PasswordUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74)
                    .addComponent(jLabel76)
                    .addComponent(ComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarUsuario)
                    .addComponent(btnGuardarUsuario)))
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuarios Registrados"));

        txtbuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarUsuarioActionPerformed(evt);
            }
        });
        txtbuscarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarUsuarioKeyReleased(evt);
            }
        });

        TablaUsuarios.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Nombre", "Apellido", "Usuario", "Clave", "Estado"
            }
        ));
        TablaUsuarios.setColumnSelectionAllowed(true);
        TablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(TablaUsuarios);
        TablaUsuarios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarUsuario.setText("Editar");
        btnEditarUsuario.setEnabled(false);
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        btnDesactivarUsuario.setText("Desactivar");
        btnDesactivarUsuario.setEnabled(false);
        btnDesactivarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarUsuarioActionPerformed(evt);
            }
        });

        jLabel53.setText("Buscar:");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDesactivarUsuario)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane13))
                        .addGap(21, 21, 21))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarUsuario)
                    .addComponent(btnDesactivarUsuario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(400, 400, 400))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        maestros.addTab("Usuarios", jPanel11);

        jTabbedPane4.addTab("Maestros", maestros);

        getContentPane().add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 870, 690));

        faq1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tuerca configuracion.PNG"))); // NOI18N
        getContentPane().add(faq1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 20, 20));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/casa.PNG"))); // NOI18N
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 50, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void txtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteActionPerformed

    private void txtTelefonoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoClienteActionPerformed

    private void txtEmailClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailClienteActionPerformed

    private void txtRutClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutClienteActionPerformed

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteActionPerformed

    private void ComboBoxRedSocialClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxRedSocialClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxRedSocialClienteActionPerformed

    private void txtNombreArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreArticuloActionPerformed

    private void txtUnidadArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnidadArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadArticuloActionPerformed

    private void txtMarcaArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaArticuloActionPerformed

    private void txtBuscarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarArticuloActionPerformed

    private void txtCodigoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoArticuloActionPerformed

    private void txtNombreCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCategoriaActionPerformed

    private void txtBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCategoriaActionPerformed

    private void txtCodigoCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoCategoriaActionPerformed

    private void txtNombreComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreComunaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreComunaActionPerformed

    private void txtBuscarComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarComunaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarComunaActionPerformed

    private void txtCodigoComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoComunaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoComunaActionPerformed

    private void txtNombreBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreBancoActionPerformed

    private void txtBuscarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarBancoActionPerformed

    private void txtCodigoBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoBancoActionPerformed

    private void txtEstadoPagoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoPagoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoPagoVentaActionPerformed

    private void txtBuscarEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEstadoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarEstadoVentaActionPerformed

    private void txtCodigoEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoEstadoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoEstadoVentaActionPerformed

    private void jTextField45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField45ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField45ActionPerformed

    private void jTextField46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46ActionPerformed

    private void jTextField47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47ActionPerformed

    private void jTextField49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49ActionPerformed

    private void jTextField50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField50ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50ActionPerformed

    private void jTextField53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53ActionPerformed

    private void jTextField55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55ActionPerformed

    private void jTextField58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField58ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        //GuardarCliente();
        //listarCliente();
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnCancelarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarClienteActionPerformed
        //limpiarCliente();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarClienteActionPerformed

    private void TablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaClientesMouseClicked

    }//GEN-LAST:event_TablaClientesMouseClicked

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
    
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void txtBuscarCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCanalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCanalActionPerformed

    private void btnCancelarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBancoActionPerformed
        // TODO add your handling code here:
        txtBuscarBanco.setText("");
        LimpiarFormularioBanco();
        LimpiarTablaBanco();
        ListarBancoVista();          
    }//GEN-LAST:event_btnCancelarBancoActionPerformed

    private void TablaCanalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaCanalMouseClicked
        // TODO add your handling code here:
        int fila = TablaCanal.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Canal no Seleccionado");
        } else {
            btnEditarCanal.setEnabled(true);
            btnGuardarCanal.setEnabled(false);
            int identificador = Integer.parseInt((String) TablaCanal.getValueAt(fila, 0).toString());
            String Nombre = (String) TablaCanal.getValueAt(fila, 1);
            String Estado = (String) TablaCanal.getValueAt(fila, 2);
            if ("Inactivo".equals(Estado)) {
                ComboBoxEstadoCanal.setEnabled(true);
                btnDesactivarCanal.setEnabled(false);
            } else {
                btnDesactivarCanal.setEnabled(true);
                ComboBoxEstadoCanal.setEnabled(false);
            }
            txtCodigoCanal.setText("" + identificador);
            txtNombreCanal.setText(Nombre);
            ComboBoxEstadoCanal.setSelectedItem(Estado);
        }        
    }//GEN-LAST:event_TablaCanalMouseClicked

    private void txtNombreComunaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComunaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)|| 
            (c >= 123 && c <= 179)|| (c >= 181 && c <= 208)||
            (c >= 210 && c <= 240)|| (c >= 242 && c <= 255) || (txtNombreComuna.getText().length()>=45))evt.consume();   
    }//GEN-LAST:event_txtNombreComunaKeyTyped

    private void btnCancelarComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarComunaActionPerformed
        // TODO add your handling code here:
        txtBuscarComuna.setText("");
        LimpiarFormularioComuna();
        LimpiarTablaComuna();
        ListarComunasVista();        
    }//GEN-LAST:event_btnCancelarComunaActionPerformed

    private void btnGuardarComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarComunaActionPerformed
        // TODO add your handling code here:
        String nombreComuna = txtNombreComuna.getText();
        nombreComuna= nombreComuna.toUpperCase();
        if ((txtNombreComuna.getText().isEmpty())) {
               LimpiarFormularioComuna();
               JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre de la comuna");    
        } 
        else if (ComunasNegocio.BuscarComuna(nombreComuna)){
               LimpiarFormularioComuna();
               JOptionPane.showMessageDialog(null, "El nombre de la comuna: "+nombreComuna+", esta registrado");
           }
        else {
            int estado = 1;
            Comuna ClaseComuna = new Comuna();
            ClaseComuna.setNombreComuna(nombreComuna);
            ClaseComuna.setEstadoComuna(estado);
            if (ComunasNegocio.GuardarComuna(ClaseComuna)) {
                LimpiarFormularioComuna();
                LimpiarTablaComuna();
                ListarComunasVista();
                JOptionPane.showMessageDialog(null, "Comuna Ingresado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }        
    }//GEN-LAST:event_btnGuardarComunaActionPerformed

    private void TablaComunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaComunaMouseClicked
        // TODO add your handling code here:
        int fila = TablaComuna.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Comuna no Seleccionado");
        } else {
            btnEditarComuna.setEnabled(true);
            btnGuardarComuna.setEnabled(false);
            int identificador = Integer.parseInt((String) TablaComuna.getValueAt(fila, 0).toString());
            String Nombre = (String) TablaComuna.getValueAt(fila, 1);
            String Estado = (String) TablaComuna.getValueAt(fila, 2);
            if ("Inactivo".equals(Estado)) {
                ComboBoxEstadoComuna.setEnabled(true);
                btnDesactivarComuna.setEnabled(false);
            } else {
                btnDesactivarComuna.setEnabled(true);
                ComboBoxEstadoComuna.setEnabled(false);
            }
            txtCodigoComuna.setText("" + identificador);
            txtNombreComuna.setText(Nombre);
            ComboBoxEstadoComuna.setSelectedItem(Estado);
        }         
    }//GEN-LAST:event_TablaComunaMouseClicked

    private void txtBuscarComunaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarComunaKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarComuna.getText())){ 
           LimpiarFormularioComuna();
           LimpiarTablaComuna();
           ComunasNegocio.FiltrarComuna(TablaComuna, txtBuscarComuna.getText());
           }
        else{
            LimpiarFormularioComuna();
            LimpiarTablaComuna();
            ListarComunasVista();
        }
        
    }//GEN-LAST:event_txtBuscarComunaKeyReleased

    private void txtBuscarCanalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCanalKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarCanal.getText())){ 
           LimpiarFormularioCanal();
           LimpiarTablaCanal();
           MediosNegocio.FiltrarCanal(TablaCanal, txtBuscarCanal.getText());
           }
        else{
            LimpiarFormularioCanal();
            LimpiarTablaCanal();
            ListarCanalesVista();
        }        
    }//GEN-LAST:event_txtBuscarCanalKeyReleased

    private void ComboBoxEstadoBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxEstadoBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxEstadoBancoActionPerformed

    private void btnGuardarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarBancoActionPerformed
        // TODO add your handling code here:
        String nombreBanco = txtNombreBanco.getText();
        nombreBanco = nombreBanco.toUpperCase();
        if ((txtNombreBanco.getText().isEmpty())) {
               LimpiarFormularioBanco();
               JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre del banco");  
        } 
        else if (BancoNegocio.BuscarBanco(nombreBanco)){
               LimpiarFormularioBanco();
               JOptionPane.showMessageDialog(null, "El nombre "+nombreBanco+", esta registrado");
           }
        else {
            int estado = 1;
            Bancos ClaseBanco = new Bancos();
            ClaseBanco.setNombreBanco(nombreBanco);
            ClaseBanco.setEstadoBanco(estado);
            if (BancoNegocio.GuardarBanco(ClaseBanco)) {
                LimpiarFormularioBanco();
                LimpiarTablaBanco();
                ListarBancoVista();
                JOptionPane.showMessageDialog(null, "Banco Ingresado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }        
    }//GEN-LAST:event_btnGuardarBancoActionPerformed

    private void txtBuscarBancoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarBancoKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarBanco.getText())){ 
           LimpiarFormularioBanco();
           LimpiarTablaBanco();
           BancoNegocio.FiltrarBanco(TablaBanco, txtBuscarBanco.getText());
           }
        else{
            LimpiarFormularioBanco();
            LimpiarTablaBanco();
            ListarBancoVista();
        }        
    }//GEN-LAST:event_txtBuscarBancoKeyReleased

    private void txtNombreBancoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreBancoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)|| 
            (c >= 123 && c <= 179)|| (c >= 181 && c <= 208)||
            (c >= 210 && c <= 240)|| (c >= 242 && c <= 255) || (txtNombreBanco.getText().length()>=45))evt.consume();   
        
    }//GEN-LAST:event_txtNombreBancoKeyTyped

    private void TablaBancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaBancoMouseClicked
        // TODO add your handling code here:
        int fila = TablaBanco.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Banco no Seleccionado");
        } else {
            btnEditarBanco.setEnabled(true);
            btnGuardarBanco.setEnabled(false);
            int identificador = Integer.parseInt((String) TablaBanco.getValueAt(fila, 0).toString());
            String Nombre = (String) TablaBanco.getValueAt(fila, 1);
            String Estado = (String) TablaBanco.getValueAt(fila, 2);
            if ("Inactivo".equals(Estado)) {
                ComboBoxEstadoBanco.setEnabled(true);
                btnDesactivarBanco.setEnabled(false);
            } else {
                btnDesactivarBanco.setEnabled(true);
                ComboBoxEstadoBanco.setEnabled(false);
            }
            txtCodigoBanco.setText("" + identificador);
            txtNombreBanco.setText(Nombre);
            ComboBoxEstadoBanco.setSelectedItem(Estado);
        }                 
    }//GEN-LAST:event_TablaBancoMouseClicked

    private void btnCancelarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCategoriaActionPerformed
        // TODO add your handling code here:
        txtBuscarCategoria.setText("");
        LimpiarFormularioCategoria();
        LimpiarTablaCategoria();
        ListarCategoriaVista();        
    }//GEN-LAST:event_btnCancelarCategoriaActionPerformed

    private void btnGuardarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCategoriaActionPerformed
        // TODO add your handling code here:
        String nombreCategoria = txtNombreCategoria.getText();
        nombreCategoria = nombreCategoria.toUpperCase();
        if ((txtNombreCategoria.getText().isEmpty())) {
               LimpiarFormularioCategoria();
               JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre de la categoría");  
        } 
        else if (CategoriaNegocio.BuscarCategoria(nombreCategoria)){
               LimpiarFormularioCategoria();
               JOptionPane.showMessageDialog(null, "El nombre "+nombreCategoria+", esta registrado");
           }
        else {
            int estado = 1;
            
            Categoria ClaseCategoria = new Categoria();
            ClaseCategoria.setNombreCategoria(nombreCategoria);
            ClaseCategoria.setEstadoCategoria(estado);
            ClaseCategoria.setLiteralCategoria(nombreCategoria.substring(0, 3));
            if (CategoriaNegocio.GuardarCategoria(ClaseCategoria)) {
                LimpiarFormularioCategoria();
                LimpiarTablaCategoria();
                ListarCategoriaVista();
                JOptionPane.showMessageDialog(null, "Categoría Ingresada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }    
    }//GEN-LAST:event_btnGuardarCategoriaActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed

    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnCancelarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProveedorActionPerformed

    }//GEN-LAST:event_btnCancelarProveedorActionPerformed

    private void txtEmailProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailProveedorActionPerformed

    private void txtTelefonoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProveedorActionPerformed

    private void txtRazonSocialProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazonSocialProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocialProveedorActionPerformed

    private void txtDireccionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionProveedorActionPerformed

    private void txtNombreProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProveedorActionPerformed

    private void txtRutProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutProveedorActionPerformed

    private void btnDesactivarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarUsuarioActionPerformed
        //        // Invoca los metodos que desactivan y actualizan la informacion en la grilla de datos y formulario:
        if (!"".equals(txtNombre.getText()) && !"".equals(txtApellido.getText()) && !"".equals(PasswordUsuario.getText())) {
            int estado = 0;
            Usuario ClaseUsuario = new Usuario();
            ClaseUsuario.setEstado(estado);
            ClaseUsuario.setIdentificador( Integer.parseInt(txtIdUsuario.getText()));
            if (UserNegocio.DesactivarUsuario(ClaseUsuario)){
                LimpiarFormularioUsuario();
                LimpiarTablaUsuario();
                ListarUsuariosVista();
                JOptionPane.showMessageDialog(null, "Usuario Desactivado Exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        }
    }//GEN-LAST:event_btnDesactivarUsuarioActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        // Este evento invoca los metodos que actualizan la informacion en la BD y de la pantalla
        if (!"".equals(txtNombre.getText()) && !"".equals(txtApellido.getText()) && !"".equals(PasswordUsuario.getText())) {
            int estado = 0;
            if (ComboBoxEstado.getSelectedItem() == "Activo") {
                estado = 1;
            }
            Usuario ClaseUsuario = new Usuario();
            ClaseUsuario.setNombre(txtNombre.getText());
            ClaseUsuario.setApellido(txtApellido.getText());
            ClaseUsuario.setUserName(txtCuentaUsuario.getText());
            ClaseUsuario.setClave(PasswordUsuario.getText());
            ClaseUsuario.setEstado(estado);
            ClaseUsuario.setIdentificador(Integer.parseInt(txtIdUsuario.getText()));
            if (UserNegocio.ModificarUsuario(ClaseUsuario)) {
                LimpiarFormularioUsuario();
                LimpiarTablaUsuario();
                ListarUsuariosVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        }
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void TablaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaUsuariosMouseClicked
        //Este evento permite ver el efecto de seleccion de un registro
        //en la tabla o grilla de datos y se muestran la informacion
        //seleccionada en el formulario
        int fila = TablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Usuario no Seleccionado");
        } else {
            btnEditarUsuario.setEnabled(true);
            btnGuardarUsuario.setEnabled(false);
            int identificador = Integer.parseInt((String) TablaUsuarios.getValueAt(fila, 0).toString());
            String Nombre = (String) TablaUsuarios.getValueAt(fila, 1);
            String Apellido = (String) TablaUsuarios.getValueAt(fila, 2);
            String Usuario = (String) TablaUsuarios.getValueAt(fila, 3);
            String Clave = (String) TablaUsuarios.getValueAt(fila, 4);
            String Estado = (String) TablaUsuarios.getValueAt(fila, 5);
            if ("Inactivo".equals(Estado)) {
                ComboBoxEstado.setEnabled(true);
                btnDesactivarUsuario.setEnabled(false);
            } else {
                btnDesactivarUsuario.setEnabled(true);
                ComboBoxEstado.setEnabled(false);
            }
            txtIdUsuario.setText("" + identificador);
            txtNombre.setText(Nombre);
            txtApellido.setText(Apellido);
            txtCuentaUsuario.setText(Usuario);
            PasswordUsuario.setText(Clave);
            ComboBoxEstado.setSelectedItem(Estado);
        }
    }//GEN-LAST:event_TablaUsuariosMouseClicked

    private void txtbuscarUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarUsuarioKeyReleased
        // Este evento invoca los metodos que filtran la informacion de la grilla
        // y actualizan la pantalla
        if (!"".equals(txtbuscarUsuario.getText())){
            LimpiarFormularioUsuario();
            LimpiarTablaUsuario();
            UserNegocio.FiltrarUsuario(TablaUsuarios, txtbuscarUsuario.getText());
        }
        else{
            LimpiarFormularioUsuario();
            LimpiarTablaUsuario();
            ListarUsuariosVista();
        }
    }//GEN-LAST:event_txtbuscarUsuarioKeyReleased

    private void txtbuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarUsuarioActionPerformed

    private void btnGuardarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuarioActionPerformed
        // Mediante este evento validamos que los campos del formulario tengan
        //datos para ejecutar los metodos que insertan la informacion en la BD y actualiza la información del formulario
        if ((txtNombre.getText().isEmpty()) || (txtApellido.getText().isEmpty())
            || (PasswordUsuario.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        } else {
            int estado = 1;
            Usuario ClaseUsuario = new Usuario();
            ClaseUsuario.setNombre(txtNombre.getText());
            ClaseUsuario.setApellido(txtApellido.getText());
            ClaseUsuario.setUserName(txtCuentaUsuario.getText());
            ClaseUsuario.setClave(PasswordUsuario.getText());
            ClaseUsuario.setEstado(estado);
            if (UserNegocio.GuardarUsuario(ClaseUsuario)) {
                LimpiarFormularioUsuario();
                LimpiarTablaUsuario();
                ListarUsuariosVista();
                JOptionPane.showMessageDialog(null, "Usuario Ingresado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }
    }//GEN-LAST:event_btnGuardarUsuarioActionPerformed

    private void btnCancelarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarUsuarioActionPerformed
        //Invoca los metodos o funciones que reinician el formulario
        txtbuscarUsuario.setText("");
        LimpiarFormularioUsuario();
        LimpiarTablaUsuario();
        ListarUsuariosVista();
    }//GEN-LAST:event_btnCancelarUsuarioActionPerformed

    private void PasswordUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordUsuarioKeyTyped
        // Este evento permite controlar la cantidad maxima de caracteres permitidos
        //y que solo se permita el ingreso de numeros en la clave:
        char c = evt.getKeyChar();
        if ((c <'0' || c > '9') || (PasswordUsuario.getText().length()>=4)) evt.consume();
    }//GEN-LAST:event_PasswordUsuarioKeyTyped

    private void txtCuentaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCuentaUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCuentaUsuarioActionPerformed

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        // Este evento permite controlar la cantidad maxima de caracteres permitidos
        //y que solo se permita el ingreso de letras en el Apellido:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)||
            (c >= 123 && c <= 179)|| (c >= 181 && c <= 208)||
            (c >= 210 && c <= 240)|| (c >= 242 && c <= 255) || (txtApellido.getText().length()>=45))evt.consume();
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyReleased
        // Este evento ejecuta sentencias y metodos que permiten controlar los campos
        // del nombre y apellido para que generen la cuenta de usuario de forma
        // automatica
        try{
            if (!"".equals(txtApellido.getText()) && !btnEditarUsuario.isEnabled()){
                String Nombre   = txtNombre.getText();
                String Apellido = txtApellido.getText();
                txtCuentaUsuario.setText(FormarCuentaUsuaria(Nombre, Apellido));
            }
        }
        catch(Exception e)
        {

        }
    }//GEN-LAST:event_txtApellidoKeyReleased

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // Este evento permite controlar la cantidad maxima de caracteres permitidos
        //y que solo se permita el ingreso de letras en el Nombre:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)||
            (c >= 123 && c <= 179)|| (c >= 181 && c <= 208)||
            (c >= 210 && c <= 240)|| (c >= 242 && c <= 255) || (txtNombre.getText().length()>=45))evt.consume();
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        // Este evento ejecuta sentencias y metodos que permiten controlar los campos
        // del nombre y apellido para que generen la cuenta de usuario de forma
        // automatica
        try {
            if ((!btnEditarUsuario.isEnabled()) && ("".equals(txtNombre.getText()))){
                txtCuentaUsuario.setText(txtApellido.getText());
            }
            else if ((txtNombre.getText().length() > 0)&& (txtApellido.getText().length() > 0) && !btnEditarUsuario.isEnabled()){
                String Nombre   = txtNombre.getText();
                String Apellido = txtApellido.getText();
                txtCuentaUsuario.setText(FormarCuentaUsuaria(Nombre, Apellido));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void txtPrecioPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioPackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioPackActionPerformed

    private void txtNombrePackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrePackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombrePackActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnEditarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarBancoActionPerformed
        // TODO add your handling code here:
        int estado = 0;
        if (ComboBoxEstadoBanco.getSelectedItem() == "Activo") {
                estado = 1;
            }
        Bancos ClaseBanco = new Bancos();
        ClaseBanco.setEstadoBanco(estado);
        ClaseBanco.setIdentificadorBanco(Integer.parseInt(txtCodigoBanco.getText()));        
        if (!"".equals(txtNombreBanco.getText()) && ((BancoNegocio.BuscarBancoEstado(ClaseBanco) == false) || (BancoNegocio.BuscarBanco(txtNombreBanco.getText())== false))) {
            String nombreBanco = txtNombreBanco.getText();
            nombreBanco = nombreBanco.toUpperCase();
            ClaseBanco.setNombreBanco(nombreBanco);
            if (BancoNegocio.ModificarBanco(ClaseBanco)) {
                LimpiarFormularioBanco();
                LimpiarTablaBanco();
                ListarBancoVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");            }
        } else {
            if ("".equals(txtNombreBanco.getText())){
               JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar"); 
            }
            else{
               JOptionPane.showMessageDialog(null, "El nombre del banco: "+txtNombreBanco.getText()+", esta registrado");
            }
        }   
        
    }//GEN-LAST:event_btnEditarBancoActionPerformed

    private void btnGuardarCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCanalActionPerformed
        // TODO add your handling code here:
        String nombreCanal = txtNombreCanal.getText();
        nombreCanal = nombreCanal.toUpperCase();
        if ((txtNombreCanal.getText().isEmpty())) {
            LimpiarFormularioCanal();
            JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre de canal");
        }
        else if (MediosNegocio.BuscarCanal(nombreCanal)){
            LimpiarFormularioCanal();
            JOptionPane.showMessageDialog(null, "El nombre del canal: "+nombreCanal+", esta registrado");
        }
        else {
            int estado = 1;
            Canal ClaseMedios = new Canal();
            ClaseMedios.setNombreCanal(nombreCanal);
            ClaseMedios.setEstadoCanal(estado);
            if (MediosNegocio.GuardarCanal(ClaseMedios)) {
                LimpiarFormularioCanal();
                LimpiarTablaCanal();
                ListarCanalesVista();
                JOptionPane.showMessageDialog(null, "Canal Ingresado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }
    }//GEN-LAST:event_btnGuardarCanalActionPerformed

    private void btnCancelarCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCanalActionPerformed
        // TODO add your handling code here:
        txtBuscarCanal.setText("");
        LimpiarFormularioCanal();
        LimpiarTablaCanal();
        ListarCanalesVista();
    }//GEN-LAST:event_btnCancelarCanalActionPerformed

    private void txtNombreCanalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCanalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)||
            (c >= 123 && c <= 179)|| (c >= 181 && c <= 208)||
            (c >= 210 && c <= 240)|| (c >= 242 && c <= 255) || (txtNombreCanal.getText().length()>=45))evt.consume();
    }//GEN-LAST:event_txtNombreCanalKeyTyped

    private void txtNombreCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCanalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCanalActionPerformed

    private void txtCodigoCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoCanalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoCanalActionPerformed

    private void txtCelularClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularClienteActionPerformed

    private void txtDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDVActionPerformed

    private void btnEditarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCategoriaActionPerformed
        // TODO add your handling code here:
        int estado = 0;   
        if (ComboBoxEstadoCategoria.getSelectedItem() == "Activo") {
                estado = 1;
            }
        Categoria ClaseCategoria = new Categoria();
        ClaseCategoria.setEstadoCategoria(estado);
        ClaseCategoria.setIdentificadorCategoria(Integer.parseInt(txtCodigoCategoria.getText()));
        if (!"".equals(txtNombreCategoria.getText()) && (CategoriaNegocio.BuscarCategoriaEstado(ClaseCategoria) == false) || (CategoriaNegocio.BuscarCategoria(txtNombreCategoria.getText())== false)) {
            String nombreCategoria = txtNombreCategoria.getText();
            ClaseCategoria.setNombreCategoria(nombreCategoria.toUpperCase());
            ClaseCategoria.setLiteralCategoria(nombreCategoria.substring(0, 3));
            if (CategoriaNegocio.ModificarCategoria(ClaseCategoria)) {
                LimpiarFormularioCategoria();
                LimpiarTablaCategoria();
                ListarCategoriaVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");            }
        } else {
            if ("".equals(txtNombreCategoria.getText())){
               JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar"); 
            }
            else{
               JOptionPane.showMessageDialog(null, "El nombre de la categoría: "+txtNombreCategoria.getText()+", esta registrado");
            }
        }          
    }//GEN-LAST:event_btnEditarCategoriaActionPerformed

    private void btnDesactivarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarBancoActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreBanco.getText())) {
            int estado = 0;
            Bancos ClaseBanco = new Bancos();
            ClaseBanco.setEstadoBanco(estado);            
            ClaseBanco.setIdentificadorBanco(Integer.parseInt(txtCodigoBanco.getText()));
            if (BancoNegocio.DesactivarBanco(ClaseBanco)){
                LimpiarFormularioBanco();
                LimpiarTablaBanco();
                ListarBancoVista();    
                JOptionPane.showMessageDialog(null, "Banco Desactivado Exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
                
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        }           
    }//GEN-LAST:event_btnDesactivarBancoActionPerformed

    private void btnEditarComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarComunaActionPerformed
        // TODO add your handling code here:
        int estado = 0;   
        if (ComboBoxEstadoComuna.getSelectedItem() == "Activo") {
                estado = 1;
            }
        Comuna ClaseComuna = new Comuna();
        ClaseComuna.setEstadoComuna(estado);
        ClaseComuna.setidentificadorComuna(Integer.parseInt(txtCodigoComuna.getText()));
        if (!"".equals(txtNombreComuna.getText()) && (ComunasNegocio.BuscarComunaEstado(ClaseComuna)== false) || (ComunasNegocio.BuscarComuna(txtNombreComuna.getText())== false)) {
            String nombreComuna = txtNombreComuna.getText();
            nombreComuna = nombreComuna.toUpperCase();
            ClaseComuna.setNombreComuna(nombreComuna);
            if (ComunasNegocio.ModificarComuna(ClaseComuna)) {
                LimpiarFormularioComuna();
                LimpiarTablaComuna();
                ListarComunasVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");            }
        } else {
            if ("".equals(txtNombreComuna.getText())){
               JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar"); 
            }
            else{
               JOptionPane.showMessageDialog(null, "El nombre de comuna: "+txtNombreComuna.getText()+", esta registrado");
            }
        }        
        
    }//GEN-LAST:event_btnEditarComunaActionPerformed

    private void btnDesactivarComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarComunaActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreComuna.getText())) {
            int estado = 0;
            Comuna ClaseComuna = new Comuna();
            ClaseComuna.setEstadoComuna(estado);            
            ClaseComuna.setidentificadorComuna(Integer.parseInt(txtCodigoComuna.getText()));
            if (ComunasNegocio.DesactivarComuna(ClaseComuna)){
                LimpiarFormularioComuna();
                LimpiarTablaComuna();
                ListarComunasVista();    
                JOptionPane.showMessageDialog(null, "Comuna Desactivada Exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
                
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        }        
        
    }//GEN-LAST:event_btnDesactivarComunaActionPerformed

    private void btnEditarCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCanalActionPerformed
        // TODO add your handling code here:
        int estado = 0;
        if (ComboBoxEstadoCanal.getSelectedItem() == "Activo") {
                estado = 1;
            }   
        Canal ClaseMedios = new Canal();
        ClaseMedios.setEstadoCanal(estado);
        ClaseMedios.setIdentificadorCanal(Integer.parseInt(txtCodigoCanal.getText()));         
        if (!"".equals(txtNombreCanal.getText()) && (MediosNegocio.BuscarCanalEstado(ClaseMedios) == false) || (MediosNegocio.BuscarCanal(txtNombreCanal.getText()) == false)) {
            String nombreCanal = txtNombreCanal.getText();
            nombreCanal = nombreCanal.toUpperCase();            
            ClaseMedios.setNombreCanal(nombreCanal);
           if (MediosNegocio.ModificarCanal(ClaseMedios)) {
                LimpiarFormularioCanal();
                LimpiarTablaCanal();
                ListarCanalesVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");            }
        } else {
            if ("".equals(txtNombreCanal.getText())){
               JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar"); 
            }
            else{
               JOptionPane.showMessageDialog(null, "El nombre del canal: "+txtNombreCanal.getText()+", esta registrado");
            }
            
        }
        
    }//GEN-LAST:event_btnEditarCanalActionPerformed

    private void btnDesactivarCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarCanalActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreCanal.getText())) {
            int estado = 0;
            Canal ClaseMedios = new Canal();
            ClaseMedios.setEstadoCanal(estado);            
            ClaseMedios.setIdentificadorCanal(Integer.parseInt(txtCodigoCanal.getText()));
            if (MediosNegocio.DesactivarCanal(ClaseMedios)){
                LimpiarFormularioCanal();
                LimpiarTablaCanal();
                ListarCanalesVista();    
                JOptionPane.showMessageDialog(null, "Canal Desactivado Exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
                
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        }
        
    }//GEN-LAST:event_btnDesactivarCanalActionPerformed

    private void txtBuscarCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCategoriaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCategoriaMouseClicked

    private void TablaCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaCategoriaMouseClicked
        // TODO add your handling code here:
        int fila = TablaCategoria.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Categoria no Seleccionado");
        } else {
            btnEditarCategoria.setEnabled(true);
            btnGuardarCategoria.setEnabled(false);
            int identificador = Integer.parseInt((String) TablaCategoria.getValueAt(fila, 0).toString());
            String Nombre = (String) TablaCategoria.getValueAt(fila, 1);
            String Estado = (String) TablaCategoria.getValueAt(fila, 2);
            if ("Inactivo".equals(Estado)) {
                ComboBoxEstadoCategoria.setEnabled(true);
                btnDesactivarCategoria.setEnabled(false);
            } else {
                btnDesactivarCategoria.setEnabled(true);
                ComboBoxEstadoCategoria.setEnabled(false);
            }
            txtCodigoCategoria.setText("" + identificador);
            txtNombreCategoria.setText(Nombre);
            ComboBoxEstadoCategoria.setSelectedItem(Estado);
        }     
    }//GEN-LAST:event_TablaCategoriaMouseClicked

    private void txtBuscarCategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCategoriaKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarCategoria.getText())){ 
           LimpiarFormularioCategoria();
           LimpiarTablaCategoria();
           CategoriaNegocio.FiltrarCategoria(TablaCategoria, txtBuscarCategoria.getText());
           }
        else{
            LimpiarFormularioCategoria();
            LimpiarTablaCategoria();
            ListarCategoriaVista();   
        }
    }//GEN-LAST:event_txtBuscarCategoriaKeyReleased

    private void btnDesactivarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarCategoriaActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreCategoria.getText())) {
            int estado = 0;
            Categoria ClaseCategoria = new Categoria();
            ClaseCategoria.setEstadoCategoria(estado);            
            ClaseCategoria.setIdentificadorCategoria(Integer.parseInt(txtCodigoCategoria.getText()));
            if (CategoriaNegocio.DesactivarCategoria(ClaseCategoria)){
                LimpiarFormularioCategoria();
                LimpiarTablaCategoria();
                ListarCategoriaVista();    
                JOptionPane.showMessageDialog(null, "Categoria Desactivada Exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
                
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        }        
    }//GEN-LAST:event_btnDesactivarCategoriaActionPerformed

    private void ComboBoxCategoriaArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboBoxCategoriaArticuloMouseClicked
        // TODO add your handling code here:
        //CategoriaNegocio.CargarCategoria(ComboBoxCategoriaArticulo);
    
    }//GEN-LAST:event_ComboBoxCategoriaArticuloMouseClicked

    private void btnCancelarArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarArticulosActionPerformed
        // TODO add your handling code here:
        txtBuscarArticulo.setText("");
        LimpiarFormularioArticulos();
        LimpiarTablaArticulos();
        ListarArticulosVista();        
    }//GEN-LAST:event_btnCancelarArticulosActionPerformed

    private void btnGuardarArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarArticulosActionPerformed
        // TODO add your handling code here:
        Articulos ClaseArticulos = new Articulos();
        java.util.Date date = new java.util.Date(); 
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Esta es la fecha:"+fecha.format(DateFVArticulo.getDate()));
        int idCategoria = CategoriaNegocio.BuscarIdCategoria((String)(ComboBoxCategoriaArticulo.getSelectedItem()));
        int idProveedor = ProveedorNegocio.BuscarIdProveedor((String)(ComboBoxProveedorArticulo.getSelectedItem()));
        String nombreArticulo = txtNombreArticulo.getText();  
        String MarcaArticulo = txtMarcaArticulo.getText();
        ClaseArticulos.setNombreArticulo(nombreArticulo.toUpperCase());
        ClaseArticulos.setIdCategoriaArticulo(idCategoria);
        ClaseArticulos.setIdProveedorArticulo(idProveedor); 
        //System.out.println("Esta es la fecha"+DateFVArticulo.getJCalendar());
        if ((txtNombreArticulo.getText().isEmpty()) || (txtUnidadArticulo.getText().isEmpty())
          || (txtMarcaArticulo.getText().isEmpty()) || (ComboBoxCategoriaArticulo.getSelectedItem() == "Seleccione Categoria") 
          || (DateFVArticulo.getDate() == null) || (ComboBoxProveedorArticulo.getSelectedItem() == "Seleccione Proveedor")) {
            //LimpiarFormularioArticulos();
            JOptionPane.showMessageDialog(null, "Debe Ingresar los datos solicitados", "Error", JOptionPane.ERROR_MESSAGE);
        }     
        else if (ArticulosNegocio.BuscarArticulo(ClaseArticulos)){
            LimpiarFormularioArticulos();
            JOptionPane.showMessageDialog(null, "Este artículo: "+nombreArticulo+", esta registrado","Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String codigoArticulo = FormarCodigoArticulo(idCategoria, (String) ComboBoxCategoriaArticulo.getSelectedItem());
            int estado = 1;
            ClaseArticulos.setMarcaArticulo(MarcaArticulo.toUpperCase());
            ClaseArticulos.setStockArticulo(Integer.parseInt(txtUnidadArticulo.getText()));
            ClaseArticulos.setIdCategoriaArticulo(idCategoria);
            ClaseArticulos.setIdProveedorArticulo(idProveedor);
            ClaseArticulos.setFechaVencimientoArticulo(fecha.format(DateFVArticulo.getDate()));  
            ClaseArticulos.setEstadoArticulo(estado);
            ClaseArticulos.setCodigoLetraArticulo(codigoArticulo);
            if (ArticulosNegocio.GuardarArticulo(ClaseArticulos)) {
                LimpiarFormularioArticulos();
                LimpiarTablaArticulos();
                ListarArticulosVista();
                JOptionPane.showMessageDialog(null, "Artículo Ingresado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }
    }//GEN-LAST:event_btnGuardarArticulosActionPerformed

    private void txtIdentificadorArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdentificadorArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdentificadorArticuloActionPerformed
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxCategoriaArticulo;
    private javax.swing.JComboBox<String> ComboBoxEstado;
    private javax.swing.JComboBox<String> ComboBoxEstadoArticulo;
    private javax.swing.JComboBox<String> ComboBoxEstadoBanco;
    private javax.swing.JComboBox<String> ComboBoxEstadoCanal;
    private javax.swing.JComboBox<String> ComboBoxEstadoCategoria;
    private javax.swing.JComboBox<String> ComboBoxEstadoComuna;
    private javax.swing.JComboBox<String> ComboBoxProveedorArticulo;
    public javax.swing.JComboBox<String> ComboBoxRedSocialCliente;
    private com.toedter.calendar.JDateChooser DateFVArticulo;
    private javax.swing.JPasswordField PasswordUsuario;
    private javax.swing.JTable TablaArticulos;
    private javax.swing.JTable TablaBanco;
    private javax.swing.JTable TablaCanal;
    private javax.swing.JTable TablaCategoria;
    public javax.swing.JTable TablaClientes;
    private javax.swing.JTable TablaComuna;
    private javax.swing.JTable TablaEstadoVenta;
    private javax.swing.JTable TablaPack;
    private javax.swing.JTable TablaUsuarios;
    private javax.swing.JTable Tabla_Provedores;
    private javax.swing.JLabel Titulo_Prov;
    private javax.swing.JLabel Titulo_Prov10;
    private javax.swing.JLabel Titulo_Prov11;
    private javax.swing.JLabel Titulo_Prov7;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnCancelarArticulos;
    private javax.swing.JButton btnCancelarBanco;
    private javax.swing.JButton btnCancelarCanal;
    private javax.swing.JButton btnCancelarCategoria;
    public javax.swing.JButton btnCancelarCliente;
    private javax.swing.JButton btnCancelarComuna;
    private javax.swing.JButton btnCancelarEstadoVenta;
    public static javax.swing.JButton btnCancelarProveedor;
    private javax.swing.JButton btnCancelarUsuario;
    private javax.swing.JButton btnComprarProveedor;
    private javax.swing.JButton btnDesactivarArticulos;
    private javax.swing.JButton btnDesactivarBanco;
    private javax.swing.JButton btnDesactivarCanal;
    private javax.swing.JButton btnDesactivarCategoria;
    public javax.swing.JButton btnDesactivarCliente;
    private javax.swing.JButton btnDesactivarComuna;
    private javax.swing.JButton btnDesactivarEstadoVenta;
    private javax.swing.JButton btnDesactivarPack;
    private javax.swing.JButton btnDesactivarProveedor;
    private javax.swing.JButton btnDesactivarUsuario;
    private javax.swing.JButton btnEditarArticulos;
    private javax.swing.JButton btnEditarBanco;
    private javax.swing.JButton btnEditarCanal;
    private javax.swing.JButton btnEditarCategoria;
    public javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarComuna;
    private javax.swing.JButton btnEditarEstadoVenta;
    private javax.swing.JButton btnEditarPack;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton btnGuardarArticulos;
    private javax.swing.JButton btnGuardarBanco;
    private javax.swing.JButton btnGuardarCanal;
    private javax.swing.JButton btnGuardarCategoria;
    public javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarComuna;
    private javax.swing.JButton btnGuardarEstadoVenta;
    public static javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnGuardarUsuario;
    public javax.swing.JButton btnVentaCliente;
    private javax.swing.JButton buscar;
    private javax.swing.JButton buscar1;
    private javax.swing.JPanel clientes;
    private javax.swing.JTabbedPane compras;
    private javax.swing.JButton configuracion;
    private javax.swing.JLabel dreamGifts;
    private javax.swing.JButton faq;
    private javax.swing.JButton faq1;
    private javax.swing.JTabbedPane informes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JFrame jFrame6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuBar jMenuBar5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    public javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JPopupMenu jPopupMenu5;
    private javax.swing.JPopupMenu jPopupMenu6;
    private javax.swing.JPopupMenu jPopupMenu7;
    private javax.swing.JPopupMenu jPopupMenu8;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTabbedPane maestros;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JButton notificaciones;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarArticulo;
    private javax.swing.JTextField txtBuscarBanco;
    private javax.swing.JTextField txtBuscarCanal;
    private javax.swing.JTextField txtBuscarCategoria;
    private javax.swing.JTextField txtBuscarComuna;
    private javax.swing.JTextField txtBuscarEstadoVenta;
    private javax.swing.JTextField txtBuscarPack;
    private javax.swing.JTextField txtBuscarProveedor;
    private javax.swing.JTextField txtCelularCliente;
    public javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCodigoArticulo;
    private javax.swing.JTextField txtCodigoBanco;
    private javax.swing.JTextField txtCodigoCanal;
    private javax.swing.JTextField txtCodigoCategoria;
    private javax.swing.JTextField txtCodigoComuna;
    private javax.swing.JTextField txtCodigoEstadoVenta;
    private javax.swing.JTextField txtCuentaUsuario;
    private javax.swing.JTextField txtDV;
    public static javax.swing.JTextField txtDireccionProveedor;
    public javax.swing.JTextField txtEmailCliente;
    public static javax.swing.JTextField txtEmailProveedor;
    private javax.swing.JTextField txtEstadoPagoVenta;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtIdentificadorArticulo;
    private javax.swing.JTextField txtMarcaArticulo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreArticulo;
    private javax.swing.JTextField txtNombreBanco;
    private javax.swing.JTextField txtNombreCanal;
    private javax.swing.JTextField txtNombreCategoria;
    public javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreComuna;
    private javax.swing.JTextField txtNombrePack;
    public static javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecioPack;
    public static javax.swing.JTextField txtRazonSocialProveedor;
    public javax.swing.JTextField txtRutCliente;
    public static javax.swing.JTextField txtRutProveedor;
    public javax.swing.JTextField txtTelefonoCliente;
    public static javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JTextField txtUnidadArticulo;
    private javax.swing.JTextField txtbuscarUsuario;
    private javax.swing.JTabbedPane ventas;
    // End of variables declaration//GEN-END:variables
}

