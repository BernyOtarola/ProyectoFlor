package vista.empleado;

import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Empleado;

public class EmpleadoPanel extends javax.swing.JPanel {

   public EmpleadoPanel() {
        initComponents();
        configurarTabla();
        new controlador.EmpleadoController(this); 
    }

    private void configurarTabla() {
        tblEmpleados.setRowHeight(24);
        tblEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmpleados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(60);  
        tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(200); 
        tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(130); 
        tblEmpleados.getColumnModel().getColumn(3).setPreferredWidth(150);
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
                e.getClave()
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
        e.setClave((String) tblEmpleados.getValueAt(fila, 4));

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
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addGap(58, 58, 58)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addContainerGap())
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
