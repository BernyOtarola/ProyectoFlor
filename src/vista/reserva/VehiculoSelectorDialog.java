package vista.reserva;

import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Diálogo para seleccionar un vehículo al crear una reserva
 */
public class VehiculoSelectorDialog extends JDialog {

    public JTable tblVehiculos;
    public JButton btnSeleccionar;
    public JButton btnCancelar;
    private JTextField txtBuscar;

    public VehiculoSelectorDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Seleccionar Vehículo");
        setSize(700, 450);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con búsqueda
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(30);
        panelSuperior.add(txtBuscar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de vehículos
        String[] columnas = {"ID", "Placa", "Marca", "Modelo", "Año", "Precio/Día", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblVehiculos = new JTable(modelo);
        tblVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblVehiculos.setRowHeight(24);
        
        JScrollPane scroll = new JScrollPane(tblVehiculos);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSeleccionar = new JButton("✅ Seleccionar");
        btnCancelar = new JButton("🚫 Cancelar");
        
        panelBotones.add(btnSeleccionar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Carga vehículos disponibles en la tabla
     */
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

    /**
     * Obtiene el vehículo seleccionado de la tabla
     */
    public Vehiculo getVehiculoSeleccionado() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila == -1) return null;

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