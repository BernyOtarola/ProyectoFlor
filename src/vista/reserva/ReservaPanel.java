package vista.reserva;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import modelo.DetalleReserva;
import modelo.Reserva;

public class ReservaPanel extends javax.swing.JPanel {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HOVER_COLOR = new Color(52, 152, 219);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);

    public ReservaPanel() {
        initComponents();
        aplicarEstiloModerno();
        configurarTabla();
        new controlador.ReservaController(this);
    }

    private void aplicarEstiloModerno() {

        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        estilizarBoton(btnNuevaReserva, ACCENT_COLOR, "Nueva Reserva");
        estilizarBoton(btnVerDetalle, PRIMARY_COLOR, "Ver Detalle");
        estilizarBoton(btnEliminarReserva, DANGER_COLOR, "Eliminar");
        estilizarBoton(btnBuscar, SECONDARY_COLOR, "Buscar");
        estilizarBoton(btnFiltrarFechas, WARNING_COLOR, "Filtrar Fechas");

        estilizarCampoTexto(txtBuscar);
        estilizarCampoTexto(txtFechaInicio);
        estilizarCampoTexto(txtFechaFin);

        estilizarLabel(jLabel1);
        estilizarLabel(jLabel2);
        estilizarLabel(jLabel3);

        estilizarTabla();
    }

    private void estilizarBoton(JButton btn, Color color, String texto) {
        btn.setText(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));

        // Efecto hover
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
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
    }

    private void estilizarLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
    }

    private void estilizarTabla() {
        tblReservas.setBackground(CARD_COLOR);
        tblReservas.setForeground(TEXT_COLOR);
        tblReservas.setGridColor(new Color(224, 224, 224));
        tblReservas.setRowHeight(35);
        tblReservas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblReservas.setSelectionBackground(HOVER_COLOR);
        tblReservas.setSelectionForeground(Color.WHITE);
        tblReservas.setShowVerticalLines(false);
        tblReservas.setIntercellSpacing(new Dimension(0, 1));

        // Header oscuro
        JTableHeader header = tblReservas.getTableHeader();
        header.setBackground(SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setBorder(BorderFactory.createEmptyBorder());

        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        jScrollPane1.getViewport().setBackground(CARD_COLOR);
    }

    private void configurarTabla() {
        tblReservas.setRowHeight(24);
        tblReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblReservas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tblReservas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblReservas.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblReservas.getColumnModel().getColumn(2).setPreferredWidth(140);
        tblReservas.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblReservas.getColumnModel().getColumn(4).setPreferredWidth(120);
        tblReservas.getColumnModel().getColumn(5).setPreferredWidth(100);
    }

    /**
     * @param lista Lista de reservas a mostrar
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
     * @param detalles Lista de detalles (vehículos) de la reserva
     */
    public void mostrarDetalle(List<DetalleReserva> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Esta reserva no tiene vehículos asociados.",
                    "Detalle",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dlg = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                "Detalle de Reserva", true);
        dlg.setSize(800, 500);
        dlg.setLocationRelativeTo(this);
        dlg.getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(800, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 20));

        JLabel lblTituloDetalle = new JLabel("DETALLE DE VEHÍCULOS RESERVADOS");
        lblTituloDetalle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloDetalle.setForeground(Color.WHITE);
        headerPanel.add(lblTituloDetalle);

        String[] columnas = {"ID Vehículo", "Placa", "Precio/Día", "Días", "Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        double totalGeneral = 0;
        for (DetalleReserva d : detalles) {
            modelo.addRow(new Object[]{
                d.getIdVehiculo(),
                d.getPlacaVehiculo(),
                String.format("₡%.2f", d.getPrecio()),
                d.getDias(),
                String.format("₡%.2f", d.getSubtotal())
            });
            totalGeneral += d.getSubtotal();
        }

        JTable tblDetalle = new JTable(modelo);
        tblDetalle.setBackground(CARD_COLOR);
        tblDetalle.setForeground(TEXT_COLOR);
        tblDetalle.setGridColor(new Color(224, 224, 224));
        tblDetalle.setRowHeight(35);
        tblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

        tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(100);  
        tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(120);  
        tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(150);  
        tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(80);   
        tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(150);  

        JScrollPane scroll = new JScrollPane(tblDetalle);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scroll.getViewport().setBackground(CARD_COLOR);

        JPanel panelTotal = new JPanel();
        panelTotal.setBackground(BACKGROUND_COLOR);
        panelTotal.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelTotal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Label "Total de la Reserva:"
        JLabel lblTextoTotal = new JLabel("TOTAL DE LA RESERVA: ");
        lblTextoTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTextoTotal.setForeground(TEXT_COLOR);

        // Label con el valor del total
        JLabel lblTotal = new JLabel(String.format("₡%.2f", totalGeneral));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setForeground(ACCENT_COLOR);

        panelTotal.add(lblTextoTotal);
        panelTotal.add(lblTotal);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBackground(PRIMARY_COLOR);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(120, 40));

        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = PRIMARY_COLOR;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCerrar.setBackground(PRIMARY_COLOR.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCerrar.setBackground(originalColor);
            }
        });

        btnCerrar.addActionListener(e -> dlg.dispose());
        panelTotal.add(btnCerrar);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(scroll, BorderLayout.CENTER);

        dlg.setLayout(new BorderLayout());
        dlg.add(headerPanel, BorderLayout.NORTH);
        dlg.add(contentPanel, BorderLayout.CENTER);
        dlg.add(panelTotal, BorderLayout.SOUTH);

        dlg.setVisible(true);
    }

    /**
     * @return 
     */
    public int getReservaSeleccionada() {
        int fila = tblReservas.getSelectedRow();
        if (fila == -1) {
            return -1;
        }

        return (int) tblReservas.getValueAt(fila, 0);
    }

    /**
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
     * @return Fecha inicio en formato yyyy-MM-dd
     */
    public String getFechaInicio() {
        String fecha = txtFechaInicio.getText().trim();
        // Si es el placeholder, retornar vacío
        return "yyyy-MM-dd".equals(fecha) ? "" : fecha;
    }

    /**
     * @return Fecha fin en formato yyyy-MM-dd
     */
    public String getFechaFin() {
        String fecha = txtFechaFin.getText().trim();
        // Si es el placeholder, retornar vacío
        return "yyyy-MM-dd".equals(fecha) ? "" : fecha;
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

        txtFechaInicio.setText("    YYYY-MM-DD");
        txtFechaInicio.setMinimumSize(new java.awt.Dimension(94, 22));
        txtFechaInicio.setPreferredSize(new java.awt.Dimension(94, 22));
        txtFechaInicio.addActionListener(this::txtFechaInicioActionPerformed);

        jLabel3.setText("Fin:");

        txtFechaFin.setText("    YYYY-MM-DD");
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
                        .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevaReserva)
                    .addComponent(btnVerDetalle)
                    .addComponent(btnEliminarReserva)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrarFechas))
                .addGap(46, 46, 46)
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
