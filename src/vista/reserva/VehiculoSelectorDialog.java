package vista.reserva;

import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class VehiculoSelectorDialog extends JDialog {

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(30, 39, 46);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HOVER_COLOR = new Color(52, 152, 219);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);

    public JTable tblVehiculos;
    public JButton btnSeleccionar;
    public JButton btnCancelar;
    private JTextField txtBuscar;
    private JLabel lblBuscar;

    public VehiculoSelectorDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        aplicarEstiloModerno();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Seleccionar Vehículo");
        setSize(800, 500);
        setMinimumSize(new Dimension(800, 500));
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Header Panel
        JPanel headerPanel = crearHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Panel superior con búsqueda
        JPanel panelBusqueda = crearPanelBusqueda();
        add(panelBusqueda, BorderLayout.PAGE_START);

        // Tabla de vehículos
        JScrollPane scrollPane = crearTablaVehiculos();
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 20));

        JLabel lblTitulo = new JLabel("SELECCIONAR VEHÍCULO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);

        return headerPanel;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelBusqueda.setBackground(BACKGROUND_COLOR);
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscar.setForeground(TEXT_COLOR);

        txtBuscar = new JTextField(35);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtBuscar.setPreferredSize(new Dimension(350, 40));

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);

        return panelBusqueda;
    }

    private JScrollPane crearTablaVehiculos() {
        String[] columnas = {"ID", "Placa", "Marca", "Modelo", "Año", "Precio/Día", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblVehiculos = new JTable(modelo);
        tblVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblVehiculos.setBackground(CARD_COLOR);
        tblVehiculos.setForeground(TEXT_COLOR);
        tblVehiculos.setGridColor(new Color(224, 224, 224));
        tblVehiculos.setRowHeight(35);
        tblVehiculos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblVehiculos.setSelectionBackground(HOVER_COLOR);
        tblVehiculos.setSelectionForeground(Color.WHITE);
        tblVehiculos.setShowVerticalLines(true);
        tblVehiculos.setShowHorizontalLines(true);
        tblVehiculos.setIntercellSpacing(new Dimension(1, 1));

        // ⭐ SOLUCIÓN: Renderer personalizado para el header
        JTableHeader header = tblVehiculos.getTableHeader();
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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

        // Ajustar anchos de columna
        tblVehiculos.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tblVehiculos.getColumnModel().getColumn(1).setPreferredWidth(100);  // Placa
        tblVehiculos.getColumnModel().getColumn(2).setPreferredWidth(120);  // Marca
        tblVehiculos.getColumnModel().getColumn(3).setPreferredWidth(120);  // Modelo
        tblVehiculos.getColumnModel().getColumn(4).setPreferredWidth(70);   // Año
        tblVehiculos.getColumnModel().getColumn(5).setPreferredWidth(100);  // Precio/Día
        tblVehiculos.getColumnModel().getColumn(6).setPreferredWidth(100);  // Estado

        JScrollPane scroll = new JScrollPane(tblVehiculos);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scroll.getViewport().setBackground(CARD_COLOR);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        return scroll;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelBotones.setBackground(BACKGROUND_COLOR);
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        btnSeleccionar = crearBotonEstilizado("Seleccionar", ACCENT_COLOR);
        btnCancelar = crearBotonEstilizado("Cancelar", DANGER_COLOR);

        panelBotones.add(btnSeleccionar);
        panelBotones.add(btnCancelar);

        return panelBotones;
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 42));

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

        return btn;
    }

    private void aplicarEstiloModerno() {
        // Aplicar efecto de foco al campo de búsqueda
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
    }

    public void cargarVehiculos(List<Vehiculo> vehiculos) {
        DefaultTableModel modelo = (DefaultTableModel) tblVehiculos.getModel();
        modelo.setRowCount(0);

        for (Vehiculo v : vehiculos) {
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

    public Vehiculo getVehiculoSeleccionado() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila == -1) {
            return null;
        }

        Vehiculo v = new Vehiculo();
        v.setIdVehiculo((int) tblVehiculos.getValueAt(fila, 0));
        v.setPlaca(tblVehiculos.getValueAt(fila, 1).toString());
        v.setMarca(tblVehiculos.getValueAt(fila, 2).toString());
        v.setModelo(tblVehiculos.getValueAt(fila, 3).toString());
        v.setAnio((int) tblVehiculos.getValueAt(fila, 4));

        // Limpiar el formato de precio
        String precioStr = tblVehiculos.getValueAt(fila, 5).toString()
                .replace("₡", "").replace(",", "").trim();
        v.setPrecioDia(Double.parseDouble(precioStr));

        v.setEstado(tblVehiculos.getValueAt(fila, 6).toString());

        return v;
    }
}
