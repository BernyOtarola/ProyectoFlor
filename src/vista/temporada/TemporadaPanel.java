package vista.temporada;

import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;


public class TemporadaPanel extends javax.swing.JPanel {

    public TemporadaPanel() {
        initComponents();
        configurarTabla();
        // ⭐ INICIALIZAR EL CONTROLADOR
        new controlador.TemporadaController(this);
    }

    private void configurarTabla() {
        tblTemporadas.setRowHeight(24);
        tblTemporadas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTemporadas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        // Ajustar anchos de columna para óptima visualización
        tblTemporadas.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
        tblTemporadas.getColumnModel().getColumn(1).setPreferredWidth(250); // Nombre
        tblTemporadas.getColumnModel().getColumn(2).setPreferredWidth(120); // Fecha Inicio
        tblTemporadas.getColumnModel().getColumn(3).setPreferredWidth(120); // Fecha Fin
        tblTemporadas.getColumnModel().getColumn(4).setPreferredWidth(100); // Recargo
    }

    /**
     * @param lista Lista de temporadas a mostrar
     */
    public void cargarTabla(List<modelo.Temporada> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblTemporadas.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (modelo.Temporada t : lista) {

            double recargo = (t.getFactor() - 1.0) * 100;

            modelo.addRow(new Object[]{
                t.getIdTemporada(),
                t.getNombre(),
                t.getFechaInicio(),
                t.getFechaFin(),
                String.format("%.0f%%", recargo)
            });
        }
    }

    /**
     * @param lista Lista de temporadas
     */
    public void actualizarTabla(List<modelo.Temporada> lista) {
        cargarTabla(lista);
    }

    /**
     * @return Temporada seleccionada o null si no hay selección
     */
    public modelo.Temporada getTemporadaSeleccionada() {
        int fila = tblTemporadas.getSelectedRow();
        if (fila == -1) {
            return null;
        }

        modelo.Temporada t = new modelo.Temporada();

        // Obtener datos de la fila seleccionada
        t.setIdTemporada(Integer.parseInt(tblTemporadas.getValueAt(fila, 0).toString()));
        t.setNombre(tblTemporadas.getValueAt(fila, 1).toString());
        t.setFechaInicio(tblTemporadas.getValueAt(fila, 2).toString());
        t.setFechaFin(tblTemporadas.getValueAt(fila, 3).toString());

        // Parsear el recargo (remover el símbolo % y convertir)
        String recargoStr = tblTemporadas.getValueAt(fila, 4).toString().replace("%", "").trim();
        double recargo = Double.parseDouble(recargoStr);

        // Establecer el recargo (internamente calcula el factor)
        t.setRecargo(recargo);

        return t;
    }

    /**
     * @param texto Texto para filtrar
     */
    public void filtrar(String texto) {
        DefaultTableModel modelo = (DefaultTableModel) tblTemporadas.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblTemporadas.setRowSorter(sorter);

        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null); // Mostrar todo
        } else {
            // Filtro case-insensitive
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    /**
     * @return ID de temporada o -1 si no hay selección
     */
    public int getIdSeleccionado() {
        int fila = tblTemporadas.getSelectedRow();
        if (fila == -1) {
            return -1;
        }
        return Integer.parseInt(tblTemporadas.getValueAt(fila, 0).toString());
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
        tblTemporadas = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(1150, 650));
        setPreferredSize(new java.awt.Dimension(1150, 650));

        jLabel1.setText("Buscar:");

        btnBuscar.setText("🔎 Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        btnNuevo.setText("➕ Nuevo");

        btnEditar.setText("✏ Editar");

        btnEliminar.setText("❌ Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        tblTemporadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Fecha Inicio", "Fecha Fin", "Recargo"
            }
        ));
        jScrollPane1.setViewportView(tblTemporadas);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1144, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblTemporadas;
    public javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
