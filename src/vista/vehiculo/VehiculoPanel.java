package vista.vehiculo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import modelo.Vehiculo;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;


public class VehiculoPanel extends javax.swing.JPanel {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(30, 39, 46);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HOVER_COLOR = new Color(52, 152, 219);

    public VehiculoPanel() {
        initComponents();
        aplicarEstiloModerno();
        configurarTabla();
        new controlador.VehiculoController(this);
    }

    private void aplicarEstiloModerno() {
        // Estilo del panel principal
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Estilizar botones
        estilizarBoton(btnNuevo, ACCENT_COLOR, "Nuevo Vehículo");
        estilizarBoton(btnEditar, PRIMARY_COLOR, "Editar");
        estilizarBoton(btnEliminar, new Color(231, 76, 60), "Eliminar");
        estilizarBoton(btnBuscar, SECONDARY_COLOR, "Buscar");

        // Estilizar campo de búsqueda
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Estilizar label
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel1.setForeground(TEXT_COLOR);

        // Estilizar tabla
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

    private void estilizarTabla() {
        
    // Estilo del contenido de la tabla
    tblVehiculos.setBackground(CARD_COLOR);
    tblVehiculos.setForeground(TEXT_COLOR);
    tblVehiculos.setGridColor(new Color(224, 224, 224));
    tblVehiculos.setRowHeight(35);
    tblVehiculos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    tblVehiculos.setSelectionBackground(HOVER_COLOR);
    tblVehiculos.setSelectionForeground(Color.WHITE);
    tblVehiculos.setShowVerticalLines(false);
    tblVehiculos.setIntercellSpacing(new Dimension(0, 1));

    // ⭐ SOLUCIÓN: Renderer personalizado para el header
    JTableHeader header = tblVehiculos.getTableHeader();
    header.setDefaultRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value != null ? value.toString() : "");
            label.setFont(new Font("Segoe UI", Font.BOLD, 13));
            label.setForeground(Color.WHITE);
            label.setBackground(SECONDARY_COLOR);
            label.setOpaque(true);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    });
    header.setPreferredSize(new Dimension(header.getWidth(), 45));
    header.setReorderingAllowed(false);

    // Estilo del scroll pane
    jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
    jScrollPane1.getViewport().setBackground(CARD_COLOR);
}

    private void configurarTabla() {
        tblVehiculos.setRowHeight(35);
        tblVehiculos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblVehiculos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        tblVehiculos.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblVehiculos.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblVehiculos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblVehiculos.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblVehiculos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblVehiculos.getColumnModel().getColumn(5).setPreferredWidth(120);
        tblVehiculos.getColumnModel().getColumn(6).setPreferredWidth(120);
    }

    public void cargarTabla(List<Vehiculo> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblVehiculos.getModel();
        modelo.setRowCount(0);

        for (Vehiculo v : lista) {
            modelo.addRow(new Object[]{
                v.getIdVehiculo(),
                v.getPlaca(),
                v.getMarca(),
                v.getModelo(),
                v.getAnio(),
                String.format("₡%.2f", v.getPrecioDia()),
                v.getEstado()
            });
        }
    }

    public void actualizarTabla(List<Vehiculo> lista) {
        cargarTabla(lista);
    }

    public Vehiculo getVehiculoSeleccionado() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila == -1) {
            return null;
        }

        Vehiculo v = new Vehiculo();
        v.setIdVehiculo(Integer.parseInt(tblVehiculos.getValueAt(fila, 0).toString()));
        v.setPlaca(tblVehiculos.getValueAt(fila, 1).toString());
        v.setMarca(tblVehiculos.getValueAt(fila, 2).toString());
        v.setModelo(tblVehiculos.getValueAt(fila, 3).toString());
        v.setAnio(Integer.parseInt(tblVehiculos.getValueAt(fila, 4).toString()));

        String precioStr = tblVehiculos.getValueAt(fila, 5).toString()
                .replace("₡", "").replace(",", "").trim();
        v.setPrecioDia(Double.parseDouble(precioStr));

        v.setEstado(tblVehiculos.getValueAt(fila, 6).toString());

        return v;
    }

    public void filtrar(String texto) {
        DefaultTableModel modelo = (DefaultTableModel) tblVehiculos.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblVehiculos.setRowSorter(sorter);

        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + texto));
        }
    }

    public int getIdSeleccionado() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila == -1) {
            return -1;
        }
        return Integer.parseInt(tblVehiculos.getValueAt(fila, 0).toString());
    }

    public void limpiarBusqueda() {
        txtBuscar.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVehiculos = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(1150, 650));
        setPreferredSize(new java.awt.Dimension(1150, 650));

        jLabel1.setText("Buscar:");

        btnBuscar.setText("🔎 Buscar");

        btnNuevo.setText("➕ Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnEditar.setText("✏ Editar");

        btnEliminar.setText("❌ Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        tblVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID ", "PLACA", "MARCA", "MODELO", " AÑO", "PRECIO/DÍA ", "ESTADO"
            }
        ));
        jScrollPane1.setViewportView(tblVehiculos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addGap(171, 171, 171)
                .addComponent(btnNuevo)
                .addGap(50, 50, 50)
                .addComponent(btnEditar)
                .addGap(50, 50, 50)
                .addComponent(btnEliminar)
                .addContainerGap(100, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblVehiculos;
    public javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
