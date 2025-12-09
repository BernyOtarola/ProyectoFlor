package vista.reserva;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.DetalleReserva;

public class ReservaDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReservaDialog.class.getName());

   private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HOVER_COLOR = new Color(52, 152, 219);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);

    /**
     * @param parent
     * @param modal
     */
    public ReservaDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        aplicarEstiloModerno();
        setLocationRelativeTo(null);
        setTitle("Nueva Reserva de Vehículos");
        configurarTablaDetalle();

        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        modelo.setRowCount(0);

        txtTotal.setEditable(false);
        txtTotal.setText("₡0.00");

        LocalDate hoy = LocalDate.now();
        txtFechaInicio.setText(hoy.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE));
        txtFechaFin.setText(hoy.plusDays(1).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE));
    }

    private void aplicarEstiloModerno() {
        getContentPane().setBackground(BACKGROUND_COLOR);

        agregarHeader();

        estilizarBoton(btnAgregarVehiculo, ACCENT_COLOR, "Agregar Vehículo");
        estilizarBoton(btnQuitarDetalle, WARNING_COLOR, "Quitar Detalle");
        estilizarBoton(btnGuardar, PRIMARY_COLOR, "Guardar");
        estilizarBoton(btnCancelar, DANGER_COLOR, "Cancelar");

        estilizarCombo(cboCliente);
        estilizarCombo(cboEmpleado);

        estilizarCampoTexto(txtFechaInicio);
        estilizarCampoTexto(txtFechaFin);
        estilizarCampoTextoTotal(txtTotal);

        estilizarLabel(jLabel1);
        estilizarLabel(jLabel2);
        estilizarLabel(jLabel3);
        estilizarLabel(jLabel4);
        estilizarLabel(jLabel5);

        estilizarTabla();
    }

    private void agregarHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 20));

        JLabel lblTitulo = new JLabel("🚗 NUEVA RESERVA DE VEHÍCULOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);

        getContentPane().add(headerPanel, BorderLayout.NORTH);
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(CARD_COLOR);
        combo.setForeground(TEXT_COLOR);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        combo.setPreferredSize(new Dimension(combo.getPreferredSize().width, 40));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarBoton(JButton btn, Color color, String texto) {
        btn.setText(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 42));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = color;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(originalColor);
            }
        });
    }

    private void estilizarCampoTexto(javax.swing.text.JTextComponent campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        campo.setPreferredSize(new Dimension(campo.getPreferredSize().width, 40));

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
            }
        });
    }

    private void estilizarCampoTextoTotal(javax.swing.JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        campo.setForeground(ACCENT_COLOR);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        campo.setBackground(new Color(232, 247, 237));
        campo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campo.setPreferredSize(new Dimension(campo.getPreferredSize().width, 45));
    }

    private void estilizarLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
    }

    private void estilizarTabla() {
        tblDetalle.setBackground(CARD_COLOR);
        tblDetalle.setForeground(TEXT_COLOR);
        tblDetalle.setGridColor(new Color(224, 224, 224));
        tblDetalle.setRowHeight(35);
        tblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblDetalle.setSelectionBackground(HOVER_COLOR);
        tblDetalle.setSelectionForeground(Color.WHITE);
        tblDetalle.setShowVerticalLines(true);
        tblDetalle.setShowHorizontalLines(true);
        tblDetalle.setIntercellSpacing(new Dimension(1, 1));

        JTableHeader header = tblDetalle.getTableHeader();
        header.setBackground(SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setBorder(BorderFactory.createEmptyBorder());

        jScrollPane1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        jScrollPane1.getViewport().setBackground(CARD_COLOR);
    }

    private void configurarTablaDetalle() {
        tblDetalle.setRowHeight(35);
        tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblDetalle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(130);
    }

    public void cargarClientes(List<modelo.Cliente> clientes) {
        cboCliente.removeAllItems();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay clientes registrados.\nDebe crear al menos un cliente primero.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (modelo.Cliente c : clientes) {
            cboCliente.addItem(c.getIdCliente() + " - " + c.getNombre());
        }
    }

    public void cargarEmpleados(List<modelo.Empleado> empleados) {
        cboEmpleado.removeAllItems();
        if (empleados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay empleados registrados.\nDebe crear al menos un empleado primero.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (modelo.Empleado e : empleados) {
            cboEmpleado.addItem(e.getIdEmpleado() + " - " + e.getNombre());
        }
    }

    public boolean validarFechas() {
        String inicio = txtFechaInicio.getText().trim();
        String fin = txtFechaFin.getText().trim();

        if (!util.ValidacionUtil.rangoFechasValido(inicio, fin)) {
            JOptionPane.showMessageDialog(this,
                    "Las fechas son inválidas.\n"
                    + "Verifique que:\n"
                    + "- El formato sea yyyy-MM-dd\n"
                    + "- La fecha fin sea posterior a la fecha inicio\n"
                    + "- Las fechas no sean pasadas",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            LocalDate fechaInicio = LocalDate.parse(inicio);
            LocalDate fechaFin = LocalDate.parse(fin);
            long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

            if (dias > 365) {
                JOptionPane.showMessageDialog(this,
                        "La reserva no puede exceder 365 días.",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (dias < 1) {
                JOptionPane.showMessageDialog(this,
                        "La reserva debe ser de al menos 1 día.",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use: yyyy-MM-dd\n"
                    + "Ejemplo: 2024-12-25",
                    "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public int calcularDias() {
        try {
            LocalDate inicio = LocalDate.parse(txtFechaInicio.getText().trim());
            LocalDate fin = LocalDate.parse(txtFechaFin.getText().trim());
            long dias = ChronoUnit.DAYS.between(inicio, fin);
            return dias > 0 ? (int) dias : 1;
        } catch (Exception e) {
            return 1;
        }
    }

    public void agregarDetalle(DetalleReserva d) {
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object valorCelda = modelo.getValueAt(i, 0);
            if (valorCelda != null) {
                int idExistente = (int) valorCelda;
                if (idExistente == d.getIdVehiculo()) {
                    JOptionPane.showMessageDialog(this,
                            "Este vehículo ya fue agregado a la reserva.",
                            "Vehículo Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }

        modelo.addRow(new Object[]{
            d.getIdVehiculo(),
            d.getPlacaVehiculo(),
            String.format("₡%.2f", d.getPrecioDia()),
            d.getDias(),
            String.format("₡%.2f", d.getSubtotal())
        });

        recalcularTotal();
    }

    public void quitarDetalle() {
        int fila = tblDetalle.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un vehículo de la tabla para quitar.",
                    "Sin Selección", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea quitar este vehículo de la reserva?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
            modelo.removeRow(fila);
            recalcularTotal();
        }
    }

    private void recalcularTotal() {
        double total = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String subtotalStr = modelo.getValueAt(i, 4).toString();
            subtotalStr = subtotalStr.replace("₡", "").replace(",", "").trim();
            total += Double.parseDouble(subtotalStr);
        }

        txtTotal.setText(String.format("₡%.2f", total));
    }

    public int getClienteSeleccionado() {
        if (cboCliente.getSelectedIndex() == -1) {
            return -1;
        }
        String seleccion = cboCliente.getSelectedItem().toString();
        String id = seleccion.split(" - ")[0];
        return Integer.parseInt(id);
    }

    public int getEmpleadoSeleccionado() {
        if (cboEmpleado.getSelectedIndex() == -1) {
            return -1;
        }
        String seleccion = cboEmpleado.getSelectedItem().toString();
        String id = seleccion.split(" - ")[0];
        return Integer.parseInt(id);
    }

    public List<DetalleReserva> getDetalles() {
        List<DetalleReserva> lista = new ArrayList<>();
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            DetalleReserva d = new DetalleReserva();

            d.setIdVehiculo((int) modelo.getValueAt(i, 0));
            d.setPlacaVehiculo(modelo.getValueAt(i, 1).toString());

            String precioStr = modelo.getValueAt(i, 2).toString()
                    .replace("₡", "").replace(",", "").trim();
            d.setPrecioDia(Double.parseDouble(precioStr));

            d.setDias((int) modelo.getValueAt(i, 3));
            String subtotalStr = modelo.getValueAt(i, 4).toString()
                    .replace("₡", "").replace(",", "").trim();
            d.setSubtotal(Double.parseDouble(subtotalStr));

            lista.add(d);
        }

        return lista;
    }

    public void limpiarFormulario() {
        cboCliente.setSelectedIndex(-1);
        cboEmpleado.setSelectedIndex(-1);

        LocalDate hoy = LocalDate.now();
        txtFechaInicio.setText(hoy.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE));
        txtFechaFin.setText(hoy.plusDays(1).format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE));

        txtTotal.setText("₡0.00");

        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        modelo.setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        btnAgregarVehiculo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnQuitarDetalle = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();
        cboEmpleado = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtFechaInicio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFechaFin = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(700, 600));
        setMinimumSize(new java.awt.Dimension(700, 600));
        setPreferredSize(new java.awt.Dimension(700, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(700, 600));

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "IdVehiculo", "Placa", "Precio", "Dias", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(tblDetalle);

        btnAgregarVehiculo.setText("🚗 Agregar Vehiculo");

        btnCancelar.setText("🚫 Cancelar ");

        btnQuitarDetalle.setText("🗑️ Quitar Detalle");

        btnGuardar.setText("📝 Guardar ");

        jLabel1.setText("Cliente:");

        jLabel2.setText("Empleado:");

        jLabel3.setText("FechaInicio:");

        jLabel4.setText("Total:");

        jLabel5.setText("FechaFin:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnAgregarVehiculo)
                        .addGap(50, 50, 50)
                        .addComponent(btnQuitarDetalle)
                        .addGap(70, 70, 70)
                        .addComponent(btnGuardar)
                        .addGap(60, 60, 60)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboEmpleado, 0, 157, Short.MAX_VALUE)
                            .addComponent(cboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtFechaFin))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitarDetalle)
                    .addComponent(btnGuardar)
                    .addComponent(btnAgregarVehiculo)
                    .addComponent(btnCancelar))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            ReservaDialog dialog = new ReservaDialog(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAgregarVehiculo;
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnQuitarDetalle;
    public javax.swing.JComboBox<String> cboCliente;
    public javax.swing.JComboBox<String> cboEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblDetalle;
    public javax.swing.JTextField txtFechaFin;
    public javax.swing.JTextField txtFechaInicio;
    public javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
