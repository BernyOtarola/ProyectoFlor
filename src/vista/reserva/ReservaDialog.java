/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.DetalleReserva;

public class ReservaDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(ReservaDialog.class.getName());

    public ReservaDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        configurarTablaDetalle();
        txtTotal.setEditable(false);

        // Configurar estado inicial
        //cboEstado.setVisible(false); // Si no lo usas, ocultarlo
    }

    /**
     * Configura la tabla de detalles
     */
    private void configurarTablaDetalle() {
        tblDetalle.setRowHeight(24);
        tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblDetalle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Ajustar anchos de columna
        tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(80);  // IdVehiculo
        tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(120); // Placa
        tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(100); // Precio
        tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(80);  // Dias
        tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(120); // Subtotal
    }

    /**
     * Carga clientes en el combo
     *
     * @param clientes
     */
    public void cargarClientes(List<modelo.Cliente> clientes) {
        cboCliente.removeAllItems();
        for (modelo.Cliente c : clientes) {
            cboCliente.addItem(c.getIdCliente() + " - " + c.getNombre());
        }
    }

    /**
     * Carga empleados en el combo
     *
     * @param empleados
     */
    public void cargarEmpleados(List<modelo.Empleado> empleados) {
        cboEmpleado.removeAllItems();
        for (modelo.Empleado e : empleados) {
            cboEmpleado.addItem(e.getIdEmpleado() + " - " + e.getNombre());
        }
    }

    // Agregar método en ReservaDialog:
    private boolean validarFechas() {
        String inicio = txtFechaInicio.getText().trim();
        String fin = txtFechaFin.getText().trim();

        if (!util.ValidacionUtil.rangoFechasValido(inicio, fin)) {
            JOptionPane.showMessageDialog(this,
                    "Las fechas son inválidas o la fecha fin es anterior a la fecha inicio.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que no sea más de 1 año
        try {
            LocalDate fechaInicio = LocalDate.parse(inicio);
            LocalDate fechaFin = LocalDate.parse(fin);
            long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);

            if (dias > 365) {
                JOptionPane.showMessageDialog(this,
                        "La reserva no puede exceder 365 días.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use: yyyy-MM-dd",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Agrega un detalle a la tabla
     *
     * @param d
     */
    public void agregarDetalle(DetalleReserva d) {
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();

        modelo.addRow(new Object[]{
            d.getIdVehiculo(),
            d.getPlacaVehiculo(),
            String.format("₡%.2f", d.getPrecioDia()),
            d.getDias(),
            String.format("₡%.2f", d.getSubtotal())
        });

        recalcularTotal();
    }

    /**
     * Quita el detalle seleccionado
     */
    public void quitarDetalle() {
        int fila = tblDetalle.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un detalle para quitar.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
        modelo.removeRow(fila);
        recalcularTotal();
    }

    /**
     * Recalcula el total de la reserva
     */
    private void recalcularTotal() {
        double total = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String subtotalStr = modelo.getValueAt(i, 4).toString();
            // Limpiar formato de moneda
            subtotalStr = subtotalStr.replace("₡", "").replace(",", "").trim();
            total += Double.parseDouble(subtotalStr);
        }

        txtTotal.setText(String.format("₡%.2f", total));
    }

    /**
     * Obtiene el ID del cliente seleccionado
     *
     * @return
     */
    public int getClienteSeleccionado() {
        if (cboCliente.getSelectedIndex() == -1) {
            return -1;
        }

        String seleccion = cboCliente.getSelectedItem().toString();
        String id = seleccion.split(" - ")[0];
        return Integer.parseInt(id);
    }

    /**
     * Obtiene el ID del empleado seleccionado
     *
     * @return
     */
    public int getEmpleadoSeleccionado() {
        if (cboEmpleado.getSelectedIndex() == -1) {
            return -1;
        }

        String seleccion = cboEmpleado.getSelectedItem().toString();
        String id = seleccion.split(" - ")[0];
        return Integer.parseInt(id);
    }

    /**
     * Obtiene la lista de detalles de la tabla
     *
     * @return
     */
    public List<DetalleReserva> getDetalles() {
        List<DetalleReserva> lista = new ArrayList<>();
        DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            DetalleReserva d = new DetalleReserva();

            d.setIdVehiculo((int) modelo.getValueAt(i, 0));
            d.setPlacaVehiculo(modelo.getValueAt(i, 1).toString());

            // Limpiar formato de precio
            String precioStr = modelo.getValueAt(i, 2).toString()
                    .replace("₡", "").replace(",", "").trim();
            d.setPrecioDia(Double.parseDouble(precioStr));

            d.setDias((int) modelo.getValueAt(i, 3));

            // Limpiar formato de subtotal
            String subtotalStr = modelo.getValueAt(i, 4).toString()
                    .replace("₡", "").replace(",", "").trim();
            d.setSubtotal(Double.parseDouble(subtotalStr));

            lista.add(d);
        }

        return lista;
    }

    /**
     * Limpia el formulario
     */
    public void limpiarFormulario() {
        cboCliente.setSelectedIndex(-1);
        cboEmpleado.setSelectedIndex(-1);
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
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
        setMaximumSize(new java.awt.Dimension(700, 500));
        setMinimumSize(new java.awt.Dimension(700, 500));
        setPreferredSize(new java.awt.Dimension(700, 500));
        setResizable(false);
        setSize(new java.awt.Dimension(700, 500));

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
        btnAgregarVehiculo.addActionListener(this::btnAgregarVehiculoActionPerformed);

        btnCancelar.setText("🚫 Cancelar ");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnQuitarDetalle.setText("🗑️ Quitar Detalle");
        btnQuitarDetalle.addActionListener(this::btnQuitarDetalleActionPerformed);

        btnGuardar.setText("📝 Guardar ");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        jLabel1.setText("Cliente:");

        jLabel2.setText("Empleado:");

        cboCliente.addActionListener(this::cboClienteActionPerformed);

        jLabel3.setText("FechaInicio:");

        txtFechaInicio.addActionListener(this::txtFechaInicioActionPerformed);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
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

    private void cboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboClienteActionPerformed

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioActionPerformed

    private void btnAgregarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarVehiculoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnQuitarDetalleActionPerformed

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
