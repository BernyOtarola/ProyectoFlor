package vista.cliente;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;

public class ClientePanel extends javax.swing.JPanel {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HOVER_COLOR = new Color(52, 152, 219);

    public ClientePanel() {
        initComponents();
        aplicarEstiloModerno();
        configurarTabla();
        new controlador.ClienteController(this);
    }

    private void aplicarEstiloModerno() {

        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        estilizarBoton(btnNuevo, ACCENT_COLOR, "Nuevo Cliente");
        estilizarBoton(btnEditar, PRIMARY_COLOR, "Editar");
        estilizarBoton(btnEliminar, new Color(231, 76, 60), "Eliminar");
        estilizarBoton(btnBuscar, SECONDARY_COLOR, "Buscar");

        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscar.setForeground(TEXT_COLOR);

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
        btn.setPreferredSize(new Dimension(140, 40));

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

        tblClientes.setBackground(CARD_COLOR);
        tblClientes.setForeground(TEXT_COLOR);
        tblClientes.setGridColor(new Color(224, 224, 224));
        tblClientes.setRowHeight(35);
        tblClientes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblClientes.setSelectionBackground(HOVER_COLOR);
        tblClientes.setSelectionForeground(Color.WHITE);
        tblClientes.setShowVerticalLines(false);
        tblClientes.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = tblClientes.getTableHeader();
        header.setBackground(SECONDARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setBorder(BorderFactory.createEmptyBorder());

        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        jScrollPane1.getViewport().setBackground(CARD_COLOR);
    }

    private void configurarTabla() {
        tblClientes.setRowHeight(35);
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(140);
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblClientes.getColumnModel().getColumn(4).setPreferredWidth(220);
        tblClientes.getColumnModel().getColumn(5).setPreferredWidth(200);
    }

    public void cargarTabla(List<Cliente> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setRowCount(0);

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                c.getIdCliente(),
                c.getNombre(),
                c.getIdentificacion(),
                c.getTelefono(),
                c.getCorreo(),
                c.getDireccion()
            });
        }
    }

    public Cliente getClienteSeleccionado() {
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) {
            return null;
        }

        Cliente c = new Cliente();
        c.setIdCliente((int) tblClientes.getValueAt(fila, 0));
        c.setNombre((String) tblClientes.getValueAt(fila, 1));
        c.setIdentificacion((String) tblClientes.getValueAt(fila, 2));
        c.setTelefono((String) tblClientes.getValueAt(fila, 3));
        c.setCorreo((String) tblClientes.getValueAt(fila, 4));
        c.setDireccion((String) tblClientes.getValueAt(fila, 5));

        return c;
    }

    public void filtrar(String filtro) {
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblClientes.setRowSorter(sorter);
        sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + filtro));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1150, 650));
        setPreferredSize(new java.awt.Dimension(1150, 650));

        lblBuscar.setText("Buscar:");

        txtBuscar.setMinimumSize(new java.awt.Dimension(400, 22));
        txtBuscar.setPreferredSize(new java.awt.Dimension(400, 22));

        btnBuscar.setText("🔎 Buscar");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Identificación", "Teléfono", "Correo", "Direccion"
            }
        ));
        jScrollPane1.setViewportView(tblClientes);

        btnNuevo.setText("➕ Nuevo");

        btnEditar.setText("✏ Editar");

        btnEliminar.setText("❌ Eliminar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addGap(50, 50, 50)
                .addComponent(btnEditar)
                .addGap(50, 50, 50)
                .addComponent(btnEliminar)
                .addGap(100, 100, 100))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    public javax.swing.JTable tblClientes;
    public javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
