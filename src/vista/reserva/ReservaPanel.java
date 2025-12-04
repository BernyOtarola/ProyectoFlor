package vista.reserva;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.DetalleReserva;
import modelo.Reserva;

public class ReservaPanel extends javax.swing.JPanel {

   public ReservaPanel() {
        initComponents();
        configurarTabla();
    }

    /**
     * Configuración inicial de la tabla
     */
    private void configurarTabla() {
        tblReservas.setRowHeight(24);
        tblReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblReservas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Ajustar anchos de columna
        tblReservas.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tblReservas.getColumnModel().getColumn(1).setPreferredWidth(220); // Cliente
        tblReservas.getColumnModel().getColumn(2).setPreferredWidth(140); // Fecha Reserva
        tblReservas.getColumnModel().getColumn(3).setPreferredWidth(120); // Inicio
        tblReservas.getColumnModel().getColumn(4).setPreferredWidth(120); // Fin
        tblReservas.getColumnModel().getColumn(5).setPreferredWidth(100); // Total
    }

    /**
     * NUEVO: Carga lista de reservas en la tabla
     * @param lista
     */
    public void cargarReservas(List<Reserva> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        modelo.setRowCount(0);

        for (Reserva r : lista) {
            modelo.addRow(new Object[]{
                r.getIdReserva(),
                r.getClienteNombre(),
                r.getFechaReserva(),
                r.getFechaInicio(),
                r.getFechaFin(),
                String.format("₡%.2f", r.getTotal())
            });
        }
    }

    /**
     * NUEVO: Sobrecarga sin parámetros para compatibilidad
     */
    public void cargarReservas() {
        // Este método se mantiene vacío o puede cargar todas las reservas
        // Se llama desde el controlador con la lista como parámetro
    }

    /**
     * Muestra el detalle de una reserva en un diálogo
     * @param detalles
     */
    public void mostrarDetalle(List<DetalleReserva> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Esta reserva no tiene vehículos asociados.", 
                "Detalle", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear diálogo para mostrar detalle
        JDialog dlg = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                  "Detalle de Reserva", true);
        dlg.setSize(700, 400);
        dlg.setLocationRelativeTo(this);

        // Crear tabla de detalles
        String[] columnas = {"Vehículo", "Placa", "Precio/Día", "Días", "Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        double totalGeneral = 0;
        for (DetalleReserva d : detalles) {
            modelo.addRow(new Object[]{
                "ID: " + d.getIdVehiculo(),
                d.getPlacaVehiculo(),
                String.format("₡%.2f", d.getPrecio()),
                d.getDias(),
                String.format("₡%.2f", d.getSubtotal())
            });
            totalGeneral += d.getSubtotal();
        }

        JTable tblDetalle = new JTable(modelo);
        tblDetalle.setRowHeight(24);
        JScrollPane scroll = new JScrollPane(tblDetalle);

        // Panel con total
        JPanel panelTotal = new JPanel();
        panelTotal.add(new JLabel("Total de la Reserva: "));
        JLabel lblTotal = new JLabel(String.format("₡%.2f", totalGeneral));
        lblTotal.setFont(lblTotal.getFont().deriveFont(16f));
        panelTotal.add(lblTotal);

        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dlg.dispose());
        panelTotal.add(btnCerrar);

        dlg.setLayout(new java.awt.BorderLayout());
        dlg.add(scroll, java.awt.BorderLayout.CENTER);
        dlg.add(panelTotal, java.awt.BorderLayout.SOUTH);

        dlg.setVisible(true);
    }

    /**
     * Obtiene el ID de la reserva seleccionada
     * @return 
     */
    public int getReservaSeleccionada() {
        int fila = tblReservas.getSelectedRow();
        if (fila == -1) return -1;
        
        return (int) tblReservas.getValueAt(fila, 0);
    }

    /**
     * Filtra la tabla por texto
     * @param texto
     */
    public void filtrar(String texto) {
        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblReservas.setRowSorter(sorter);
        
        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    /**
     * Obtiene fecha de inicio del filtro
     * @return 
     */
    public String getFechaInicio() {
        return txtFechaInicio.getText().trim();
    }

    /**
     * Obtiene fecha de fin del filtro
     * @return 
     */
    public String getFechaFin() {
        return txtFechaFin.getText().trim();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnNuevaReserva = new javax.swing.JButton();
        btnVerDetalle = new javax.swing.JButton();
        btnEliminarReserva = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtFechaInicio = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFechaFin = new javax.swing.JTextField();
        btnFiltrarFechas = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1150, 650));
        setPreferredSize(new java.awt.Dimension(1150, 650));

        jLabel1.setText("Buscar:");

        txtBuscar.setMinimumSize(new java.awt.Dimension(400, 22));
        txtBuscar.setPreferredSize(new java.awt.Dimension(400, 22));
        txtBuscar.addActionListener(this::txtBuscarActionPerformed);

        btnBuscar.setText("🔎 Buscar");

        btnNuevaReserva.setText("➕ Nueva Reserva");
        btnNuevaReserva.addActionListener(this::btnNuevaReservaActionPerformed);

        btnVerDetalle.setText("📄 Ver Detalle");
        btnVerDetalle.addActionListener(this::btnVerDetalleActionPerformed);

        btnEliminarReserva.setText("❌ Eliminar Reserva");
        btnEliminarReserva.addActionListener(this::btnEliminarReservaActionPerformed);

        tblReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Cliente ", "Fecha Reserva", "Fecha Inicio", "Fecha Fin", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblReservas);

        jLabel2.setText("Inicio:");

        txtFechaInicio.setText("yyyy-MM-dd");
        txtFechaInicio.setMinimumSize(new java.awt.Dimension(94, 22));
        txtFechaInicio.setPreferredSize(new java.awt.Dimension(94, 22));
        txtFechaInicio.addActionListener(this::txtFechaInicioActionPerformed);

        jLabel3.setText("Fin:");

        txtFechaFin.setText("yyyy-MM-dd");
        txtFechaFin.setMinimumSize(new java.awt.Dimension(94, 22));
        txtFechaFin.setPreferredSize(new java.awt.Dimension(94, 22));
        txtFechaFin.addActionListener(this::txtFechaFinActionPerformed);

        btnFiltrarFechas.setText("📅 Filtrar Fechas");
        btnFiltrarFechas.addActionListener(this::btnFiltrarFechasActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addGap(171, 171, 171)
                        .addComponent(btnNuevaReserva)
                        .addGap(35, 35, 35)
                        .addComponent(btnVerDetalle)
                        .addGap(35, 35, 35)
                        .addComponent(btnEliminarReserva)
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFiltrarFechas)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevaReserva)
                    .addComponent(btnVerDetalle)
                    .addComponent(btnEliminarReserva))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrarFechas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioActionPerformed

    private void txtFechaFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinActionPerformed

    private void btnNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevaReservaActionPerformed

    private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerDetalleActionPerformed

    private void btnEliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarReservaActionPerformed

    private void btnFiltrarFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarFechasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFiltrarFechasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEliminarReserva;
    public javax.swing.JButton btnFiltrarFechas;
    public javax.swing.JButton btnNuevaReserva;
    public javax.swing.JButton btnVerDetalle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblReservas;
    public javax.swing.JTextField txtBuscar;
    public javax.swing.JTextField txtFechaFin;
    public javax.swing.JFormattedTextField txtFechaInicio;
    // End of variables declaration//GEN-END:variables
}
