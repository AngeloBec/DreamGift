package vista;

import Entidades.Articulos;
import Entidades.Bancos;
import Entidades.Canal;
import Entidades.Categoria;
import Entidades.Comuna;
import Entidades.EstadoVenta;
import Entidades.Pack;
import Entidades.PackArticulo;
import Entidades.Proveedor;
import Entidades.Usuario;
import Entidades.Clientes;
import Negocio.NegocioArticulo;
import Negocio.NegocioBancos;
import Negocio.NegocioCanales;
import Negocio.NegocioCategoria;
import Negocio.NegocioClientes;
import Negocio.NegocioComuna;
import Negocio.NegocioEstadoVenta;
import Negocio.NegocioPack;
import Negocio.NegocioPackArticulo;
import Negocio.NegocioProveedor;
import Negocio.NegocioUsuario;
import Negocio.ValidacionRut;
import conexion.Conexion;
import java.awt.Color;
import java.awt.Font;
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

    DefaultTableModel modelo = new DefaultTableModel();
    private NegocioUsuario UserNegocio = new NegocioUsuario();
    private NegocioCanales MediosNegocio = new NegocioCanales();
    private NegocioComuna ComunasNegocio = new NegocioComuna();
    private NegocioBancos BancoNegocio = new NegocioBancos();
    private NegocioCategoria CategoriaNegocio = new NegocioCategoria();
    private NegocioProveedor ProveedorNegocio = new NegocioProveedor();
    private NegocioArticulo ArticulosNegocio = new NegocioArticulo();
    private NegocioEstadoVenta EstadoVentaNegocio = new NegocioEstadoVenta();
    private NegocioPack PackNegocio = new NegocioPack();
    private NegocioPackArticulo PackArticuloNegocio = new NegocioPackArticulo();
    private NegocioClientes ClientesNegocio = new NegocioClientes();
    public int cantidadRegistrosInicial = 0;
    public boolean modificar = false;

    public Vista() {
        initComponents();
        this.setLocationRelativeTo(null);
        modelo.addColumn("Código Artículo");
        modelo.addColumn("Artículo");
        modelo.addColumn("Unidades");
        TablaPackConArticulos.setModel(modelo);
        ListarUsuariosVista();
        ListarCanalesVista();
        ListarComunasVista();
        ListarBancoVista();
        ListarCategoriaVista();
        ListarArticulosVista();
        ListarEstadoVentaVista();
        ListarPackVista();
        ListarProveedorVista();
        ListarClienteVista();

    }

    public void ListarUsuariosVista() {
        HeaderTablas(TablaUsuarios);
        UserNegocio.ListarUsuario(TablaUsuarios);
    }

    public void ListarCanalesVista() {
        HeaderTablas(TablaCanal);
        MediosNegocio.ListarCanal(TablaCanal);
    }

    public void ListarComunasVista() {
        HeaderTablas(TablaComuna);
        ComunasNegocio.ListarComuna(TablaComuna);
    }

    public void ListarBancoVista() {
        HeaderTablas(TablaBanco);
        BancoNegocio.ListarBanco(TablaBanco);
    }

    public void ListarCategoriaVista() {
        HeaderTablas(TablaCategoria);
        CategoriaNegocio.ListarCategoria(TablaCategoria);
        CategoriaNegocio.CargarCategoria(ComboBoxCategoriaArticulo);
        //ProveedorNegocio.CargarProveedor(ComboBoxProveedorArticulo);

    }

    public void ListarArticulosVista() {
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formateoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String Formateo = "2400-12-31";
        try {
            fecha = (java.util.Date) formateoFecha.parse(Formateo);
        } catch (Exception e) {
        }
        DateFVArticulo.setDate(fecha);
        HeaderTablas(TablaArticulos);
        ArticulosNegocio.ListarArticulos(TablaArticulos);
        LimpiarTablaPackSeleccionArticulos();
        HeaderTablas(TablaPackSeleccionArticulos);
        ArticulosNegocio.ListarArticulosPack(TablaPackSeleccionArticulos);
//        ProveedorNegocio.CargarProveedor(ComboBoxProveedorArticulo);
    }

    public void ListarEstadoVentaVista() {
        HeaderTablas(TablaEstadoVenta);
        EstadoVentaNegocio.ListarEstadoVenta(TablaEstadoVenta);
    }

    public void ListarPackVista() {
        LimpiarTablaPackSeleccionArticulos();
        HeaderTablas(TablaPackSeleccionArticulos);
        ArticulosNegocio.ListarArticulosPack(TablaPackSeleccionArticulos);
        HeaderTablas(TablaPack);
        PackNegocio.ListarPack(TablaPack);

    }

    public void ListarProveedorVista() {
        HeaderTablas(TablaProveedor);
        ProveedorNegocio.ListarProveedor(TablaProveedor);
        ProveedorNegocio.CargarProveedor(ComboBoxProveedorArticulo);
    }

    public void ListarClienteVista() {
        DeshabilitarCamposClientes();
        ClientesNegocio.ListarCliente(TablaClientes);
        CargarCanal();
    }

    public void CargarCanal() {
        ClientesNegocio.CargarCanalCliente(ComboBoxRedSocialCliente);
        ComboBoxRedSocialCliente.addItem("Seleccionar Canal");
        ComboBoxRedSocialCliente.setSelectedItem("Seleccionar Canal");

    }

    /*LimpiarFormularioUsuario(): Este metodo limpia o deja en en blanco
    todos los campos que contiene el formulario de Usuarios
     */
    public void HeaderTablas(JTable Tabla) {
        Tabla.getTableHeader().setFont(new Font("Segue UI", Font.BOLD, 11));
        Tabla.getTableHeader().setOpaque(false);
        Tabla.getTableHeader().setBackground(new Color(32, 136, 203));
        Tabla.getTableHeader().setForeground(new Color(255, 255, 255));
        Tabla.setRowHeight(16);
    }

    public void LimpiarFormularioUsuario() {
        txtIdUsuario.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCuentaUsuario.setText("");
        PasswordUsuario.setText("");
        ComboBoxEstado.setSelectedIndex(0);
        ComboBoxEstado.setEnabled(false);
        btnGuardarUsuario.setEnabled(true);
        btnEditarUsuario.setEnabled(false);
        btnDesactivarUsuario.setEnabled(false);
    }

    public void LimpiarFormularioCanal() {
        txtCodigoCanal.setText("");
        txtNombreCanal.setText("");
        ComboBoxEstadoCanal.setSelectedIndex(0);
        ComboBoxEstadoCanal.setEnabled(false);
        btnGuardarCanal.setEnabled(true);
        btnEditarCanal.setEnabled(false);
        btnDesactivarCanal.setEnabled(false);
    }

    public void LimpiarFormularioComuna() {
        txtCodigoComuna.setText("");
        txtNombreComuna.setText("");
        ComboBoxEstadoComuna.setSelectedIndex(0);
        ComboBoxEstadoComuna.setEnabled(false);
        btnGuardarComuna.setEnabled(true);
        btnEditarComuna.setEnabled(false);
        btnDesactivarComuna.setEnabled(false);
    }

    public void LimpiarFormularioBanco() {
        txtCodigoBanco.setText("");
        txtNombreBanco.setText("");
        ComboBoxEstadoBanco.setSelectedIndex(0);
        ComboBoxEstadoBanco.setEnabled(false);
        btnGuardarBanco.setEnabled(true);
        btnEditarBanco.setEnabled(false);
        btnDesactivarBanco.setEnabled(false);
    }

    public void LimpiarFormularioCategoria() {
        txtCodigoCategoria.setText("");
        txtNombreCategoria.setText("");
        ComboBoxCategoriaArticulo.removeAllItems(); //Hacer lo mismo en proveedor
        ComboBoxProveedorArticulo.removeAllItems();
        ComboBoxEstadoCategoria.setSelectedIndex(0);
        ComboBoxEstadoCategoria.setEnabled(false);
        btnGuardarCategoria.setEnabled(true);
        btnEditarCategoria.setEnabled(false);
        btnDesactivarCategoria.setEnabled(false);
    }

    public void LimpiarFormularioArticulos() {
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formateoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String Formateo = "2400-12-31";
        try {
            fecha = (java.util.Date) formateoFecha.parse(Formateo);
        } catch (Exception e) {
        }
        CategoriaNegocio.CargarCategoria(ComboBoxCategoriaArticulo);
        ProveedorNegocio.CargarProveedor(ComboBoxProveedorArticulo);
        txtNombreArticulo.setText("");
        txtUnidadArticulo.setText("");
        txtMarcaArticulo.setText("");
        txtCodigoArticulo.setText("");
        txtIdentificadorArticulo.setText("");
        ComboBoxCategoriaArticulo.setSelectedIndex(0);
        DateFVArticulo.setDate(fecha);
        ComboBoxProveedorArticulo.setSelectedIndex(0);
        ComboBoxEstadoArticulo.setSelectedIndex(0);
        ComboBoxEstadoArticulo.setEnabled(false);
        btnGuardarArticulos.setEnabled(true);
        btnEditarArticulos.setEnabled(false);
        btnDesactivarArticulos.setEnabled(false);
    }

    public void LimpiarFormularioEstadoVenta() {
        txtCodigoEstadoVenta.setText("");
        txtNombreEstadoPagoVenta.setText("");
        ComboBoxEstadoVenta.setSelectedIndex(0);
        ComboBoxEstadoVenta.setEnabled(false);
        btnGuardarEstadoVenta.setEnabled(true);
        btnEditarEstadoVenta.setEnabled(false);
        btnDesactivarEstadoVenta.setEnabled(false);
    }

    public void LimpiarFormularioPack() {
        txtCodigoPack.setText("");
        txtNombrePack.setText("");
        txtPrecioPack.setText("");
        txtStockPack.setText("");
        txtUnidadesArticulosPack.setText("");
        txtUnidadesArticulosPack.setEnabled(false);
        ComboBoxEstadoPack.setSelectedIndex(0);
        ComboBoxEstadoPack.setEnabled(false);
        btnAgregarArticuloPack.setEnabled(false);
        btnEliminarArticuloPack.setEnabled(false);
        btnCrearPack.setEnabled(true);
        btnEditarPack.setEnabled(false);
        btnDesactivarPack.setEnabled(false);
        TablaPackConArticulos.setEnabled(true);
        TablaPackSeleccionArticulos.setEnabled(true);
        LimpiarTablaPackConArticulos();
    }

    public void LimpiarFormularioProveedor() {
        txtRutProveedor.setText("");
        txtNombreContacto.setText("");
        combobxEstadoProveedor.setSelectedIndex(0);
        txtDireccionProveedor.setText("");
        txtRazonSocialProveedor.setText("");
        txtProveedorTelefono.setText("");
        txtEmailProveedor.setText("");
        combobxEstadoProveedor.setEnabled(false);
        btnGuardarProveedor.setEnabled(true);
        btnEditarProveedor.setEnabled(false);
        btnDesactivarProveedor.setEnabled(false);
        txtDvProveedor.setText("");
        txtIdProveedor.setText("");
        ComboBoxProveedorArticulo.removeAllItems();
    }

    public void LimpiarFormularioClientes() {
        txtRutCliente.setText("");
        txtDVCliente.setText("");
        txtNombreCliente.setText("");
        txtApellidoCliente.setText("");
        txtEmailCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDireccionCliente.setText("");
        jDateCliente.setDate(null);
        ComboBoxRedSocialCliente.setSelectedItem("Seleccionar Canal");
        btnGuardarCliente.setEnabled(true);
        btnEditarCliente.setEnabled(false);
        btnDesactivarCliente.setEnabled(false);
        combobxEstadoCliente.setEnabled(false);
    }

    /*VerificarCuentaUsuario: Esta Función se conecta con la BD de dreamgifts mediante
    la clase conexion, para luego ejecutar un sentencia SQL de consulta para validar
    que la cuenta de usuario generada si existe o no en la BD, retornando verdadero
    o falso segun sea el caso
    Maneja Caso de Excepción por si falla la conexio hacia la BD
     */
    public boolean VerificarCuentaUsuario(String Buscar) {
        boolean busqueda = false;
        if (UserNegocio.BuscarUsuario(Buscar)) {
            busqueda = true;
            return busqueda;
        } else {
            return busqueda;
        }

    }

    /*FormarCuentaUsuaria(): Este funcion genera la cuenta usuario de cada persona,
      con la union de las dos primeras letras del nombre y su apellido.
      En conjunto con otra funcion permiten generar una cuenta de usuario unica
     */
    public String FormarCodigoArticulo(int idCategoria, String NombreCategoria, boolean edicion) {
        String codigoArticulo = "";
        String codigoCategoria = CategoriaNegocio.BuscarCodigoCategoria(idCategoria);
        if (edicion) {
            codigoArticulo = codigoCategoria + txtIdentificadorArticulo.getText();
        } else {
            int idArticulo = ArticulosNegocio.CantidadArticulos() + 1;
            codigoArticulo = codigoCategoria + idArticulo;
        }
        return codigoArticulo;
    }

    public String FormarCuentaUsuaria(String Nombre, String Apellido) {
        int i = 0;
        String ParteUno = Nombre.substring(0, 2);
        String ParteDos = Apellido;
        txtCuentaUsuario.setText(ParteUno.toLowerCase() + ParteDos.toLowerCase());
        String Buscar = txtCuentaUsuario.getText();
        //System.out.println("Nombre de Usuario"+ VerificarCuentaUsuario(Buscar));
        while (VerificarCuentaUsuario(Buscar)) {
            i++;
            Buscar = txtCuentaUsuario.getText() + i;
        }
        return Buscar;
    }

    public void DeshabilitarCamposClientes() {
        txtNombreCliente.setEnabled(false);
        txtApellidoCliente.setEnabled(false);
        txtTelefonoCliente.setEnabled(false);
        ComboBoxRedSocialCliente.setEnabled(false);
        txtEmailCliente.setEnabled(false);
        txtDireccionCliente.setEnabled(false);
        jDateCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(false);

    }

    public void HabilitarCamposClientes() {
        txtNombreCliente.setEnabled(true);
        txtApellidoCliente.setEnabled(true);
        txtTelefonoCliente.setEnabled(true);
        ComboBoxRedSocialCliente.setEnabled(true);
        txtEmailCliente.setEnabled(true);
        txtDireccionCliente.setEnabled(true);
        jDateCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(true);

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

    public void LimpiarTablaEstadoVenta() {
        DefaultTableModel model1 = (DefaultTableModel) TablaEstadoVenta.getModel();
        while (TablaEstadoVenta.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }

    public void LimpiarTablaPack() {
        DefaultTableModel model1 = (DefaultTableModel) TablaPack.getModel();
        while (TablaPack.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }

    public void LimpiarTablaPackSeleccionArticulos() {
        DefaultTableModel model1 = (DefaultTableModel) TablaPackSeleccionArticulos.getModel();
        while (TablaPackSeleccionArticulos.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }

    public void LimpiarTablaPackConArticulos() {
        DefaultTableModel model1 = (DefaultTableModel) TablaPackConArticulos.getModel();
        while (TablaPackConArticulos.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }

    public void LimpiarTablaProveedor() {
        DefaultTableModel model1 = (DefaultTableModel) TablaProveedor.getModel();
        while (TablaProveedor.getRowCount() > 0) {
            model1.removeRow(0);
        }
    }

    public void LimpiarTablaCliente() {
        DefaultTableModel model1 = (DefaultTableModel) TablaClientes.getModel();
        while (TablaClientes.getRowCount() > 0) {
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
        jPanel37 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        txtNumeroPedidoVenta = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtNombreClienteVenta = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtEmailClienteVenta = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txtRutClienteVenta = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        txtDVClienteVenta = new javax.swing.JTextField();
        btnBuscarVenta = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        txtTelefonoClienteVenta = new javax.swing.JTextField();
        btnGuardarVenta = new javax.swing.JButton();
        btnCancelarVenta = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        txtNombreDestinatario = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtDireccionDestinatario = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        ComboBoxComunaDestinatario = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        txtSaludoDestinatario = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        combobxPackDestinatario = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        combobxHoraEntrega = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        combobxHoraFinEntrega = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtEnvio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnCancelarDatosDestinatario = new javax.swing.JButton();
        btnGuardarDatosDestinatario = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        jDateVenta = new com.toedter.calendar.JDateChooser();
        jPanel39 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        txtNumeroPedidoConf = new javax.swing.JTextField();
        txtRutClienteConf = new javax.swing.JTextField();
        txtNombreClienteConf = new javax.swing.JTextField();
        jDateConfirmacion = new com.toedter.calendar.JDateChooser();
        txtTransaccionConf = new javax.swing.JTextField();
        ComboBoxBancoConf = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        txtBuscarVenta = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        TablaConfirmacion = new javax.swing.JTable();
        jPanel40 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        TablaListadoDestino = new javax.swing.JTable();
        jDateBuscarDestinos = new com.toedter.calendar.JDateChooser();
        jPanel41 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jDateBuscarDespacho = new com.toedter.calendar.JDateChooser();
        jScrollPane18 = new javax.swing.JScrollPane();
        TablaListadoEstadosDespacho = new javax.swing.JTable();
        compras = new javax.swing.JTabbedPane();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        informes = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
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
        btnCancelarCliente = new javax.swing.JButton();
        btnGuardarCliente = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        ComboBoxRedSocialCliente = new javax.swing.JComboBox<>();
        txtDVCliente = new javax.swing.JTextField();
        txtApellidoCliente = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jDateCliente = new com.toedter.calendar.JDateChooser();
        jLabel82 = new javax.swing.JLabel();
        txtDireccionCliente = new javax.swing.JTextField();
        combobxEstadoCliente = new javax.swing.JComboBox<>();
        jLabel83 = new javax.swing.JLabel();
        mensajeRutCliente = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaClientes = new javax.swing.JTable();
        txtBuscarCliente = new javax.swing.JTextField();
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
        txtNombreContacto = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        txtRazonSocialProveedor = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        txtProveedorTelefono = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        txtEmailProveedor = new javax.swing.JTextField();
        btnCancelarProveedor = new javax.swing.JButton();
        btnGuardarProveedor = new javax.swing.JButton();
        txtDvProveedor = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        combobxEstadoProveedor = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        txtIdProveedor = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TablaProveedor = new javax.swing.JTable();
        btnComprarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnDesactivarProveedor = new javax.swing.JButton();
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
        txtIdentificadorArticulo = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        ComboBoxEstadoArticulo = new javax.swing.JComboBox<>();
        jLabel85 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        btnDesactivarArticulos = new javax.swing.JButton();
        btnEditarArticulos = new javax.swing.JButton();
        txtBuscarArticulo = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaArticulos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaPack = new javax.swing.JTable();
        txtBuscarPack = new javax.swing.JTextField();
        btnEditarPack = new javax.swing.JButton();
        btnDesactivarPack = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtNombrePack = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        btnCancelarPack = new javax.swing.JButton();
        btnCrearPack = new javax.swing.JButton();
        txtPrecioPack = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUnidadesArticulosPack = new javax.swing.JTextField();
        btnAgregarArticuloPack = new javax.swing.JButton();
        btnEliminarArticuloPack = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        TablaPackSeleccionArticulos = new javax.swing.JTable();
        jScrollPane16 = new javax.swing.JScrollPane();
        TablaPackConArticulos = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        ComboBoxEstadoPack = new javax.swing.JComboBox<>();
        jLabel86 = new javax.swing.JLabel();
        txtCodigoPack = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        txtStockPack = new javax.swing.JTextField();
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
        jPanel27 = new javax.swing.JPanel();
        txtBuscarComuna = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        TablaComuna = new javax.swing.JTable();
        btnEditarComuna = new javax.swing.JButton();
        btnDesactivarComuna = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtNombreComuna = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtCodigoComuna = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        ComboBoxEstadoComuna = new javax.swing.JComboBox<>();
        btnCancelarComuna = new javax.swing.JButton();
        btnGuardarComuna = new javax.swing.JButton();
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
        txtNombreEstadoPagoVenta = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        btnCancelarEstadoVenta = new javax.swing.JButton();
        btnGuardarEstadoVenta = new javax.swing.JButton();
        txtCodigoEstadoVenta = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        ComboBoxEstadoVenta = new javax.swing.JComboBox<>();
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

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Clientes Solicitantes"));

        jLabel50.setText("Número Pedido");

        txtNumeroPedidoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroPedidoVentaActionPerformed(evt);
            }
        });

        jLabel51.setText("Nombre Cliente");

        txtNombreClienteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteVentaActionPerformed(evt);
            }
        });

        jLabel52.setText("E-Mail");

        txtEmailClienteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailClienteVentaActionPerformed(evt);
            }
        });

        jLabel54.setText("Rut");

        txtRutClienteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutClienteVentaActionPerformed(evt);
            }
        });

        jLabel81.setText("-");

        btnBuscarVenta.setBackground(new java.awt.Color(153, 153, 0));
        btnBuscarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.PNG"))); // NOI18N

        jLabel55.setText("Teléfono");

        txtTelefonoClienteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoClienteVentaActionPerformed(evt);
            }
        });

        btnGuardarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnGuardarVenta.setText("Guardar");

        btnCancelarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarVenta.setText("Cancelar");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(txtEmailClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNumeroPedidoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(btnCancelarVenta)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarVenta))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRutClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDVClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefonoClienteVenta)))
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel50)
                        .addComponent(txtNumeroPedidoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel54)
                        .addComponent(txtRutClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel81)
                        .addComponent(txtDVClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(jLabel55)
                    .addComponent(txtTelefonoClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarVenta)
                    .addComponent(btnGuardarVenta))
                .addContainerGap())
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Destinatario"));

        jLabel58.setText("Nombre Destinatario");

        txtNombreDestinatario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreDestinatarioActionPerformed(evt);
            }
        });

        jLabel59.setText("Fecha de Entrega");

        txtDireccionDestinatario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionDestinatarioActionPerformed(evt);
            }
        });

        jLabel60.setText("Dirección");

        jLabel63.setText("Comuna");

        ComboBoxComunaDestinatario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione Una Comuna", "Las Condes", "Santiago", "San Ramón", "Maipú" }));

        jLabel64.setText("Saludo");

        txtSaludoDestinatario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaludoDestinatarioActionPerformed(evt);
            }
        });

        jLabel61.setText("Pack");

        combobxPackDestinatario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Un Pack", "Pack-01 - Cumpleaños", "Pack-02 - Dia del Niño", "Pack-03 - Dia del Padre" }));

        jLabel62.setText("Hora Inicio Entrega");

        combobxHoraEntrega.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Una Hora", "08:00", "09:00", "10:00", "11:00" }));

        jLabel65.setText("Hora Fin Entrega");

        combobxHoraFinEntrega.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Una Hora", "08:00", "09:00", "10:00", "11:00" }));

        jLabel1.setText("Sub - Total");

        txtSubTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubTotalActionPerformed(evt);
            }
        });

        jLabel2.setText("Envios");

        jLabel3.setText("Total");

        btnCancelarDatosDestinatario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarDatosDestinatario.setText("Cancelar");

        btnGuardarDatosDestinatario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnGuardarDatosDestinatario.setText("Guardar");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addComponent(ComboBoxComunaDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtDireccionDestinatario)))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58)
                                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreDestinatario)
                                    .addComponent(jDateVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSaludoDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(combobxHoraEntrega, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(combobxPackDestinatario, 0, 181, Short.MAX_VALUE))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel3)
                                        .addGap(50, 50, 50))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1))
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                    .addComponent(txtEnvio)
                                    .addComponent(txtSubTotal)))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(combobxHoraFinEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarDatosDestinatario)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarDatosDestinatario)))
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txtNombreDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61)
                    .addComponent(combobxPackDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel59)
                        .addComponent(jLabel62)
                        .addComponent(combobxHoraEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(txtDireccionDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(combobxHoraFinEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelarDatosDestinatario)
                            .addComponent(btnGuardarDatosDestinatario))
                        .addContainerGap())
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(ComboBoxComunaDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(txtSaludoDestinatario)
                                .addGap(31, 31, 31))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel64)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        ventas.addTab("Ventas", jPanel20);

        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder("Confirmar pago cliente"));

        jLabel89.setText("Número de pedido:");

        jLabel90.setText("Rut:");

        jLabel91.setText("Banco");

        jLabel92.setText("Nombre Cliente:");

        jLabel93.setText("Fecha de pago:");

        jLabel94.setText("Codigo de Transacción:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        jButton2.setText("Cancelar");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        jButton3.setText("Confirmar");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel89)
                            .addComponent(jLabel90)
                            .addComponent(jLabel91))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel42Layout.createSequentialGroup()
                                .addComponent(txtNumeroPedidoConf, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(124, 124, 124)
                                .addComponent(jLabel92))
                            .addGroup(jPanel42Layout.createSequentialGroup()
                                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtRutClienteConf)
                                    .addComponent(ComboBoxBancoConf, 0, 151, Short.MAX_VALUE))
                                .addGap(124, 124, 124)
                                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel93)
                                    .addComponent(jLabel94))))
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel42Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombreClienteConf)
                                    .addComponent(jDateConfirmacion, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
                            .addGroup(jPanel42Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtTransaccionConf, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(470, 470, 470)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel93)
                            .addComponent(jDateConfirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel89)
                            .addComponent(jLabel92)
                            .addComponent(txtNumeroPedidoConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreClienteConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel90)
                            .addComponent(txtRutClienteConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel94)
                    .addComponent(txtTransaccionConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel91)
                    .addComponent(ComboBoxBancoConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder("Ventas pendientes de pago"));

        jLabel96.setText("Buscar:");

        TablaConfirmacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Número pedido", "Fecha pedido", "Nombre cliente", "Número teléfono", "Monto", "Pack"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(TablaConfirmacion);

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel96)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane14))
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(txtBuscarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );

        ventas.addTab("Confirmación", jPanel39);

        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder("Despacho"));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel97.setText("Lista destinos despacho por día");

        jLabel98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descargaexcel.png"))); // NOI18N

        jLabel99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descargapdf.png"))); // NOI18N

        TablaListadoDestino.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Registro Venta", "Pack", "Destinatario", "Fecha entrega", "Comuna", "Dirección", "Hora entrega"
            }
        ));
        jScrollPane17.setViewportView(TablaListadoDestino);

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane17)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel98)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel99)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateBuscarDestinos, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel98, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateBuscarDestinos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(302, Short.MAX_VALUE))
        );

        ventas.addTab("Lista Destinos", jPanel40);

        jPanel46.setBorder(javax.swing.BorderFactory.createTitledBorder("Despacho"));

        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descargaexcel.png"))); // NOI18N

        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descargapdf.png"))); // NOI18N

        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel102.setText("Actualización estado despacho");

        TablaListadoEstadosDespacho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Número pedido", "Pack", "Destinatario", "Fecha entrega", "Comuna", "Hora entrega", "Entregado", "Anulado", "Rechazado", "No entregado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane18.setViewportView(TablaListadoEstadosDespacho);

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addComponent(jLabel100)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel101)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                        .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(jDateBuscarDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel100, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateBuscarDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE))
        );

        ventas.addTab("Actualización Despachos", jPanel41);

        jTabbedPane4.addTab("Ventas", ventas);

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        compras.addTab("Ordenes de Compra", jPanel52);

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        compras.addTab("Registro Compra", jPanel53);

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        compras.addTab("Revisión Factura", jPanel54);

        jTabbedPane4.addTab("Compras", compras);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        informes.addTab("Informe de Ventas", jPanel16);

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        informes.addTab("Informe Inventario", jPanel47);

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        informes.addTab("Informe de Vencimiento", jPanel48);

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        informes.addTab("Informe de Proveedores", jPanel49);

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        informes.addTab("Informe de Ventas de Pack", jPanel50);

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        informes.addTab("Informe por Comuna", jPanel51);

        jTabbedPane4.addTab("Informes", informes);

        maestros.setBackground(new java.awt.Color(255, 255, 255));
        maestros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

        jLabel13.setText("Nombre");

        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });
        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        jLabel14.setText("Teléfono");

        txtTelefonoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoClienteActionPerformed(evt);
            }
        });
        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
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
        txtRutCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRutClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutClienteKeyTyped(evt);
            }
        });

        jLabel23.setText("F. Nacimiento");

        btnCancelarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarCliente.setText("Cancelar");
        btnCancelarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarClienteActionPerformed(evt);
            }
        });

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnGuardarCliente.setText("Guardar");
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        jLabel26.setText("-");

        jLabel27.setText("Canal");

        ComboBoxRedSocialCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Canal" }));
        ComboBoxRedSocialCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxRedSocialClienteActionPerformed(evt);
            }
        });

        txtDVCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDVClienteActionPerformed(evt);
            }
        });
        txtDVCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDVClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDVClienteKeyTyped(evt);
            }
        });

        txtApellidoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoClienteKeyTyped(evt);
            }
        });

        jLabel80.setText("Apellido");

        jLabel82.setText("Dirección");

        combobxEstadoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        combobxEstadoCliente.setEnabled(false);

        jLabel83.setText("Estado");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarCliente)
                        .addGap(29, 29, 29)
                        .addComponent(btnGuardarCliente))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel80)
                                            .addComponent(jLabel13))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                                            .addComponent(txtApellidoCliente))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel22)
                                .addGap(79, 79, 79)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(mensajeRutCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtRutCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDVCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel82)
                            .addComponent(jLabel83))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ComboBoxRedSocialCliente, 0, 202, Short.MAX_VALUE)
                            .addComponent(jDateCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDireccionCliente)
                            .addComponent(combobxEstadoCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(50, 50, 50))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ComboBoxRedSocialCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRutCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)
                        .addComponent(txtDVCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mensajeRutCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jDateCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel80)
                            .addComponent(txtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel82))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel83)
                            .addComponent(combobxEstadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelarCliente)
                            .addComponent(btnGuardarCliente))
                        .addContainerGap())))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder("TablaClientes"));

        TablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Rut", "Nombre ", "Apellido ", "Direccion", "Teléfono", "E-Mail", "F.Nacimiento", "Canal", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaClientes.setColumnSelectionAllowed(true);
        TablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaClientesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaClientes);
        TablaClientes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        txtBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarClienteActionPerformed(evt);
            }
        });
        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarCliente.setText("Editar");
        btnEditarCliente.setEnabled(false);
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnVentaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ventas2.png"))); // NOI18N
        btnVentaCliente.setText("Venta");
        btnVentaCliente.setEnabled(false);

        btnDesactivarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
        btnDesactivarCliente.setText("Desactivar");
        btnDesactivarCliente.setEnabled(false);
        btnDesactivarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarClienteActionPerformed(evt);
            }
        });

        jLabel73.setText("Buscar:");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
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
                        .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(clientesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        clientesLayout.setVerticalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
        );

        maestros.addTab("Clientes", clientes);

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores"));

        jLabel56.setText("Rut Proveedor");

        jLabel57.setText("Nombre Contacto");

        jLabel66.setText("Dirección");

        txtRutProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutProveedorActionPerformed(evt);
            }
        });

        txtNombreContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreContactoActionPerformed(evt);
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

        txtProveedorTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProveedorTelefonoActionPerformed(evt);
            }
        });

        jLabel69.setText("E-Mail");

        txtEmailProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailProveedorActionPerformed(evt);
            }
        });

        btnCancelarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarProveedor.setText("Cancelar");
        btnCancelarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProveedorActionPerformed(evt);
            }
        });

        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnGuardarProveedor.setText("Guardar");
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        jLabel25.setText("-");

        combobxEstadoProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        combobxEstadoProveedor.setEnabled(false);

        jLabel24.setText("Estado:");

        txtIdProveedor.setEditable(false);
        txtIdProveedor.setEnabled(false);

        jLabel88.setText("Codigo Proveedor:");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarProveedor)
                        .addGap(4, 4, 4))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel32Layout.createSequentialGroup()
                                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel32Layout.createSequentialGroup()
                                        .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(5, 5, 5)))
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel32Layout.createSequentialGroup()
                                        .addComponent(txtRutProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDvProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combobxEstadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel67)
                                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)
                                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProveedorTelefono, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtEmailProveedor)
                                    .addComponent(txtRazonSocialProveedor)))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 178, Short.MAX_VALUE)))))
                .addContainerGap())
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
                            .addComponent(txtDvProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRazonSocialProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel67))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(txtProveedorTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addComponent(txtNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69)
                    .addComponent(txtEmailProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobxEstadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88))
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarProveedor)
                    .addComponent(btnGuardarProveedor)))
        );

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("Proveedores Registrados"));

        TablaProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Razón Social", "Nombre", "Rut", "Teléfono", "Email", "Dirección", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProveedorMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TablaProveedor);

        btnComprarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compras.png"))); // NOI18N
        btnComprarProveedor.setText("Comprar");
        btnComprarProveedor.setEnabled(false);

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarProveedor.setText("Editar");
        btnEditarProveedor.setEnabled(false);
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnDesactivarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
        btnDesactivarProveedor.setText("Desactivar");
        btnDesactivarProveedor.setEnabled(false);
        btnDesactivarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarProveedorActionPerformed(evt);
            }
        });

        txtBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProveedorKeyReleased(evt);
            }
        });

        jLabel72.setText("Buscar:");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                                .addComponent(jLabel72)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                                .addComponent(btnComprarProveedor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarProveedor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDesactivarProveedor)))))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 50, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        maestros.addTab("Proveedores", jPanel21);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Artículos"));

        jLabel15.setText("Nombre Artículo");

        txtNombreArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreArticuloActionPerformed(evt);
            }
        });
        txtNombreArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreArticuloKeyTyped(evt);
            }
        });

        jLabel16.setText("Unidades");

        txtUnidadArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadArticuloActionPerformed(evt);
            }
        });

        jLabel28.setText("Marca");

        txtMarcaArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaArticuloActionPerformed(evt);
            }
        });
        txtMarcaArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaArticuloKeyTyped(evt);
            }
        });

        jLabel29.setText("Categoria Artículo");

        jLabel30.setText("F. Vencimiento");

        jLabel31.setText("Proveedor");

        btnCancelarArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarArticulos.setText("Cancelar");
        btnCancelarArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarArticulosActionPerformed(evt);
            }
        });

        btnGuardarArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnGuardarArticulos.setText("Guardar");
        btnGuardarArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarArticulosActionPerformed(evt);
            }
        });

        jLabel34.setText("Codigo Artículo");

        txtCodigoArticulo.setEnabled(false);
        txtCodigoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoArticuloActionPerformed(evt);
            }
        });

        ComboBoxCategoriaArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboBoxCategoriaArticuloMouseClicked(evt);
            }
        });

        ComboBoxProveedorArticulo.setToolTipText("");

        txtIdentificadorArticulo.setEnabled(false);
        txtIdentificadorArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdentificadorArticuloActionPerformed(evt);
            }
        });

        jLabel84.setText("Id Artículo:");

        ComboBoxEstadoArticulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoArticulo.setEnabled(false);

        jLabel85.setText("Estado:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUnidadArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdentificadorArticulo)
                            .addComponent(txtMarcaArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(txtCodigoArticulo))))
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarArticulos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarArticulos))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel85)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboBoxEstadoArticulo, 0, 156, Short.MAX_VALUE)
                                    .addComponent(ComboBoxProveedorArticulo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ComboBoxCategoriaArticulo, 0, 156, Short.MAX_VALUE)
                                    .addComponent(DateFVArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
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
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addComponent(jLabel31))
                    .addComponent(ComboBoxProveedorArticulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel84)
                            .addComponent(txtIdentificadorArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBoxEstadoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel85))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardarArticulos)
                            .addComponent(btnCancelarArticulos)))))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla Artículos"));

        btnDesactivarArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
        btnDesactivarArticulos.setText("Desactivar");
        btnDesactivarArticulos.setEnabled(false);
        btnDesactivarArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarArticulosActionPerformed(evt);
            }
        });

        btnEditarArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarArticulos.setText("Editar");
        btnEditarArticulos.setEnabled(false);
        btnEditarArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarArticulosActionPerformed(evt);
            }
        });

        txtBuscarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarArticuloActionPerformed(evt);
            }
        });
        txtBuscarArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarArticuloKeyReleased(evt);
            }
        });

        jLabel44.setText("Buscar:");

        TablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Artículo", "Artículo", "Código", "Unidades", "Marca", "F. Vencimiento", "Estado", "Categoria", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaArticulos.setColumnSelectionAllowed(true);
        TablaArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaArticulosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TablaArticulos);
        TablaArticulos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (TablaArticulos.getColumnModel().getColumnCount() > 0) {
            TablaArticulos.getColumnModel().getColumn(0).setMinWidth(60);
            TablaArticulos.getColumnModel().getColumn(0).setMaxWidth(60);
            TablaArticulos.getColumnModel().getColumn(1).setMinWidth(120);
            TablaArticulos.getColumnModel().getColumn(1).setMaxWidth(120);
            TablaArticulos.getColumnModel().getColumn(2).setMinWidth(50);
            TablaArticulos.getColumnModel().getColumn(2).setMaxWidth(50);
            TablaArticulos.getColumnModel().getColumn(3).setMinWidth(60);
            TablaArticulos.getColumnModel().getColumn(3).setMaxWidth(60);
            TablaArticulos.getColumnModel().getColumn(4).setMinWidth(70);
            TablaArticulos.getColumnModel().getColumn(4).setMaxWidth(70);
            TablaArticulos.getColumnModel().getColumn(5).setMinWidth(90);
            TablaArticulos.getColumnModel().getColumn(5).setMaxWidth(90);
            TablaArticulos.getColumnModel().getColumn(6).setMinWidth(60);
            TablaArticulos.getColumnModel().getColumn(6).setMaxWidth(60);
            TablaArticulos.getColumnModel().getColumn(7).setMinWidth(80);
            TablaArticulos.getColumnModel().getColumn(7).setMaxWidth(80);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(btnEditarArticulos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDesactivarArticulos)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtBuscarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesactivarArticulos)
                    .addComponent(btnEditarArticulos))
                .addContainerGap())
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(42, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        maestros.addTab("Artículos", jPanel4);

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de Packs"));

        TablaPack.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo De Pack", "Nombre De Pack", "Costo del pack", "Stock", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPack.getTableHeader().setReorderingAllowed(false);
        TablaPack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPackMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TablaPack);

        txtBuscarPack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPackKeyReleased(evt);
            }
        });

        btnEditarPack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarPack.setText("Editar");
        btnEditarPack.setEnabled(false);
        btnEditarPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPackActionPerformed(evt);
            }
        });

        btnDesactivarPack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
        btnDesactivarPack.setText("Desactivar");
        btnDesactivarPack.setEnabled(false);
        btnDesactivarPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarPackActionPerformed(evt);
            }
        });

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
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEditarPack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDesactivarPack)))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarPack)
                    .addComponent(btnDesactivarPack))
                .addGap(6, 6, 6))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Packs"));

        jLabel17.setText("Nombre Pack:");

        txtNombrePack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrePackActionPerformed(evt);
            }
        });
        txtNombrePack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombrePackKeyTyped(evt);
            }
        });

        jLabel35.setText("Costo Pack:");

        btnCancelarPack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarPack.setText("Cancelar");
        btnCancelarPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPackActionPerformed(evt);
            }
        });

        btnCrearPack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnCrearPack.setText("Crear Pack");
        btnCrearPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearPackActionPerformed(evt);
            }
        });

        txtPrecioPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioPackActionPerformed(evt);
            }
        });
        txtPrecioPack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioPackKeyTyped(evt);
            }
        });

        jLabel4.setText("Unidades");

        txtUnidadesArticulosPack.setEnabled(false);
        txtUnidadesArticulosPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadesArticulosPackActionPerformed(evt);
            }
        });

        btnAgregarArticuloPack.setText("-->");
        btnAgregarArticuloPack.setEnabled(false);
        btnAgregarArticuloPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarArticuloPackActionPerformed(evt);
            }
        });

        btnEliminarArticuloPack.setText("<--");
        btnEliminarArticuloPack.setEnabled(false);
        btnEliminarArticuloPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarArticuloPackActionPerformed(evt);
            }
        });

        TablaPackSeleccionArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Artículo", "Artículo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPackSeleccionArticulos.getTableHeader().setReorderingAllowed(false);
        TablaPackSeleccionArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPackSeleccionArticulosMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(TablaPackSeleccionArticulos);

        TablaPackConArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Artículo", "Artículo", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPackConArticulos.getTableHeader().setReorderingAllowed(false);
        TablaPackConArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPackConArticulosMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(TablaPackConArticulos);
        if (TablaPackConArticulos.getColumnModel().getColumnCount() > 0) {
            TablaPackConArticulos.getColumnModel().getColumn(2).setMinWidth(60);
            TablaPackConArticulos.getColumnModel().getColumn(2).setMaxWidth(60);
        }

        jLabel38.setText("Estado:");

        ComboBoxEstadoPack.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo", " ", " " }));
        ComboBoxEstadoPack.setEnabled(false);
        ComboBoxEstadoPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxEstadoPackActionPerformed(evt);
            }
        });

        jLabel86.setText("Código Pack:");

        txtCodigoPack.setEditable(false);
        txtCodigoPack.setEnabled(false);

        jLabel87.setText("Stock:");

        txtStockPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockPackActionPerformed(evt);
            }
        });
        txtStockPack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockPackKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(562, 562, 562)
                        .addComponent(btnCancelarPack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrearPack))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEliminarArticuloPack, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregarArticuloPack)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(txtUnidadesArticulosPack, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(53, 53, 53)
                                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel86))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigoPack, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                    .addComponent(txtNombrePack)
                                    .addComponent(ComboBoxEstadoPack, 0, 146, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel87))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPrecioPack, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                    .addComponent(txtStockPack))))))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel86)
                    .addComponent(txtCodigoPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombrePack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel87)
                        .addComponent(txtStockPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(ComboBoxEstadoPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(btnAgregarArticuloPack)
                        .addGap(13, 13, 13)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUnidadesArticulosPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarArticuloPack)))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarPack)
                    .addComponent(btnCrearPack)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 122, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
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

        TablaCanal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Canal", "Nombre de Canal", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCanal.setColumnSelectionAllowed(true);
        TablaCanal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCanalMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TablaCanal);
        TablaCanal.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarCanal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarCanal.setText("Editar");
        btnEditarCanal.setEnabled(false);
        btnEditarCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCanalActionPerformed(evt);
            }
        });

        btnDesactivarCanal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
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

        btnCancelarCanal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarCanal.setText("Cancelar");
        btnCancelarCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCanalActionPerformed(evt);
            }
        });

        btnGuardarCanal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
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
                .addContainerGap(252, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(316, Short.MAX_VALUE))
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
        txtNombreCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCategoriaKeyTyped(evt);
            }
        });

        jLabel79.setText("Estado:");

        ComboBoxEstadoCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoCategoria.setEnabled(false);

        btnGuardarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnGuardarCategoria.setText("Guardar");
        btnGuardarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCategoriaActionPerformed(evt);
            }
        });

        btnCancelarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        TablaCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCategoria.setColumnSelectionAllowed(true);
        TablaCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCategoriaMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(TablaCategoria);
        TablaCategoria.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarCategoria.setText("Editar");
        btnEditarCategoria.setEnabled(false);
        btnEditarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCategoriaActionPerformed(evt);
            }
        });

        btnDesactivarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
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
            .addGap(0, 956, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(56, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 207, Short.MAX_VALUE)))
        );

        maestros.addTab("Categorías Artículos", jPanel7);

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

        TablaComuna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Comuna", "Nombre de la Comuna", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaComuna.setColumnSelectionAllowed(true);
        TablaComuna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaComunaMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(TablaComuna);
        TablaComuna.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarComuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarComuna.setText("Editar");
        btnEditarComuna.setEnabled(false);
        btnEditarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarComunaActionPerformed(evt);
            }
        });

        btnDesactivarComuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
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
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        btnCancelarComuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarComuna.setText("Cancelar");
        btnCancelarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarComunaActionPerformed(evt);
            }
        });

        btnGuardarComuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
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
                        .addGap(0, 0, Short.MAX_VALUE))))
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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(269, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
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

        btnCancelarBanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarBanco.setText("Cancelar");
        btnCancelarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBancoActionPerformed(evt);
            }
        });

        btnGuardarBanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
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

        TablaBanco.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del Banco", "Nombre del Banco", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        btnEditarBanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarBanco.setText("Editar");
        btnEditarBanco.setEnabled(false);
        btnEditarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarBancoActionPerformed(evt);
            }
        });

        btnDesactivarBanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
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
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
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
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(120, Short.MAX_VALUE))
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
            .addGap(0, 956, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addContainerGap(76, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(142, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 174, Short.MAX_VALUE)))
        );

        maestros.addTab("Bancos", jPanel9);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Estados De Venta"));

        jLabel45.setText("Estado Pago De Venta:");

        txtNombreEstadoPagoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreEstadoPagoVentaActionPerformed(evt);
            }
        });
        txtNombreEstadoPagoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreEstadoPagoVentaKeyTyped(evt);
            }
        });

        jLabel46.setText("Código Estado De Ventas:");

        btnCancelarEstadoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarEstadoVenta.setText("Cancelar");
        btnCancelarEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarEstadoVentaActionPerformed(evt);
            }
        });

        btnGuardarEstadoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
        btnGuardarEstadoVenta.setText("Guardar");
        btnGuardarEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEstadoVentaActionPerformed(evt);
            }
        });

        txtCodigoEstadoVenta.setEditable(false);
        txtCodigoEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoEstadoVentaActionPerformed(evt);
            }
        });

        jLabel47.setText("Estado:");

        ComboBoxEstadoVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        ComboBoxEstadoVenta.setEnabled(false);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarEstadoVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarEstadoVenta))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel47))
                            .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ComboBoxEstadoVenta, javax.swing.GroupLayout.Alignment.LEADING, 0, 129, Short.MAX_VALUE)
                                .addComponent(txtCodigoEstadoVenta, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(txtNombreEstadoPagoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txtNombreEstadoPagoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtCodigoEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(ComboBoxEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarEstadoVenta)
                    .addComponent(btnCancelarEstadoVenta)))
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla Estado De Ventas"));

        txtBuscarEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarEstadoVentaActionPerformed(evt);
            }
        });
        txtBuscarEstadoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarEstadoVentaKeyReleased(evt);
            }
        });

        btnDesactivarEstadoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
        btnDesactivarEstadoVenta.setText("Desactivar");
        btnDesactivarEstadoVenta.setEnabled(false);
        btnDesactivarEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarEstadoVentaActionPerformed(evt);
            }
        });

        btnEditarEstadoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarEstadoVenta.setText("Editar");
        btnEditarEstadoVenta.setEnabled(false);
        btnEditarEstadoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarEstadoVentaActionPerformed(evt);
            }
        });

        TablaEstadoVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código De Venta", "Estado De Venta", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaEstadoVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaEstadoVentaMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(TablaEstadoVenta);

        jLabel70.setText("Buscar:");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addComponent(btnEditarEstadoVenta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDesactivarEstadoVenta))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDesactivarEstadoVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarEstadoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 278, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(244, Short.MAX_VALUE))
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

        PasswordUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordUsuarioActionPerformed(evt);
            }
        });
        PasswordUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PasswordUsuarioKeyTyped(evt);
            }
        });

        jLabel76.setText("Estado:");

        btnCancelarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnCancelar.png"))); // NOI18N
        btnCancelarUsuario.setText("Cancelar");
        btnCancelarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarUsuarioActionPerformed(evt);
            }
        });

        btnGuardarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnGuardar.png"))); // NOI18N
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
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel75)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel76)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addComponent(btnCancelarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCuentaUsuario)
                        .addComponent(PasswordUsuario)
                        .addComponent(ComboBoxEstado, 0, 116, Short.MAX_VALUE))
                    .addComponent(btnGuardarUsuario))
                .addContainerGap(25, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        TablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Nombre", "Apellido", "Usuario", "Clave", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaUsuarios.setColumnSelectionAllowed(true);
        TablaUsuarios.getTableHeader().setReorderingAllowed(false);
        TablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(TablaUsuarios);
        TablaUsuarios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnEditar.png"))); // NOI18N
        btnEditarUsuario.setText("Editar");
        btnEditarUsuario.setEnabled(false);
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        btnDesactivarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnDesactivar.png"))); // NOI18N
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
                        .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE))
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
                    .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(263, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );

        maestros.addTab("Usuarios", jPanel11);

        jTabbedPane4.addTab("Maestros", maestros);

        getContentPane().add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 970, 790));

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

    private void txtBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarClienteActionPerformed

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

    private void txtNombreEstadoPagoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreEstadoPagoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreEstadoPagoVentaActionPerformed

    private void txtBuscarEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEstadoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarEstadoVentaActionPerformed

    private void txtCodigoEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoEstadoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoEstadoVentaActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if ((!"".equals(txtNombreCliente.getText())) && (!"".equals(txtApellidoCliente.getText())) && (!"".equals(txtTelefonoCliente.getText()))
                && (!"".equals(txtEmailCliente.getText())) && (ComboBoxRedSocialCliente.getSelectedItem() != "Seleccionar Canal")
                && (jDateCliente.getDate() != null) && (!"".equals(txtDireccionCliente.getText()))) {

            String nombreCliente = txtNombreCliente.getText();
            nombreCliente = nombreCliente.toUpperCase();

            String apellidoCliente = txtApellidoCliente.getText();
            apellidoCliente = apellidoCliente.toUpperCase();

            String telefonoCliente = txtTelefonoCliente.getText();

            int canalRs = ClientesNegocio.BuscarIdCanal((String) (ComboBoxRedSocialCliente.getSelectedItem()));

            String correoCliente = txtEmailCliente.getText();
            correoCliente = correoCliente.toUpperCase();

            String rutCliente = txtRutCliente.getText() + "-" + txtDVCliente.getText();
            rutCliente = rutCliente.toUpperCase();

            String direccionCliente = txtDireccionCliente.getText();
            direccionCliente = direccionCliente.toUpperCase();

            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String fechanacimientoCliente = date.format(jDateCliente.getDate());

            if (ClientesNegocio.BuscarCliente(rutCliente)) {
                LimpiarFormularioClientes();
                JOptionPane.showMessageDialog(null, "El Cliente con RUT: " + rutCliente + ", ya esta registrado");
            } else {
                int estado = 1;
                Clientes ClaseCliente = new Clientes();
                ClaseCliente.setRutCliente(rutCliente);
                ClaseCliente.setNombreCliente(nombreCliente);
                ClaseCliente.setApellidoCliente(apellidoCliente);
                ClaseCliente.setDireccionCliente(direccionCliente);
                ClaseCliente.setTelefonoCliente("+56" + telefonoCliente);
                ClaseCliente.setCorreoCliente(correoCliente);
                ClaseCliente.setFechaNacimientoCliente(fechanacimientoCliente);
                ClaseCliente.setCanalCliente(canalRs);
                ClaseCliente.setEstadoCliente(estado);

                if (ClientesNegocio.GuardarCliente(ClaseCliente)) {

                    ComboBoxRedSocialCliente.removeAllItems();
                    LimpiarFormularioClientes();
                    LimpiarTablaCliente();
                    ListarClienteVista();
                    JOptionPane.showMessageDialog(null, "Cliente Ingresado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, Ingrese todos los campos requeridos");
        }

    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnCancelarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarClienteActionPerformed
        //limpiarCliente();        // TODO add your handling code here:
        ComboBoxRedSocialCliente.removeAllItems();
        LimpiarFormularioClientes();
        LimpiarTablaCliente();
        ListarClienteVista();
        DeshabilitarCamposClientes();
    }//GEN-LAST:event_btnCancelarClienteActionPerformed

    private void TablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaClientesMouseClicked
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formateoFecha = new SimpleDateFormat("yyyy-MM-dd");

        int fila = TablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Cliente no Seleccionado");
        } else {
            HabilitarCamposClientes();
            btnEditarCliente.setEnabled(true);
            btnGuardarCliente.setEnabled(false);

            String rutCliente = (String) TablaClientes.getValueAt(fila, 1);
            String NombreCliente = (String) TablaClientes.getValueAt(fila, 2);
            String ApellidoCliente = (String) TablaClientes.getValueAt(fila, 3);
            String DireccionCliente = (String) TablaClientes.getValueAt(fila, 4);
            String Telefono = ((String) TablaClientes.getValueAt(fila, 5));
            String CorreoCliente = (String) TablaClientes.getValueAt(fila, 6);
            String Formateo = String.valueOf(TablaClientes.getValueAt(fila, 7));
            try {
                fecha = (java.util.Date) formateoFecha.parse(Formateo);
                jDateCliente.setDate(fecha);
            } catch (Exception e) {
            }
            String CanalCliente = (String) TablaClientes.getValueAt(fila, 8);
            String EstadoCliente = (String) TablaClientes.getValueAt(fila, 9);

            if ("Inactivo".equals(EstadoCliente)) {
                combobxEstadoCliente.setEnabled(true);
                btnDesactivarCliente.setEnabled(false);
            } else {
                btnDesactivarCliente.setEnabled(true);
                combobxEstadoCliente.setEnabled(false);
            }

            txtRutCliente.setText(rutCliente.substring(0, 8));
            txtDVCliente.setText(rutCliente.substring(9, 10));
            txtNombreCliente.setText(NombreCliente);
            txtApellidoCliente.setText(ApellidoCliente);
            txtDireccionCliente.setText(DireccionCliente);
            txtTelefonoCliente.setText(Telefono.substring(3, 12));
            txtEmailCliente.setText(CorreoCliente);
            combobxEstadoCliente.setSelectedItem(EstadoCliente);
            ComboBoxRedSocialCliente.setSelectedItem(CanalCliente);
        }
    }//GEN-LAST:event_TablaClientesMouseClicked

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        Clientes ClaseClientes = new Clientes();
        java.util.Date date = new java.util.Date();
        SimpleDateFormat fechanacimientoCliente = new SimpleDateFormat("yyyy-MM-dd");
        boolean edicion = true;

        int estado = 0;
        if (combobxEstadoCliente.getSelectedItem() == "Activo") {
            estado = 1;
        }

        int fila = TablaClientes.getSelectedRow();

        if ("Rut Invalido".equals(jLabel86.getText())) {
            JOptionPane.showMessageDialog(null, "Por favor, debe corregir el RUT");
        } else {
            if ((!"".equals(txtNombreCliente.getText())) && (!"".equals(txtApellidoCliente.getText())) && (!"".equals(txtTelefonoCliente.getText()))
                    && (!"".equals(txtEmailCliente.getText())) && (ComboBoxRedSocialCliente.getSelectedItem() != "Seleccionar Canal")
                    && (jDateCliente.getDate() != null) && (!"".equals(txtRutCliente.getText())) && (!"".equals(txtDireccionCliente.getText()))
                    && (!"".equals(txtDVCliente.getText()))) {

                ClaseClientes.setEstadoCliente(estado);

                int identificador = Integer.parseInt((String) TablaClientes.getValueAt(fila, 0).toString());
                ClaseClientes.setIdentificadorCliente(identificador);

                String nombreCliente = txtNombreCliente.getText();
                ClaseClientes.setNombreCliente(nombreCliente.toUpperCase());

                String apellidoCliente = txtApellidoCliente.getText();
                ClaseClientes.setApellidoCliente(apellidoCliente.toUpperCase());

                String direccionCliente = txtDireccionCliente.getText();
                ClaseClientes.setDireccionCliente(direccionCliente.toUpperCase());

                String rutCliente = (txtRutCliente.getText() + "-" + txtDVCliente.getText());
                ClaseClientes.setRutCliente(rutCliente.toUpperCase());

                String correoCliente = txtEmailCliente.getText();
                ClaseClientes.setCorreoCliente(correoCliente.toUpperCase());

                String telefono = txtTelefonoCliente.getText();
                ClaseClientes.setTelefonoCliente("+56" + telefono);

                String FNCliente = fechanacimientoCliente.format(jDateCliente.getDate());
                ClaseClientes.setFechaNacimientoCliente(FNCliente);

                ClaseClientes.setCanalCliente(ClientesNegocio.BuscarIdCanal((String) (ComboBoxRedSocialCliente.getSelectedItem())));

                if (ClientesNegocio.ModificarCliente(ClaseClientes)) {
                    ComboBoxRedSocialCliente.removeAllItems();
                    LimpiarFormularioClientes();
                    LimpiarTablaCliente();
                    ListarClienteVista();
                    DeshabilitarCamposClientes();
                    JOptionPane.showMessageDialog(null, "Información modificada con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, Ingrese todos los campos requeridos");
            }

        }

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
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombreComuna.getText().length() >= 45))
            evt.consume();
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
        nombreComuna = nombreComuna.toUpperCase();
        if ((txtNombreComuna.getText().isEmpty())) {
            LimpiarFormularioComuna();
            JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre de la comuna", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (ComunasNegocio.BuscarComuna(nombreComuna)) {
            LimpiarFormularioComuna();
            JOptionPane.showMessageDialog(null, "El nombre de la comuna: " + nombreComuna + ", esta registrado");
        } else {
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
        if (!"".equals(txtBuscarComuna.getText())) {
            LimpiarFormularioComuna();
            LimpiarTablaComuna();
            ComunasNegocio.FiltrarComuna(TablaComuna, txtBuscarComuna.getText());
        } else {
            LimpiarFormularioComuna();
            LimpiarTablaComuna();
            ListarComunasVista();
        }

    }//GEN-LAST:event_txtBuscarComunaKeyReleased

    private void txtBuscarCanalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCanalKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarCanal.getText())) {
            LimpiarFormularioCanal();
            LimpiarTablaCanal();
            MediosNegocio.FiltrarCanal(TablaCanal, txtBuscarCanal.getText());
        } else {
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
            JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre del banco", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (BancoNegocio.BuscarBanco(nombreBanco)) {
            LimpiarFormularioBanco();
            JOptionPane.showMessageDialog(null, "El nombre " + nombreBanco + ", esta registrado");
        } else {
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
        if (!"".equals(txtBuscarBanco.getText())) {
            LimpiarFormularioBanco();
            LimpiarTablaBanco();
            BancoNegocio.FiltrarBanco(TablaBanco, txtBuscarBanco.getText());
        } else {
            LimpiarFormularioBanco();
            LimpiarTablaBanco();
            ListarBancoVista();
        }
    }//GEN-LAST:event_txtBuscarBancoKeyReleased

    private void txtNombreBancoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreBancoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombreBanco.getText().length() >= 45)) {
            evt.consume();
        }

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
            JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre de la categoría", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (CategoriaNegocio.BuscarCategoria(nombreCategoria)) {
            LimpiarFormularioCategoria();
            JOptionPane.showMessageDialog(null, "El nombre " + nombreCategoria + ", esta registrado");
        } else {
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

        // Mediante este evento validamos que los campos del formulario tengan
        //datos para ejecutar los metodos que insertan la informacion en la BD y actualiza la información del formulario
        ValidacionRut validar = new ValidacionRut();
        String rut = txtRutProveedor.getText() + "-" + txtDvProveedor.getText();

        validar = new ValidacionRut(rut);
        if (validar.Validacion() == true) {
            txtNombreContacto.setEnabled(true);
            txtDireccionProveedor.setEnabled(true);
            txtRazonSocialProveedor.setEnabled(true);
            txtProveedorTelefono.setEnabled(true);
            txtEmailProveedor.setEnabled(true);
            combobxEstadoProveedor.setEnabled(true);

            if ((txtRutProveedor.getText().isEmpty()) || (txtNombreContacto.getText().isEmpty())
                    || (txtDireccionProveedor.getText().isEmpty()) || (txtRazonSocialProveedor.getText().isEmpty())
                    || (txtProveedorTelefono.getText().isEmpty())
                    || (txtEmailProveedor.getText().isEmpty()) || (txtDvProveedor.getText().isEmpty())) {
                getToolkit().beep();
                JOptionPane.showMessageDialog(null, "Complete los Datos Solicitados");
            } else {
                int estado = 1;
                Proveedor ClaseProveedor = new Proveedor();
                ClaseProveedor.setRut(txtRutProveedor.getText() + "-" + txtDvProveedor.getText());
                ClaseProveedor.setNombreProveedor(txtNombreContacto.getText());
                ClaseProveedor.setDireccionProveedor(txtDireccionProveedor.getText());
                ClaseProveedor.setRazonSocialProveedor(txtRazonSocialProveedor.getText());
                ClaseProveedor.setTelefonoProveedor("+56" + txtProveedorTelefono.getText());
                ClaseProveedor.setCorreo(txtEmailProveedor.getText());
                ClaseProveedor.setEstado(estado);

                if (ProveedorNegocio.GuardarProveedor(ClaseProveedor)) {
                    LimpiarFormularioProveedor();
                    LimpiarTablaProveedor();
                    ListarProveedorVista();

                    JOptionPane.showMessageDialog(null, "Proveedor Agregado Con Exito ");
                } else {
                    JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
                }
            }

        } else {

            txtNombreContacto.setEnabled(false);
            txtDireccionProveedor.setEnabled(false);
            txtRazonSocialProveedor.setEnabled(false);
            txtProveedorTelefono.setEnabled(false);
            txtEmailProveedor.setEnabled(false);
            combobxEstadoProveedor.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Rut Invalido", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnCancelarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProveedorActionPerformed
        txtBuscarProveedor.setText("");
        LimpiarFormularioProveedor();
        LimpiarTablaProveedor();
        ListarProveedorVista();
    }//GEN-LAST:event_btnCancelarProveedorActionPerformed

    private void txtEmailProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailProveedorActionPerformed

    private void txtProveedorTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProveedorTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProveedorTelefonoActionPerformed

    private void txtRazonSocialProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazonSocialProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocialProveedorActionPerformed

    private void txtDireccionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionProveedorActionPerformed

    private void txtNombreContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreContactoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreContactoActionPerformed

    private void txtRutProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutProveedorActionPerformed

    private void btnDesactivarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarUsuarioActionPerformed
        //        // Invoca los metodos que desactivan y actualizan la informacion en la grilla de datos y formulario:
        if (!"".equals(txtNombre.getText()) && !"".equals(txtApellido.getText()) && !"".equals(PasswordUsuario.getText())) {
            int estado = 0;
            Usuario ClaseUsuario = new Usuario();
            ClaseUsuario.setEstado(estado);
            ClaseUsuario.setIdentificador(Integer.parseInt(txtIdUsuario.getText()));
            if (UserNegocio.DesactivarUsuario(ClaseUsuario)) {
                LimpiarFormularioUsuario();
                LimpiarTablaUsuario();
                ListarUsuariosVista();
                JOptionPane.showMessageDialog(null, "Usuario Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
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
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
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
        if (!"".equals(txtbuscarUsuario.getText())) {
            LimpiarFormularioUsuario();
            LimpiarTablaUsuario();
            UserNegocio.FiltrarUsuario(TablaUsuarios, txtbuscarUsuario.getText());
        } else {
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
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
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
        if ((c < '0' || c > '9') || (PasswordUsuario.getText().length() >= 4))
            evt.consume();
    }//GEN-LAST:event_PasswordUsuarioKeyTyped

    private void txtCuentaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCuentaUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCuentaUsuarioActionPerformed

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        // Este evento permite controlar la cantidad maxima de caracteres permitidos
        //y que solo se permita el ingreso de letras en el Apellido:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtApellido.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyReleased
        // Este evento ejecuta sentencias y metodos que permiten controlar los campos
        // del nombre y apellido para que generen la cuenta de usuario de forma
        // automatica
        try {
            if (!"".equals(txtApellido.getText()) && !btnEditarUsuario.isEnabled()) {
                String Nombre = txtNombre.getText();
                String Apellido = txtApellido.getText();
                txtCuentaUsuario.setText(FormarCuentaUsuaria(Nombre, Apellido));
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_txtApellidoKeyReleased

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // Este evento permite controlar la cantidad maxima de caracteres permitidos
        //y que solo se permita el ingreso de letras en el Nombre:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombre.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        // Este evento ejecuta sentencias y metodos que permiten controlar los campos
        // del nombre y apellido para que generen la cuenta de usuario de forma
        // automatica
        try {
            if ((!btnEditarUsuario.isEnabled()) && ("".equals(txtNombre.getText()))) {
                txtCuentaUsuario.setText(txtApellido.getText());
            } else if ((txtNombre.getText().length() > 0) && (txtApellido.getText().length() > 0) && !btnEditarUsuario.isEnabled()) {
                String Nombre = txtNombre.getText();
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

    private void btnEliminarArticuloPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarArticuloPackActionPerformed
        // TODO add your handling code here:
        int fila = TablaPackConArticulos.getSelectedRow();
        if (btnEditarPack.isEnabled()) {
            PackArticulo ClasePackArticulo = new PackArticulo();
            ClasePackArticulo.setIdentificadorPackArticulo(Integer.parseInt(txtCodigoPack.getText()));
            ClasePackArticulo.setIdentificadorArticulo(Integer.parseInt(TablaPackConArticulos.getValueAt(fila, 0).toString()));
//            System.out.println("Valores" + ClasePackArticulo.getIdentificadorPackArticulo() + " " + ClasePackArticulo.getIdentificadorArticulo());
            if (PackArticuloNegocio.EliminarPackArticulo(ClasePackArticulo)) {
                modelo.removeRow(fila);
                TablaPackConArticulos.setModel(modelo);
            } else {
            }
        } else {
            modelo.removeRow(fila);
            TablaPackConArticulos.setModel(modelo);
        }


    }//GEN-LAST:event_btnEliminarArticuloPackActionPerformed

    private void btnAgregarArticuloPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarArticuloPackActionPerformed
        // TODO add your handling code here:
        String[] datos = new String[3];
        int fila = TablaPackSeleccionArticulos.getSelectedRow();
        String idArticulo = TablaPackSeleccionArticulos.getValueAt(fila, 0).toString();
        String nombreArticulo = TablaPackSeleccionArticulos.getValueAt(fila, 1).toString();
        int filasCount = TablaPackConArticulos.getRowCount();
        boolean encontrado = false;
        if (filasCount > 0) {
            for (int i = 0; i < filasCount; i++) {
                if (nombreArticulo.equals(TablaPackConArticulos.getValueAt(i, 1).toString())) {
                    encontrado = true;
                }
            }
            if (encontrado) {
                JOptionPane.showMessageDialog(null, "Artículo ya esta agregado");
            } else {
                datos[0] = idArticulo;
                datos[1] = nombreArticulo;
                datos[2] = txtUnidadesArticulosPack.getText();
                modelo.addRow(datos);
                TablaPackConArticulos.setModel(modelo);
            }
        } else {
            datos[0] = idArticulo;
            datos[1] = nombreArticulo;
            datos[2] = txtUnidadesArticulosPack.getText();
            modelo.addRow(datos);
            TablaPackConArticulos.setModel(modelo);
        }


    }//GEN-LAST:event_btnAgregarArticuloPackActionPerformed

    private void txtPrecioPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioPackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioPackActionPerformed

    private void txtNombrePackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrePackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombrePackActionPerformed

    private void ComboBoxEstadoPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxEstadoPackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxEstadoPackActionPerformed

    private void btnEditarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarBancoActionPerformed
        // TODO add your handling code here:
        int estado = 0;
        if (ComboBoxEstadoBanco.getSelectedItem() == "Activo") {
            estado = 1;
        }
        Bancos ClaseBanco = new Bancos();
        ClaseBanco.setEstadoBanco(estado);
        ClaseBanco.setIdentificadorBanco(Integer.parseInt(txtCodigoBanco.getText()));
        if (!"".equals(txtNombreBanco.getText()) && ((BancoNegocio.BuscarBancoEstado(ClaseBanco) == false) || (BancoNegocio.BuscarBanco(txtNombreBanco.getText()) == false))) {
            String nombreBanco = txtNombreBanco.getText();
            nombreBanco = nombreBanco.toUpperCase();
            ClaseBanco.setNombreBanco(nombreBanco);
            if (BancoNegocio.ModificarBanco(ClaseBanco)) {
                LimpiarFormularioBanco();
                LimpiarTablaBanco();
                ListarBancoVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        } else {
            if ("".equals(txtNombreBanco.getText())) {
                JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El nombre del banco: " + txtNombreBanco.getText() + ", esta registrado");
            }
        }

    }//GEN-LAST:event_btnEditarBancoActionPerformed

    private void btnGuardarCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCanalActionPerformed
        // TODO add your handling code here:
        String nombreCanal = txtNombreCanal.getText();
        nombreCanal = nombreCanal.toUpperCase();
        if ((txtNombreCanal.getText().isEmpty())) {
            LimpiarFormularioCanal();
            JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre de canal", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (MediosNegocio.BuscarCanal(nombreCanal)) {
            LimpiarFormularioCanal();
            JOptionPane.showMessageDialog(null, "El nombre del canal: " + nombreCanal + ", esta registrado");
        } else {
            int estado = 1;
            Canal ClaseMedios = new Canal();
            ClaseMedios.setNombreCanal(nombreCanal);
            ClaseMedios.setEstadoCanal(estado);
            if (MediosNegocio.GuardarCanal(ClaseMedios)) {
                LimpiarFormularioCanal();
                LimpiarTablaCanal();
                ListarCanalesVista();
                ComboBoxRedSocialCliente.removeAllItems();
                LimpiarFormularioClientes();
                LimpiarTablaCliente();
                ListarClienteVista();
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
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombreCanal.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtNombreCanalKeyTyped

    private void txtNombreCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCanalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCanalActionPerformed

    private void txtCodigoCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoCanalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoCanalActionPerformed

    private void txtDVClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDVClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDVClienteActionPerformed

    private void btnEditarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCategoriaActionPerformed
        // TODO add your handling code here:
        int estado = 0;
        if (ComboBoxEstadoCategoria.getSelectedItem() == "Activo") {
            estado = 1;
        }
        Categoria ClaseCategoria = new Categoria();
        ClaseCategoria.setEstadoCategoria(estado);
        ClaseCategoria.setIdentificadorCategoria(Integer.parseInt(txtCodigoCategoria.getText()));
        if (!"".equals(txtNombreCategoria.getText()) && (CategoriaNegocio.BuscarCategoriaEstado(ClaseCategoria) == false) || (CategoriaNegocio.BuscarCategoria(txtNombreCategoria.getText()) == false)) {
            String nombreCategoria = txtNombreCategoria.getText();
            ClaseCategoria.setNombreCategoria(nombreCategoria.toUpperCase());
            ClaseCategoria.setLiteralCategoria(nombreCategoria.substring(0, 3));
            if (CategoriaNegocio.ModificarCategoria(ClaseCategoria)) {
                LimpiarFormularioCategoria();
                LimpiarTablaCategoria();
                ListarCategoriaVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        } else {
            if ("".equals(txtNombreCategoria.getText())) {
                JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El nombre de la categoría: " + txtNombreCategoria.getText() + ", esta registrado");
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
            if (BancoNegocio.DesactivarBanco(ClaseBanco)) {
                LimpiarFormularioBanco();
                LimpiarTablaBanco();
                ListarBancoVista();
                JOptionPane.showMessageDialog(null, "Banco Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
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
        if (!"".equals(txtNombreComuna.getText()) && (ComunasNegocio.BuscarComunaEstado(ClaseComuna) == false) || (ComunasNegocio.BuscarComuna(txtNombreComuna.getText()) == false)) {
            String nombreComuna = txtNombreComuna.getText();
            nombreComuna = nombreComuna.toUpperCase();
            ClaseComuna.setNombreComuna(nombreComuna);
            if (ComunasNegocio.ModificarComuna(ClaseComuna)) {
                LimpiarFormularioComuna();
                LimpiarTablaComuna();
                ListarComunasVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        } else {
            if ("".equals(txtNombreComuna.getText())) {
                JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El nombre de comuna: " + txtNombreComuna.getText() + ", esta registrado");
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
            if (ComunasNegocio.DesactivarComuna(ClaseComuna)) {
                LimpiarFormularioComuna();
                LimpiarTablaComuna();
                ListarComunasVista();
                JOptionPane.showMessageDialog(null, "Comuna Desactivada Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
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
                ComboBoxRedSocialCliente.removeAllItems();
                LimpiarFormularioClientes();
                LimpiarTablaCliente();
                ListarClienteVista();

                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        } else {
            if ("".equals(txtNombreCanal.getText())) {
                JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El nombre del canal: " + txtNombreCanal.getText() + ", esta registrado");
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
            if (MediosNegocio.DesactivarCanal(ClaseMedios)) {
                LimpiarFormularioCanal();
                LimpiarTablaCanal();
                ListarCanalesVista();
                ComboBoxRedSocialCliente.removeAllItems();
                LimpiarFormularioClientes();
                LimpiarTablaCliente();
                ListarClienteVista();
                JOptionPane.showMessageDialog(null, "Canal Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
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
        if (!"".equals(txtBuscarCategoria.getText())) {
            LimpiarFormularioCategoria();
            LimpiarTablaCategoria();
            CategoriaNegocio.FiltrarCategoria(TablaCategoria, txtBuscarCategoria.getText());
        } else {
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
            if (CategoriaNegocio.DesactivarCategoria(ClaseCategoria)) {
                LimpiarFormularioCategoria();
                LimpiarTablaCategoria();
                ListarCategoriaVista();
                JOptionPane.showMessageDialog(null, "Categoria Desactivada Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDesactivarCategoriaActionPerformed

    private void ComboBoxCategoriaArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboBoxCategoriaArticuloMouseClicked
        // TODO add your handling code here:
        //CategoriaNegocio.CargarCategoria(ComboBoxCategoriaArticulo);

    }//GEN-LAST:event_ComboBoxCategoriaArticuloMouseClicked

    private void PasswordUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordUsuarioActionPerformed

    private void btnGuardarArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarArticulosActionPerformed
        // TODO add your handling code here:
        boolean edicion = false;
        Articulos ClaseArticulos = new Articulos();
        java.util.Date date = new java.util.Date();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        int idCategoria = CategoriaNegocio.BuscarIdCategoria((String) (ComboBoxCategoriaArticulo.getSelectedItem()));
        int idProveedor = ProveedorNegocio.BuscarIdProveedor((String) (ComboBoxProveedorArticulo.getSelectedItem()));
        String nombreArticulo = txtNombreArticulo.getText();
        String MarcaArticulo = txtMarcaArticulo.getText();
        ClaseArticulos.setNombreArticulo(nombreArticulo.toUpperCase());
        ClaseArticulos.setIdCategoriaArticulo(idCategoria);
        ClaseArticulos.setIdProveedorArticulo(idProveedor);
        if ((txtNombreArticulo.getText().isEmpty()) || (txtUnidadArticulo.getText().isEmpty())
                || (txtMarcaArticulo.getText().isEmpty()) || (ComboBoxCategoriaArticulo.getSelectedItem() == "Seleccione Categoria")
                || (DateFVArticulo.getDate() == null) || (ComboBoxProveedorArticulo.getSelectedItem() == "Seleccione Proveedor")) {
            //LimpiarFormularioArticulos();
            JOptionPane.showMessageDialog(null, "Debe Ingresar los datos solicitados", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (ArticulosNegocio.BuscarArticulo(ClaseArticulos)) {
            LimpiarFormularioArticulos();
            JOptionPane.showMessageDialog(null, "Este artículo: " + nombreArticulo + ", esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String codigoArticulo = FormarCodigoArticulo(idCategoria, (String) ComboBoxCategoriaArticulo.getSelectedItem(), edicion);
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

    private void txtUnidadesArticulosPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnidadesArticulosPackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadesArticulosPackActionPerformed

    private void txtIdentificadorArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdentificadorArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdentificadorArticuloActionPerformed

    private void TablaArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaArticulosMouseClicked
        // TODO add your handling code here:
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formateoFecha = new SimpleDateFormat("yyyy-MM-dd");
        int fila = TablaArticulos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Articulo no Seleccionado");
        } else {
            btnEditarArticulos.setEnabled(true);
            btnGuardarArticulos.setEnabled(false);
            txtIdentificadorArticulo.setText(TablaArticulos.getValueAt(fila, 0).toString());
            txtNombreArticulo.setText(TablaArticulos.getValueAt(fila, 1).toString());
            txtCodigoArticulo.setText(TablaArticulos.getValueAt(fila, 2).toString());
            txtUnidadArticulo.setText(TablaArticulos.getValueAt(fila, 3).toString());
            txtMarcaArticulo.setText(TablaArticulos.getValueAt(fila, 4).toString());
            String Formateo = String.valueOf(TablaArticulos.getValueAt(fila, 5));
            try {
                fecha = (java.util.Date) formateoFecha.parse(Formateo);
                DateFVArticulo.setDate(fecha);
            } catch (Exception e) {
            }
            ComboBoxEstadoArticulo.setSelectedItem(TablaArticulos.getValueAt(fila, 6));
            String Estado = (String) TablaArticulos.getValueAt(fila, 6);
            if ("Inactivo".equals(Estado)) {
                ComboBoxEstadoArticulo.setEnabled(true);
                btnDesactivarArticulos.setEnabled(false);
            } else {
                btnDesactivarArticulos.setEnabled(true);
                ComboBoxEstadoArticulo.setEnabled(false);
            }
            ComboBoxCategoriaArticulo.setSelectedItem(TablaArticulos.getValueAt(fila, 7));
            ComboBoxProveedorArticulo.setSelectedItem(TablaArticulos.getValueAt(fila, 8));
        }
    }//GEN-LAST:event_TablaArticulosMouseClicked

    private void btnCancelarArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarArticulosActionPerformed
        // TODO add your handling code here:
        txtBuscarArticulo.setText("");
        ComboBoxCategoriaArticulo.removeAllItems();
        ComboBoxProveedorArticulo.removeAllItems();
        LimpiarTablaArticulos();
        ListarArticulosVista();
        LimpiarFormularioArticulos();
    }//GEN-LAST:event_btnCancelarArticulosActionPerformed

    private void txtBuscarArticuloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarArticuloKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarArticulo.getText())) {
            LimpiarFormularioArticulos();
            LimpiarTablaArticulos();
            ArticulosNegocio.FiltrarArticulos(TablaArticulos, txtBuscarArticulo.getText());
        } else {
            LimpiarFormularioArticulos();
            LimpiarTablaArticulos();
            ListarArticulosVista();
        }
    }//GEN-LAST:event_txtBuscarArticuloKeyReleased

    private void btnEditarArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarArticulosActionPerformed
        // TODO add your handling code here:
        Articulos ClaseArticulos = new Articulos();
        java.util.Date date = new java.util.Date();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        boolean edicion = true;
        int estado = 0;
        if (ComboBoxEstadoArticulo.getSelectedItem() == "Activo") {
            estado = 1;
        }
        String nombreArticulo = txtNombreArticulo.getText();
        ClaseArticulos.setNombreArticulo(nombreArticulo.toUpperCase());
        ClaseArticulos.setEstadoArticulo(estado);
        ClaseArticulos.setIdentificadorArticulo(Integer.parseInt(txtIdentificadorArticulo.getText()));
        ClaseArticulos.setIdCategoriaArticulo(CategoriaNegocio.BuscarIdCategoria((String) (ComboBoxCategoriaArticulo.getSelectedItem())));
        ClaseArticulos.setIdProveedorArticulo(ProveedorNegocio.BuscarIdProveedor((String) (ComboBoxProveedorArticulo.getSelectedItem())));
        if ((!"".equals(txtNombreArticulo.getText())) && (!"".equals(txtUnidadArticulo.getText())) && (!"".equals(txtMarcaArticulo.getText()))
                && (!"".equals(txtMarcaArticulo.getText())) && (ComboBoxCategoriaArticulo.getSelectedItem() != "Seleccione Categoria")
                && (DateFVArticulo.getDate() != null) && (ComboBoxProveedorArticulo.getSelectedItem() != "Seleccione Proveedor")) {
            //&& (ArticulosNegocio.BuscarArticuloEstado(ClaseArticulos) == false) || (ArticulosNegocio.BuscarArticulo(ClaseArticulos) == false)) {
            String codigoArticulo = FormarCodigoArticulo(CategoriaNegocio.BuscarIdCategoria((String) (ComboBoxCategoriaArticulo.getSelectedItem())), (String) ComboBoxCategoriaArticulo.getSelectedItem(), edicion);
            String MarcaArticulo = txtMarcaArticulo.getText();
            ClaseArticulos.setMarcaArticulo(MarcaArticulo.toUpperCase());
            ClaseArticulos.setStockArticulo(Integer.parseInt(txtUnidadArticulo.getText()));
            ClaseArticulos.setFechaVencimientoArticulo(fecha.format(DateFVArticulo.getDate()));
            ClaseArticulos.setCodigoLetraArticulo(codigoArticulo);
            if (ArticulosNegocio.ModificarArticulo(ClaseArticulos)) {
                ComboBoxCategoriaArticulo.removeAllItems();
                ComboBoxProveedorArticulo.removeAllItems();
                LimpiarFormularioArticulos();
                LimpiarTablaArticulos();
                ListarArticulosVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        } else if ((txtNombreArticulo.getText().isEmpty()) || (txtUnidadArticulo.getText().isEmpty())
                || (txtMarcaArticulo.getText().isEmpty()) || (ComboBoxCategoriaArticulo.getSelectedItem() == "Seleccione Categoria")
                || (DateFVArticulo.getDate() == null) || (ComboBoxProveedorArticulo.getSelectedItem() == "Seleccione Proveedor")) {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El Artículo: " + txtNombreArticulo.getText() + ", esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEditarArticulosActionPerformed

    private void btnDesactivarArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarArticulosActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreArticulo.getText()) && !"".equals(txtUnidadArticulo.getText()) && !"".equals(txtMarcaArticulo.getText())
                && !"".equals(txtMarcaArticulo.getText()) && ComboBoxCategoriaArticulo.getSelectedItem() != "Seleccione Categoria"
                && DateFVArticulo.getDate() != null && ComboBoxProveedorArticulo.getSelectedItem() != "Seleccione Proveedor") {
            int estado = 0;
            Articulos ClaseArticulos = new Articulos();
            ClaseArticulos.setEstadoArticulo(estado);
            ClaseArticulos.setIdentificadorArticulo(Integer.parseInt(txtIdentificadorArticulo.getText()));
            if (ArticulosNegocio.DesactivarArticulo(ClaseArticulos)) {
                ComboBoxCategoriaArticulo.removeAllItems();
                ComboBoxProveedorArticulo.removeAllItems();
                LimpiarFormularioArticulos();
                LimpiarTablaArticulos();
                ListarArticulosVista();
                JOptionPane.showMessageDialog(null, "Artículo Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDesactivarArticulosActionPerformed

    private void btnCancelarEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarEstadoVentaActionPerformed
        // TODO add your handling code here:
        txtBuscarEstadoVenta.setText("");
        LimpiarFormularioEstadoVenta();
        LimpiarTablaEstadoVenta();
        ListarEstadoVentaVista();
    }//GEN-LAST:event_btnCancelarEstadoVentaActionPerformed

    private void btnGuardarEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEstadoVentaActionPerformed
        // TODO add your handling code here:
        String nombreEstadoVenta = txtNombreEstadoPagoVenta.getText();
        if ((txtNombreEstadoPagoVenta.getText().isEmpty())) {
            LimpiarFormularioEstadoVenta();
            JOptionPane.showMessageDialog(null, "Debe Ingresar Estado de Pago", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (EstadoVentaNegocio.BuscarEstadoVenta(nombreEstadoVenta)) {
            LimpiarFormularioEstadoVenta();
            JOptionPane.showMessageDialog(null, "El Estado de pago " + nombreEstadoVenta + ", esta registrado");
        } else {
            int estado = 1;
            EstadoVenta ClaseEstadoVenta = new EstadoVenta();
            ClaseEstadoVenta.setNombreEstadoVenta(nombreEstadoVenta.toUpperCase());
            ClaseEstadoVenta.setEstado(estado);
            if (EstadoVentaNegocio.GuardarEstadoVenta(ClaseEstadoVenta)) {
                LimpiarFormularioEstadoVenta();
                LimpiarTablaEstadoVenta();
                ListarEstadoVentaVista();
                JOptionPane.showMessageDialog(null, "Estado de pago ingresado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }
    }//GEN-LAST:event_btnGuardarEstadoVentaActionPerformed

    private void txtBuscarEstadoVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarEstadoVentaKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarEstadoVenta.getText())) {
            LimpiarFormularioEstadoVenta();
            LimpiarTablaEstadoVenta();
            EstadoVentaNegocio.FiltrarEstadoVenta(TablaEstadoVenta, txtBuscarEstadoVenta.getText());
        } else {
            LimpiarFormularioEstadoVenta();
            LimpiarTablaEstadoVenta();
            ListarEstadoVentaVista();
        }
    }//GEN-LAST:event_txtBuscarEstadoVentaKeyReleased

    private void TablaEstadoVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaEstadoVentaMouseClicked
        // TODO add your handling code here:
        int fila = TablaEstadoVenta.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Estado de venta no Seleccionado");
        } else {
            btnEditarEstadoVenta.setEnabled(true);
            btnGuardarEstadoVenta.setEnabled(false);
            int identificador = Integer.parseInt((String) TablaEstadoVenta.getValueAt(fila, 0).toString());
            String Nombre = (String) TablaEstadoVenta.getValueAt(fila, 1);
            String Estado = (String) TablaEstadoVenta.getValueAt(fila, 2);
            if ("Inactivo".equals(Estado)) {
                ComboBoxEstadoVenta.setEnabled(true);
                btnDesactivarEstadoVenta.setEnabled(false);
            } else {
                btnDesactivarEstadoVenta.setEnabled(true);
                ComboBoxEstadoVenta.setEnabled(false);
            }
            txtCodigoEstadoVenta.setText("" + identificador);
            txtNombreEstadoPagoVenta.setText(Nombre);
            ComboBoxEstadoVenta.setSelectedItem(Estado);
        }
    }//GEN-LAST:event_TablaEstadoVentaMouseClicked

    private void btnEditarEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEstadoVentaActionPerformed
        // TODO add your handling code here:
        int estado = 0;
        if (ComboBoxEstadoVenta.getSelectedItem() == "Activo") {
            estado = 1;
        }
        EstadoVenta ClaseEstadoVenta = new EstadoVenta();
        ClaseEstadoVenta.setEstado(estado);
        ClaseEstadoVenta.setIdentificadorEstadoVenta(Integer.parseInt(txtCodigoEstadoVenta.getText()));
        if (!"".equals(txtNombreEstadoPagoVenta.getText()) && (EstadoVentaNegocio.BuscarEstadoVentaEstado(ClaseEstadoVenta) == false) || (EstadoVentaNegocio.BuscarEstadoVenta(txtNombreEstadoPagoVenta.getText()) == false)) {
            String nombreEstadoVenta = txtNombreEstadoPagoVenta.getText();
            ClaseEstadoVenta.setNombreEstadoVenta(nombreEstadoVenta.toUpperCase());
            if (EstadoVentaNegocio.ModificarEstadoVenta(ClaseEstadoVenta)) {
                LimpiarFormularioEstadoVenta();
                LimpiarTablaEstadoVenta();
                ListarEstadoVentaVista();
                JOptionPane.showMessageDialog(null, "Información modificada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        } else {
            if ("".equals(txtNombreEstadoPagoVenta.getText())) {
                JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Estado de pago: " + txtNombreEstadoPagoVenta.getText() + ", esta registrado");
            }
        }
    }//GEN-LAST:event_btnEditarEstadoVentaActionPerformed

    private void btnDesactivarEstadoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarEstadoVentaActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreEstadoPagoVenta.getText())) {
            int estado = 0;
            EstadoVenta ClaseEstadoVenta = new EstadoVenta();
            ClaseEstadoVenta.setEstado(estado);
            ClaseEstadoVenta.setIdentificadorEstadoVenta(Integer.parseInt(txtCodigoEstadoVenta.getText()));
            if (EstadoVentaNegocio.DesactivarEstadoVenta(ClaseEstadoVenta)) {
                LimpiarFormularioEstadoVenta();
                LimpiarTablaEstadoVenta();
                ListarEstadoVentaVista();
                JOptionPane.showMessageDialog(null, "Estado de pago Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDesactivarEstadoVentaActionPerformed

    private void TablaPackSeleccionArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPackSeleccionArticulosMouseClicked
        // TODO add your handling code here:
        if (TablaPackSeleccionArticulos.isEnabled()) {
            btnAgregarArticuloPack.setEnabled(true);
            btnEliminarArticuloPack.setEnabled(false);
            txtUnidadesArticulosPack.setEnabled(true);
            txtUnidadesArticulosPack.setText("1");
        }

    }//GEN-LAST:event_TablaPackSeleccionArticulosMouseClicked

    private void TablaPackConArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPackConArticulosMouseClicked
        // TODO add your handling code here:
        if (TablaPackConArticulos.isEnabled()) {
            btnAgregarArticuloPack.setEnabled(false);
            btnEliminarArticuloPack.setEnabled(true);
            txtUnidadesArticulosPack.setText("");
            txtUnidadesArticulosPack.setEnabled(false);
            modificar = true;
        }

    }//GEN-LAST:event_TablaPackConArticulosMouseClicked

    private void btnCancelarPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPackActionPerformed
        // TODO add your handling code here:
        txtBuscarPack.setText("");
        LimpiarTablaPack();
        LimpiarTablaPackSeleccionArticulos();
        ListarPackVista();
        LimpiarFormularioPack();
    }//GEN-LAST:event_btnCancelarPackActionPerformed

    private void btnCrearPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearPackActionPerformed
        // TODO add your handling code here:
        String nombrePack = txtNombrePack.getText();
        if ((txtNombrePack.getText().isEmpty()) || (txtPrecioPack.getText().isEmpty()) || txtStockPack.getText().isEmpty()) {
            LimpiarFormularioPack();
            JOptionPane.showMessageDialog(null, "Debe Ingresar los datos solicitados", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (TablaPackConArticulos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Debe agregar articulos al pack", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (PackNegocio.BuscarPack(nombrePack.toUpperCase())) {
            JOptionPane.showMessageDialog(null, "El nombre de pack: " + nombrePack + ", esta registrado");
        } else {
            int estado = 1;
            Pack ClasePack = new Pack();
            ClasePack.setNombrePack(nombrePack.toUpperCase());
            ClasePack.setStockPack(Integer.parseInt(txtStockPack.getText()));
            ClasePack.setPrecioPack(Integer.parseInt(txtPrecioPack.getText()));
            ClasePack.setEstadoPack(estado);
            if (PackNegocio.GuardarPack(ClasePack)) {
                int identificadorPack = PackNegocio.CantidadPack();
                if (PackArticuloNegocio.GuardarPackArticulo(TablaPackConArticulos, identificadorPack)) {
                    LimpiarFormularioPack();
                    LimpiarTablaPackSeleccionArticulos();
                    LimpiarTablaPack();
                    ListarPackVista();
                    JOptionPane.showMessageDialog(null, "Pack creado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }
        }
    }//GEN-LAST:event_btnCrearPackActionPerformed

    private void txtBuscarPackKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPackKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarPack.getText())) {
            LimpiarFormularioPack();
            LimpiarTablaPack();
            PackNegocio.FiltrarPack(TablaPack, txtBuscarPack.getText());
        } else {
            LimpiarFormularioPack();
            LimpiarTablaPackSeleccionArticulos();
            LimpiarTablaPack();
            ListarPackVista();
        }
    }//GEN-LAST:event_txtBuscarPackKeyReleased

    private void TablaPackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPackMouseClicked
        // TODO add your handling code here:
        int fila = TablaPack.getSelectedRow();
        LimpiarTablaPackConArticulos();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Pack no Seleccionado");
        } else {
            btnEditarPack.setEnabled(true);
            btnCrearPack.setEnabled(false);
            int identificadorPack = Integer.parseInt((String) TablaPack.getValueAt(fila, 0).toString());
            String nombrePack = TablaPack.getValueAt(fila, 1).toString();
            String costoPack = TablaPack.getValueAt(fila, 2).toString();
            String stockPack = TablaPack.getValueAt(fila, 3).toString();
            String estadoPack = TablaPack.getValueAt(fila, 4).toString();
            if ("Inactivo".equals(estadoPack)) {
                ComboBoxEstadoPack.setEnabled(true);
                btnDesactivarPack.setEnabled(false);
                btnAgregarArticuloPack.setEnabled(false);
                btnEliminarArticuloPack.setEnabled(false);
                txtNombrePack.setEnabled(false);
                txtPrecioPack.setEnabled(false);
                txtStockPack.setEnabled(false);
                txtUnidadesArticulosPack.setText("");
                txtUnidadesArticulosPack.setEnabled(false);
                TablaPackConArticulos.setEnabled(false);
//                TablaPackConArticulos.setCellSelectionEnabled(false);
                TablaPackSeleccionArticulos.setEnabled(false);
//                TablaPackSeleccionArticulos.setCellSelectionEnabled(false);
            } else {
                btnDesactivarPack.setEnabled(true);
                ComboBoxEstadoPack.setEnabled(false);
                txtNombrePack.setEnabled(true);
                txtPrecioPack.setEnabled(true);
                txtStockPack.setEnabled(true);
                TablaPackConArticulos.setEnabled(true);
                TablaPackSeleccionArticulos.setEnabled(true);
            }
            txtCodigoPack.setText("" + identificadorPack);
            txtNombrePack.setText(nombrePack);
            txtPrecioPack.setText(costoPack);
            txtStockPack.setText(stockPack);
            ComboBoxEstadoPack.setSelectedItem(estadoPack);
            HeaderTablas(TablaPackConArticulos);
            ArticulosNegocio.ListarArticulosdelPack(TablaPackConArticulos, identificadorPack);
            cantidadRegistrosInicial = TablaPackConArticulos.getRowCount();

        }
    }//GEN-LAST:event_TablaPackMouseClicked

    private void btnDesactivarPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarPackActionPerformed
        // TODO add your handling code here:      
        if (!"".equals(txtNombrePack.getText()) && !"".equals(txtPrecioPack.getText()) && (TablaPackConArticulos.getRowCount() != 0)) {
            int estado = 0;
            Pack ClasePack = new Pack();
            ClasePack.setEstadoPack(estado);
            ClasePack.setIdentificadorPack(Integer.parseInt(txtCodigoPack.getText()));
            if (PackNegocio.DesactivarPack(ClasePack)) {
                LimpiarFormularioPack();
                LimpiarTablaPack();
                LimpiarTablaPackSeleccionArticulos();
                ListarPackVista();
                JOptionPane.showMessageDialog(null, "Pack Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDesactivarPackActionPerformed

    private void btnEditarPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPackActionPerformed
        // TODO add your handling code here:
        int estado = 0;
        if ((cantidadRegistrosInicial != TablaPackConArticulos.getRowCount())) {
            modificar = true;
        }
        if (ComboBoxEstadoPack.getSelectedItem() == "Activo") {
            estado = 1;
        }
        Pack ClasePack = new Pack();
        ClasePack.setEstadoPack(estado);
        ClasePack.setIdentificadorPack(Integer.parseInt(txtCodigoPack.getText()));
        ClasePack.setPrecioPack(Integer.parseInt(txtPrecioPack.getText()));
        ClasePack.setStockPack(Integer.parseInt(txtStockPack.getText()));
        if (!"".equals(txtNombrePack.getText()) && (!"".equals(txtPrecioPack.getText())) && (!"".equals(txtStockPack.getText())) && (TablaPackConArticulos.getRowCount() > 0) && (PackNegocio.BuscarPackEspecifico(ClasePack) == false) || (modificar)) {
            String nombrePack = txtNombrePack.getText();
            ClasePack.setNombrePack(nombrePack.toUpperCase());
            if (PackNegocio.ModificarPack(ClasePack)) {
                int identificadorPack = Integer.parseInt(txtCodigoPack.getText());
                if (PackArticuloNegocio.ModificarPackArticulo(TablaPackConArticulos, identificadorPack)) {
                    LimpiarFormularioPack();
                    LimpiarTablaPackSeleccionArticulos();
                    LimpiarTablaPack();
                    ListarPackVista();
                    JOptionPane.showMessageDialog(null, "Información modificada con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            if ("".equals(txtNombrePack.getText())) {
                JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El nombre del pack: " + txtNombrePack.getText() + ", esta registrado");
            }

        }

    }//GEN-LAST:event_btnEditarPackActionPerformed

    private void txtStockPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockPackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockPackActionPerformed

    private void txtNombreCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCategoriaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombreCategoria.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtNombreCategoriaKeyTyped

    private void txtNombreEstadoPagoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEstadoPagoVentaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombreEstadoPagoVenta.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtNombreEstadoPagoVentaKeyTyped

    private void txtNombrePackKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombrePackKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombrePack.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtNombrePackKeyTyped

    private void txtPrecioPackKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioPackKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9') || (txtPrecioPack.getText().length() >= 15))
            evt.consume();
    }//GEN-LAST:event_txtPrecioPackKeyTyped

    private void txtStockPackKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockPackKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9') || (txtStockPack.getText().length() >= 4))
            evt.consume();
    }//GEN-LAST:event_txtStockPackKeyTyped

    private void txtNombreArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreArticuloKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombreArticulo.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtNombreArticuloKeyTyped

    private void txtMarcaArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaArticuloKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtMarcaArticulo.getText().length() >= 45))
            evt.consume();
    }//GEN-LAST:event_txtMarcaArticuloKeyTyped

    private void txtBuscarProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProveedorKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarProveedor.getText())) {
            LimpiarFormularioProveedor();
            LimpiarTablaProveedor();
            ProveedorNegocio.FiltrarProveedor(TablaProveedor, txtBuscarProveedor.getText());
        } else {
            LimpiarFormularioProveedor();
            LimpiarTablaProveedor();
            ListarProveedorVista();
        }
    }//GEN-LAST:event_txtBuscarProveedorKeyReleased

    private void TablaProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProveedorMouseClicked
        // TODO add your handling code here:
        int fila = TablaProveedor.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Proveedor no Seleccionado");
        } else {
            btnEditarProveedor.setEnabled(true);
            btnGuardarProveedor.setEnabled(false);
            int IdentificadorProveedor = Integer.parseInt((String) TablaProveedor.getValueAt(fila, 0).toString());
            String RazonSocial = TablaProveedor.getValueAt(fila, 1).toString();
            String Nombre = TablaProveedor.getValueAt(fila, 2).toString();
            String Rut = TablaProveedor.getValueAt(fila, 3).toString();
            Rut = Rut.substring(0, 8);
            String Dv = TablaProveedor.getValueAt(fila, 3).toString();
            Dv = Dv.substring(9, 10);
            String Telefono = TablaProveedor.getValueAt(fila, 4).toString();
            System.out.println("telefono" + Telefono.length());
            Telefono = Telefono.substring(3, 12);
            String Email = TablaProveedor.getValueAt(fila, 5).toString();
            String Direccion = TablaProveedor.getValueAt(fila, 6).toString();
            String Estado = TablaProveedor.getValueAt(fila, 7).toString();
            if ("Inactivo".equals(Estado)) {
                combobxEstadoProveedor.setEnabled(true);
                btnDesactivarProveedor.setEnabled(false);
            } else {
                btnDesactivarProveedor.setEnabled(true);

                combobxEstadoProveedor.setEnabled(false);
            }
            txtIdProveedor.setText("" + IdentificadorProveedor);
            txtRazonSocialProveedor.setText(RazonSocial);
            txtNombreContacto.setText(Nombre);
            txtRutProveedor.setText(Rut);
            txtDireccionProveedor.setText(Direccion);
            txtProveedorTelefono.setText(Telefono);
            txtEmailProveedor.setText(Email);
            txtDvProveedor.setText(Dv);
            combobxEstadoProveedor.setSelectedItem(Estado);
        }
    }//GEN-LAST:event_TablaProveedorMouseClicked

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        // TODO add your handling code here:
        ValidacionRut validar = new ValidacionRut();
        String rut = txtRutProveedor.getText() + "-" + txtDvProveedor.getText();
        validar = new ValidacionRut(rut);
        if (validar.Validacion() == true) {
            txtNombreContacto.setEnabled(true);
            txtDireccionProveedor.setEnabled(true);
            txtRazonSocialProveedor.setEnabled(true);
            txtProveedorTelefono.setEnabled(true);
            txtEmailProveedor.setEnabled(true);
            combobxEstadoProveedor.setEnabled(true);

            if (!"".equals(txtRazonSocialProveedor.getText()) && !"".equals(txtNombreContacto.getText())
                    && !"".equals(txtRutProveedor.getText())
                    && !"".equals(txtDireccionProveedor.getText())
                    && !"".equals(txtProveedorTelefono.getText())
                    && !"".equals(txtEmailProveedor.getText())
                    && !"".equals(txtDvProveedor.getText())) {

                int estado = 0;
                if (combobxEstadoProveedor.getSelectedItem() == "Activo") {
                    estado = 1;
                }
                Proveedor ClaseProveedor = new Proveedor();
                ClaseProveedor.setNombreProveedor(txtNombreContacto.getText());
                ClaseProveedor.setRazonSocialProveedor(txtRazonSocialProveedor.getText());
                ClaseProveedor.setDireccionProveedor(txtDireccionProveedor.getText());
                ClaseProveedor.setRut(txtRutProveedor.getText() + "-" + txtDvProveedor.getText());
                ClaseProveedor.setTelefonoProveedor("+56" + txtProveedorTelefono.getText());
                ClaseProveedor.setCorreo(txtEmailProveedor.getText());
                ClaseProveedor.setEstado(estado);
                ClaseProveedor.setIdentificadorProveedor(Integer.parseInt(txtIdProveedor.getText()));
                if (ProveedorNegocio.ModificarProveedor(ClaseProveedor)) {
                    LimpiarFormularioProveedor();
                    LimpiarTablaProveedor();
                    ListarProveedorVista();
                    JOptionPane.showMessageDialog(null, "Información modificada con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
            }

        } else {

            txtNombreContacto.setEnabled(false);
            txtDireccionProveedor.setEnabled(false);
            txtRazonSocialProveedor.setEnabled(false);
            txtProveedorTelefono.setEnabled(false);
            txtEmailProveedor.setEnabled(false);
            combobxEstadoProveedor.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Rut Invalido", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnDesactivarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreContacto.getText())) {
            int estado = 0;
            Proveedor ClaseProveedor = new Proveedor();
            ClaseProveedor.setEstado(estado);
            ClaseProveedor.setIdentificadorProveedor(Integer.parseInt(txtIdProveedor.getText()));
            if (ProveedorNegocio.DesactivarProveedor(ClaseProveedor)) {
                LimpiarFormularioProveedor();
                LimpiarTablaProveedor();
                ListarProveedorVista();
                JOptionPane.showMessageDialog(null, "Proveedor Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar");
        }
    }//GEN-LAST:event_btnDesactivarProveedorActionPerformed

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        // TODO add your handling code here:
        if (!"".equals(txtBuscarCliente.getText())) {
            LimpiarFormularioClientes();
            LimpiarTablaCliente();
            ClientesNegocio.FiltrarCliente(TablaClientes, txtBuscarCliente.getText());
        } else {
            LimpiarFormularioClientes();
            LimpiarTablaCliente();
            ListarClienteVista();
        }

    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void btnDesactivarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNombreCliente.getText()) && !"".equals(txtApellidoCliente.getText()) && !"".equals(txtEmailCliente.getText())
                && !"".equals(txtTelefonoCliente.getText()) && !"".equals(txtDireccionCliente.getText()) && !"".equals(txtRutCliente.getText())
                && ComboBoxRedSocialCliente.getSelectedItem() != "Seleccionar Canal" && jDateCliente.getDate() != null) {
            int estado = 0;
            Clientes ClaseClientes = new Clientes();
            ClaseClientes.setEstadoCliente(estado);
            int fila = TablaClientes.getSelectedRow();
            int IdentificadorCliente = Integer.parseInt((String) TablaClientes.getValueAt(fila, 0).toString());
            ClaseClientes.setIdentificadorCliente(IdentificadorCliente);
            if (ClientesNegocio.DesactivarCliente(ClaseClientes)) {

                ComboBoxRedSocialCliente.removeAllItems();
                LimpiarFormularioClientes();
                LimpiarTablaCliente();
                ListarClienteVista();
                JOptionPane.showMessageDialog(null, "Cliente Desactivado Exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Problemas de Conexion, Intente mas tarde");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan datos por Ingresar", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnDesactivarClienteActionPerformed

    private void txtRutClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutClienteKeyReleased
        // TODO add your handling code here:
        ValidacionRut valor = new ValidacionRut();

        int max = 8;

        if (txtRutCliente.getText().length() < max) {
            DeshabilitarCamposClientes();
            mensajeRutCliente.setText("");
        } else {
            if (txtRutCliente.getText().length() == max) {

                if (!"".equals(txtDVCliente.getText())) {
                    String rut = txtRutCliente.getText() + "-" + txtDVCliente.getText();
                    valor = new ValidacionRut(rut.toUpperCase());

                    if (valor.Validacion() == true) {
                        HabilitarCamposClientes();

                        if (btnEditarCliente.isEnabled() == true) {
                            btnGuardarCliente.setEnabled(false);
                        }
                        mensajeRutCliente.setText("");
                    } else {
                        DeshabilitarCamposClientes();
                        mensajeRutCliente.setText("Rut Invalido");
                    }
                } else {
                    txtDVCliente.requestFocus();
                }

            }

        }

    }//GEN-LAST:event_txtRutClienteKeyReleased

    private void txtRutClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutClienteKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 0 && c <= 47) || (c >= 58 && c <= 500) || (txtRutCliente.getText().length() == 8)) {
            evt.consume();
        }

    }//GEN-LAST:event_txtRutClienteKeyTyped

    private void txtDVClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDVClienteKeyReleased
        // TODO add your handling code here:
        ValidacionRut valor = new ValidacionRut();

        int max = 1;

        if (txtDVCliente.getText().length() < max) {
            DeshabilitarCamposClientes();
            mensajeRutCliente.setText("");
        } else {
            if (txtDVCliente.getText().length() == max) {

                if (!"".equals(txtRutCliente.getText())) {
                    String rut = txtRutCliente.getText() + "-" + txtDVCliente.getText();
                    rut = rut.toUpperCase();
                    valor = new ValidacionRut(rut);

                    if (valor.Validacion() == true) {
                        HabilitarCamposClientes();
                        if (btnEditarCliente.isEnabled() == true) {
                            btnGuardarCliente.setEnabled(false);
                        }
                        mensajeRutCliente.setText("");
                    } else {
                        DeshabilitarCamposClientes();
                        mensajeRutCliente.setText("Rut Invalido");
                    }
                }

            }

        }

    }//GEN-LAST:event_txtDVClienteKeyReleased

    private void txtDVClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDVClienteKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 0 && c <= 47) || (c >= 58 && c <= 74) || (c >= 76 && c <= 106) || (c >= 108 && c <= 500) || (txtDVCliente.getText().length() == 1)) {
            evt.consume();
        }

    }//GEN-LAST:event_txtDVClienteKeyTyped

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtNombreCliente.getText().length() >= 45)) {
            evt.consume();
        }

    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtApellidoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoClienteKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 33 && c <= 64) || (c >= 91 && c <= 96)
                || (c >= 123 && c <= 179) || (c >= 181 && c <= 208)
                || (c >= 210 && c <= 240) || (c >= 242 && c <= 255) || (txtApellidoCliente.getText().length() >= 45)) {
            evt.consume();
        }

    }//GEN-LAST:event_txtApellidoClienteKeyTyped

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if ((c >= 0 && c <= 47) || (c >= 58 && c <= 500) || (txtTelefonoCliente.getText().length() == 9)) {
            evt.consume();
        }

    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void txtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubTotalActionPerformed

    private void txtSaludoDestinatarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaludoDestinatarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaludoDestinatarioActionPerformed

    private void txtDireccionDestinatarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionDestinatarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionDestinatarioActionPerformed

    private void txtNombreDestinatarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreDestinatarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreDestinatarioActionPerformed

    private void txtTelefonoClienteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoClienteVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoClienteVentaActionPerformed

    private void txtRutClienteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutClienteVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutClienteVentaActionPerformed

    private void txtEmailClienteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailClienteVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailClienteVentaActionPerformed

    private void txtNombreClienteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteVentaActionPerformed

    private void txtNumeroPedidoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroPedidoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroPedidoVentaActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Vista().setVisible(true);
//            }
//        });
//    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxBancoConf;
    private javax.swing.JComboBox<String> ComboBoxCategoriaArticulo;
    private javax.swing.JComboBox<String> ComboBoxComunaDestinatario;
    private javax.swing.JComboBox<String> ComboBoxEstado;
    private javax.swing.JComboBox<String> ComboBoxEstadoArticulo;
    private javax.swing.JComboBox<String> ComboBoxEstadoBanco;
    private javax.swing.JComboBox<String> ComboBoxEstadoCanal;
    private javax.swing.JComboBox<String> ComboBoxEstadoCategoria;
    private javax.swing.JComboBox<String> ComboBoxEstadoComuna;
    private javax.swing.JComboBox<String> ComboBoxEstadoPack;
    private javax.swing.JComboBox<String> ComboBoxEstadoVenta;
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
    private javax.swing.JTable TablaConfirmacion;
    private javax.swing.JTable TablaEstadoVenta;
    private javax.swing.JTable TablaListadoDestino;
    private javax.swing.JTable TablaListadoEstadosDespacho;
    private javax.swing.JTable TablaPack;
    private javax.swing.JTable TablaPackConArticulos;
    private javax.swing.JTable TablaPackSeleccionArticulos;
    private javax.swing.JTable TablaProveedor;
    private javax.swing.JTable TablaUsuarios;
    private javax.swing.JTable Tabla_Provedores;
    private javax.swing.JLabel Titulo_Prov;
    private javax.swing.JLabel Titulo_Prov7;
    private javax.swing.JButton btnAgregarArticuloPack;
    private javax.swing.JButton btnBuscarVenta;
    private javax.swing.JButton btnCancelarArticulos;
    private javax.swing.JButton btnCancelarBanco;
    private javax.swing.JButton btnCancelarCanal;
    private javax.swing.JButton btnCancelarCategoria;
    public javax.swing.JButton btnCancelarCliente;
    private javax.swing.JButton btnCancelarComuna;
    private javax.swing.JButton btnCancelarDatosDestinatario;
    private javax.swing.JButton btnCancelarEstadoVenta;
    private javax.swing.JButton btnCancelarPack;
    public static javax.swing.JButton btnCancelarProveedor;
    private javax.swing.JButton btnCancelarUsuario;
    private javax.swing.JButton btnCancelarVenta;
    private javax.swing.JButton btnComprarProveedor;
    private javax.swing.JButton btnCrearPack;
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
    private javax.swing.JButton btnEliminarArticuloPack;
    private javax.swing.JButton btnGuardarArticulos;
    private javax.swing.JButton btnGuardarBanco;
    private javax.swing.JButton btnGuardarCanal;
    private javax.swing.JButton btnGuardarCategoria;
    public javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarComuna;
    private javax.swing.JButton btnGuardarDatosDestinatario;
    private javax.swing.JButton btnGuardarEstadoVenta;
    public static javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnGuardarUsuario;
    private javax.swing.JButton btnGuardarVenta;
    public javax.swing.JButton btnVentaCliente;
    private javax.swing.JButton buscar;
    private javax.swing.JPanel clientes;
    private javax.swing.JComboBox<String> combobxEstadoCliente;
    private javax.swing.JComboBox<String> combobxEstadoProveedor;
    private javax.swing.JComboBox<String> combobxHoraEntrega;
    private javax.swing.JComboBox<String> combobxHoraFinEntrega;
    private javax.swing.JComboBox<String> combobxPackDestinatario;
    private javax.swing.JTabbedPane compras;
    private javax.swing.JButton configuracion;
    private javax.swing.JLabel dreamGifts;
    private javax.swing.JButton faq1;
    private javax.swing.JTabbedPane informes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private com.toedter.calendar.JDateChooser jDateBuscarDespacho;
    private com.toedter.calendar.JDateChooser jDateBuscarDestinos;
    private com.toedter.calendar.JDateChooser jDateCliente;
    private com.toedter.calendar.JDateChooser jDateConfirmacion;
    private com.toedter.calendar.JDateChooser jDateVenta;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JFrame jFrame6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
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
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
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
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
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
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTabbedPane maestros;
    private javax.swing.JLabel mensajeRutCliente;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JButton notificaciones;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtApellidoCliente;
    private javax.swing.JTextField txtBuscarArticulo;
    private javax.swing.JTextField txtBuscarBanco;
    private javax.swing.JTextField txtBuscarCanal;
    private javax.swing.JTextField txtBuscarCategoria;
    public javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtBuscarComuna;
    private javax.swing.JTextField txtBuscarEstadoVenta;
    private javax.swing.JTextField txtBuscarPack;
    private javax.swing.JTextField txtBuscarProveedor;
    private javax.swing.JTextField txtBuscarVenta;
    private javax.swing.JTextField txtCodigoArticulo;
    private javax.swing.JTextField txtCodigoBanco;
    private javax.swing.JTextField txtCodigoCanal;
    private javax.swing.JTextField txtCodigoCategoria;
    private javax.swing.JTextField txtCodigoComuna;
    private javax.swing.JTextField txtCodigoEstadoVenta;
    private javax.swing.JTextField txtCodigoPack;
    private javax.swing.JTextField txtCuentaUsuario;
    private javax.swing.JTextField txtDVCliente;
    private javax.swing.JTextField txtDVClienteVenta;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionDestinatario;
    public static javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDvProveedor;
    public javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtEmailClienteVenta;
    public static javax.swing.JTextField txtEmailProveedor;
    private javax.swing.JTextField txtEnvio;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtIdentificadorArticulo;
    private javax.swing.JTextField txtMarcaArticulo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreArticulo;
    private javax.swing.JTextField txtNombreBanco;
    private javax.swing.JTextField txtNombreCanal;
    private javax.swing.JTextField txtNombreCategoria;
    public javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteConf;
    private javax.swing.JTextField txtNombreClienteVenta;
    private javax.swing.JTextField txtNombreComuna;
    public static javax.swing.JTextField txtNombreContacto;
    private javax.swing.JTextField txtNombreDestinatario;
    private javax.swing.JTextField txtNombreEstadoPagoVenta;
    private javax.swing.JTextField txtNombrePack;
    private javax.swing.JTextField txtNumeroPedidoConf;
    private javax.swing.JTextField txtNumeroPedidoVenta;
    private javax.swing.JTextField txtPrecioPack;
    public static javax.swing.JTextField txtProveedorTelefono;
    public static javax.swing.JTextField txtRazonSocialProveedor;
    public javax.swing.JTextField txtRutCliente;
    private javax.swing.JTextField txtRutClienteConf;
    private javax.swing.JTextField txtRutClienteVenta;
    public static javax.swing.JTextField txtRutProveedor;
    private javax.swing.JTextField txtSaludoDestinatario;
    private javax.swing.JTextField txtStockPack;
    private javax.swing.JTextField txtSubTotal;
    public javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoClienteVenta;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTransaccionConf;
    private javax.swing.JTextField txtUnidadArticulo;
    private javax.swing.JTextField txtUnidadesArticulosPack;
    private javax.swing.JTextField txtbuscarUsuario;
    private javax.swing.JTabbedPane ventas;
    // End of variables declaration//GEN-END:variables
}
