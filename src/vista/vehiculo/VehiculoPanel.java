package vista.vehiculo;

import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Vehiculo;

/**
 * Panel principal del módulo de Vehículos
 * Gestiona la visualización y acciones sobre los vehículos
 */
public class VehiculoPanel extends javax.swing.JPanel {

    /**
     * Constructor - Inicializa el panel y el controlador
     */
    public VehiculoPanel() {
        initComponents();
        configurarTabla();
        // ⭐ INICIALIZAR EL CONTROLADOR
        new controlador.VehiculoController(this);
    }

    /**
     * Configuración inicial de la tabla de vehículos
     */
    private void configurarTabla() {
        tblVehiculos.setRowHeight(24);
        tblVehiculos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblVehiculos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        // Ajustar anchos de columna para óptima visualización
        tblVehiculos.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
        tblVehiculos.getColumnModel().getColumn(1).setPreferredWidth(120);  // Placa
        tblVehiculos.getColumnModel().getColumn(2).setPreferredWidth(150);  // Marca
        tblVehiculos.getColumnModel().getColumn(3).setPreferredWidth(150);  // Modelo
        tblVehiculos.getColumnModel().getColumn(4).setPreferredWidth(80);   // Año
        tblVehiculos.getColumnModel().getColumn(5).setPreferredWidth(120);  // Precio/Día
        tblVehiculos.getColumnModel().getColumn(6).setPreferredWidth(120);  // Estado
    }

    /**
     * Carga lista de vehículos en la tabla
     * @param lista Lista de vehículos a mostrar
     */
    public void cargarTabla(List<Vehiculo> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblVehiculos.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Vehiculo v : lista) {
            modelo.addRow(new Object[]{
                v.getIdVehiculo(),
                v.getPlaca(),
                v.getMarca(),
                v.getModelo(),
                v.getAnio(),
                String.format("₡%.2f", v.getPrecioDia()),
                v.getEstado() // ⭐ COLUMNA ESTADO AGREGADA
            });
        }
    }

    /**
     * Actualiza la tabla con nueva información
     * @param lista Lista de vehículos actualizada
     */
    public void actualizarTabla(List<Vehiculo> lista) {
        cargarTabla(lista);
    }

    /**
     * Obtiene el vehículo seleccionado de la tabla
     * @return Vehículo seleccionado o null si no hay selección
     */
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
        
        // Limpiar formato de precio
        String precioStr = tblVehiculos.getValueAt(fila, 5).toString()
            .replace("₡", "").replace(",", "").trim();
        v.setPrecioDia(Double.parseDouble(precioStr));
        
        v.setEstado(tblVehiculos.getValueAt(fila, 6).toString()); // ⭐ ESTADO AGREGADO

        return v;
    }

    /**
     * Filtra la tabla por texto de búsqueda
     * @param texto Texto para filtrar (case-insensitive)
     */
    public void filtrar(String texto) {
        DefaultTableModel modelo = (DefaultTableModel) tblVehiculos.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblVehiculos.setRowSorter(sorter);

        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null); // Mostrar todo
        } else {
            // Filtro case-insensitive
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    /**
     * Obtiene el ID del vehículo seleccionado
     * @return ID del vehículo o -1 si no hay selección
     */
    public int getIdSeleccionado() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila == -1) {
            return -1;
        }
        return Integer.parseInt(tblVehiculos.getValueAt(fila, 0).toString());
    }

    /**
     * Limpia el campo de búsqueda
     */
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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID ", "PLACA", "MARCA", "MODELO", " AÑO", "PRECIO/DÍA "
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
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addGap(58, 58, 58)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblVehiculos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
