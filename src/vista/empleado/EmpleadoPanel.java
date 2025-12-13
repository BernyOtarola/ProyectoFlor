package vista.empleado;

import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import modelo.Empleado;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class EmpleadoPanel extends javax.swing.JPanel {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(30, 39, 46);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HOVER_COLOR = new Color(52, 152, 219);

    public EmpleadoPanel() {
        initComponents();
        aplicarEstiloModerno(); 
        configurarTabla();
        new controlador.EmpleadoController(this);
    }

    private void aplicarEstiloModerno() {

        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        estilizarBoton(btnNuevo, ACCENT_COLOR, "Nuevo Empleado");
        estilizarBoton(btnEditar, PRIMARY_COLOR, "Editar");
        estilizarBoton(btnEliminar, new Color(231, 76, 60), "Eliminar");
        estilizarBoton(btnBuscar, SECONDARY_COLOR, "Buscar");

        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel1.setForeground(TEXT_COLOR);

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
        tblEmpleados.setBackground(CARD_COLOR);
        tblEmpleados.setForeground(TEXT_COLOR);
        tblEmpleados.setGridColor(new Color(224, 224, 224));
        tblEmpleados.setRowHeight(35);
        tblEmpleados.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblEmpleados.setSelectionBackground(HOVER_COLOR);
        tblEmpleados.setSelectionForeground(Color.WHITE);
        tblEmpleados.setShowVerticalLines(false);
        tblEmpleados.setIntercellSpacing(new Dimension(0, 1));

        // ⭐ SOLUCIÓN: Renderer personalizado para el header
        JTableHeader header = tblEmpleados.getTableHeader();
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
        tblEmpleados.setRowHeight(35);
        tblEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmpleados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(60);  
        tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(240); 
        tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(180); 
        tblEmpleados.getColumnModel().getColumn(3).setPreferredWidth(180);
        tblEmpleados.getColumnModel().getColumn(4).setPreferredWidth(150); 
    }

    public void cargarTabla(List<Empleado> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblEmpleados.getModel();
        modelo.setRowCount(0);

        for (Empleado e : lista) {
            modelo.addRow(new Object[]{
                e.getIdEmpleado(),
                e.getNombre(),
                e.getCargo(),
                e.getUsuario(),
                "••••••••" 
            });
        }
    }

    public Empleado getEmpleadoSeleccionado() {
        int fila = tblEmpleados.getSelectedRow();
        if (fila == -1) return null;

        Empleado e = new Empleado();
        e.setIdEmpleado((int) tblEmpleados.getValueAt(fila, 0));
        e.setNombre((String) tblEmpleados.getValueAt(fila, 1));
        e.setCargo((String) tblEmpleados.getValueAt(fila, 2));
        e.setUsuario((String) tblEmpleados.getValueAt(fila, 3));

        e.setClave(""); 

        return e;
    }

    public void filtrar(String texto) {
        DefaultTableModel modelo = (DefaultTableModel) tblEmpleados.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblEmpleados.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
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
        tblEmpleados = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(1150, 650));
        setPreferredSize(new java.awt.Dimension(1150, 650));

        jLabel1.setText("Buscar:");

        txtBuscar.setMinimumSize(new java.awt.Dimension(400, 22));
        txtBuscar.setPreferredSize(new java.awt.Dimension(400, 22));

        btnBuscar.setText("🔎 Buscar");

        btnNuevo.setText("➕ Nuevo");

        btnEditar.setText("✏ Editar");

        btnEliminar.setText("❌ Eliminar");

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Cargo", "Usuario", "Clave"
            }
        ));
        jScrollPane1.setViewportView(tblEmpleados);

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblEmpleados;
    public javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
